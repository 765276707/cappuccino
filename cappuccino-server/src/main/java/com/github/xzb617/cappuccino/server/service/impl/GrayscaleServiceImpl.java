package com.github.xzb617.cappuccino.server.service.impl;

import com.github.xzb617.cappuccino.commons.utils.DigestUtil;
import com.github.xzb617.cappuccino.commons.utils.MetaUtil;
import com.github.xzb617.cappuccino.commons.utils.StrUtil;
import com.github.xzb617.cappuccino.server.clients.ClientsTransmitter;
import com.github.xzb617.cappuccino.server.constant.AuditlogType;
import com.github.xzb617.cappuccino.server.constant.ConfigType;
import com.github.xzb617.cappuccino.server.constant.ReleaseType;
import com.github.xzb617.cappuccino.server.domain.dto.ConfigDTO;
import com.github.xzb617.cappuccino.server.domain.dto.GrayscaleDTO;
import com.github.xzb617.cappuccino.server.domain.entity.Client;
import com.github.xzb617.cappuccino.server.domain.entity.Grayscale;
import com.github.xzb617.cappuccino.server.exception.ServiceException;
import com.github.xzb617.cappuccino.server.mapper.ClientMapper;
import com.github.xzb617.cappuccino.server.mapper.GrayscaleMapper;
import com.github.xzb617.cappuccino.server.security.SecurityCapability;
import com.github.xzb617.cappuccino.server.service.AuditlogService;
import com.github.xzb617.cappuccino.server.service.ConfigService;
import com.github.xzb617.cappuccino.server.service.GrayscaleService;
import com.github.xzb617.cappuccino.server.service.ReleaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Set;

@Service
public class GrayscaleServiceImpl extends SecurityCapability implements GrayscaleService {

    private final GrayscaleMapper grayscaleMapper;
    private final ClientMapper clientMapper;
    private final ConfigService configService;
    private final AuditlogService auditlogService;
    private final ReleaseService releaseService;
    private final ClientsTransmitter clientsTransmitter;

    public GrayscaleServiceImpl(GrayscaleMapper grayscaleMapper, ClientMapper clientMapper, ConfigService configService, AuditlogService auditlogService, ReleaseService releaseService, ClientsTransmitter clientsTransmitter) {
        this.grayscaleMapper = grayscaleMapper;
        this.clientMapper = clientMapper;
        this.configService = configService;
        this.auditlogService = auditlogService;
        this.releaseService = releaseService;
        this.clientsTransmitter = clientsTransmitter;
    }

    @Override
    public Grayscale getByClientId(Long clientId) {
        return this.grayscaleMapper.selectByClientId(clientId);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Grayscale grayscale = this.grayscaleMapper.selectByPrimaryKey(id);
        if (grayscale == null) {
            throw new ServiceException("????????????????????????????????????");
        }
        Client client = this.clientMapper.selectById(grayscale.getClientId());
        if (client == null) {
            throw new ServiceException("?????????????????????????????????");
        }
        this.grayscaleMapper.deleteByPrimaryKey(id);

        // ??????????????????
        this.auditlogService.log(grayscale.getClientId(), AuditlogType.DELETE_GRAYSCALE, "?????????????????????????????????" + grayscale.getVersion(), ConfigType.GRAYSCALE);

        // TODO ?????????????????????????????????????????????????????????????????????
        if (!StringUtils.isEmpty(grayscale.getRules())) {
            String clientKey = MetaUtil.getClientKey(client.getEnvName(), client.getGroupName(), client.getClientName());
            Set<String> instanceKeys = StrUtil.strToSet(grayscale.getRules());
            this.clientsTransmitter.transmitInstances(clientKey, instanceKeys);
        }
    }

    @Override
    @Transactional
    public void release(GrayscaleDTO dto) {
        // ???????????????
        Client client = this.clientMapper.selectById(dto.getClientId());
        if (client == null) {
            throw new ServiceException("?????????????????????????????????");
        }
        // ??????????????????????????????
        Grayscale gc = this.grayscaleMapper.selectByPrimaryKey(dto.getId());
        int version;
        if (gc == null) {
            gc = new Grayscale();
            Integer lastVersion = this.releaseService.getLastVersion(client.getId(), ReleaseType.GRAYSCALE);
            version = lastVersion==null?1:lastVersion+1;
        } else {
            version = gc.getVersion()==null?1:gc.getVersion()+1;
        }
        gc.setId(dto.getId());
        gc.setClientId(dto.getClientId());
        gc.setContent(dto.getContent());
        gc.setFileExtension(dto.getFileExtension());
        gc.setDescription(dto.getDescription());
        gc.setSign(DigestUtil.md5(dto.getContent(), "utf-8"));
        gc.setVersion(version);
        gc.setConfigVersion(dto.getConfigVersion());
        gc.setRules(dto.getRules());
        gc.setCreateTime(new Date());
        gc.setCreateUser(getLoginName());
        gc.setReleaseTime(new Date());
        gc.setReleaseUser(getLoginName());
        if (gc.getId() == null) {
            this.grayscaleMapper.insertSelective(gc);
        } else {
            this.grayscaleMapper.updateByPrimaryKeySelective(gc);
        }

        // ??????????????????
        this.releaseService.snapshot(gc);
        // ??????????????????
        this.auditlogService.log(dto.getClientId(), AuditlogType.RELEASE_GRAYSCALE, "?????????????????????????????????" + version, ConfigType.GRAYSCALE);

        // TODO ????????????????????????????????????????????????
        if (!StringUtils.isEmpty(gc.getRules())) {
            String clientKey = MetaUtil.getClientKey(client.getEnvName(), client.getGroupName(), client.getClientName());
            Set<String> instanceKeys = StrUtil.strToSet(gc.getRules());
            this.clientsTransmitter.transmitInstances(clientKey, instanceKeys);
        }
    }

    @Override
    @Transactional
    public void releaseFull(Long id) {
        // ????????????
        Grayscale grayscale = this.grayscaleMapper.selectByPrimaryKey(id);
        if (grayscale == null) {
            throw new ServiceException("????????????????????????????????????");
        }
        // ???????????????
        ConfigDTO configDTO = new ConfigDTO();
        configDTO.setClientId(grayscale.getClientId());
        configDTO.setContent(grayscale.getContent());
        configDTO.setDescription(grayscale.getDescription());
        configDTO.setFileExtension(grayscale.getFileExtension());
        this.configService.update(configDTO);
        // ??????????????????
        this.grayscaleMapper.deleteByPrimaryKey(id);
    }
}

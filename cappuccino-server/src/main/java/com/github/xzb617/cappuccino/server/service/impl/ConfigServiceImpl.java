package com.github.xzb617.cappuccino.server.service.impl;

import com.github.xzb617.cappuccino.commons.utils.DigestUtil;
import com.github.xzb617.cappuccino.commons.utils.MetaUtil;
import com.github.xzb617.cappuccino.server.clients.ClientsTransmitter;
import com.github.xzb617.cappuccino.server.constant.AuditlogType;
import com.github.xzb617.cappuccino.server.constant.ConfigType;
import com.github.xzb617.cappuccino.server.constant.ReleaseType;
import com.github.xzb617.cappuccino.server.domain.dto.ConfigDTO;
import com.github.xzb617.cappuccino.server.domain.entity.Client;
import com.github.xzb617.cappuccino.server.domain.entity.Config;
import com.github.xzb617.cappuccino.server.exception.ServiceException;
import com.github.xzb617.cappuccino.server.mapper.ClientMapper;
import com.github.xzb617.cappuccino.server.mapper.ConfigMapper;
import com.github.xzb617.cappuccino.server.security.SecurityCapability;
import com.github.xzb617.cappuccino.server.service.AuditlogService;
import com.github.xzb617.cappuccino.server.service.ConfigService;
import com.github.xzb617.cappuccino.server.service.ReleaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class ConfigServiceImpl extends SecurityCapability implements ConfigService {

    private final ConfigMapper configMapper;
    private final ClientMapper clientMapper;
    private final AuditlogService auditlogService;
    private final ReleaseService releaseService;
    private final ClientsTransmitter clientsTransmitter;

    public ConfigServiceImpl(ConfigMapper configMapper, ClientMapper clientMapper, AuditlogService auditlogService, ReleaseService releaseService, ClientsTransmitter clientsTransmitter) {
        this.configMapper = configMapper;
        this.clientMapper = clientMapper;
        this.auditlogService = auditlogService;
        this.releaseService = releaseService;
        this.clientsTransmitter = clientsTransmitter;
    }

    @Override
    public Config getById(Long id) {
        return this.configMapper.selectByPrimaryKey(id);
    }

    @Override
    public Config getByClientId(Long clientId) {
        return this.configMapper.selectByClientId(clientId);
    }

    @Override
    @Transactional
    public void save(ConfigDTO dto) {
        // ???????????????
        Client client = this.clientMapper.selectById(dto.getClientId());
        if (client == null) {
            throw new ServiceException("?????????????????????????????????");
        }
        // ??????????????????????????????
        Config config = this.getByClientId(client.getId());
        if (config != null) {
            throw new ServiceException("?????????????????????");
        }
        // ???????????? TODO ???????????????????????????????????????
        Integer lastVersion = this.releaseService.getLastVersion(client.getId(), ReleaseType.CONFIG);
        int version = lastVersion==null?1:lastVersion + 1;
        // ????????????
        config = new Config();
        config.setClientId(dto.getClientId());
        config.setContent(dto.getContent());
        config.setSign(DigestUtil.md5(dto.getContent(), "utf-8"));
        config.setDescription(dto.getDescription());
        config.setFileExtension(dto.getFileExtension());
        config.setVersion(version);
        config.setCreateTime(new Date());
        config.setCreateUser(getLoginName());
        config.setReleaseTime(new Date());
        config.setReleaseUser(getLoginName());
        this.configMapper.insertSelective(config);
        // ????????????
        this.releaseService.snapshot(config);
        // ??????????????????
        this.auditlogService.log(client.getId(), AuditlogType.RELEASE_MASTER, "????????????????????????????????????" + version , ConfigType.MASTER);
        // TODO ???????????? & ????????????
        String clientKey = MetaUtil.getClientKey(client.getEnvName(), client.getGroupName(), client.getClientName());
        this.clientsTransmitter.transmitClient(clientKey);
    }

    @Override
    @Transactional
    public void update(ConfigDTO dto) {
        // ???????????????
        Client client = this.clientMapper.selectById(dto.getClientId());
        if (client == null) {
            throw new ServiceException("?????????????????????????????????");
        }
        // ??????????????????????????????
        Config config = this.getByClientId(client.getId());
        if (config == null) {
            throw new ServiceException("??????????????????");
        }
        // ????????????
        config.setClientId(dto.getClientId());
        config.setContent(dto.getContent());
        config.setSign(DigestUtil.md5(dto.getContent(), "utf-8"));
        config.setDescription(dto.getDescription());
        config.setFileExtension(dto.getFileExtension());
        int version = config.getVersion()==null?1:config.getVersion()+1;
        config.setVersion(version);
        config.setCreateTime(new Date());
        config.setCreateUser(getLoginName());
        config.setReleaseTime(new Date());
        config.setReleaseUser(getLoginName());
        this.configMapper.updateByPrimaryKeySelective(config);
        // ????????????
        this.releaseService.snapshot(config);
        // ??????????????????
        this.auditlogService.log(client.getId(), AuditlogType.RELEASE_MASTER, "????????????????????????????????????" + version, ConfigType.MASTER);
        // TODO ???????????? & ????????????
        String clientKey = MetaUtil.getClientKey(client.getEnvName(), client.getGroupName(), client.getClientName());
        this.clientsTransmitter.transmitClient(clientKey);
    }

    @Override
    public void deleteById(Long id) {
        this.configMapper.deleteByPrimaryKey(id);
    }

}

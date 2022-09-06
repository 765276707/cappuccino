package com.github.xzb617.cappuccino.server.service.impl;

import com.github.xzb617.cappuccino.commons.utils.MetaUtil;
import com.github.xzb617.cappuccino.commons.utils.StrUtil;
import com.github.xzb617.cappuccino.server.clients.ClientsTransmitter;
import com.github.xzb617.cappuccino.server.constant.AuditlogType;
import com.github.xzb617.cappuccino.server.constant.ConfigType;
import com.github.xzb617.cappuccino.server.constant.ReleaseType;
import com.github.xzb617.cappuccino.server.domain.condition.ReleaseCondition;
import com.github.xzb617.cappuccino.server.domain.entity.Client;
import com.github.xzb617.cappuccino.server.domain.entity.Config;
import com.github.xzb617.cappuccino.server.domain.entity.Grayscale;
import com.github.xzb617.cappuccino.server.domain.entity.Release;
import com.github.xzb617.cappuccino.server.domain.vo.ReleaseVO;
import com.github.xzb617.cappuccino.server.exception.ServiceException;
import com.github.xzb617.cappuccino.server.mapper.ClientMapper;
import com.github.xzb617.cappuccino.server.mapper.ConfigMapper;
import com.github.xzb617.cappuccino.server.mapper.GrayscaleMapper;
import com.github.xzb617.cappuccino.server.mapper.ReleaseMapper;
import com.github.xzb617.cappuccino.server.security.SecurityCapability;
import com.github.xzb617.cappuccino.server.service.AuditlogService;
import com.github.xzb617.cappuccino.server.service.ReleaseService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class ReleaseServiceImpl extends SecurityCapability implements ReleaseService {

    private final ReleaseMapper releaseMapper;
    private final ConfigMapper configMapper;
    private final AuditlogService auditlogService;
    private final ClientsTransmitter clientsTransmitter;
    private final ClientMapper clientMapper;
    private final GrayscaleMapper grayscaleMapper;

    public ReleaseServiceImpl(ReleaseMapper releaseMapper, ConfigMapper configMapper, AuditlogService auditlogService, ClientsTransmitter clientsTransmitter, ClientMapper clientMapper, GrayscaleMapper grayscaleMapper) {
        this.releaseMapper = releaseMapper;
        this.configMapper = configMapper;
        this.auditlogService = auditlogService;
        this.clientsTransmitter = clientsTransmitter;
        this.clientMapper = clientMapper;
        this.grayscaleMapper = grayscaleMapper;
    }

    @Override
    public List<ReleaseVO> getList(ReleaseCondition condition) {
        if (isSuperAdmin()) {
            return this.releaseMapper.selectList(condition, null);
        }
        return this.releaseMapper.selectList(condition, getLoginUid());
    }

    @Override
    public Integer getLastVersion(Long clientId, int releaseType) {
        return this.releaseMapper.selectLastVersion(clientId, releaseType);
    }

    @Override
    @Transactional
    public void rollback(Long id) {
        // 查询历史快照
        Release release = this.releaseMapper.selectByPrimaryKey(id);
        if (release == null) {
            throw new ServiceException("历史快照不存在或已被删除");
        }
        // 查询客户端
        Client client = this.clientMapper.selectById(release.getClientId());
        if (client == null) {
            throw new ServiceException("客户端不存在或已被删除");
        }
        // 判断快照类型
        Integer type = release.getType();
        if (ReleaseType.CONFIG == type) {
            // 判断是否已有主配置
            Config config = this.configMapper.selectByClientId(release.getClientId());
            int version = 1;
            if (config == null) {
                config = new Config();
                Integer lastVersion = this.getLastVersion(client.getId(), ReleaseType.CONFIG);
                version = lastVersion==null?1:lastVersion+1;
            } else {
                version = config.getVersion()==null?1:config.getVersion()+1;
            }
            BeanUtils.copyProperties(release, config, "id");
            config.setCreateTime(new Date());
            config.setCreateUser(getLoginName());
            config.setReleaseTime(new Date());
            config.setReleaseUser(getLoginName());
            config.setVersion(version);
            if (config.getId() == null) {
                this.configMapper.insertSelective(config);
            } else {
                this.configMapper.updateByPrimaryKeySelective(config);
            }

            // 生成快照
            this.snapshot(config);
            // 记录操作日志
            this.auditlogService.log(config.getClientId(), AuditlogType.ROLLBACK_MASTER, "回滚了主配置，回滚版本为：" + release.getVersion()+ "，当前版本号： " + version,  ConfigType.MASTER);

            // TODO 回滚主配置，通知所有客户端
            String clientKey = MetaUtil.getClientKey(client.getEnvName(), client.getGroupName(), client.getClientName());
            this.clientsTransmitter.transmitClient(clientKey);
        } else {
            Grayscale grayscale = this.grayscaleMapper.selectByClientId(client.getId());
            int version = 1;
            if (grayscale == null) {
                grayscale = new Grayscale();
                Integer lastVersion = this.getLastVersion(client.getId(), ReleaseType.GRAYSCALE);
                version = lastVersion==null?1:lastVersion+1;
            } else {
                version = grayscale.getVersion()==null?1:grayscale.getVersion()+1;
            }
            BeanUtils.copyProperties(release, grayscale, "id");
            grayscale.setCreateTime(new Date());
            grayscale.setCreateUser(getLoginName());
            grayscale.setReleaseTime(new Date());
            grayscale.setReleaseUser(getLoginName());
            grayscale.setVersion(version);
            if (grayscale.getId() == null) {
                this.grayscaleMapper.insertSelective(grayscale);
            } else {
                this.grayscaleMapper.updateByPrimaryKeySelective(grayscale);
            }

            // 生成快照
            this.snapshot(grayscale);
            // 记录操作日志
            this.auditlogService.log(grayscale.getClientId(), AuditlogType.ROLLBACK_MASTER, "回滚了灰度配置，回滚版本为：" + release.getVersion()+ "，当前版本号： " + version,  ConfigType.MASTER);

            // TODO 回滚灰度发布（只通知指定的灰度实例）
            if (!StringUtils.isEmpty(grayscale.getRules())) {
                String clientKey = MetaUtil.getClientKey(client.getEnvName(), client.getGroupName(), client.getClientName());
                Set<String> instanceKeys = StrUtil.strToSet(grayscale.getRules());
                this.clientsTransmitter.transmitInstances(clientKey, instanceKeys);
            }
        }
    }

    @Override
    @Transactional
    public void snapshot(Config config) {
        Release release = new Release();
        BeanUtils.copyProperties(config, release, "id", "createTime", "createUser");
        release.setCreateTime(new Date());
        release.setCreateUser(getLoginName());
        release.setType(ReleaseType.CONFIG);
        this.releaseMapper.insertSelective(release);
    }

    @Override
    @Transactional
    public void snapshot(Grayscale grayscale) {
        Release release = new Release();
        BeanUtils.copyProperties(grayscale, release, "id", "createTime", "createUser");
        release.setCreateTime(new Date());
        release.setCreateUser(getLoginName());
        release.setType(ReleaseType.GRAYSCALE);
        this.releaseMapper.insertSelective(release);
    }
}

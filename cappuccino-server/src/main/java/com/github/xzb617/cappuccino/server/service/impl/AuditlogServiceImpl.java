package com.github.xzb617.cappuccino.server.service.impl;

import com.github.xzb617.cappuccino.server.constant.AuditlogType;
import com.github.xzb617.cappuccino.server.constant.ConfigType;
import com.github.xzb617.cappuccino.server.domain.condition.AuditlogCondition;
import com.github.xzb617.cappuccino.server.domain.entity.Auditlog;
import com.github.xzb617.cappuccino.server.domain.vo.AuditlogVO;
import com.github.xzb617.cappuccino.server.mapper.AuditlogMapper;
import com.github.xzb617.cappuccino.server.security.SecurityCapability;
import com.github.xzb617.cappuccino.server.service.AuditlogService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AuditlogServiceImpl extends SecurityCapability implements AuditlogService {

    private final AuditlogMapper auditlogMapper;

    public AuditlogServiceImpl(AuditlogMapper auditlogMapper) {
        this.auditlogMapper = auditlogMapper;
    }

    @Override
    public List<AuditlogVO> getList(AuditlogCondition condition) {
        return this.auditlogMapper.selectList(condition);
    }

    @Override
    public List<Auditlog> getRecentlyList(Long clientId, int size) {
        return this.auditlogMapper.selectRecentlyList(clientId, size);
    }

    @Override
    public void log(Long clientId, AuditlogType auditlogType, String desc, ConfigType configType) {
        Auditlog log = new Auditlog();
        log.setOpClientId(clientId);
        log.setConfigType(configType.getType());
        log.setOpType(auditlogType.getType());
        log.setColor(auditlogType.getColor());
        log.setOpDesc(desc);
        log.setOpTime(new Date());
        log.setOpUser(getLoginName());
        this.auditlogMapper.insertSelective(log);
    }
}

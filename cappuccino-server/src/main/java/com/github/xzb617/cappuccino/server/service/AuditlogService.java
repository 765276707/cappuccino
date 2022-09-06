package com.github.xzb617.cappuccino.server.service;

import com.github.xzb617.cappuccino.server.constant.AuditlogType;
import com.github.xzb617.cappuccino.server.constant.ConfigType;
import com.github.xzb617.cappuccino.server.domain.condition.AuditlogCondition;
import com.github.xzb617.cappuccino.server.domain.entity.Auditlog;
import com.github.xzb617.cappuccino.server.domain.vo.AuditlogVO;

import java.util.List;

public interface AuditlogService {

    List<AuditlogVO> getList(AuditlogCondition condition);

    List<Auditlog> getRecentlyList(Long clientId, int size);

    void log(Long clientId, AuditlogType auditlogType, String desc, ConfigType configType);
}

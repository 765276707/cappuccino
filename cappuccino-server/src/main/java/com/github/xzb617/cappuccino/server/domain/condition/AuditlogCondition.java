package com.github.xzb617.cappuccino.server.domain.condition;

import com.github.xzb617.cappuccino.server.base.TextAndTimeCondition;

public class AuditlogCondition extends TextAndTimeCondition {

    private String opType;

    private Long envId;

    private Long groupId;

    public String getOpType() {
        return opType;
    }

    public void setOpType(String opType) {
        this.opType = opType;
    }

    public Long getEnvId() {
        return envId;
    }

    public void setEnvId(Long envId) {
        this.envId = envId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}

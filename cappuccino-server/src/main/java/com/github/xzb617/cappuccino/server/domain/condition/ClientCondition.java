package com.github.xzb617.cappuccino.server.domain.condition;

import com.github.xzb617.cappuccino.server.base.TextCondition;

public class ClientCondition extends TextCondition {

    private Long envId;

    private Long groupId;

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

    @Override
    public String toString() {
        return "ClientCondition{" +
                "envId=" + envId +
                ", groupId=" + groupId +
                '}';
    }
}

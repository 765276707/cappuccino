package com.github.xzb617.cappuccino.server.domain.vo;

import com.github.xzb617.cappuccino.server.domain.entity.Auditlog;

public class AuditlogVO extends Auditlog {

    private Long clientId;

    private String clientName;

    private String envName;

    private String groupName;

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getEnvName() {
        return envName;
    }

    public void setEnvName(String envName) {
        this.envName = envName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}

package com.github.xzb617.cappuccino.server.domain.vo;

import com.github.xzb617.cappuccino.server.domain.entity.Release;

public class ReleaseVO extends Release {

    private String clientName;

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
}

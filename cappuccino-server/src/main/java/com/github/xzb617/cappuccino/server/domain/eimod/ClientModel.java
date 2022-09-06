package com.github.xzb617.cappuccino.server.domain.eimod;

import com.github.xzb617.cappuccino.server.domain.entity.Client;

import java.util.List;

public class ClientModel {

    private List<ClientWithConfigModel> clientWithConfigModels;

    public List<ClientWithConfigModel> getClientWithConfigModels() {
        return clientWithConfigModels;
    }

    public void setClientWithConfigModels(List<ClientWithConfigModel> clientWithConfigModels) {
        this.clientWithConfigModels = clientWithConfigModels;
    }

    @Override
    public String toString() {
        return "ClientModel{" +
                "clientWithConfigModels=" + clientWithConfigModels +
                '}';
    }
}

package com.github.xzb617.cappuccino.server.domain.vo;

import com.github.xzb617.cappuccino.server.domain.entity.Client;
import com.github.xzb617.cappuccino.server.domain.entity.Config;
import com.github.xzb617.cappuccino.server.domain.entity.Grayscale;

public class ClientImportAndExport {

    private Client client;

    private Config config;

    private Grayscale grayscale;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public Grayscale getGrayscale() {
        return grayscale;
    }

    public void setGrayscale(Grayscale grayscale) {
        this.grayscale = grayscale;
    }
}

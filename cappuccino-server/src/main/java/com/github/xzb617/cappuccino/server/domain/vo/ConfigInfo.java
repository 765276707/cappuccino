package com.github.xzb617.cappuccino.server.domain.vo;

import com.github.xzb617.cappuccino.server.domain.entity.Auditlog;
import com.github.xzb617.cappuccino.server.domain.entity.Client;
import com.github.xzb617.cappuccino.server.domain.entity.Config;
import com.github.xzb617.cappuccino.server.domain.entity.Grayscale;

import java.util.List;

public class ConfigInfo {

    private Client client;

    private Config config;

    private Grayscale grayscale;

    private List<Auditlog> auditlogs;

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

    public List<Auditlog> getAuditlogs() {
        return auditlogs;
    }

    public void setAuditlogs(List<Auditlog> auditlogs) {
        this.auditlogs = auditlogs;
    }
}

package com.github.xzb617.cappuccino.server.constant;

public enum ConfigType {

    MASTER(1),
    GRAYSCALE(2);

    // 配置类型
    private Integer type;

    ConfigType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }
}

package com.github.xzb617.cappuccino.server.constant;

public enum ResourceType {

    CLIENT("c"),
    GROUP("g"),
    ENV("e");

    // 配置类型
    private String type;

    ResourceType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}

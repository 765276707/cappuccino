package com.github.xzb617.cappuccino.server.security.perms;

/**
 * 角色
 */
public enum Role {

    /**
     * 超级管理员
     * 权限范围： 全部（主要是可以管理账号）
     */
    SUPER_ADMIN("SA"),
    /**
     * 普通管理员
     * 权限范围： 控制台、客户端配置（不包含授权）、个人中心
     */
    COMMON_ADMIN("CA");

    private String value;

    Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}

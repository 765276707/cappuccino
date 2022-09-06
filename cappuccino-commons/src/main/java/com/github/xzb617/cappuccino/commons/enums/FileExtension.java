package com.github.xzb617.cappuccino.commons.enums;

/**
 * 配置文件类型
 * @author xzb617
 */
public enum FileExtension {

    /**
     * properties
     */
    PROPERTIES("properties"),
    /**
     * yaml/yml
     */
    YAML("yaml");

    private String value;

    FileExtension(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static FileExtension of(String fileExtension) {
        if ("properties".equals(fileExtension)) {
            return PROPERTIES;
        }
        if ("yaml".equals(fileExtension) || "yml".equals(fileExtension)) {
            return YAML;
        }
        throw new IllegalArgumentException("Illegal string fileExtension, can not convert to FileExtension.");
    }

}

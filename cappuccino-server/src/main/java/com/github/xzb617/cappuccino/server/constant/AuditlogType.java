package com.github.xzb617.cappuccino.server.constant;

public enum AuditlogType {

    RELEASE_MASTER("发布主配置", "#67C23A"),
    RELEASE_GRAYSCALE("灰度发布", "#409EFF"),
    RELEASE_GRAYSCALE_TO_MASTER("全量发布", "#409EFF"),
    ROLLBACK_MASTER("回滚主配置", "#E6A23C"),
    DELETE_MASTER("删除主配置", "#F56C6C"),
    DELETE_GRAYSCALE("取消灰度配置", "#F56C6C");

    // 操作类型
    private String type;
    // 节点颜色
    private String color;

    AuditlogType(String type, String color) {
        this.type = type;
        this.color = color;
    }

    public String getType() {
        return this.type;
    }

    public String getColor() {
        return color;
    }

}

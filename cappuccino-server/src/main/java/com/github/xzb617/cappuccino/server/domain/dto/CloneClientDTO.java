package com.github.xzb617.cappuccino.server.domain.dto;

import java.util.List;

/**
 * 克隆客户端
 * @author xzb617
 */
public class CloneClientDTO {

    /**
     * 要克隆的客户端编号集
     */
    private List<Long> clientIds;

    /**
     * 克隆到的环境
     */
    private Long envId;

    /**
     * 克隆到的分组
     */
    private Long groupId;

    /**
     * 是否克隆其配置
     */
    private Boolean withConfig;

    public List<Long> getClientIds() {
        return clientIds;
    }

    public void setClientIds(List<Long> clientIds) {
        this.clientIds = clientIds;
    }

    public Long getEnvId() {
        return envId;
    }

    public void setEnvId(Long envId) {
        this.envId = envId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Boolean getWithConfig() {
        return withConfig;
    }

    public void setWithConfig(Boolean withConfig) {
        this.withConfig = withConfig;
    }
}

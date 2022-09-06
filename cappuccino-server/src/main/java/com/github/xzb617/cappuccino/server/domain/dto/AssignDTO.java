package com.github.xzb617.cappuccino.server.domain.dto;

import com.github.xzb617.cappuccino.server.constant.ResourceType;

import java.util.List;

public class AssignDTO {

    private List<Long> userIds;

    private Long resourceId;

    private String resourceType;

    public AssignDTO() {
    }

    public AssignDTO(List<Long> userIds, Long resourceId, ResourceType resourceType) {
        this.userIds = userIds;
        this.resourceId = resourceId;
        this.resourceType = resourceType.getType();
    }

    public List<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    @Override
    public String toString() {
        return "AssignDTO{" +
                "userIds=" + userIds +
                ", resourceId=" + resourceId +
                ", resourceType='" + resourceType + '\'' +
                '}';
    }
}

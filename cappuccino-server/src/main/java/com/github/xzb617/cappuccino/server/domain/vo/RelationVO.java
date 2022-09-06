package com.github.xzb617.cappuccino.server.domain.vo;

import java.util.List;

public class RelationVO {

    private List<Long> userIds;

    private List<ElCheckboxOption> options;

    public RelationVO() {
    }

    public RelationVO(List<Long> userIds, List<ElCheckboxOption> options) {
        this.userIds = userIds;
        this.options = options;
    }

    public List<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
    }

    public List<ElCheckboxOption> getOptions() {
        return options;
    }

    public void setOptions(List<ElCheckboxOption> options) {
        this.options = options;
    }

}

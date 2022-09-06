package com.github.xzb617.cappuccino.server.domain.condition;

import com.github.xzb617.cappuccino.server.base.TextAndTimeCondition;

public class ReleaseCondition extends TextAndTimeCondition {

    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

}

package com.github.xzb617.cappuccino.server.domain.condition;

import com.github.xzb617.cappuccino.server.base.TextCondition;

public class UserCondition extends TextCondition {

    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

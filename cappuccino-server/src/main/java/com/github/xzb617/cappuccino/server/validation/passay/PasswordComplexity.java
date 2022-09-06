package com.github.xzb617.cappuccino.server.validation.passay;

public enum PasswordComplexity {
    /**
     * 简单
     */
    SIMPLE("密码长度必须在6-20之间，不允许连续6个字母或数字字符，不允许空白字符"),

    /**
     * 中等
     */
    MEDIUM("密码长度必须在6-20之间，至少包含一个字母和一个数字，不允许连续6个字母或数字字符，不允许空白字符"),
    /**
     * 复杂
     */
    COMPLEX("密码长度必须在6-20之间，必须同时包含大小写字母、数字和特殊字符，不允许连续6个字母或数字字符，不允许空白字符");

    private String errorMessage;

    PasswordComplexity(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}

package com.github.xzb617.cappuccino.server.domain.vo;

import java.util.Date;
import java.util.Set;

public class AuthVO {

    private String token;

    private String username;

    private Set<String> roles;

    private Date loginTime;

    public AuthVO() {
    }

    public AuthVO(String token, String username, Set<String> roles, Date loginTime) {
        this.token = token;
        this.username = username;
        this.roles = roles;
        this.loginTime = loginTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

}

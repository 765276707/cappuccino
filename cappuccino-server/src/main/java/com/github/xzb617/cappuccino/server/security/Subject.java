package com.github.xzb617.cappuccino.server.security;

import com.github.xzb617.cappuccino.commons.utils.StrUtil;
import com.github.xzb617.cappuccino.server.security.perms.Role;

import java.util.Set;

public class Subject {

    private Long userId;
    private String username;
    private Boolean isSuperAdmin;
    private Set<String> roles;

    public Subject(Long userId, String username) {
        this.userId = userId;
        this.username = username;
        this.isSuperAdmin = false;
    }

    public Subject(Long userId, String username, Boolean isSuperAdmin) {
        this.userId = userId;
        this.username = username;
        this.isSuperAdmin = isSuperAdmin;
    }

    public Subject(Long userId, String username, Boolean isSuperAdmin, Set<String> roles) {
        this.userId = userId;
        this.username = username;
        this.isSuperAdmin = isSuperAdmin;
        this.roles = roles;
    }

    public Subject(JwtClaims claims) {
        this.userId = claims.getUid();
        this.username = claims.getSubject();
        this.roles = StrUtil.strToSet(claims.getRoles());
        this.isSuperAdmin = !roles.isEmpty() && roles.contains(Role.SUPER_ADMIN.getValue());
    }

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public Boolean getSuperAdmin() {
        return isSuperAdmin;
    }

    public Set<String> getRoles() {
        return roles;
    }

    /**
     * 是否有某个角色
     * @param role 角色
     * @return
     */
    public boolean hasRole(String role) {
        if (this.roles.isEmpty()) {
            return false;
        }
        return this.roles.contains(role);
    }

}

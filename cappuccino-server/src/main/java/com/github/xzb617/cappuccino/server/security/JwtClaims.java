package com.github.xzb617.cappuccino.server.security;

import io.jsonwebtoken.Claims;

import java.util.Date;

public class JwtClaims {

    private String id;
    private Long uid;
    // 角色
    private String roles;
    // subject == username
    private String subject;
    private String audience;
    private Date issuedAt;
    private Date expiration;

    public JwtClaims() {
    }

    public JwtClaims(String id, String subject, String audience, Date issuedAt, Date expiration) {
        this.id = id;
        this.subject = subject;
        this.audience = audience;
        this.issuedAt = issuedAt;
        this.expiration = expiration;
    }

    public JwtClaims(Claims claims) {
        this.id = claims.getId();
        this.subject = claims.getSubject();
        this.audience = claims.getAudience();
        this.issuedAt = claims.getIssuedAt();
        this.expiration = claims.getExpiration();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAudience() {
        return audience;
    }

    public void setAudience(String audience) {
        this.audience = audience;
    }

    public Date getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(Date issuedAt) {
        this.issuedAt = issuedAt;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }
}

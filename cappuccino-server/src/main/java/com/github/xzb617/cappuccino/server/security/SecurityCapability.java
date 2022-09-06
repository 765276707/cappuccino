package com.github.xzb617.cappuccino.server.security;

import com.github.xzb617.cappuccino.server.exception.UnauthorizedException;

/**
 * 具有获取安全相关信息的能力
 * @author xzb617
 */
public abstract class SecurityCapability {

    /**
     * 获取当前登录用户的编号
     * @return
     */
    protected Long getLoginUid() {
        Subject context = SubjectContextHolder.getContext();
        if (context == null) {
            throw new UnauthorizedException();
        }
        return context.getUserId();
    }

    /**
     * 获取当前登录用户的用户名
     * @return
     */
    protected String getLoginName() {
        Subject context = SubjectContextHolder.getContext();
        if (context == null) {
            throw new UnauthorizedException();
        }
        return context.getUsername();
    }

    public boolean isSuperAdmin() {
        Subject context = SubjectContextHolder.getContext();
        if (context == null) {
            throw new UnauthorizedException();
        }
        return context.getSuperAdmin();
    }

}

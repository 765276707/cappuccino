package com.github.xzb617.cappuccino.server.security;

import org.springframework.util.Assert;

public class SubjectContextHolder {

    private final static ThreadLocal<Subject> CONTEXT_HOLDER = new ThreadLocal<>();

    public SubjectContextHolder() {
    }

    public static Subject getContext() {
        return CONTEXT_HOLDER.get();
    }

    public static void setContext(Subject var) {
        Assert.notNull(var, "Only non-null SecurityHolder instances are permitted");
        CONTEXT_HOLDER.set(var);
    }

    public static void clearContext() {
        CONTEXT_HOLDER.remove();
    }

}

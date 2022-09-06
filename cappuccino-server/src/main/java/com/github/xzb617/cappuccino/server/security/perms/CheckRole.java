package com.github.xzb617.cappuccino.server.security.perms;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 权限校验注解
 */
@Target({METHOD})
@Retention(RUNTIME)
@Documented
public @interface CheckRole {

    /**
     * 角色，默认： CA
     * @return Role
     */
    Role value() default Role.COMMON_ADMIN;

}

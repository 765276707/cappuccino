package com.github.xzb617.cappuccino.server.security.auth;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 允许匿名访问的接口
 * @author xzb617
 */
@Target({METHOD})
@Retention(RUNTIME)
@Documented
public @interface Anonymous {
}

package com.github.xzb617.cappuccino.server.aoplog;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日志注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Logger {

    /**
     * 操作描述
     * @return
     */
    String desc();

    /**
     * 是否屏蔽参数
     * @return
     */
    boolean shieldArgs() default false;

}

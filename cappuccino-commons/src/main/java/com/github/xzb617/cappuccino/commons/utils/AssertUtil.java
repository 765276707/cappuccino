package com.github.xzb617.cappuccino.commons.utils;

/**
 * 断言工具类
 * @author xzb617
 */
public class AssertUtil {

    /**
     * 不为 NULL
     * @param var
     * @param errMsg
     */
    public static void notNull(Object var, String errMsg) {
        if (var == null) {
            throw new IllegalArgumentException(errMsg);
        }
    }

}

package com.github.xzb617.cappuccino.commons.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 字符串工具类
 */
public class StrUtil {

    private final static String DEFAULT_REGEX = ",";

    public static boolean isEmpty(String source) {
        return source==null || "".equals(source);
    }

    public static String[] strToArr(String source, String regex) {
        AssertUtil.notNull(source, "source can not be null.");
        return source.split(regex);
    }

    public static String[] strToArr(String source) {
        return strToArr(source, DEFAULT_REGEX);
    }

    public static Set<String> strToSet(String source) {
        String[] arr = strToArr(source);
        Set<String> set = new HashSet<>(arr.length);
        for (String var : arr) {
            set.add(var);
        }
        return set;
    }

    public static List<String> strToList(String source) {
        String[] arr = strToArr(source);
        List<String> list = new ArrayList<>(arr.length);
        for (String var : arr) {
            list.add(var);
        }
        return list;
    }

    public static List<Long> strToLongList(String source) {
        String[] arr = strToArr(source);
        List<Long> list = new ArrayList<>(arr.length);
        for (String var : arr) {
            if (var != null) {
                list.add(Long.parseLong(var));
            }
        }
        return list;
    }
}

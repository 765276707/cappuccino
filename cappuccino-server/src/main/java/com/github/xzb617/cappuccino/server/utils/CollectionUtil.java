package com.github.xzb617.cappuccino.server.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 集合工具类
 * @author xzb617
 */
public class CollectionUtil {

    public static Set<String> splitToSet(String source, String splitChar) {
        if (source == null) {
            throw new IllegalArgumentException("splitToSet func param <source> can not be null.");
        }
        String[] arr = source.split(splitChar);
        return Arrays.stream(arr).collect(Collectors.toSet());
    }

}

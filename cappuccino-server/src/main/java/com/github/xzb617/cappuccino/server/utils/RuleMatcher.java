package com.github.xzb617.cappuccino.server.utils;

import java.util.Arrays;
import java.util.Collections;

/**
 * 灰度规则匹配器
 * @author xzb617
 */
public class RuleMatcher {

    public static boolean match(String rules, String instanceKey) {
        if (rules==null || rules.length()==0 || instanceKey==null) {
            return false;
        }
        return rules.contains(instanceKey);
    }

}

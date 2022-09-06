package com.github.xzb617.cappuccino.commons.utils;

import com.github.xzb617.cappuccino.commons.data.Meta;

import java.util.HashMap;
import java.util.Map;

public class MetaUtil {

    private final static String SPLIT_CHAR = ":";

    public static String getClientKey(Meta meta) {
        AssertUtil.notNull(meta, "meta can not be null.");
        AssertUtil.notNull(meta.getEnv(), "env in meta can not be null.");
        AssertUtil.notNull(meta.getGroup(), "group in meta can not be null.");
        AssertUtil.notNull(meta.getName(), "name in meta can not be null.");
        return meta.getEnv() + SPLIT_CHAR + meta.getGroup() + SPLIT_CHAR + meta.getName();
    }

    public static String getClientKey(String env, String group, String name) {
        AssertUtil.notNull(env, "env can not be null.");
        AssertUtil.notNull(group, "group can not be null.");
        AssertUtil.notNull(name, "name can not be null.");
        return env + SPLIT_CHAR + group + SPLIT_CHAR + name;
    }


    public static String getInstanceKey(String ip, String port) {
        if (port == null) {
            return ip;
        }
        return ip + SPLIT_CHAR + port;
    }
}

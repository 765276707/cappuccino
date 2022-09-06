package com.github.xzb617.cappuccino.client.cache;

import java.util.Map;

public class RefresherCache {

    private static boolean sameLoadTimes = false;

    private static Map config = null;

    private final static Object LOCK = new Object();

    public static void resetCache() {
        synchronized (LOCK) {
            sameLoadTimes = false;
            config = null;
        }
    }

    public static void markSameBatch(Map prop) {
        synchronized (LOCK) {
            sameLoadTimes = true;
            config = prop;
        }
    }

    public static boolean isSameBatch() {
        return sameLoadTimes;
    }

    public static Map getCache() {
        return config;
    }

}

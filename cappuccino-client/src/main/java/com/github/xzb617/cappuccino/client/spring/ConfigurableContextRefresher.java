package com.github.xzb617.cappuccino.client.spring;

import com.github.xzb617.cappuccino.client.cache.RefresherCache;
import org.springframework.cloud.context.refresh.ContextRefresher;

import java.util.Properties;

/**
 * 配置刷新逻辑
 */
public class ConfigurableContextRefresher {


    private final ContextRefresher contextRefresher;

    public ConfigurableContextRefresher(ContextRefresher contextRefresher) {
        this.contextRefresher = contextRefresher;
    }

    public void refreshEnvironment() {
        // 重置标记缓存
        RefresherCache.resetCache();
        // 刷新配置
        this.contextRefresher.refresh();
    }
}

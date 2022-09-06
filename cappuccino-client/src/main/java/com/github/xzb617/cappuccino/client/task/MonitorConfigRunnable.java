package com.github.xzb617.cappuccino.client.task;

import com.github.xzb617.cappuccino.client.core.ConfigService;
import com.github.xzb617.cappuccino.client.spring.ConfigurableContextRefresher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 长轮询监听配置变化状态
 * <p>1.服务端超时时间为 60 秒</p>
 * @author xzb617
 */
public class MonitorConfigRunnable implements Runnable {

    private final static Logger LOGGER = LoggerFactory.getLogger(MonitorConfigRunnable.class);
    private final ConfigService configService;
    private final ConfigurableContextRefresher configurableContextRefresher;

    public MonitorConfigRunnable(ConfigService configService, ConfigurableContextRefresher configurableContextRefresher) {
        this.configService = configService;
        this.configurableContextRefresher = configurableContextRefresher;
    }

    @Override
    public void run() {
        try {
            boolean modified = this.configService.monitorConfig();
            if (modified) {
                // 有配置文件更新
                this.configurableContextRefresher.refreshEnvironment();
            }
        } catch (Throwable e) {
            LOGGER.error("An error occurred while executing the scheduled pull configuration, cause by {}", e.getMessage(), e);
        }
    }

}

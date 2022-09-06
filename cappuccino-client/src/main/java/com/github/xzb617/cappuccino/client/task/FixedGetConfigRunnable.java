package com.github.xzb617.cappuccino.client.task;

import com.github.xzb617.cappuccino.client.core.ConfigService;
import com.github.xzb617.cappuccino.client.spring.ConfigurableContextRefresher;
import com.github.xzb617.cappuccino.commons.data.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 定期拉取配置看有没有配置更新
 * <p>1.对长轮询小概率出现的漏掉更新通知进行兜底</p>
 * <p>2.每5分钟执行一次</p>
 * @author xzb617
 */
public class FixedGetConfigRunnable implements Runnable {

    private String lastConfigSign;
    /**
     * 日志
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(FixedGetConfigRunnable.class);
    private final ConfigService configService;
    private final ConfigurableContextRefresher configurableContextRefresher;

    public FixedGetConfigRunnable(ConfigService configService, ConfigurableContextRefresher configurableContextRefresher) {
        this.configService = configService;
        this.configurableContextRefresher = configurableContextRefresher;
    }

    @Override
    public void run() {
        try {
            Config config = this.configService.getConfig();
            String sign = config.getSign();
            boolean modified = !sign.equals(this.lastConfigSign);
            if (modified) {
                // 有配置文件更新
                this.lastConfigSign = sign;
                this.configurableContextRefresher.refreshEnvironment();
                LOGGER.info("Fixed polling found configuration updates in server, and the current configuration environment has been refreshed.");
            }
        } catch (Throwable e) {
            LOGGER.error("An error occurred while executing the scheduled pull configuration, cause by {}", e.getMessage(), e);
        }
    }
}

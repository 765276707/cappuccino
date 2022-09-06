package com.github.xzb617.cappuccino.client.listener;

import com.github.xzb617.cappuccino.client.core.ConfigService;
import com.github.xzb617.cappuccino.client.core.ConfigServiceFactory;
import com.github.xzb617.cappuccino.client.props.ClientProperties;
import com.github.xzb617.cappuccino.client.spring.ConfigurableContextRefresher;
import com.github.xzb617.cappuccino.client.task.FixedGetConfigRunnable;
import com.github.xzb617.cappuccino.client.task.MonitorConfigRunnable;
import com.github.xzb617.cappuccino.client.task.ServerHeartbeatRunnable;
import com.github.xzb617.cappuccino.commons.executor.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CappuccinoConfigurableEnvironmentListener implements ApplicationListener<ContextRefreshedEvent> {

    private final static Logger LOGGER = LoggerFactory.getLogger(CappuccinoConfigurableEnvironmentListener.class);
    private final ClientProperties clientProperties;
    private final ConfigurableContextRefresher configurableContextRefresher;

    private final ScheduledExecutorService monitorConfigService = Executors.newSingleThreadScheduledExecutor("monitor-config", true);
    private final ScheduledExecutorService fixedGetConfigService = Executors.newSingleThreadScheduledExecutor("fixed-get-config", true);
    private final ScheduledExecutorService serverHeartbeatService = Executors.newSingleThreadScheduledExecutor("server-heartbeat", true);

    public CappuccinoConfigurableEnvironmentListener(ClientProperties clientProperties, ConfigurableContextRefresher configurableContextRefresher) {
        this.clientProperties = clientProperties;
        this.configurableContextRefresher = configurableContextRefresher;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        Boolean refreshEnabled = this.clientProperties.getRefreshEnabled();
        if (refreshEnabled) {
            LOGGER.info("The auto refresh configuration from config server is enabled on the current client.");
            // 获取 ConfigService
            ConfigService configService = ConfigServiceFactory.getInstance(this.clientProperties);

            // 监听配置
            MonitorConfigRunnable monitorConfigRunnable = new MonitorConfigRunnable(configService, this.configurableContextRefresher);
            this.monitorConfigService.scheduleAtFixedRate(monitorConfigRunnable, 2000,  1000, TimeUnit.MILLISECONDS);

            // 定期获取配置校对
            FixedGetConfigRunnable fixedGetConfigRunnable = new FixedGetConfigRunnable(configService, this.configurableContextRefresher);
            this.fixedGetConfigService.scheduleAtFixedRate(fixedGetConfigRunnable, 300000, 300000, TimeUnit.MILLISECONDS);

            // 服务节点心跳检测
            ServerHeartbeatRunnable serverHeartbeatRunnable = new ServerHeartbeatRunnable(configService);
            this.serverHeartbeatService.scheduleAtFixedRate(serverHeartbeatRunnable, 2000, 30000, TimeUnit.MILLISECONDS);
        }
    }

}

package com.github.xzb617.cappuccino.client.task;

import com.github.xzb617.cappuccino.client.core.ConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 服务节点心跳检测
 * <p>1.每30秒发送一次心跳包，连续3次无心跳响应则会将该服务更新为dead状态</p>
 * @author xzb617
 */
public class ServerHeartbeatRunnable implements Runnable {

    private final Logger LOGGER = LoggerFactory.getLogger(ServerHeartbeatRunnable.class);
    private final ConfigService configService;

    public ServerHeartbeatRunnable(ConfigService configService) {
        this.configService = configService;
    }

    @Override
    public void run() {
        try {
            this.configService.pingServers();
        } catch (Throwable e) {
            LOGGER.error("Service node heartbeat detection thread exception");
        }
    }
}

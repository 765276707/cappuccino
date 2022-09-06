package com.github.xzb617.cappuccino.client.listener;

import com.github.xzb617.cappuccino.client.core.ConfigService;
import com.github.xzb617.cappuccino.client.core.ConfigServiceFactory;
import com.github.xzb617.cappuccino.client.props.ClientProperties;
import com.github.xzb617.cappuccino.http.ex.RpcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

import java.util.Map;

public class ApplicationShutdownListener implements ApplicationListener<ApplicationReadyEvent> {

    private final static Logger LOGGER = LoggerFactory.getLogger(ApplicationShutdownListener.class);
    private final ClientProperties clientProperties;

    public ApplicationShutdownListener(ClientProperties clientProperties) {
        this.clientProperties = clientProperties;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        ConfigService configService = ConfigServiceFactory.getInstance(this.clientProperties);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            LOGGER.info("Application exit, now send shutdown command to config server.");
            try {
                configService.shutdownClient();
            } catch (RpcException e) {
                LOGGER.error("Application exit, now execute shutdown command failed.");
            }
        }));
    }

}

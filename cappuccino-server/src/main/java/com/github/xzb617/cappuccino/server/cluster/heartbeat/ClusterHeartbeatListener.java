package com.github.xzb617.cappuccino.server.cluster.heartbeat;

import com.github.xzb617.cappuccino.commons.executor.Executors;
import com.github.xzb617.cappuccino.server.cluster.ClusterContainer;
import com.github.xzb617.cappuccino.server.properties.ServerProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 集群节点心跳检测监听器
 * @author xzb617
 */
public class ClusterHeartbeatListener implements ApplicationListener<ContextRefreshedEvent> {

    private final RestTemplate restTemplate;
    private final ClusterContainer clusterContainer;
    private final ServerProperties serverProperties;
    private final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor("cluster-heartbeat", true);

    public ClusterHeartbeatListener(RestTemplate restTemplate, ClusterContainer clusterContainer, ServerProperties serverProperties) {
        this.restTemplate = restTemplate;
        this.clusterContainer = clusterContainer;
        this.serverProperties = serverProperties;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        ClusterHeartbeatRunnable heartbeatRunnable = new ClusterHeartbeatRunnable(this.clusterContainer, this.restTemplate);
        this.scheduledExecutorService.scheduleAtFixedRate(heartbeatRunnable, 1000, this.serverProperties.getClusterHeartbeatInterval(), TimeUnit.MILLISECONDS);
    }

}

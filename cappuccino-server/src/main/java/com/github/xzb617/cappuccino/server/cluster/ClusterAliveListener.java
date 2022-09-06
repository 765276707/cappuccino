package com.github.xzb617.cappuccino.server.cluster;

import com.github.xzb617.cappuccino.http.ex.RpcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class ClusterAliveListener implements ApplicationListener<ContextRefreshedEvent> {

    private final static Logger LOGGER = LoggerFactory.getLogger(ClusterAliveListener.class);

    private final ClusterBroadcaster clusterBroadcaster;

    public ClusterAliveListener(ClusterBroadcaster clusterBroadcaster) {
        this.clusterBroadcaster = clusterBroadcaster;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        // 通知上线
        this.clusterBroadcaster.broadcastStartupServer();
        // 通知下线
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            LOGGER.info("Server cluster node exit, now send shutdown command to other server cluster nodes.");
            this.clusterBroadcaster.broadcastShutdownServer();
        }));
    }

}

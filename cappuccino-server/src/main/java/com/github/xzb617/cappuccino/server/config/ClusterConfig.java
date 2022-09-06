package com.github.xzb617.cappuccino.server.config;

import com.github.xzb617.cappuccino.server.cluster.ClusterAliveListener;
import com.github.xzb617.cappuccino.server.cluster.ClusterBroadcaster;
import com.github.xzb617.cappuccino.server.cluster.ClusterContainer;
import com.github.xzb617.cappuccino.server.cluster.heartbeat.ClusterHeartbeatListener;
import com.github.xzb617.cappuccino.server.properties.ServerProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * 集群的相关配置
 * @author xzb617
 */
@Configuration
public class ClusterConfig {

    @Bean
    @ConditionalOnMissingBean(RestTemplate.class)
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    @ConditionalOnBean({ClusterContainer.class, ServerProperties.class})
    @ConditionalOnProperty(prefix = ServerProperties.PREFIX, name = "cluster-enabled", havingValue = "true")
    @ConditionalOnWebApplication
    public ClusterHeartbeatListener clusterHeartbeatListener(ClusterContainer clusterContainer,
                                        RestTemplate restTemplate, ServerProperties serverProperties) {
        return new ClusterHeartbeatListener(restTemplate, clusterContainer, serverProperties);
    }

    @Bean
    @ConditionalOnBean(ClusterBroadcaster.class)
    @ConditionalOnProperty(prefix = ServerProperties.PREFIX, name = "cluster-enabled", havingValue = "true")
    @ConditionalOnWebApplication
    public ClusterAliveListener clusterAliveListener(ClusterBroadcaster clusterBroadcaster) {
        return new ClusterAliveListener(clusterBroadcaster);
    }

}

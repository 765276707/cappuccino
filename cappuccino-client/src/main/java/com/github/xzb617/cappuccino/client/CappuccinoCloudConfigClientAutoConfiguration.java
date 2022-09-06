package com.github.xzb617.cappuccino.client;

import com.github.xzb617.cappuccino.client.core.ConfigService;
import com.github.xzb617.cappuccino.client.core.ConfigServiceFactory;
import com.github.xzb617.cappuccino.client.listener.ApplicationShutdownListener;
import com.github.xzb617.cappuccino.client.listener.CappuccinoConfigurableEnvironmentListener;
import com.github.xzb617.cappuccino.client.props.ClientProperties;
import com.github.xzb617.cappuccino.client.spring.ConfigurableContextRefresher;
import com.github.xzb617.cappuccino.rpc.LoadBalanceRpcBroker;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 客户端自动装配
 * @author xzb617
 */
@Configuration
@EnableConfigurationProperties(ClientProperties.class)
public class CappuccinoCloudConfigClientAutoConfiguration {

    @Bean
    @ConditionalOnBean(ClientProperties.class)
    public ConfigService configService(ClientProperties clientProperties) {
        return ConfigServiceFactory.getInstance(clientProperties);
    }

    @Bean
    public ConfigurableContextRefresher configurableContextRefresher(ContextRefresher contextRefresher) {
        return new ConfigurableContextRefresher(contextRefresher);
    }

    @Bean
    public CappuccinoConfigurableEnvironmentListener cappuccinoConfigurableEnvironmentListener(ClientProperties clientProperties, ConfigurableContextRefresher configurableContextRefresher) {
        return new CappuccinoConfigurableEnvironmentListener(clientProperties, configurableContextRefresher);
    }

    @Bean
    public ApplicationShutdownListener applicationShutdownListener(ClientProperties clientProperties) {
        return new ApplicationShutdownListener(clientProperties);
    }

}

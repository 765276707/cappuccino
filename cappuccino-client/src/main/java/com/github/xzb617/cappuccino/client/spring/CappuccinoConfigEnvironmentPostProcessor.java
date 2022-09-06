package com.github.xzb617.cappuccino.client.spring;

import com.github.xzb617.cappuccino.client.cache.RefresherCache;
import com.github.xzb617.cappuccino.client.converter.FileConverterDelegate;
import com.github.xzb617.cappuccino.client.core.ConfigService;
import com.github.xzb617.cappuccino.client.core.ConfigServiceFactory;
import com.github.xzb617.cappuccino.client.props.ClientProperties;
import com.github.xzb617.cappuccino.client.props.ClientPropertiesFactory;
import com.github.xzb617.cappuccino.client.utils.PropertiesUtil;
import com.github.xzb617.cappuccino.commons.data.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * CappuccinoConfigEnvironmentPostProcessor
 * @author xzb617
 */
public class CappuccinoConfigEnvironmentPostProcessor implements EnvironmentPostProcessor, Ordered {

    /**
     * 日志
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(CappuccinoConfigEnvironmentPostProcessor.class);

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment configurableEnvironment, SpringApplication application) {
        MutablePropertySources propertySources = configurableEnvironment.getPropertySources();
        if (!RefresherCache.isSameBatch())
        {
            // bootstrap
            Map config = this.getConfigToProperties(propertySources);
            if (config != null) {
                // 处理配置
                this.handleCappuccinoPropertySources(propertySources, config);
            }
            // 添加标识，即缓存同一批次的结果（重复请求了两次）
            RefresherCache.markSameBatch(config);
        }
        else
        {
            // application
            Map config = RefresherCache.getCache();
            // 处理配置
            if (config != null) {
                this.handleCappuccinoPropertySources(propertySources, config);
            }
        }

        // 将ConfigurationPropertySources的优先级重置到最优先位置（适配Spring环境后置处理器编排逻辑）
        ConfigurationPropertySources.attach(configurableEnvironment);
    }

    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE;
    }


    private Map getConfigToProperties(MutablePropertySources propertySources) {
        // 解析 ClientProperties
        ClientProperties clientProperties = ClientPropertiesFactory.load(propertySources);
        // 生成 ConfigService
        ConfigService configService = ConfigServiceFactory.getInstance(clientProperties);
        // 查询
        Config config = configService.getConfig();
        if (config == null) {
            return null;
        }
        // 转换器代理转换
        return FileConverterDelegate.delegateConvert(config);
    }


    private void handleCappuccinoPropertySources(MutablePropertySources propertySources, Map properties) {
        // 解析 PropertySource
        CappuccinoPropertySource cappuccinoPropertySource = new CappuccinoPropertySource(properties);
        String propertySourceName = cappuccinoPropertySource.getName();
        if (propertySources.get(propertySourceName) != null) {
            propertySources.replace(propertySourceName, cappuccinoPropertySource);
        } else {
            propertySources.addFirst(cappuccinoPropertySource);
        }
        if (LOGGER.isInfoEnabled()) {
            LOGGER.debug("Successfully loaded the configuration file from the config server, includes {} configuration items", properties.keySet().size());
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Successfully loaded the configuration file from the config server, includes {} configuration items, details is {}", properties.keySet().size(), properties.toString());
        }
    }

}

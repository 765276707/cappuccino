package com.github.xzb617.cappuccino.client.props;

import com.github.xzb617.cappuccino.commons.utils.ReflectUtil;
import com.github.xzb617.cappuccino.commons.utils.StrUtil;
import com.github.xzb617.cappuccino.loadbalance.factory.LoadBalanceStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertyResolver;
import org.springframework.core.env.PropertySourcesPropertyResolver;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ClientProperties 工厂
 * @author xzb617
 */
public class ClientPropertiesFactory {

    /**
     * 日志
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(ClientPropertiesFactory.class);
    /**
     * 大写字母正则
     */
    private final static Pattern CAPITAL_PATTERN = Pattern.compile("[A-Z]");


    /**
     * 提前加载客户端配置类
     * @param propertySources MutablePropertySources
     * @return ClientProperties
     */
    public static ClientProperties load(MutablePropertySources propertySources) {
        Properties properties = loadFromYaml();
        if (properties == null) {
            try {
                properties = loadFromProperties();
            } catch (IOException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error("Unable to find the bootstrap file under the classpath. cause by {}", e.getMessage());
                }
            }
        }
        if (properties == null) {
            throw new BeanCreationException("Unable to find the bootstrap file under the classpath.");
        }
        return load(properties, propertySources);
    }


    /**
     * 从环境中解析 ClientProperties 的值
     * @param environment 当前项目环境
     * @return ClientProperties
     */
    private static ClientProperties load(Properties environment, MutablePropertySources propertySources)  {
        // 构建一个属性解析器
        PropertyResolver propertyResolver = new PropertySourcesPropertyResolver(propertySources);
        // 通过反射获取 ClientProperties 所有属性值反驼峰模式名称
        ClientProperties clientProperties = new ClientProperties();
        List<Field> fields = ReflectUtil.getFields(ClientProperties.class);
        try {
            for (Field field : fields) {
                String fieldName = field.getName();
                String propName = convertConfPropName(fieldName, ClientProperties.PREFIX);
                Object property = environment.getProperty(propName);
                if (fieldName.equals("loadBalanceStrategy")) {
                    LoadBalanceStrategy loadBalanceStrategy = LoadBalanceStrategy.get(String.valueOf(property));
                    if (loadBalanceStrategy != null) {
                        ReflectUtil.setFieldValue(clientProperties, field, loadBalanceStrategy);
                    }
                } else {
                    // 处理占位符 ${xxx}
                    String strProperty = propertyResolver.resolvePlaceholders(String.valueOf(property));
                    // 如果属性有额外设定，则重新赋值
                    if (property != null) {
                        ReflectUtil.setFieldValue(clientProperties, field, strProperty);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error("Unable to resolve ClientProperties from current configurable environment. cause by {}", e.getMessage());
            }
            throw new BeanCreationException("Unable to resolve ClientProperties from current configurable environment.", e);
        }

        // 设置 name 的默认值
        if (StrUtil.isEmpty(clientProperties.getName())) {
            String applicationName = environment.getProperty("spring.application.name");
            if (StrUtil.isEmpty(applicationName)) {
                throw new BeanCreationException("Unable to resolve ClientProperties from current configurable environment, you must provide an name config value.");
            }
            clientProperties.setName(applicationName);
        }

        // 设置 grayscale 的默认值
        if (StrUtil.isEmpty(clientProperties.getGrayscale())) {
            String serverPort = environment.getProperty("server.port");
            if (StrUtil.isEmpty(serverPort)) {
                throw new BeanCreationException("Unable to resolve ClientProperties from current configurable environment, you must provide an grayscale config value.");
            }
            clientProperties.setGrayscale(serverPort);
        }

        return clientProperties;
    }

    /**
     * 加载类路径下 bootstrap.yml 或 bootstrap.yaml 文件
     * @return Properties
     */
    private static Properties loadFromYaml() {
        ClassPathResource resource = new ClassPathResource("/bootstrap.yaml");
        if (!resource.exists()) {
            resource = new ClassPathResource("/bootstrap.yml");
        }
        if (!resource.exists()) {
            return null;
        }
        YamlPropertiesFactoryBean factoryBean = new YamlPropertiesFactoryBean();
        factoryBean.setResources(resource);
        return factoryBean.getObject();
    }

    /**
     * 加载类路径下的 bootstrap.properties 文件
     * @return Properties
     * @throws IOException IOException
     */
    private static Properties loadFromProperties() throws IOException {
        ClassPathResource resource = new ClassPathResource("/bootstrap.properties");
        if (!resource.exists()) {
            return null;
        }
        Properties properties = new Properties();
        InputStream inputStream = resource.getInputStream();
        properties.load(inputStream);
        return properties;
    }


    /**
     * 对象属性名转成配置属性名
     * @param objectFieldName 对象属性名
     * @param prefix 固定前缀
     * @return String
     */
    private static String convertConfPropName(String objectFieldName, String prefix) {
        String fixedPrefix = prefix + ".";
        return fixedPrefix + camelToLine(objectFieldName);
    }

    /**
     * 驼峰转换，默认转换成短横线
     * 示例： userName -> user-name
     * @param value 属性值
     * @return String
     */
    private static String camelToLine(String value) {
        Matcher matcher = CAPITAL_PATTERN.matcher(value);
        StringBuffer stringBuffer = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(stringBuffer, "-" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(stringBuffer);
        return stringBuffer.toString();
    }

}

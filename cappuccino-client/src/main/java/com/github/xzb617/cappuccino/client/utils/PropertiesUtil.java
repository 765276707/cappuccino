package com.github.xzb617.cappuccino.client.utils;


import com.github.xzb617.cappuccino.commons.data.Config;
import com.github.xzb617.cappuccino.commons.enums.FileExtension;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.springframework.beans.factory.config.YamlMapFactoryBean;
import org.springframework.beans.factory.config.YamlProcessor;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.json.YamlJsonParser;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.yaml.snakeyaml.Yaml;
import sun.applet.resources.MsgAppletViewer;

import java.io.IOException;
import java.io.StringReader;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Properties 工具类
 */
public class PropertiesUtil {

    /**
     * properties
     * @return Properties字符串转 properties 类型
     * @throws IOException IOException
     */
    public static Properties loadFrom(String content) throws IOException {
        Properties properties = new Properties();
        properties.load(new StringReader(content));
        return properties;
    }


    /**
     * 合并多个 properties
     * @param sources Properties
     * @return
     */
    public static Properties mergeAll(Properties ... sources) {
        Properties properties = new Properties();
        for (Properties source : sources) {
            properties.putAll(source);
        }
        return properties;
    }


}

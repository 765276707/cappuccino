package com.github.xzb617.cappuccino.client.spring;

import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertiesPropertySource;

import java.util.Map;
import java.util.Properties;

public class CappuccinoPropertySource extends MapPropertySource {

    public CappuccinoPropertySource(Map source) {
        super("CappuccinoPropertySource", source);
    }

}

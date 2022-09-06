package com.github.xzb617.cappuccino.client.converter.impl;

import com.github.xzb617.cappuccino.client.converter.FileConverter;
import com.github.xzb617.cappuccino.client.utils.PropertiesUtil;
import com.github.xzb617.cappuccino.commons.enums.FileExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

public class PropertiesFileConverter implements FileConverter {

    private final static Logger LOGGER = LoggerFactory.getLogger(PropertiesFileConverter.class);

    @Override
    public FileExtension support() {
        return FileExtension.PROPERTIES;
    }

    @Override
    public Map convert(String source) {
        Map map = null;
        try {
            map = PropertiesUtil.loadFrom(source);
        } catch (IOException e) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error("Convert file to Map failed, " + e.getMessage());
            }
        }
        return map;
    }
}

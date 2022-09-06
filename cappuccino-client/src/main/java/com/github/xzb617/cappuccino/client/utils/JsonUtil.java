package com.github.xzb617.cappuccino.client.utils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Json工具类
 */
public class JsonUtil {

    private final static Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);
    private final static ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T toObject(String source, Class<T> type) {
        try {
            return objectMapper.readValue(source, type);
        } catch (IOException e) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error(e.getMessage());
            }
            return null;
        }
    }

}

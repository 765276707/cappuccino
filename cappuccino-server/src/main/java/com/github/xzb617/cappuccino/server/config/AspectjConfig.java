package com.github.xzb617.cappuccino.server.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.xzb617.cappuccino.server.aoplog.LoggerAspectj;
import com.github.xzb617.cappuccino.server.aoplog.LoggerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * AOP 相关配置
 */
@Configuration
public class AspectjConfig {

    @Resource
    private LoggerService loggerService;
    @Resource
    private ObjectMapper objectMapper;

    @Bean
    public LoggerAspectj loggerAspectj() {
        return new LoggerAspectj(this.loggerService, this.objectMapper);
    }

}

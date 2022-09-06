package com.client.demo.controller;

import com.client.demo.properties.AppProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ConfigurableEnvironment configurableEnvironment;
    @Value("${application.name}")
    private String applicationName;
    @Autowired
    private AppProperties appProperties;

    @GetMapping("/key/{key}")
    public ResponseEntity getValue(@PathVariable("key") String key) {
        Object value = this.redisTemplate.opsForValue().get(key);
        return ResponseEntity.status(HttpStatus.OK).body(value);
    }

    @GetMapping("/name")
    public ResponseEntity getName() {
        return ResponseEntity.status(HttpStatus.OK).body(this.applicationName);
    }

    @GetMapping("/version")
    public ResponseEntity getVersion() {
        return ResponseEntity.status(HttpStatus.OK).body(this.appProperties.getVersion());
    }

}

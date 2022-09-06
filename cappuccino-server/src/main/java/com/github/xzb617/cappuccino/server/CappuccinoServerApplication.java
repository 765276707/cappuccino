package com.github.xzb617.cappuccino.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan(basePackages = {"com.github.xzb617.cappuccino.server.mapper"})
@EnableTransactionManagement
@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class CappuccinoServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CappuccinoServerApplication.class, args);
    }

}

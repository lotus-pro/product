package com.platform.support;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableDiscoveryClient
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class},scanBasePackages = {"com.platform"})
@EnableFeignClients(basePackages = {"com.platform"})
@MapperScan({"com.platform.**.mapper"})
@EnableAsync //开启异步调用
@Slf4j
public class ProductSupportApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductSupportApplication.class, args);
        log.info("==============================product-support启动完成========================================");
    }

}

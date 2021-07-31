package com.platform.admin;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

@Slf4j
@EnableAsync //开启异步调用
@MapperScan({"com.platform.**.mapper"})
@EnableFeignClients(basePackages = {"com.platform"})
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class},scanBasePackages = {"com.platform"})
public class ProductAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductAdminApplication.class, args);
		log.info("==============================product-admin启动完成========================================");
	}

}

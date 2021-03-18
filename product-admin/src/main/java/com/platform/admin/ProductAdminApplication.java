package com.platform.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class},scanBasePackages = {"com.platform"})
public class ProductAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductAdminApplication.class, args);
	}

}

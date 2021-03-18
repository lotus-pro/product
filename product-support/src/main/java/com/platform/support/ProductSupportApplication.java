package com.platform.support;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ProductSupportApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductSupportApplication.class, args);
    }

}

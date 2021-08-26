package com.platform.core.config;

import com.platform.core.interceptor.FeignHeaderInterceptor;
import feign.Logger;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfiguration {

    public FeignConfiguration() {
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public RequestInterceptor feignHeader() {
        return new FeignHeaderInterceptor();
    }

}

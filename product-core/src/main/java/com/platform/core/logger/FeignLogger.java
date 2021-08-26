package com.platform.core.logger;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自定义feign日志信息
 */

public class FeignLogger extends Logger {
    org.slf4j.Logger logger;

    @Override
    protected void log(String configKey, String format, Object... args) {
        this.logger.info(String.format(methodTag(configKey).concat(format), args));
    }

}

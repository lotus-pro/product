package com.platform.core.annotation;

import com.platform.core.config.XxlJobConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({XxlJobConfig.class})
public @interface EnableXxlJob {

}

package com.platform.feign.support;

import com.platform.common.web.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "product-support")
public interface SupportFeignClient {

    @GetMapping("/support/timeOut/{id}")
    ResponseResult timeOut(@PathVariable("id") String id);

}

package com.platform.admin.controller;

import com.platform.common.web.ResponseResult;
import com.platform.feign.support.SupportFeignClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @description:
 * @author: zengzheng
 * @create: 2021-01-22 10:53
 */
@Api(tags = {"admin"})
@RestController
@Slf4j
@RequestMapping("/aaaa")
public class TestController {

    @Resource
    SupportFeignClient supportFeignClient;

    @ApiOperation("测试feign调用超时情况")
    @GetMapping(value = "/feign/timeOut/{id}")
    public ResponseResult adminFeignTimeOut(@PathVariable("id") String id) {
        log.info("接收参数" + id);
        ResponseResult result = supportFeignClient.timeOut(id);
        return result;
    }

    public static void main(String[] args) {
        String pattern = "曾正正在%s";
        String 测试 = String.format(pattern, "测试");
        System.out.println("" + 测试);
    }
}

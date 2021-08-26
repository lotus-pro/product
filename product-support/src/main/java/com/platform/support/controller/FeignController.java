package com.platform.support.controller;

import com.platform.common.web.BaseController;
import com.platform.common.web.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"feign测试"})
@RestController
@Slf4j
@RequestMapping("/support")
public class FeignController extends BaseController {

    @ApiOperation("超时测试")
    @GetMapping("/timeOut/{id}")
    public ResponseResult timeOut(@PathVariable("id") String id){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("调用成功，返回O(∩_∩)O哈哈~");
        return result("调用成功，返回你的参数" + id + "\r\nO(∩_∩)O哈哈~");
    }
}

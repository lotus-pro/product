package com.platform.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Constructor;

/**
 * @description:
 * @author: zengzheng
 * @create: 2021-01-22 10:53
 */
@Api(tags = {"无聊写着玩"})
@RestController
@Slf4j
@RequestMapping("/aaaa")
public class TestController {

    @ApiOperation("无聊写着玩")
    @PostMapping(value = "/test/wan")
    public ResponseEntity testWan(@RequestBody JSONObject params) {
        try {
            Class<?> driverImplClass = Class.forName("com.platform.controller.EmailController");
            Constructor<?> constructor1 = driverImplClass.getConstructor();
//            Constructor constructor = driverImplClass.getConstructor(String.class);
            EmailController ss = (EmailController)constructor1.newInstance();
            ss.sendMessage(null, null);
            return null;
        } catch (Throwable e) {

        }
        return null;
    }

    @ApiOperation("申请休假1111111")
    @PostMapping(value = "/test/kkkk")
    public ResponseEntity addFileInfo111(@RequestParam String id,
                                         @RequestParam(required = false) String app) {
        log.info("申请休假成功" + id + app);
        return null;
    }

    @ApiOperation("申请休假1111111")
    @GetMapping(value = "/test/{id}/{app}")
    public ResponseEntity addFileInfo(@PathVariable String id,
                                      @PathVariable String app) {
        log.info("申请休假成功" + id + app);
        return null;
    }

    public static void main(String[] args) {
        String pattern = "曾正正在%s";
        String 测试 = String.format(pattern, "测试");
        System.out.println("" + 测试);
    }
}

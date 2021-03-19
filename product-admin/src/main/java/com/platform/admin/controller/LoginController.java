package com.platform.admin.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Api(tags = {"登录认证"})
@RestController
@Slf4j
@RequestMapping("/authentication")
public class LoginController {

    @ApiOperation("用户登录")
    @PostMapping("/system/login")
    public void sendMessage(@RequestBody Map<String, Object> params) {
        try {
            log.info("5555555555555555555");
        } catch (Exception e) {
            log.error("" + e);
        }
    }
}

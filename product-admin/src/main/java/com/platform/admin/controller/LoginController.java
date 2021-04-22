package com.platform.admin.controller;

import com.platform.common.web.BaseController;
import com.platform.common.web.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Api(tags = {"登录认证"})
@RestController
@Slf4j
@RequestMapping("/auth")
public class LoginController extends BaseController {

//    @ApiOperation("用户登录")
//    @PostMapping("/system/login")
//    public void sendMessage(@RequestBody Map<String, Object> params) {
//        try {
//            log.info("5555555555555555555");
//        } catch (Exception e) {
//            log.error("" + e);
//        }
//    }

//    @ApiOperation("登录成功")
//    @PostMapping("/system/login/success")
//    public ResponseResult loginSucces(@RequestBody Map<String, Object> params) {
//        log.info("登录成功");
//        return result();
//    }

    @ApiOperation("登录失败")
    @PostMapping("/system/login/failure")
    public ResponseResult loginFail(@RequestBody Map<String, Object> params) {
        log.info("登录失败");
        return resultError("登录失败");
    }
}

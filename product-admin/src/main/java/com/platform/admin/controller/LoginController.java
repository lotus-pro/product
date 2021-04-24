package com.platform.admin.controller;

import com.google.common.collect.Maps;
import com.platform.common.pojo.admin.ProductUser;
import com.platform.common.web.BaseController;
import com.platform.common.web.ResponseResult;
import com.platform.core.userdetail.ProductUserService;
import com.platform.core.util.AuthenticationUtils;
import com.platform.core.util.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/auth")
public class LoginController extends BaseController {

    @Autowired
    ProductUserService productUserService;

    @PostMapping("/system/login")
    public void login(@RequestBody Map<String, Object> params) {
        log.info("");
    }

    @PostMapping("/system/login/success")
    public ResponseResult loginSucces(@RequestBody Map<String, Object> params) {
        String username = (String)params.get("username");
        Map<String, Object> map = Maps.newHashMap();
        ProductUser productUser = productUserService.queryPcmcUser(username);
        map.put("userInfo", productUser);
        map.put("access_token_expire_timestamp", 1800);
        map.put("identity", username);
        String access_token = JWTUtils.sign(map, 1800);
        map.put("access_token", access_token);
        AuthenticationUtils.setUserMap(username, map);
        return result(map);
    }

    @PostMapping("/system/login/failure")
    public ResponseResult loginFail(@RequestBody Map<String, Object> params) {
        log.info("登录失败");
        return resultError("登录失败");
    }

    @PostMapping("/system/loginOut")
    public ResponseResult loginOut(@RequestBody Map<String, Object> params) {
        String username = (String)params.get("username");
        AuthenticationUtils.removeUserMap(username);
        return result("product.info.00006");
    }

}

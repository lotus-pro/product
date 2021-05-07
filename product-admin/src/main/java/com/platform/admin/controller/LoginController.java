package com.platform.admin.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.common.collect.Maps;
import com.platform.common.cache.Cache;
import com.platform.common.pojo.admin.ProductUser;
import com.platform.common.util.IpUtils;
import com.platform.common.web.BaseController;
import com.platform.common.web.ResponseResult;
import com.platform.core.userdetail.ProductUserService;
import com.platform.core.util.AuthenticationUtils;
import com.platform.core.util.JWTUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Api(tags = {"登录处理类"})
@RestController
@Slf4j
@RequestMapping("/auth")
public class LoginController extends BaseController {

    @Autowired
    ProductUserService productUserService;
    @Autowired
    DefaultKaptcha defaultKaptcha;
    @Autowired
    Cache cache;

    @ApiOperation("验证码获取")
    @GetMapping("/system/kaptcha")
    public ResponseResult login(HttpServletRequest request) {
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        String base64Img = null;
        try {
            // 生产验证码字符串并保存到redis中
            String createText = defaultKaptcha.createText();
            // 使用生成的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
            BufferedImage challenge = defaultKaptcha.createImage(createText);
            ImageIO.write(challenge, "jpg", jpegOutputStream);
            BASE64Encoder encoder = new BASE64Encoder();
            String str = "data:image/jpeg;base64,";
            base64Img = str + encoder.encode(jpegOutputStream.toByteArray());
            String ipAddr = IpUtils.getIpAddr(request).replaceAll("\\.", "");
            cache.set("captcha" + ipAddr, createText, 60, TimeUnit.SECONDS);
        } catch (IOException e) {
            return resultError("验证码生成失败");
        }
        return result(base64Img);
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

    @PostMapping("/system/loginOut")
    public ResponseResult loginOut(@RequestBody Map<String, Object> params) {
        String username = (String)params.get("username");
        AuthenticationUtils.removeUserMap(username);
        return result("product.info.00006");
    }

}

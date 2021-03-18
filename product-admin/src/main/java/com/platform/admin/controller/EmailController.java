package com.platform.admin.controller;

import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Description
 *
 * @author ex-zzh
 * @create 2020/7/20 23:39
 **/
@Api(tags = {"Email邮件"})
@RestController
@Slf4j
@RequestMapping("/email")
public class EmailController{

    @ApiOperation("Email邮件发送")
    @PostMapping("/message/send")
    public void sendMessage(@RequestBody List<String> path){
        try {
            log.info("5555555555555555555");
        } catch (Exception e) {
            log.error("" + e);
        }
    }
}

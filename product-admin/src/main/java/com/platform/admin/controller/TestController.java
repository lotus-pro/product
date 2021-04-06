package com.platform.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.platform.common.fegin.SupportFeginClient;
import com.platform.common.pojo.support.T1MqInfo;
import com.platform.common.util.IDGenerate;
import com.platform.common.web.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    SupportFeginClient supportFeginClient;

    @ApiOperation("无聊写着玩")
    @PostMapping(value = "/test/wan")
    public ResponseEntity testWan(@RequestBody JSONObject params) {
        try {
            Class<?> driverImplClass = Class.forName("com.platform.admin.controller.EmailController");
            Constructor<?> constructor1 = driverImplClass.getConstructor();
//            Constructor constructor = driverImplClass.getConstructor(String.class);
            EmailController ss = (EmailController)constructor1.newInstance();
            ss.sendMessage(null);
            return null;
        } catch (Throwable e) {

        }
        return null;
    }

    @RequestMapping(value = "/",produces = "text/plain;charset=UTF-8")
    String index(){
        return "Hello Spring Boot!";
    }

    @ApiOperation("新增mq消息")
    @PostMapping(value = "/test/addMessage")
    public ResponseResult addMessage(@RequestBody String aa) {
        String uuid = IDGenerate.getUUIDString();
        T1MqInfo t1MqInfo = new T1MqInfo(uuid, "product-admin", "product-admin,product-support",
                "topic111", "tags111", "测试消费失败后，其中一个consumer继续消费，成功的则不消费");
        supportFeginClient.addMessage(t1MqInfo);
        return null;
    }

    @ApiOperation("新增mq事务消息")
    @PostMapping(value = "/trans/addMessage")
    public ResponseResult transMqMessage(@RequestBody String aa) {
        String uuid = IDGenerate.getUUIDString();
        T1MqInfo t1MqInfo = new T1MqInfo(uuid, "product-admin", "product-admin",
                "topicTrans", "tagsTrans", "小周周  想你了,在干嘛");
        supportFeginClient.transmessage(t1MqInfo);
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

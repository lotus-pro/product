package com.platform.support.controller;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.platform.common.web.BaseController;
import com.platform.common.web.ResponseResult;
import com.platform.support.entity.MsgLog;
import com.platform.support.service.MsgLogService;
import com.platform.support.util.Mail;
import com.platform.support.util.RabbitConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"feign测试"})
@RestController
@Slf4j
@RequestMapping("/support")
public class FeignController extends BaseController {

    @Autowired
    MsgLogService msgLogService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @ApiOperation("超时测试")
    @GetMapping("/timeOut")
    public ResponseResult timeOut(){
        String msgId = RandomUtil.randomString(32);
        Mail mail = new Mail();
        mail.setMsgId(msgId);
        mail.setContent("看看我的消息");
        mail.setReceiver("zengzheng@sunline.cn");
        mail.setSubject("测试");
        MsgLog msgLog = new MsgLog(msgId, JSON.toJSONString(mail), RabbitConfig.EMAIL_EXCHANGE, RabbitConfig.EMAIL_TOPIC_RETRY);
        msgLogService.insert(msgLog);// 消息入库
        sendMsg(mail);
        return result("调用成功，返回你的参数" + "\r\nO(∩_∩)O哈哈~");
    }

    public void sendMsg(Mail mail) {
        CorrelationData correlationData = new CorrelationData(mail.getMsgId());
        rabbitTemplate.convertAndSend(RabbitConfig.EMAIL_EXCHANGE, "email.topic.queue", JSON.toJSONString(mail), correlationData);// 发送消息
    }

}

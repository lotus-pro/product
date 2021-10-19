//package com.platform.support.util.mq;
//
//import com.platform.support.service.MsgLogService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.rabbit.connection.CorrelationData;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//
//@Slf4j
//@Component
//public class ConfirmCallBackConfig implements RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnCallback {
//
//    @Autowired
//    private RabbitTemplate rabbitTemplate;
//    @Autowired
//    MsgLogService msgLogService;
//
//    @PostConstruct
//    public void init(){
//        rabbitTemplate.setConfirmCallback(this);
//        rabbitTemplate.setReturnCallback(this);
//    }
//
//    @Override
//    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
//        if (!ack) {
//            log.warn("《消息生产确认失败》消息唯一标识：{}，失败原因：{}", correlationData.getId(), cause);
//            msgLogService.updateStatus(correlationData.getId(), 1, null);
//            return;
//        }
//        log.info("《消息生产确认成功》消息唯一标识：{}", correlationData.getId());
//    }
//
//    @Override
//    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
//        System.out.println("消息主体 message : "+message);
//        System.out.println("消息主体 message : "+replyCode);
//        System.out.println("描述："+replyText);
//        System.out.println("消息使用的交换器 exchange : "+exchange);
//        System.out.println("消息使用的路由键 routing : "+routingKey);
//        log.warn("未找到订阅消息的消费者");
//    }
//}

package com.platform.support.controller;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.platform.common.pojo.support.T1MqInfo;
import com.platform.common.util.BeanUtil;
import com.platform.common.web.BaseController;
import com.platform.common.web.ResponseResult;
import com.platform.support.service.CommonService;
import com.platform.support.service.T1MqInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;

@Api(tags = {"mq接口文档"})
@RestController
@Slf4j
@RequestMapping("/mq")
public class MqController extends BaseController {

    @Autowired
    T1MqInfoService t1MqInfoService;
    @Autowired
    RocketMQTemplate rocketMQTemplate;
    @Autowired
    CommonService commonService;

    @ApiOperation("查询话题标签是否被消费")
    @PostMapping("/topic/status")
    public ResponseResult isNormalConsume(@RequestBody T1MqInfo message) {
        String messageId = message.getMessageId();
        String consumer = message.getConsumer();
        Assert.notNull(messageId, "product.sys.support.0001");
        Assert.notNull(consumer, "product.sys.support.0002");

        //查询该消费者是否订阅该话题
        LambdaQueryWrapper<T1MqInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(T1MqInfo::getMessageId, messageId).eq(T1MqInfo::getConsumer, consumer);
        T1MqInfo one = t1MqInfoService.getOne(wrapper);
        Assert.notNull(one, "所在的消费者群未订阅话题");
        String cousumeStatus = one.getCousumeStatus();
        cousumeStatus = StringUtils.isEmpty(cousumeStatus) ? "0" : cousumeStatus;
        return result(cousumeStatus);
    }

    @ApiOperation("新增话题topic/tags标签")
    @PostMapping("/add/message")
    public ResponseResult addMessage(@RequestBody T1MqInfo message) {
        String producer = message.getProducer();
        String consumers = message.getConsumer(); //多个消费者用逗号分割
        String topic = message.getTopic();
        String tags = message.getTags();
        String content = message.getContent();
        String messageId = message.getMessageId();
        try {
            Assert.notNull(messageId, "product.sys.support.0001");
            Assert.notNull(consumers, "product.sys.support.0002");
            Assert.notNull(producer, "product.sys.support.0003");
            Assert.notNull(topic, "product.sys.support.0004");
            Assert.notNull(tags, "product.sys.support.0005");
            message.setConsumer(null);
            message.setCousumeStatus(null);
            rocketMQTemplate.asyncSend(topic.concat(":").concat(tags), MessageBuilder.withPayload(message).build() , new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    String[] split = consumers.split(",");
                    ArrayList<T1MqInfo> mqList = Lists.newArrayList();
                    for (String str : split) {
                        T1MqInfo t1MqInfo = new T1MqInfo();
                        BeanUtil.copyProperties(message, t1MqInfo);
                        t1MqInfo.setConsumer(str);
                        t1MqInfo.setCrtDate(new Date());
                        mqList.add(t1MqInfo);
                    }
                    t1MqInfoService.saveBatch(mqList);
                    log.info("发送成功");
                }

                @Override
                public void onException(Throwable throwable) {
                    log.error("发送失败" + throwable.getMessage());
                }
            });
        } catch (Exception e) {
            return resultError(e.getMessage());
        }
        return result();
    }

    @ApiOperation("更新topic/tags消费状态")
    @PostMapping("/update/message")
    public ResponseResult updTpoicStatus(@RequestBody T1MqInfo message) {
        String messageId = message.getMessageId();
        String cousumeStatus = message.getCousumeStatus();
        String consumer = message.getConsumer();
        try {
            Assert.notNull(messageId, "product.sys.support.0001");
            Assert.notNull(cousumeStatus, "消费状态不允许为空");
            t1MqInfoService.updMqStatus(message);
        } catch (Exception e) {
            log.error(e.getMessage());
            return resultError(e.getMessage());
        }
        return result();
    }

    @ApiOperation("新增事务mq消息")
    @PostMapping("/add/transmessage")
    public ResponseResult addTransMessage(@RequestBody T1MqInfo message) {
        String txProducerGroup = message.getTxProducerGroup();
        String topic = message.getTopic();
        String content = message.getContent();
        String messageId = message.getMessageId();
        try {
            Assert.notNull(messageId, "product.sys.support.0001");
            Assert.notNull(txProducerGroup, "product.sys.support.0003");
            MessageBuilder<String> stringMessageBuilder = MessageBuilder.withPayload(content);
            stringMessageBuilder.setHeader("content", content);
            stringMessageBuilder.setHeader("messageId", messageId);
            Message build = stringMessageBuilder.build();
            // 发送事务消息   且该消息不允许消费    tx_groups_order: 指定版事务消息组
//            TransactionSendResult result = rocketMQTemplate.sendMessageInTransaction("tx_groups_order",
//                    "my_topic", message1, null);
            TransactionSendResult result = rocketMQTemplate.sendMessageInTransaction(txProducerGroup, topic, build, null);
            LocalTransactionState state = result.getLocalTransactionState();
            //新增记录消息
//            t1MqInfoService.save(message);
            if (state.equals(LocalTransactionState.COMMIT_MESSAGE)) {
                //回调处理的事务
                commonService.addUser(3);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return resultError(e.getMessage());
        }
        return result();
    }

}

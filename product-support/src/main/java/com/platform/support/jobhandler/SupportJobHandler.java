package com.platform.support.jobhandler;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.platform.common.enums.TopicConsumerEnum;
import com.platform.common.pojo.support.T1MqInfo;
import com.platform.support.service.T1MqInfoService;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class SupportJobHandler {

    @Autowired
    T1MqInfoService t1MqInfoService;
    @Autowired
    RocketMQTemplate rocketMQTemplate;

    @XxlJob("queryMqJob")
    public void demoJobHandler() throws Exception {
        XxlJobHelper.log(">>>mq消息查询<<<");
        String jobParam = XxlJobHelper.getJobParam();
        LambdaQueryWrapper<T1MqInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(T1MqInfo::getCousumeStatus, TopicConsumerEnum.FAIL.getCode());
        List<T1MqInfo> list = t1MqInfoService.list(wrapper);
        System.out.println("未成功消费的消息" + list.toString());

        list.forEach( t -> {
            String topic = t.getTopic();
            String tags = t.getTags();
            rocketMQTemplate.asyncSend(topic.concat(":").concat(tags), MessageBuilder.withPayload(t).build() , new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    log.info("发送成功");
                }

                @Override
                public void onException(Throwable throwable) {
                    log.error("发送失败" + throwable.getMessage());
                }
            });
        });


        // default success
    }
}

package com.platform.support.linister;

import com.platform.common.enums.TopicConsumerEnum;
import com.platform.common.fegin.SupportFeginClient;
import com.platform.common.pojo.support.T1MqInfo;
import com.platform.common.web.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RocketMQMessageListener(topic = "topic111",selectorExpression = "tags111", consumerGroup = "${spring.application.name}")
public class TestLinster1 implements RocketMQListener<T1MqInfo> {

    @Autowired
    SupportFeginClient supportFeginClient;

    @Override
    public void onMessage(T1MqInfo mqInfo) {
        log.info("第二个消费者接收消息" + mqInfo.toString());
        mqInfo.setConsumer("product-support");
        ResponseResult result = supportFeginClient.isNormalConsume(mqInfo);
        Object data = result.getData();
        if (data.equals(TopicConsumerEnum.SUCCESS.getCode())) {
            log.info("已经消费过，不再消费");
            return;
        } else if (data.equals(TopicConsumerEnum.FAIL.getCode())) {
            log.info("继续消费");

        }
    }
}

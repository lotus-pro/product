package com.platform.support.linister;

import com.platform.common.enums.TopicConsumerEnum;
import com.platform.common.pojo.support.T1MqInfo;
import com.platform.common.web.ResponseResult;
import com.platform.support.controller.MqController;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
//@Component
//@RocketMQMessageListener(topic = "topic111",selectorExpression = "tags111", consumerGroup = "${spring.application.name}")
public class TestLinster1 implements RocketMQListener<T1MqInfo> {

    @Autowired
    MqController mqController;

    @Override
    public void onMessage(T1MqInfo mqInfo) {
        log.info("第二个消费者接收消息" + mqInfo.toString());
        mqInfo.setConsumer("product-support");
        ResponseResult result = mqController.isNormalConsume(mqInfo);
        Object data = result.getData();
        if (data.equals(TopicConsumerEnum.SUCCESS.getCode())) {
            log.info("第二个已经消费过，不再消费");
            return;
        } else if (data.equals(TopicConsumerEnum.FAIL.getCode()) || data.equals(TopicConsumerEnum.NO_SPEND.getCode())) {
            log.info("第二个未消费的状态为{}，开始消费",data);

            //假设消费失败
            mqInfo.setCousumeStatus(TopicConsumerEnum.SUCCESS.getCode());
            mqController.updTpoicStatus(mqInfo);
        }
    }
}

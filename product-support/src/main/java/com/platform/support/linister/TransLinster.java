package com.platform.support.linister;

import com.platform.common.enums.TopicConsumerEnum;
import com.platform.common.exception.CommonException;
import com.platform.common.pojo.support.T1MqInfo;
import com.platform.common.web.ResponseResult;
import com.platform.support.controller.MqController;
import com.platform.support.service.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
//@Component
//@RocketMQTransactionListener(txProducerGroup = "tx_groups_order")
public class TransLinster implements RocketMQLocalTransactionListener {

    @Autowired
    CommonService commonService;
    @Autowired
    MqController mqController;

    @Override
    @Transactional
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        String messageId = message.getHeaders().get("messageId", String.class);
        String content = message.getHeaders().get("content", String.class);
        log.info("请求messageId[{}],请求参数:{}", messageId, content);
        T1MqInfo t1MqInfo = new T1MqInfo(messageId);
        int aa = 0;
        try {
            ResponseResult result = mqController.isNormalConsume(t1MqInfo);
            Object data = result.getData();
            if (data.equals(TopicConsumerEnum.SUCCESS.getCode())) {
                log.info("已经消费过，不再消费");
            } else if (data.equals(TopicConsumerEnum.FAIL.getCode())) {
                log.info("没有消费");
                aa = commonService.addCompany(3);
//                t1MqInfo.setCousumeStatus(TopicConsumerEnum.SUCCESS.getCode());
//                mqController.updTpoicStatus(t1MqInfo);
            }
        } catch (Exception e) {
//            t1MqInfo.setCousumeStatus(TopicConsumerEnum.SUCCESS.getCode());
//            mqController.updTpoicStatus(t1MqInfo);
            throw new CommonException(e.getMessage());
        }
        if (aa > 0) {
            return RocketMQLocalTransactionState.COMMIT;
        } else {
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        return null;
    }
}

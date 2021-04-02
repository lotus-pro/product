package com.platform.admin.linster;

import com.platform.admin.service.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RocketMQTransactionListener(txProducerGroup = "tx_groups_order")
public class TransLinster implements RocketMQLocalTransactionListener {


    @Autowired
    CommonService commonService;

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        log.info(message.getHeaders() + "");
        log.info(message.getPayload() + "");
        MessageHeaders headers = message.getHeaders();
        Object transactionId = headers.get("rocketmq_TRANSACTION_ID");
        int aa = commonService.addCompany(transactionId.toString(), 2.5);
        System.out.println("修改条数" + aa);
        return RocketMQLocalTransactionState.COMMIT;
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        RocketMQLocalTransactionState state;
        MessageHeaders headers = message.getHeaders();
        Object transactionId = headers.get("rocketmq_TRANSACTION_ID");
        if (transactionId == null) {
            // 如果不存在， 则通知broker删除消息
            return RocketMQLocalTransactionState.ROLLBACK;
        }
        int i = commonService.checkCompany(transactionId.toString());
        if (i == 0) {
            return RocketMQLocalTransactionState.UNKNOWN;
        }
        return RocketMQLocalTransactionState.COMMIT;
    }
}

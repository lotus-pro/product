package com.platform.support.controller;

import com.platform.support.service.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.UUID;

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
//        int aa = commonService.addCompany(UUID, 2.5);
//        System.out.println("修改条数" + aa);
        return RocketMQLocalTransactionState.COMMIT;
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        RocketMQLocalTransactionState state;
        UUID id = message.getHeaders().getId();
        Object payload = message.getPayload();
        //事务id
//        String txNo = accountChangeEvent.getTxNo();
//        int isexistTx = accountInfoDao.isExistTx(txNo);
//        log.info("回查事务，事务号: {} 结果: {}", accountChangeEvent.getTxNo(),isexistTx);
//        if(isexistTx>0){
//            state=  RocketMQLocalTransactionState.COMMIT;
//        }else{
//            state=  RocketMQLocalTransactionState.UNKNOWN;
//        }

        return RocketMQLocalTransactionState.COMMIT;
    }
}

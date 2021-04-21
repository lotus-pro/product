package com.platform.support.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.enums.TopicConsumerEnum;
import com.platform.common.exception.CommonException;
import com.platform.common.pojo.support.T1MqInfo;
import com.platform.support.mapper.T1MqInfoMapper;
import com.platform.support.service.T1MqInfoService;
import org.springframework.stereotype.Service;

@Service
public class T1MqInfoServiceImpl extends ServiceImpl<T1MqInfoMapper, T1MqInfo> implements T1MqInfoService {

    @Override
    public void updMqStatus(T1MqInfo mqInfo) throws Exception {
        String messageId = mqInfo.getMessageId();
        String consumer = mqInfo.getConsumer();
        String cousumeStatus = mqInfo.getCousumeStatus();
        try {
            LambdaQueryWrapper<T1MqInfo> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(T1MqInfo::getMessageId, messageId).eq(T1MqInfo::getConsumer, consumer);
            T1MqInfo t1MqInfo = baseMapper.selectOne(wrapper);
            //消费成功
            if (cousumeStatus.equals(TopicConsumerEnum.SUCCESS.getCode())) {
                baseMapper.updateMqStatus(mqInfo);
            } else if (cousumeStatus.equals(TopicConsumerEnum.FAIL.getCode())) {//消费失败
                Integer failTimes = t1MqInfo.getFailTimes();
                if (failTimes > 1) {
                    failTimes--;
                    mqInfo.setFailTimes(failTimes);
                    baseMapper.updateMqStatus(mqInfo);
                }
                //三次都消费失败 邮件通知负责人

            }
        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        }
    }

}

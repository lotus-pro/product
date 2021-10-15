package com.platform.support.util;

import com.alibaba.fastjson.JSONObject;
import com.platform.support.entity.MsgLog;
import com.platform.support.service.MsgLogService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class MailConsumer {

    @Autowired
    private MsgLogService msgLogService;
    @Autowired
    private MailUtil mailUtil;
    @Autowired
    RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = RabbitConfig.EMAIL_QUEUE)
    public void sendMail(Message message, Channel channel) throws IOException {
        log.info("1接收到消息了");
        try {
            byte[] body = message.getBody();
            String str = new String(body, "UTF-8");
            str = str.substring(1, str.length() - 1).replace("\\", "");
            Mail mail = JSONObject.parseObject(str, Mail.class);
            String msgId = mail.getMsgId();
            MsgLog msgLog = msgLogService.selectByMsgId(msgId);
            if (null == msgLog || msgLog.getStatus() == 1) {// 消费幂等性
                log.info("重复消费, msgId: {}", msgId);
                return;
            }
            boolean success = mailUtil.send(mail);
            if (success) {
                msgLogService.updateStatus(msgId, 1);
            } else {
                msgLogService.updateStatus(msgId, 2);
                ackInfo(message);
            }
        } catch (Exception e) {
            ackInfo(message);
        } finally {
            /**
             * 无论消费成功还是消费失败,都要手动进行ack,因为即使消费失败了,也已经将消息重新投递到重试队列或者失败队列
             * 如果不进行ack,生产者在超时后会进行消息重发,如果消费者依然不能处理，则会存在死循环
             */
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
    }

    private void ackInfo(Message message) {
        MessageProperties properties = message.getMessageProperties();
        long retryCount = getRetryCount(properties);

        if (retryCount >= 3) {
            /** 重试次数超过3次,则将消息发送到失败队列等待特定消费者处理或者人工处理 */
            try {
                rabbitTemplate.convertAndSend("failExchange","email.fail.myFail", message);
                log.info("消费者消费消息在重试3次后依然失败，将消息发送到faild队列,发送消息:" + new String(message.getBody()));
            } catch (Exception e1) {
                log.error("消息在发送到faild队列的时候报错:" + e1.getMessage() + ",原始消息:"+ new String(message.getBody()));
            }

        } else {

            try {
                /** 重试次数不超过3次,则将消息发送到重试队列等待重新被消费（重试队列延迟超时后信息被发送到相应死信队列重新消费，即延迟消费）*/
                rabbitTemplate.convertAndSend("retryExchange", "email.retry.myRetry", message);
                log.info("消费者消费失败，消息发送到重试队列;" + "原始消息：" + new String(message.getBody()) + ";第" + (retryCount+1) + "次重试");
            } catch (Exception e1) {
                log.error("消息发送到重试队列的时候，异常了:" + e1.getMessage() + ",重新发送消息");
            }
        }
    }

    /**
     * 获取消息被重试的次数
     */
    public long getRetryCount(MessageProperties messageProperties) {
        Long retryCount = 0L;
        if (null != messageProperties) {
            List<Map<String, ?>> deaths = messageProperties.getXDeathHeader();
            if(deaths != null && deaths.size()>0){
                Map<String, Object> death = (Map<String, Object>)deaths.get(0);
                retryCount = (Long) death.get("count");
            }
        }
        return retryCount;
    }
}

package com.platform.support.util.mq;

import com.alibaba.fastjson.JSONObject;
import com.platform.support.entity.MsgLog;
import com.platform.support.service.MsgLogService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public abstract class MqConsumer {

    @Autowired
    private MsgLogService msgLogService;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private AmqpAdmin amqpAdmin;

    private static final String RETRY = "retry_";

    public abstract void acceptMessage(Message message, Channel channel) throws IOException;

    protected void checkConsumeFlag(Message message) throws UnsupportedEncodingException {
        JSONObject data = convertDataToMap(message);
        String msgId = data.getString("msgId");
        log.info("<<<<<mq监听到消息：{}>>>>>", data);
        MsgLog msgLog = msgLogService.selectByMsgId(msgId);
        if (null == msgLog || msgLog.getStatus() == 2) {// 消费幂等性
            log.info("重复消费, msgId: {}", msgId);
            return;
        }
    }

    /**
     * @param message
     * @param status
     * @throws UnsupportedEncodingException
     */
    protected void updateMsgStatus(Message message, Integer status, Integer retryCount) throws UnsupportedEncodingException {
        JSONObject data = convertDataToMap(message);
        String msgId = data.getString("msgId");
        msgLogService.updateStatus(msgId, status, retryCount);
    }

    protected void ackInfo(Message message) throws UnsupportedEncodingException {
        MessageProperties properties = message.getMessageProperties();
        String receivedExchange = properties.getReceivedExchange();
        String receivedRoutingKey = properties.getReceivedRoutingKey();
        JSONObject data = convertDataToMap(message);
        String msgId = data.getString("msgId");
        String consumerQueue = properties.getConsumerQueue();
        long retryCount = getRetryCount(properties);
        if (retryCount >= 3) {
            /** 重试次数超过3次,则将消息发送到失败队列等待特定消费者处理或者人工处理 */
            try {
                CorrelationData correlationData = new CorrelationData(msgId);
                rabbitTemplate.convertAndSend("failExchange", "fail.".concat(receivedRoutingKey), message, correlationData);
                log.info("消费者消费消息在重试3次后依然失败，将消息发送到fail队列,发送消息:" + new String(message.getBody()));
            } catch (Exception e1) {
                log.error("消息在发送到fail队列的时候报错:" + e1.getMessage() + ",原始消息:" + new String(message.getBody()));
            }
        } else {
            try {
                //声明重试交换机
                String exchange = RETRY.concat(receivedExchange);
                String retryRouting = receivedRoutingKey.concat("retry");
                TopicExchange retryExchange = (TopicExchange) ExchangeBuilder.topicExchange(exchange).durable(true).build();
                amqpAdmin.declareExchange(retryExchange);

                //声明重试队列
                Map<String, Object> args = new ConcurrentHashMap<>(3);
                // 将消息重新投递到emailExchange中
                args.put(RabbitConfig.X_DEAD_LETTER_EXCHANGE, receivedExchange);
                args.put(RabbitConfig.X_DEAD_LETTER_ROUTING_KEY, receivedRoutingKey);
                //消息在队列中延迟30s后超时，消息会重新投递到x-dead-letter-exchage对应的队列中，routingkey为自己指定
                args.put(RabbitConfig.X_MESSAGE_TTL, 30 * 1000);
                Queue queue = QueueBuilder.durable(RETRY.concat(consumerQueue)).withArguments(args).build();
                amqpAdmin.declareQueue(queue);

                //交换机和队列绑定，并绑定Routing key
                Binding binding = BindingBuilder.bind(queue).to(retryExchange).with(retryRouting.concat(".*"));
                amqpAdmin.declareBinding(binding);

                /** 重试次数不超过3次,则将消息发送到重试队列等待重新被消费（重试队列延迟超时后信息被发送到相应死信队列重新消费，即延迟消费）*/
                CorrelationData correlationData = new CorrelationData(msgId);
                rabbitTemplate.convertAndSend(exchange, retryRouting.concat(".myRetry"), message, correlationData);
                updateMsgStatus(message, 3, new Long(retryCount + 1).intValue());
                log.info("消费者消费失败，消息发送到重试队列;" + "原始消息：" + new String(message.getBody()) + ";第" + (retryCount + 1) + "次重试");
            } catch (Exception e1) {
                log.error("消息发送到重试队列的时候，异常了:" + e1.getMessage() + ",重新发送消息");
            }
        }
    }

    /**
     * 获取消息被重试的次数
     */
    protected long getRetryCount(MessageProperties messageProperties) {
        Long retryCount = 0L;
        if (null != messageProperties) {
            List<Map<String, ?>> deaths = messageProperties.getXDeathHeader();
            if (deaths != null && deaths.size() > 0) {
                Map<String, Object> death = (Map<String, Object>) deaths.get(0);
                retryCount = (Long) death.get("count");
            }
        }
        return retryCount;
    }

    /**
     * 数据转换
     *
     * @param message
     * @return
     * @throws UnsupportedEncodingException
     */
    protected JSONObject convertDataToMap(Message message) throws UnsupportedEncodingException {
        byte[] body = message.getBody();
        String str = new String(body, "UTF-8");
        str = str.substring(1, str.length() - 1).replace("\\", "");
        return JSONObject.parseObject(str);
    }

}

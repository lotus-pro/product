package com.platform.support.util.mq;

import com.alibaba.fastjson.JSONObject;
import com.platform.common.util.BeanUtil;
import com.platform.support.util.MailDto;
import com.platform.support.util.MailUtil;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
@Slf4j
public class MailConsumer extends MqConsumer {

    @Autowired
    private MailUtil mailUtil;
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Override
//    @RabbitListener(bindings = {@QueueBinding(value = @Queue(value = RabbitConfig.EMAIL_QUEUE,autoDelete = "false",durable = "true"),
//            exchange = @Exchange(value = RabbitConfig.EMAIL_EXCHANGE,type = ExchangeTypes.TOPIC),key = "email1.topic.support")})
    public void acceptMessage(Message message, Channel channel) throws IOException {
        try {
            //1.校验是否消费
            checkConsumeFlag(message);

            //2.自己的业务逻辑
            Random rn = new Random();
            int answer = rn.nextInt(10) + 1;
            List<Integer> integers = Arrays.asList(1, 3, 5, 7, 9);
            if (integers.contains(answer)) {
                System.out.println("answer=" + answer + ",演示错误");
                int i = 1 / 0;
            }
            //3.更新状态
            updateMsgStatus(message, 2, null);
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

    @RabbitListener(bindings = {@QueueBinding(value = @Queue(value = RabbitConfig.EMAIL_QUEUE,autoDelete = "false",durable = "true"),
            exchange = @Exchange(value = RabbitConfig.EMAIL_EXCHANGE),key = RabbitConfig.ROUTING_EMAIL_DIRECT)})
    public void senMailConsumer(Message message, Channel channel) throws IOException {
        try {
            //1.校验是否消费
            checkConsumeFlag(message);

            //2.自己的业务逻辑
            JSONObject jsonObject = convertDataToMap(message);
            MailDto mailDto = BeanUtil.mapToBean(jsonObject, MailDto.class);
            mailUtil.sendMail(mailDto);

        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            /**
             * 无论消费成功还是消费失败,都要手动进行ack,因为即使消费失败了,也已经将消息重新投递到重试队列或者失败队列
             * 如果不进行ack,生产者在超时后会进行消息重发,如果消费者依然不能处理，则会存在死循环
             */
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
    }

}

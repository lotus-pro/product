//package com.platform.support.util.mq;
//
//import com.platform.support.service.MsgLogService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.core.*;
//import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//@Configuration
//@Slf4j
//public class RabbitConfig {
//
//    @Autowired
//    private CachingConnectionFactory connectionFactory;
//
//    @Autowired
//    private MsgLogService msgLogService;
//
//    @Bean
//    public Jackson2JsonMessageConverter converter() {
//        return new Jackson2JsonMessageConverter();
//    }
//
//    /**
//     * 死信队列 交换机标识符
//     */
//    public static final String X_DEAD_LETTER_EXCHANGE = "x-dead-letter-exchange";
//    /**
//     * 死信队列交换机routing-key标识符
//     */
//    public static final String X_DEAD_LETTER_ROUTING_KEY = "x-dead-letter-routing-key";
//    /**
//     * 死信队列消息的超时时间枚举
//     */
//    public static final String X_MESSAGE_TTL = "x-message-ttl";
//    //邮件 交换机
//    public static final String EMAIL_EXCHANGE = "emailExchange";
//    //失败重试交换机 通过路由不同的key
//    public static final String RETRY_EXCHANGE = "retryExchange";
//    public static final String FAIL_EXCHANGE = "failExchange";
//    //邮件 队列
//    public static final String EMAIL_QUEUE = "emailQueue";
//    public static final String RETRY_QUEUE = "retryQueue";
//    public static final String FAIL_QUEUE = "failQueue";
//    //邮件 路由
//    public static final String EMAIL_TOPIC_RETRY = "email.topic.retry";
//    public static final String ROUTING_EMAIL_TOPIC = "email.topic";
//
//    @Bean
//    public TopicExchange emailExchange() {
//        return (TopicExchange) ExchangeBuilder.topicExchange(EMAIL_EXCHANGE).durable(true).build();
//    }
////    @Bean
////    public TopicExchange retryExchange() {
////        return (TopicExchange) ExchangeBuilder.topicExchange(RETRY_EXCHANGE).durable(true).build();
////    }
//
//    @Bean
//    public TopicExchange failExchange() {
//        return (TopicExchange) ExchangeBuilder.topicExchange(FAIL_EXCHANGE).durable(true).build();
//    }
//
//        @Bean
//    public Queue emailQueue() {
//        return new Queue(EMAIL_QUEUE);
//    }
////    @Bean
////    public Queue retryQueue() {
////        Map<String, Object> args = new ConcurrentHashMap<>(3);
////        // 将消息重新投递到emailExchange中
////        args.put(X_DEAD_LETTER_EXCHANGE, EMAIL_EXCHANGE);
////        args.put(X_DEAD_LETTER_ROUTING_KEY, EMAIL_TOPIC_RETRY);
////        //消息在队列中延迟30s后超时，消息会重新投递到x-dead-letter-exchage对应的队列中，routingkey为自己指定
////        args.put(X_MESSAGE_TTL, 30 * 1000);
////        return QueueBuilder.durable(RETRY_QUEUE).withArguments(args).build();
////    }
//
//    @Bean
//    public Queue failQueue() {
//        return QueueBuilder.durable(FAIL_QUEUE).build();
//    }
//
//        @Bean
//    public Binding topicQueueBinding() {
//        return BindingBuilder.bind(emailQueue()).to(emailExchange()).with("email.topic.*");
//    }
////    @Bean
////    public Binding retryDirectBinding(@Qualifier("retryQueue") Queue queue,
////                                      @Qualifier("retryExchange") TopicExchange exchange) {
////        return BindingBuilder.bind(queue).to(exchange).with("email.retry.*");
////    }
//
//    @Bean
//    public Binding failDirectBinding(@Qualifier("failQueue") Queue queue,
//                                     @Qualifier("failExchange") TopicExchange exchange) {
//        return BindingBuilder.bind(queue).to(exchange).with("fail.#");
//    }
//
//    /**
//     * 指定时间发送  例如订单半个小时   自动解锁
//     *
//     * @return
//     */
////    @Bean
////    public Queue testQueue() {
////        Map<String, Object> args = new ConcurrentHashMap<>(3);
////        // 将消息重新投递到emailExchange中
////        args.put(X_DEAD_LETTER_EXCHANGE, EMAIL_EXCHANGE);
////        args.put(X_DEAD_LETTER_ROUTING_KEY, EMAIL_TOPIC_RETRY);
////        //消息在队列中延迟30s后超时，消息会重新投递到x-dead-letter-exchage对应的队列中，routingkey为自己指定
////        LocalDateTime now = LocalDateTime.now();
////        LocalDateTime feature = now.plusMinutes(5);
////        long l1 = feature.toInstant(ZoneOffset.of("+8")).toEpochMilli();
////        long l2 = now.toInstant(ZoneOffset.of("+8")).toEpochMilli();
////        args.put(X_MESSAGE_TTL, l1 - l2);
////        return QueueBuilder.durable(RETRY_QUEUE).withArguments(args).build();
////    }
//
//}

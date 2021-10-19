package com.platform.support.entity;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class MqReq{

    /**
     * 交换机名称
     */
    private String exchangeName;

    /**
     * 路由
     */
    private String routingKey;

    /**
     * 发送的具体信息
     */
    private Map<String, Object> body;

    /**
     * 生产者
     */
    private String producer;

    /**
     * 消费者
     */
    private List[] consumer;

}

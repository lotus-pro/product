package com.platform.common.pojo.support;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
@Data
public class T1MqInfo implements Serializable {

    private static final long serialVersionUID = -3720609154723564752L;
    /**
     * 消息id
     */
    private String messageId;

    /**
     * 生产者 当为普通监听时候，不允许为空
     */
    private String producer;

    /**
     * 消费者 当为普通监听时候，不允许为空
     */
    private String consumer;

    /**
     * 事务生产组，当为事务监听时，不允许为空
     */
    private String txProducerGroup;

    /**
     * topic
     */
    private String topic;

    /**
     * 标签
     */
    private String tags;

    /**
     * 内容
     */
    private String content;

    /**
     * 重试次数
     */
    private Integer failTimes;

    /**
     * 消费状态 1代表消费成功  0代表消费失败
     */
    private String cousumeStatus;

    /**
     * 消费时间
     */
    private Date cousumeDate;

    /**
     * 创建时间
     */
    private Date crtDate;

    public T1MqInfo() {
    }
    public T1MqInfo(String messageId) {
        this.messageId = messageId;
    }
    public T1MqInfo(String messageId, String producer, String consumer, String topic, String tags, String content) {
        this.messageId = messageId;
        this.producer = producer;
        this.consumer = consumer;
        this.topic = topic;
        this.tags = tags;
        this.content = content;
    }
}
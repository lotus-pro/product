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
     * 生产者
     */
    private String producer;

    /**
     * 生产者
     */
    private String consumer;

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

    public T1MqInfo(String messageId, String producer, String consumer, String topic, String tags, String content) {
        this.messageId = messageId;
        this.producer = producer;
        this.consumer = consumer;
        this.topic = topic;
        this.tags = tags;
        this.content = content;
    }
}
package com.platform.common.pojo.support;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class T1PubMessage implements Serializable {
    private static final long serialVersionUID = 7087283108595623782L;
    /**
     * 消息编码
     */
    @TableId
    private String messageNo;

    /**
     * 发送人
     */
    private String sender;

    /**
     * 发送人昵称
     */
    private String nickName;

    /**
     * 接收人 多个接收人用;隔开
     */
    private String receiver;

    /**
     * 抄送人 多个抄送人用;隔开
     */
    private String carbonCopy;

    /**
     * 密送人 多个密送人用;隔开
     */
    private String darkCopy;

    /**
     * 主题
     */
    private String subject;

    /**
     * 消息类型  1：邮件  2：短信
     */
    private String messageType;

    /**
     * 内容类型  1：html 2：text 默认为html
     */
    private String contentType;

    /**
     * 发送状态  1：成功 2：失败 3：未发送
     */
    private String sendStatus;

    /**
     * 附件编码
     */
    private String attachNo;

    /**
     * 内容
     */
    private String content;
}

package com.platform.support.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailDto {

    /**
     * 邮件接收者
     */
    private String[] receiver;

    /**
     * 邮件主题
     */
    private String subject;

    /**
     * 邮件内容
     */
    private String content;

    /**
     * 抄送
     */
    private String[] carbonCopy;

    /**
     * 密送
     */
    private String[] blindCarbonCopy;

    /**
     * 邮件附件
     */
    private String[] attachments;

    /**
     * 邮件类型  文本类型  html类型
     */
    private String mailType;

    public MailDto(String[] receiver, String subject, String content) {
        this.receiver = receiver;
        this.subject = subject;
        this.content = content;
    }

    public MailDto(String[] receiver, String subject, String content, String[] carbonCopy, String[] attachments) {
        this.receiver = receiver;
        this.subject = subject;
        this.content = content;
        this.carbonCopy = carbonCopy;
        this.attachments = attachments;
    }

    public MailDto(String[] receiver, String subject, String content, String[] carbonCopy, String[] blindCarbonCopy, String[] attachments) {
        this.receiver = receiver;
        this.subject = subject;
        this.content = content;
        this.carbonCopy = carbonCopy;
        this.blindCarbonCopy = blindCarbonCopy;
        this.attachments = attachments;
    }
}

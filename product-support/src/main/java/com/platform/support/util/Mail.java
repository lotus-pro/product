package com.platform.support.util;

import lombok.Data;

@Data
public class Mail {
    private String receiver;

    private String subject;

    private String content;

    private String msgId;
}

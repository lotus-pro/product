package com.platform.exception;

import com.platform.util.I18NUtils;

/**
 * @description: 通用异常
 * @author: zengzheng
 * @create: 2021-01-21 16:25
 */
public class CommonException extends RuntimeException {

    public CommonException(String msg, Object... args) {
        super(I18NUtils.getMessage(msg, args));
    }

    public CommonException(String msg, Exception e) {
        super(I18NUtils.getMessage(msg, new Object[0]), e);
    }

    public CommonException(Exception e) {
        super(e);
    }
}

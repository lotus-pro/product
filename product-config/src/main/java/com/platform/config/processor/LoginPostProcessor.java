package com.platform.config.processor;

import com.platform.common.enums.LoginTypeEnum;

import javax.servlet.ServletRequest;

public interface LoginPostProcessor {
    LoginTypeEnum getLoginTypeEnum();

    String obtainUsername(ServletRequest request);

    String obtainPassword(ServletRequest request);
}

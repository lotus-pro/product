package com.platform.core.processor;

import com.platform.common.enums.LoginTypeEnum;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;

@Component
public class FormLoginPostProcessor implements LoginPostProcessor {
    public FormLoginPostProcessor() {
    }

    public LoginTypeEnum getLoginTypeEnum() {
        return LoginTypeEnum.FORM;
    }

    public String obtainUsername(ServletRequest request) {
        return request.getParameter("username");
    }

    public String obtainPassword(ServletRequest request) {
        return request.getParameter("password");
    }
}

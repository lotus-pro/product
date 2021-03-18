package com.platform.config.processor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.platform.common.enums.LoginTypeEnum;
import com.platform.config.wrapper.ParameterRequestWrapper;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;

@Component
public class JsonLoginPostProcessor implements LoginPostProcessor {
    private static ThreadLocal<String> passwordThreadLocal = new ThreadLocal();

    public JsonLoginPostProcessor() {
    }

    public LoginTypeEnum getLoginTypeEnum() {
        return LoginTypeEnum.JSON;
    }

    public String obtainUsername(ServletRequest request) {
        ParameterRequestWrapper requestWrapper = (ParameterRequestWrapper) request;
        String body = requestWrapper.getBody();
        JSONObject jsonObject = JSON.parseObject(body);
        passwordThreadLocal.set(jsonObject.getString("password"));
        return jsonObject.getString("username");
    }

    public String obtainPassword(ServletRequest request) {
        String s = (String) passwordThreadLocal.get();
        passwordThreadLocal.remove();
        return s;
    }
}

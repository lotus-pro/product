package com.platform.core.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * fegin请求拦截器参数，自定义
 */
public class FeignHeaderInterceptor implements RequestInterceptor {

    public FeignHeaderInterceptor() {
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (null != attributes) {
            HttpServletRequest request = attributes.getRequest();
            Enumeration<String> headerNames = request.getHeaderNames();
            if (headerNames != null) {
                while(headerNames.hasMoreElements()) {
                    String name = (String)headerNames.nextElement();
                    String values = request.getHeader(name);
                    requestTemplate.header(name, new String[]{values});
                }
            }

        }
    }
}

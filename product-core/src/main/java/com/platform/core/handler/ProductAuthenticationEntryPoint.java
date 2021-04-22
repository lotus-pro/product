package com.platform.core.handler;

import com.platform.common.enums.StatusEnum;
import com.platform.common.util.ResponseUtil;
import com.platform.common.web.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * 用来解决匿名用户访问无权限资源时的异常
 */
public class ProductAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {
    private static final Logger log = LoggerFactory.getLogger(ProductAuthenticationEntryPoint.class);
    private static final long serialVersionUID = 8764825654985640038L;

    public ProductAuthenticationEntryPoint() {
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        Object errorObject = request.getAttribute("pcmc-filter-exception");
        if (null != errorObject) {
            //自定义异常  暂时无用
            if (errorObject instanceof Exception) {
                Exception exception = (Exception)errorObject;
                log.error("pcmc-filter-exception error", exception);
                ResponseUtil.response(response, new ResponseResult(StatusEnum.FAIL.getCode(), exception.getMessage()));
            }
        } else {
            log.error("spring security error", authException);
            ResponseUtil.response(response, new ResponseResult(String.valueOf(401), "product.error.00004"));
        }
    }
}

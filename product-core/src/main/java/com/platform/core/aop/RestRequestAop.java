package com.platform.core.aop;

import com.platform.common.util.RequestUtil;
import com.platform.common.web.ResponseResult;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class RestRequestAop {
    private static final Logger log = LoggerFactory.getLogger(RestRequestAop.class);
    private AntPathMatcher antPathMatcher = new AntPathMatcher("/");

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        HttpServletRequest request = RequestUtil.getRequest();
        Object result = null;

        try {
            result = proceedingJoinPoint.proceed();
            return result;
        } catch (Throwable var5) {
            if (this.isRestRequest(request.getRequestURI())) {
                log.error("rest aop catch exception", var5);
                //远程接口调用异常  暂时不处理
                return ResponseResult.error(var5.getMessage(), new Object[0]);
//                return RPCResult.error(var5.getMessage(), new Object[0]);
            } else {
                throw var5;
            }
        }
    }

    private boolean isRestRequest(String uri) {
        return this.antPathMatcher.match("/rest/**", uri);
    }
}

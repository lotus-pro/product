package com.platform.core.aop;

import cn.hutool.json.JSONUtil;
import com.google.common.collect.Maps;
import com.platform.common.web.ResponseResult;
import javassist.*;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

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
        HttpServletRequest request = this.getRequest();
        this.printMethodParams(proceedingJoinPoint, request);
        Object result = null;

        try {
            result = proceedingJoinPoint.proceed();
            log.debug("<<<<<<<<<<<<<<<<请求结束<<<<<<<<<<<<<<<<");
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

    private void printMethodParams(ProceedingJoinPoint proceedingJoinPoint, HttpServletRequest request) throws ClassNotFoundException {
        Class<?> target = proceedingJoinPoint.getTarget().getClass();
        String className = target.getName();
        String methodName = proceedingJoinPoint.getSignature().getName();
        log.debug("<<<<<<<<<<<<<<<<请求开始<<<<<<<<<<<<<<<<");
        log.debug("Request Info: type:{},method: {},url: {}", new Object[]{request.getMethod(), methodName, request.getRequestURI()});
        String[] paramsArgsNames = this.getFieldsName(className, methodName);
        Object[] paramsArgsValue = proceedingJoinPoint.getArgs();
        if (!ArrayUtils.isEmpty(paramsArgsNames)) {
            Map<String, Object> paraMap = Maps.newHashMap();

            for(int i = 0; i < paramsArgsNames.length; ++i) {
                String name = paramsArgsNames[i];
                if (!"request".equals(name) && !"response".equals(name)) {
                    Object value = paramsArgsValue[i];
                    paraMap.put(name, value);
                }
            }

            log.debug("Request Param: {}", JSONUtil.toJsonStr(paraMap));
        }
    }

    private String[] getFieldsName(String className, String methodName) throws ClassNotFoundException {
        Class<?> clazz = Class.forName(className);
        ClassPool pool = ClassPool.getDefault();
        pool.insertClassPath(new ClassClassPath(clazz));
        CtMethod ctMethod = null;

        try {
            ctMethod = pool.get(clazz.getName()).getDeclaredMethod(methodName);
        } catch (NotFoundException var11) {
            ;
        }

        if (null == ctMethod) {
            return new String[0];
        } else {
            CodeAttribute codeAttribute = ctMethod.getMethodInfo().getCodeAttribute();
            LocalVariableAttribute attr = (LocalVariableAttribute)codeAttribute.getAttribute("LocalVariableTable");
            if (Objects.isNull(attr)) {
                return new String[0];
            } else {
                String[] paramsArgsName = new String[0];

                try {
                    paramsArgsName = new String[ctMethod.getParameterTypes().length];
                } catch (NotFoundException var10) {
                    ;
                }

                for(int i = 0; i < paramsArgsName.length; ++i) {
                    paramsArgsName[i] = attr.variableName(i + (Modifier.isStatic(ctMethod.getModifiers()) ? 0 : 1));
                }

                return paramsArgsName;
            }
        }
    }

    private HttpServletRequest getRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes attributes = (ServletRequestAttributes)requestAttributes;
        return ((ServletRequestAttributes) Objects.requireNonNull(attributes)).getRequest();
    }

    private static class ParamPrintProperty {
        public static final String REQUEST_INFO = "Request Info: type:{},method:{},url:{}";
        public static final String REQUEST_PARAM = "Request Param: {}";
        public static final String JSON_RESULT = "Response Result:";
        public static final String REQUEST = "request";
        public static final String RESPONSE = "response";

        private ParamPrintProperty() {
        }
    }
}

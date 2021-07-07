package com.platform.core.handler;

import com.platform.common.exception.CommonException;
import com.platform.common.util.RequestUtil;
import com.platform.common.web.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.MultipartProperties;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @Autowired
    private MultipartProperties multipartProperties;

    public GlobalExceptionHandler() {
    }

    @ExceptionHandler({Exception.class})
    public Object handleException(Exception e) {
        if (e instanceof HttpRequestMethodNotSupportedException) {
            HttpServletRequest request = RequestUtil.getRequest();
            if (null != request) {
                log.error("异常URI：" + request.getRequestURI() + ";Method：" + request.getMethod(), e);
            }
        } else {
            log.error("Exception异常", e);
        }

        return ResponseResult.error("product.error.00006", new Object[0]);
    }

//    @ExceptionHandler({RestException.class})
//    public Object handleRestException(RestException e) {
//        log.error("RestException异常", e);
//        return RPCResult.error(e.getMessage(), new Object[0]);
//    }

    @ExceptionHandler({CommonException.class})
    public Object handleCommonException(CommonException ex) {
        log.error("通用异常信息", ex);
        return ResponseResult.error(ex.getMessage(), new Object[0]);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public Object handleMethodArgumentNotValidException(Exception exception) {
        log.error("参数校验异常", exception);
        BindingResult bindResult = null;
        if (exception instanceof BindException) {
            bindResult = ((BindException)exception).getBindingResult();
        } else if (exception instanceof MethodArgumentNotValidException) {
            bindResult = ((MethodArgumentNotValidException)exception).getBindingResult();
        }

        String msg = null;
        if (bindResult != null && bindResult.hasErrors()) {
            msg = (String)bindResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(";", "", ""));
        }

        return ResponseResult.error(msg, new Object[0]);
    }

    @ExceptionHandler({MaxUploadSizeExceededException.class})
    public Object handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex) {
        String maxMega = this.multipartProperties.getMaxFileSize().toMegabytes() + "MB";
        log.error("上传或导入文件超出设置的最大值", ex);
        return ResponseResult.error("jraf.error.0017", new Object[]{maxMega});
    }
}

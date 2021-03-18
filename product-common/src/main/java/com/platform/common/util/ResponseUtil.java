package com.platform.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.platform.common.enums.StatusEnum;
import com.platform.common.web.ResponseResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class ResponseUtil {
    private ResponseUtil() {
    }

    public static void response(HttpServletResponse response, Object obj) throws IOException {
        response(response, 200, obj);
    }

    public static void response(HttpServletResponse response, int status, Object obj) throws IOException {
        if (!response.isCommitted()) {
            response.setStatus(status);
            response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
            response.setContentType("application/json");
            ObjectMapper objectMapper = new ObjectMapper();
            String resBody = objectMapper.writeValueAsString(obj);
            PrintWriter printWriter = response.getWriter();
            printWriter.print(resBody);
            printWriter.flush();
            printWriter.close();
        }
    }

    public static void filterResponse(HttpServletRequest request, HttpServletResponse response, String statusCode, String messageCode) throws IOException {
        response(response, 200, new ResponseResult(statusCode, I18NUtils.getMessage(messageCode, RequestUtil.getLocale(request), new Object[0])));
    }

    public static void filterResponseResultError(HttpServletRequest request, HttpServletResponse response, int httpStatus, String messageCode) throws IOException {
        if (0 == httpStatus) {
            httpStatus = 200;
        }

        response(response, httpStatus, new ResponseResult(StatusEnum.FAIL.getCode(), I18NUtils.getMessage(messageCode, RequestUtil.getLocale(request), new Object[0])));
    }

    public static void filterResponseResultError(HttpServletRequest request, HttpServletResponse response, String messageCode) throws IOException {
        filterResponseResultError(request, response, 200, messageCode);
    }

    public static void filterResponseRestError(HttpServletRequest request, HttpServletResponse response, String statusCode, String messageCode) throws IOException {
        filterResponse(request, response, statusCode, messageCode);
    }
}

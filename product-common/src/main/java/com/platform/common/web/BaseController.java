package com.platform.common.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.common.util.RequestUtil;

import java.util.List;
import java.util.Map;

public class BaseController {
    public BaseController() {
    }

    protected <T> IPage<T> getIPage(Map<String, Object> requestParam) {
        return RequestUtil.getIPage(requestParam);
    }

    protected <T> ResponseResult<T> result() {
        return ResponseResult.success();
    }

    protected <T> ResponseResult<T> result(T obj) {
        return ResponseResult.success(obj);
    }

    protected <T> ResponseResult<T> result(IPage<?> iPage) {
        return ResponseResult.success(iPage);
    }

    protected <T> ResponseResult<T> resultError(String msg, Object... args) {
        return ResponseResult.error(msg, args);
    }

    protected <T> ResponseResult<T> resultError() {
        return this.resultError("");
    }

    protected <T> List<T> getSelectedDataObjectList(Map<String, Object> param, Class<T> clazz) {
        return this.getObjectList(param, "selectedData", clazz);
    }

    protected List<String> getStringList(Map<String, Object> param, String propName) {
        return this.getObjectList(param, propName, String.class);
    }

    protected <T> List<T> getObjectList(Map<String, Object> param, String propName, Class<T> clazz) {
        return RequestUtil.getObjectList(param, propName, clazz);
    }
}

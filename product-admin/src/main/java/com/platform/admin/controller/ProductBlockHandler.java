package com.platform.admin.controller;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.platform.common.web.ResponseResult;

public class ProductBlockHandler {

    public static ResponseResult handleBusyException(BlockException e){
        return ResponseResult.error("当前系统繁忙，请稍后再试");
    }
}

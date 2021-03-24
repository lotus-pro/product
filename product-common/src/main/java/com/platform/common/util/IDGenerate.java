package com.platform.common.util;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;

import java.util.UUID;

public class IDGenerate {

    private IDGenerate() {
    }

    public static String getUUIDString() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static Long getNextSequence() {
        return IdWorker.getId();
    }
}

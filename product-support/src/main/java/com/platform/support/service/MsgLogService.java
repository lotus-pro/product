package com.platform.support.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.support.entity.MsgLog;

import java.util.List;

/**
 * 消息投递日志(MsgLog)表服务接口
 *
 * @author zengzheng
 * @since 2021-10-13 14:18:32
 */
public interface MsgLogService extends IService<MsgLog> {

    void updateStatus(String msgId, Integer status);

    void insert(MsgLog msgLog);

    MsgLog selectByMsgId(String msgId);
}
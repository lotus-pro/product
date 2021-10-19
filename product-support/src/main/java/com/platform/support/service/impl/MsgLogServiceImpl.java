package com.platform.support.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.support.entity.MsgLog;
import com.platform.support.mapper.MsgLogMapper;
import com.platform.support.service.MsgLogService;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 消息投递日志(MsgLog)表服务实现类
 *
 * @author zengzheng
 * @since 2021-10-13 14:18:33
 */
@Service
public class MsgLogServiceImpl extends ServiceImpl<MsgLogMapper, MsgLog> implements MsgLogService {

    @Override
    public void updateStatus(String msgId, Integer status, Integer retryCount) {
        LambdaUpdateWrapper<MsgLog> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(MsgLog::getStatus, status)
                .set(Objects.nonNull(retryCount), MsgLog::getTryCount, retryCount)
                .eq(MsgLog::getMsgId, msgId);
        MsgLog msgLog = new MsgLog(msgId, status, retryCount);
        baseMapper.update(msgLog, wrapper);
    }

    @Override
    public void insert(MsgLog msgLog) {
        baseMapper.insert(msgLog);
    }

    @Override
    public MsgLog selectByMsgId(String msgId) {
        MsgLog msgLog = baseMapper.selectOne(new LambdaQueryWrapper<MsgLog>().eq(MsgLog::getMsgId, msgId));
        return msgLog;
    }

}
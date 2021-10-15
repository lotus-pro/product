package com.platform.support.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;
import com.platform.support.entity.MsgLog;

import java.util.List;

/**
 * 消息投递日志(MsgLog)表数据库访问层
 *
 * @author zengzheng
 * @since 2021-10-13 14:18:33
 */
@Component
public interface MsgLogMapper extends BaseMapper<MsgLog> {

    void saveBatchData(List<MsgLog> list);
}
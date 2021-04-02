package com.platform.support.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.common.entity.dto.admin.User;
import com.platform.common.pojo.support.T1MqInfo;
import org.springframework.stereotype.Component;

@Component
public interface T1MqInfoMapper extends BaseMapper<T1MqInfo> {

    //更新mq的状态
    void updateMqStatus(T1MqInfo mqInfo);

    void addUserInfo(User user);

}

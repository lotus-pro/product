package com.platform.support.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.common.entity.dto.admin.User;
import com.platform.common.pojo.support.T1MqInfo;

public interface T1MqInfoService extends IService<T1MqInfo> {

    //更改消费的topic状态
    void updMqStatus(T1MqInfo mqInfo) throws Exception;

    void addUserInfo(User user);

}

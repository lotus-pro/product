package com.platform.common.fegin;

import com.platform.common.pojo.support.T1MqInfo;
import com.platform.common.web.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "product-support")
public interface SupportFeginClient {

    /**
     * 新增一条mq消息
     * @param t1MqInfo
     * @return
     */
    @PostMapping(value = "/mq/add/message")
    ResponseResult addMessage(@RequestBody T1MqInfo t1MqInfo);

    /**
     * 新增一条mq事务消息
     * @param t1MqInfo
     * @return
     */
    @PostMapping(value = "/mq/add/transmessage")
    ResponseResult transmessage(@RequestBody T1MqInfo t1MqInfo);

    /**
     * 查询mq消费状态
     * @param t1MqInfo
     * @return
     */
    @PostMapping(value = "/mq/consume/status")
    ResponseResult isNormalConsume(@RequestBody T1MqInfo t1MqInfo);

    /**
     * 消息消费成功或失败更新状态
     * @param t1MqInfo
     * @return
     */
    @PostMapping(value = "/mq/update/message")
    ResponseResult updTpoicStatus(@RequestBody T1MqInfo t1MqInfo);
}

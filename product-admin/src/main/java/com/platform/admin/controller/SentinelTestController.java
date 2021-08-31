package com.platform.admin.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.platform.common.web.BaseController;
import com.platform.common.web.ResponseResult;
import com.platform.feign.support.SupportFeignClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @description:
 * @author: zengzheng
 * @create: 2021-01-22 10:53
 */
@Api(tags = {"sentinel测试"})
@RestController
@Slf4j
@RequestMapping("/sentinel")
public class SentinelTestController extends BaseController {

    @Resource
    @Lazy
    SupportFeignClient supportFeignClient;

    @ApiOperation("测试feign调用超时情况")
    @GetMapping(value = "/feign/timeOut")
    public ResponseResult adminFeignTimeOut() {
        ResponseResult result = supportFeignClient.timeOut();
        return result();
    }

    /**
     * 这种方式适用于对系统处理能力确切已知的情况下，比如通过压测确定了系统的准确水位时
     * @param id
     * @return
     */
    @ApiOperation("流控-QPS-直接-失败")
    @GetMapping(value = "/flow/QPS/direct/failure")
    @SentinelResource(value ="sentinel_flow_QPS_direct_failure")
    public ResponseResult flowQpsDirectFailure(@RequestParam("id") String id) {
        return result("您的请求我已经收到" + id);
    }

    /**
     * 当系统长期处于低水位的情况下，当流量突然增加时，直接把系统拉升到高水位可能瞬间把系统压垮。
     * 通过"冷启动"，让通过的流量缓慢增加，在一定时间内逐渐增加到阈值上限，给冷系统一个预热的时间，避免冷系统被压垮
     * 场景：秒杀
     * @return
     */
    @ApiOperation("流控-QPS-直接-预热")
    @GetMapping(value = "/flow/QPS/warmUp")
    @SentinelResource(value ="sentinel_flow_QPS_warmUp")
    public ResponseResult flowQpsWarmUp() {
        return result("您的请求我已经收到");
    }

    /**
     * 匀速排队（RuleConstant.CONTROL_BEHAVIOR_RATE_LIMITER）方式会严格控制请求通过的间隔时间，
     * 也即是让请求以均匀的速度通过，对应的是漏桶算法
     * @return
     */
    @ApiOperation("流控-QPS-直接-排队等待")
    @GetMapping(value = "/flow/QPS/quene")
    @SentinelResource(value ="sentinel_flow_QPS_quene")
    public ResponseResult flowQpsquene() {
        return result("您的请求我已经收到");
    }

    /**
     * 当请求A过来访问该接口，该请求处理的很慢，还没有返回数据；此时请求B也过来访问该接口，
     * 这个时候处理请求B需要额外开启一个线程，请求B则会报错；
     * @return
     */
    @ApiOperation("流控-线程-直接")
    @GetMapping(value = "/flow/thread/direct")
    @SentinelResource(value ="sentinel_flow_thread_direct")
    public ResponseResult flowThreadDirect() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result("您的请求我已经收到");
    }

    /**
     * 当关联资源/testB的QPS阈值超过1时，就限流/testA的Rest的访问地址，当关联资源到资源阈值后限制配置好的资源名；
     * 关联通俗点说就是，当关联的资源达到阀值，就限流自己；
     * 应用场景: 比如支付接口达到阈值,就要限流下订单的接口,防止一直有订单
     * @return
     */
    @ApiOperation("流控-线程-关联")
    @GetMapping(value = "/flow/thread/relation")
    @SentinelResource(value ="sentinel_flow_thread_relation")
    public ResponseResult flowThreadRelation() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result("您的请求我已经收到");
    }

    @SentinelResource(value ="sentinel_flow_QPS_link")
    public String flowQpsRLink() {
        return "您的请求我已经收到";
    }

    @ApiOperation("降级慢调用比例")
    @GetMapping(value = "/slow_request_ratio")
    @SentinelResource(value ="sentinel_slow_request_ratio",fallback = "fallback")
    public ResponseResult slowRequestRatio() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result("您的请求我已经收到");
    }
    public ResponseResult fallback() {
        return resultError("当前访问人数过多，请稍后再试");
    }

}

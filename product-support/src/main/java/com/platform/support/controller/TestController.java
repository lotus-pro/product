package com.platform.support.controller;

import com.platform.common.pojo.admin.Company;
import com.platform.common.pojo.support.T1MqInfo;
import com.platform.common.util.Assert;
import com.platform.common.web.BaseController;
import com.platform.common.web.ResponseResult;
import com.platform.support.service.CommonService;
import com.platform.support.service.MailService;
import com.platform.support.service.T1MqInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Api(tags = {"mq接口文档11"})
@RestController
@Slf4j
@RequestMapping("/mq1")
public class TestController extends BaseController {

    @Autowired
    CommonService commonService;
    @Autowired
    T1MqInfoService t1MqInfoService;
    @Autowired
    MailService mailService;

    @ApiOperation("查询话题标签是否被消费")
    @PostMapping("/topic/status")
    public ResponseResult isNormalConsume(@RequestBody T1MqInfo message) throws Exception {
        Assert.isNull(null, "product.support.error.00001","test");
        int i = commonService.addCompany(5);
        Company company = commonService.queryOne(1);
        Date date = new Date();
        System.out.println("本地系统取时间" + date);
        System.out.println("数据库时间" + company.getCrtDate());
//        mailService.sendMail();
        mailService.sendMail();
        return result();
    }

}

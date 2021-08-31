package com.platform.admin.controller;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.platform.admin.service.SupplierInfoService;
import com.platform.common.cache.Cache;
import com.platform.common.util.Assert;
import com.platform.common.util.IpUtils;
import com.platform.common.util.RequestUtil;
import com.platform.common.web.BaseController;
import com.platform.common.web.ResponseResult;
import com.platform.product.entity.admin.SupplierInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 供应商信息表(SupplierInfo)表控制层
 *
 * @author zengzheng
 * @since 2021-07-27 09:57:59
 */
@Slf4j
@Api(tags = {"供应商"})
@RestController
@RequestMapping("/no-auth")
public class SupplierInfoController extends BaseController {

    @Autowired
    private SupplierInfoService supplierInfoService;
    @Autowired
    Cache cache;
    @Autowired
    DefaultKaptcha defaultKaptcha;

    @ApiOperation("验证码获取")
//    @SentinelResource(value = "no-auth_system_kaptcha", blockHandlerClass = ProductBlockHandler.class, blockHandler = "handleBusyException")
    @SentinelResource(value = "no-auth_system_kaptcha")
    @GetMapping("/system/kaptcha")
    public ResponseResult login() {
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        String base64Img = null;
        try {
            // 生产验证码字符串并保存到redis中
            String createText = defaultKaptcha.createText();
            System.out.println("验证码" + createText);
            // 使用生成的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
            BufferedImage challenge = defaultKaptcha.createImage(createText);
            ImageIO.write(challenge, "jpg", jpegOutputStream);
            BASE64Encoder encoder = new BASE64Encoder();
            String str = "data:image/jpeg;base64,";
            base64Img = str + encoder.encode(jpegOutputStream.toByteArray());
            HttpServletRequest request = RequestUtil.getRequest();
            String ipAddr = IpUtils.getIpAddr(request).replaceAll("\\.", "");
            cache.set("captcha" + ipAddr, createText, 60, TimeUnit.SECONDS);
        } catch (IOException e) {
            return resultError("验证码生成失败");
        }
        return result(base64Img);
    }

    @ApiOperation("供应商分页查询")
    @GetMapping("/supplierInfo/page")
    public ResponseResult queryPage(@RequestParam Map<String, Object> params) {
        IPage<SupplierInfo> page = getIPage(params);
        SupplierInfo supplierInfo = BeanUtil.fillBeanWithMap(params, new SupplierInfo(), false);
        page = supplierInfoService.queryPage(page, supplierInfo);
        return result(page);
    }

    @ApiOperation("供应商新增")
    @PostMapping("/supplierInfo/add")
    public ResponseResult saveDataInfo(@RequestBody SupplierInfo supplierInfo) {
        try {
            Assert.isTrue(supplierInfoService.exist(supplierInfo) > 0, "outSource.error.code.00001");
            Integer suprNo = supplierInfoService.getMaxSupplierNo();
            String supplierNo = null;
            String format = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy"));
            if (null == suprNo || suprNo == 0) {
                supplierNo = "SUP" + format + "000001";
            } else {
                supplierNo = "SUP" + format + StringUtils.leftPad(String.valueOf(suprNo + 1), 6, "0");
            }
            supplierInfo.setSuprNo(supplierNo);
            supplierInfoService.save(supplierInfo);
        } catch (Exception e) {
            return resultError(e.getMessage());
        }
        return result();
    }

    @ApiOperation("供应商修改")
    @PostMapping("/supplierInfo/update")
    public ResponseResult updateDataInfo(@RequestBody SupplierInfo supplierInfo) {
//        UserDetailInfo currentUser = AuthenticationUtils.getCurrentUser();
        Assert.isTrue(supplierInfoService.exist(supplierInfo) > 0, "outSource.error.code.00001");
//        supplierInfo.setLastUpdateUser(currentUser.getUserCode());
        supplierInfoService.updateById(supplierInfo);
        return result();
    }
    
    @ApiOperation("单个查询")
    @SentinelResource
    @PostMapping("/supplierInfo/unique")
    public ResponseResult uniqueOne(@RequestBody SupplierInfo supplierInfo) {
        Integer id = supplierInfo.getId();
        supplierInfo = supplierInfoService.getById(id);
        return result(supplierInfo);
    }

    @ApiOperation("供应商导出")
    @PostMapping("/export")
    public void exportData(@RequestBody Map<String, Object> params, HttpServletResponse response) throws IOException {
//        SupplierInfo supplierInfo = BeanUtil.mapToBean(params, SupplierInfo.class, false);
//        List<SupplierInfo> resList = supplierInfoService.queryList(supplierInfo);
//        ExportParams exportParams = EasyPoiUtil.getExportParams(new ValueSetDictHandler());
//        FileCommonUtil.exportCommonExcel(SupplierInfo.class, resList, exportParams, response);
    }

}
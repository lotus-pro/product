package com.platform.admin.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.admin.service.SupplierInfoService;
import com.platform.common.util.Assert;
import com.platform.common.web.BaseController;
import com.platform.common.web.ResponseResult;
import com.platform.core.entity.UserDetailInfo;
import com.platform.core.util.AuthenticationUtils;
import com.platform.product.entity.SupplierInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * 供应商信息表(SupplierInfo)表控制层
 *
 * @author zengzheng
 * @since 2021-07-27 09:57:59
 */
@Slf4j
@Api(tags = {"自定义"})
@RestController
@RequestMapping("/no-auth")
public class SupplierInfoController extends BaseController {

    @Autowired
    private SupplierInfoService supplierInfoService;

    @ApiOperation("分页查询")
    @GetMapping("/supplierInfo/page")
    public ResponseResult queryPage(@RequestParam Map<String, Object> params) {
        IPage<SupplierInfo> page = getIPage(params);
        SupplierInfo supplierInfo = BeanUtil.fillBeanWithMap(params, new SupplierInfo(), false);
        page = supplierInfoService.queryPage(page, supplierInfo);
        return result(page);
    }

    @ApiOperation("新增")
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
//            supplierInfo.setAddBaseInfo();
            supplierInfo.setSuprNo(supplierNo);
            supplierInfoService.save(supplierInfo);
        } catch (Exception e) {
            return resultError(e.getMessage());
        }
        return result();
    }

    @ApiOperation("修改")
    @PostMapping("/supplierInfo/update")
    public ResponseResult updateDataInfo(@RequestBody SupplierInfo supplierInfo) {
//        UserDetailInfo currentUser = AuthenticationUtils.getCurrentUser();
        Assert.isTrue(supplierInfoService.exist(supplierInfo) > 0, "outSource.error.code.00001");
//        supplierInfo.setLastUpdateUser(currentUser.getUserCode());
        supplierInfoService.updateById(supplierInfo);
        return result();
    }
    
    @ApiOperation("单个查询")
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
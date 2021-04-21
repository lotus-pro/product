package com.platform.support.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.common.util.Assert;
import com.platform.common.util.BeanUtil;
import com.platform.common.web.BaseController;
import com.platform.common.web.ResponseResult;
import com.platform.support.entity.SupplierInfo;
import com.platform.support.service.SupplierInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 供应商信息表(SupplierInfo)表控制层
 *
 * @author zengzheng
 * @since 2021-04-21 13:36:39
 */
@Slf4j
@Api(tags = {"自定义"})
@RestController
@RequestMapping("/supplierInfo")
public class SupplierInfoController extends BaseController {

    @Autowired
    private SupplierInfoService supplierInfoService;

    @ApiOperation("列表分页查询")
    @GetMapping("/page")
    public ResponseResult queryList(@RequestParam Map<String, Object> params) {
        Object pageSize = params.get("pageSize");
        Object currentPage = params.get("currentPage");
        boolean isPage = true;
        if (pageSize == null && currentPage == null) {
            isPage = false;
        }
        IPage<SupplierInfo> page = getIPage(params);
        List<SupplierInfo> resList = new ArrayList<SupplierInfo>();
        SupplierInfo supplierInfo = BeanUtil.mapToBean(params, SupplierInfo.class);
        if (isPage) {
            page = supplierInfoService.queryPage(page, supplierInfo);
            return result(page);
        } else {
            resList = supplierInfoService.queryList(supplierInfo);
            return result(resList);
        }
    }

    @ApiOperation("新增")
    @PostMapping("/add")
    public ResponseResult saveDataInfo(@RequestBody SupplierInfo supplierInfo) {
        Assert.isNull(null, "product.support.error.00003");
//        supplierInfoService.save(supplierInfo);
        return result();
    }

    @ApiOperation("修改")
    @PostMapping("/update")
    public ResponseResult updateDataInfo(@RequestBody SupplierInfo supplierInfo) {
//        supplierInfoService.updateById(supplierInfo);
        try {
            Assert.isNull(null, "product.support.error.00003");
        } catch (Exception e) {
            return resultError(e.getMessage());
        }
        return result();
    }
    
    @ApiOperation("单个查询")
    @PostMapping("/unique")
    public ResponseResult uniqueOne(@RequestBody SupplierInfo supplierInfo) {
        Integer id = supplierInfo.getId();
        supplierInfo = supplierInfoService.getById(id);
        return result(supplierInfo);
    }

    @ApiOperation("删除")
    @DeleteMapping("/delete")
    public ResponseResult deleteDataInfo(@RequestParam String id) {
        supplierInfoService.removeById(id);
        return result();
    }

}
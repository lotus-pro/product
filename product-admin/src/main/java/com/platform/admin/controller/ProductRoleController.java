package com.platform.admin.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.admin.service.ProductRoleService;
import com.platform.common.pojo.admin.ProductRole;
import com.platform.common.web.BaseController;
import com.platform.common.web.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * (ProductRole)表控制层
 *
 * @author zengzheng
 * @since 2021-06-20 09:22:38
 */
@Slf4j
@Api(tags = {"自定义"})
@RestController
@RequestMapping("/productRole")
public class ProductRoleController extends BaseController {

    @Autowired
    private ProductRoleService productRoleService;

    @ApiOperation("分页查询")
    @GetMapping("/page")
    public ResponseResult queryPage(@RequestParam Map<String, Object> params) {
        IPage<ProductRole> page = getIPage(params);
        ProductRole productRole = BeanUtil.mapToBean(params, ProductRole.class, false);
        page = productRoleService.queryPage(page, productRole);
        return result(page);
    }
    
    @ApiOperation("列表查询")
    @GetMapping("/list")
    public ResponseResult queryPage(@RequestParam ProductRole productRole) {
        List<ProductRole> resList = new ArrayList<ProductRole>();
        resList = productRoleService.queryList(productRole);
        return result(resList);
    }

    @ApiOperation("新增")
    @PostMapping("/add")
    public ResponseResult saveDataInfo(@RequestBody ProductRole productRole) {
        productRoleService.save(productRole);
        return result();
    }

    @ApiOperation("修改")
    @PostMapping("/update")
    public ResponseResult updateDataInfo(@RequestBody ProductRole productRole) {
        productRoleService.updateById(productRole);
        return result();
    }
    
    @ApiOperation("单个查询")
    @PostMapping("/unique")
    public ResponseResult uniqueOne(@RequestBody ProductRole productRole) {
        return result(productRole);
    }

    @ApiOperation("删除")
    @DeleteMapping("/delete")
    public ResponseResult deleteDataInfo(@RequestBody Map<String, Object> params) {
        ProductRole productRole = BeanUtil.mapToBean(params, ProductRole.class, false);
        productRoleService.removeById(productRole.getId());
        return result();
    }

}
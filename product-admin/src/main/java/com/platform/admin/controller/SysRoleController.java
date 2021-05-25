package com.platform.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.admin.service.ProductRoleService;
import com.platform.common.util.BeanUtil;
import com.platform.common.web.BaseController;
import com.platform.common.web.ResponseResult;
import com.platform.core.entity.ProductRole;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * (SysRole)表控制层
 *
 * @author zengzheng
 * @since 2021-05-14 10:22:41
 */
@Slf4j
@Api(tags = {"用户角色"})
@RestController
@RequestMapping("/sysRole")
public class SysRoleController extends BaseController {

    @Autowired
    private ProductRoleService sysRoleService;

    @ApiOperation("用户角色分页查询")
    @GetMapping("/page")
    public ResponseResult queryPage(@RequestParam Map<String, Object> params) {
        IPage<ProductRole> page = getIPage(params);
        ProductRole sysRole = BeanUtil.mapToBean(params, ProductRole.class);
        page = sysRoleService.queryPage(page, sysRole);
        return result(page);
    }
    
    @ApiOperation("列表查询")
    @GetMapping("/list")
    public ResponseResult queryPage(@RequestParam ProductRole sysRole) {
        List<ProductRole> resList = new ArrayList<ProductRole>();
        resList = sysRoleService.queryList(sysRole);
        return result(resList);
    }

    @ApiOperation("新增")
    @PostMapping("/add")
    public ResponseResult saveDataInfo(@RequestBody ProductRole sysRole) {
        sysRoleService.save(sysRole);
        return result();
    }

    @ApiOperation("修改")
    @PostMapping("/update")
    public ResponseResult updateDataInfo(@RequestBody ProductRole sysRole) {
        sysRoleService.updateById(sysRole);
        return result();
    }
    
    @ApiOperation("单个查询")
    @PostMapping("/unique")
    public ResponseResult uniqueOne(@RequestBody ProductRole sysRole) {
//        Integer id = sysRole.getId();
//        sysRole = sysRoleService.getById(id);
        return result(sysRole);
    }

    @ApiOperation("删除")
    @DeleteMapping("/delete")
    public ResponseResult deleteDataInfo(@RequestParam String id) {
        sysRoleService.removeById(id);
        return result();
    }

}
package com.platform.admin.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.admin.service.ProductRoleService;
import com.platform.admin.service.ProductUserService;
import com.platform.admin.service.SysRoleMenuService;
import com.platform.admin.service.SysUserRoleService;
import com.platform.common.pojo.admin.ProductRole;
import com.platform.common.pojo.admin.SysMenu;
import com.platform.common.pojo.admin.SysRoleMenu;
import com.platform.common.pojo.admin.SysUserRole;
import com.platform.common.web.BaseController;
import com.platform.common.web.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * (ProductRole)表控制层
 *
 * @author zengzheng
 * @since 2021-06-20 09:22:38
 */
@Slf4j
@Api(tags = {"用户角色类"})
@RestController
@RequestMapping("/productRole")
public class ProductRoleController extends BaseController {

    @Autowired
    private ProductRoleService productRoleService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    @Autowired
    private ProductUserService productUserService;

    @ApiOperation("角色分页查询")
//    @PreAuthorize("hasAuthority('sys:role:list')")
    @GetMapping("/page")
    public ResponseResult queryPage(@RequestParam Map<String, Object> params) {
        IPage<ProductRole> page = getIPage(params);
        ProductRole productRole = BeanUtil.mapToBean(params, ProductRole.class, false);
        page = productRoleService.queryPage(page, productRole);
        return result(page);
    }

    @ApiOperation("新增角色")
    @PostMapping("/save")
//    @PreAuthorize("hasAuthority('sys:role:save')")
    public ResponseResult save(@Validated @RequestBody ProductRole productRole) {
        productRole.setCreateTime(LocalDateTime.now());
        productRoleService.save(productRole);
        return result();
    }

    @ApiOperation("角色删除")
    @PostMapping("/delete")
//    @PreAuthorize("hasAuthority('sys:role:delete')")
    @Transactional
    public ResponseResult info(@RequestBody List<ProductRole> productRoles) {

        List<Long> ids = productRoles.stream().map(ProductRole::getId).collect(Collectors.toList());
        List<String> roles = productRoles.stream().map(ProductRole::getRoleCode).collect(Collectors.toList());
        productRoleService.removeByIds(ids);
        // 删除中间表
        sysUserRoleService.remove(new QueryWrapper<SysUserRole>().in("role_code", roles));
        sysRoleMenuService.remove(new QueryWrapper<SysRoleMenu>().in("role_code", roles));
        // 缓存同步删除 TODO
        roles.forEach(role -> {
            // 更新缓存
            sysRoleMenuService.clearUserAuthorityInfoByRoleId(role);
        });

        return result();
    }

    @ApiOperation("角色单个查询")
//    @PreAuthorize("hasAuthority('sys:role:perm')")
    @PostMapping("/unique")
    public ResponseResult uniqueOne(@RequestBody ProductRole productRole) {
        productRole = productRoleService.getById(productRole.getId());
        return result(productRole);
    }

}
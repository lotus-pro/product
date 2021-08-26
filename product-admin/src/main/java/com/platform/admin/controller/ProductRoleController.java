package com.platform.admin.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.admin.service.ProductRoleService;
import com.platform.admin.service.ProductUserService;
import com.platform.admin.service.SysRoleMenuService;
import com.platform.admin.service.SysUserRoleService;
import com.platform.common.web.BaseController;
import com.platform.common.web.ResponseResult;
import com.platform.product.entity.admin.ProductRole;
import com.platform.product.entity.admin.SysRoleMenu;
import com.platform.product.entity.admin.SysUserRole;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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

    @ApiOperation("修改角色")
    @PostMapping("/update")
//    @PreAuthorize("hasAuthority('sys:role:update')")
    public ResponseResult update(@RequestBody ProductRole productRole) {
        LambdaUpdateWrapper<ProductRole> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(ProductRole::getRoleName, productRole.getRoleName())
                .set(ProductRole::getIsEnabled, productRole.getIsEnabled())
                .eq(ProductRole::getId, productRole.getId());
        productRoleService.update(wrapper);
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

    @ApiOperation("角色树查询")
//    @PreAuthorize("hasAuthority('sys:role:list')")
    @GetMapping("/info/{id}")
    public ResponseResult info(@PathVariable("id") Long id) {

        ProductRole productRole = productRoleService.getById(id);

        // 获取角色相关联的菜单id
        List<SysRoleMenu> roleMenus = sysRoleMenuService.list(new QueryWrapper<SysRoleMenu>().eq("role_code", productRole.getRoleCode()));
        List<Long> menuIds = roleMenus.stream().map(p -> p.getMenuId()).collect(Collectors.toList());

        productRole.setMenuIds(menuIds);
        return result(productRole);
    }

    @ApiOperation("分配权限")
    @Transactional
    @PostMapping("/perm/{roleCode}")
//    @PreAuthorize("hasAuthority('sys:role:perm')")
    public ResponseResult info(@PathVariable("roleCode") String roleCode, @RequestBody Long[] menuIds) {

        List<SysRoleMenu> sysRoleMenus = new ArrayList<>();

        Arrays.stream(menuIds).forEach(menuId -> {
            SysRoleMenu roleMenu = new SysRoleMenu();
            roleMenu.setMenuId(menuId);
            roleMenu.setRoleCode(roleCode);

            sysRoleMenus.add(roleMenu);
        });

        // 先删除原来的记录，再保存新的
        sysRoleMenuService.remove(new QueryWrapper<SysRoleMenu>().eq("role_code", roleCode));
        sysRoleMenuService.saveBatch(sysRoleMenus);

        // 删除缓存
//        productUserService.clearUserAuthorityInfoByRoleId(roleId);

        return result();
    }

}
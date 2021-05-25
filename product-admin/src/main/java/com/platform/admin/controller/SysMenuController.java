package com.platform.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Maps;
import com.platform.admin.entity.SysMenu;
import com.platform.admin.entity.SysUserRole;
import com.platform.admin.service.SysMenuService;
import com.platform.admin.service.ProductRoleService;
import com.platform.admin.service.SysUserRoleService;
import com.platform.common.util.BeanUtil;
import com.platform.common.web.BaseController;
import com.platform.common.web.ResponseResult;
import com.platform.core.util.AuthenticationUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * (SysMenu)表控制层
 *
 * @author zengzheng
 * @since 2021-05-12 16:31:53
 */
@Slf4j
@Api(tags = {"自定义"})
@RestController
@RequestMapping("/sysMenu")
public class SysMenuController extends BaseController {

    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private ProductRoleService sysRoleService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    @ApiOperation("分页查询")
    @GetMapping("/page")
    public ResponseResult queryPage(@RequestParam Map<String, Object> params) {
        IPage<SysMenu> page = getIPage(params);
        SysMenu sysMenu = BeanUtil.mapToBean(params, SysMenu.class);
        page = sysMenuService.queryPage(page, sysMenu);
        return result(page);
    }

    @ApiOperation("菜单列表和权限查询")
    @GetMapping("/nav")
    public ResponseResult routerList() {
        String userCode = AuthenticationUtils.getCurrentUserCode();
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setUserCode(userCode);
        List<SysUserRole> sysUserRoles = sysUserRoleService.queryList(sysUserRole);
        String roleCodes = sysUserRoles.stream().map(r -> "ROLE_" + r.getRoleCode()).collect(Collectors.joining(","));
        String authority = roleCodes.concat(",");
//        ProductRole defaultRole = AuthenticationUtils.getDefaultRole(); //默认角色
//        List<SysMenu> sysMenus = sysMenuService.queryRoleOfMenu(defaultRole.getRoleCode());
        List<SysMenu> sysMenus = sysMenuService.queryRoleOfMenu("admin");
        String menuPerms = "";
        sysMenus.forEach( t -> {
            String menPerms = t.getMenPerms();
            menuPerms.concat(menPerms).concat(",");
        });
        authority.concat(menuPerms);
        String[] authorityInfoArray = StringUtils.tokenizeToStringArray(authority, ",");
        List<SysMenu> navs = sysMenuService.buildTreeMenu(sysMenus);
        HashMap<String, Object> map = Maps.newHashMap();
        map.put("authoritys", authorityInfoArray);
        map.put("nav", navs);
        return result(map);
    }

    @ApiOperation("新增")
    @PostMapping("/add")
    public ResponseResult saveDataInfo(@RequestBody SysMenu sysMenu) {
        sysMenuService.save(sysMenu);
        return result();
    }

    @ApiOperation("修改")
    @PostMapping("/update")
    public ResponseResult updateDataInfo(@RequestBody SysMenu sysMenu) {
        sysMenuService.updateById(sysMenu);
        return result();
    }
    
    @ApiOperation("单个查询")
    @PostMapping("/unique")
    public ResponseResult uniqueOne(@RequestBody SysMenu sysMenu) {
//        sysMenu = sysMenuService.getById(id);
        return result(sysMenu);
    }

    @ApiOperation("删除")
    @DeleteMapping("/delete")
    public ResponseResult deleteDataInfo(@RequestParam String id) {
        sysMenuService.removeById(id);
        return result();
    }

}
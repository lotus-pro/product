package com.platform.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.product.entity.admin.SysMenu;

import java.util.List;

/**
 * (SysMenu)表服务接口
 *
 * @author zengzheng
 * @since 2021-05-12 16:31:53
 */
public interface SysMenuService extends IService<SysMenu> {

    IPage<SysMenu> queryPage(IPage page, SysMenu sysMenu);

    List<SysMenu> queryList(SysMenu sysMenu);

    List<SysMenu> queryRoleOfMenu(String roleCode);

    List<SysMenu> buildTreeMenu(List<SysMenu> menus);

    List<SysMenu> tree();
}
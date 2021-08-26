package com.platform.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.product.entity.admin.SysRoleMenu;

/**
 * (SysRoleMenu)表服务接口
 *
 * @author zengzheng
 * @since 2021-07-07 13:41:27
 */
public interface SysRoleMenuService extends IService<SysRoleMenu> {

    void clearUserAuthorityInfoByRoleId(String roleCode);
}
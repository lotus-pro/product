package com.platform.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.common.pojo.admin.SysUserRole;

import java.util.List;

/**
 * (SysUserRole)表服务接口
 *
 * @author zengzheng
 * @since 2021-07-07 13:39:05
 */
public interface SysUserRoleService extends IService<SysUserRole> {
    
    IPage<SysUserRole> queryPage(IPage page, SysUserRole sysUserRole);

    List<SysUserRole> queryList(SysUserRole sysUserRole);
}
package com.platform.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.admin.entity.SysUserRole;

import java.util.List;

/**
 * (SysUserRole)表服务接口
 *
 * @author zengzheng
 * @since 2021-05-14 10:24:53
 */
public interface SysUserRoleService extends IService<SysUserRole> {
    
        IPage<SysUserRole> queryPage(IPage page, SysUserRole sysUserRole);

        List<SysUserRole> queryList(SysUserRole sysUserRole);
}
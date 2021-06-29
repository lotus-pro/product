package com.platform.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.admin.mapper.SysUserRoleMapper;
import com.platform.common.pojo.admin.SysUserRole;
import com.platform.admin.service.SysUserRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (SysUserRole)表服务实现类
 *
 * @author zengzheng
 * @since 2021-05-14 10:24:53
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {
    
    @Override
    public IPage<SysUserRole> queryPage(IPage page, SysUserRole sysUserRole) {
        LambdaQueryWrapper<SysUserRole> wrapper = new QueryWrapper<SysUserRole>().lambda();
        return baseMapper.selectPage(page, wrapper);
    }

    @Override
    public List<SysUserRole> queryList(SysUserRole sysUserRole) {
        LambdaQueryWrapper<SysUserRole> wrapper = new QueryWrapper<SysUserRole>().lambda();
        wrapper.eq(SysUserRole::getUserCode, sysUserRole.getUserCode());
        return baseMapper.selectList(wrapper);
    }
}
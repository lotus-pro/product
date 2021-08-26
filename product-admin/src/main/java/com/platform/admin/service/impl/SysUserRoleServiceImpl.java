package com.platform.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.admin.mapper.SysUserRoleMapper;
import com.platform.admin.service.SysUserRoleService;
import com.platform.product.entity.admin.SysUserRole;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (SysUserRole)表服务实现类
 *
 * @author zengzheng
 * @since 2021-07-07 13:39:08
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
        return baseMapper.selectList(wrapper);
    }
}
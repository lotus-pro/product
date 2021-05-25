package com.platform.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.admin.mapper.ProductRoleMapper;
import com.platform.admin.service.ProductRoleService;
import com.platform.core.entity.ProductRole;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (SysRole)表服务实现类
 *
 * @author zengzheng
 * @since 2021-05-14 10:22:41
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<ProductRoleMapper, ProductRole> implements ProductRoleService {
    
    @Override
    public IPage<ProductRole> queryPage(IPage page, ProductRole sysRole) {
        LambdaQueryWrapper<ProductRole> wrapper = new QueryWrapper<ProductRole>().lambda();
        return baseMapper.selectPage(page, wrapper);
    }

    @Override
    public List<ProductRole> queryList(ProductRole sysRole) {
        LambdaQueryWrapper<ProductRole> wrapper = new QueryWrapper<ProductRole>().lambda();
        return baseMapper.selectList(wrapper);
    }
}
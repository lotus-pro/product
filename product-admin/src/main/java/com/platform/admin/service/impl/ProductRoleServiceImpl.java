package com.platform.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.admin.mapper.ProductRoleMapper;
import com.platform.admin.service.ProductRoleService;
import com.platform.product.entity.admin.ProductRole;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (ProductRole)表服务实现类
 *
 * @author zengzheng
 * @since 2021-06-20 09:22:38
 */
@Service
public class ProductRoleServiceImpl extends ServiceImpl<ProductRoleMapper, ProductRole> implements ProductRoleService {
    
    @Override
    public IPage<ProductRole> queryPage(IPage page, ProductRole productRole) {
        String roleName = productRole.getRoleName();
        LambdaQueryWrapper<ProductRole> wrapper = new QueryWrapper<ProductRole>().lambda();
        wrapper.like(StringUtils.isNotBlank(roleName),ProductRole::getRoleName, roleName);
        return baseMapper.selectPage(page, wrapper);
    }

    @Override
    public List<ProductRole> queryList(ProductRole productRole) {
        LambdaQueryWrapper<ProductRole> wrapper = new QueryWrapper<ProductRole>().lambda();
        return baseMapper.selectList(wrapper);
    }

    @Override
    public List<ProductRole> listRolesByUserId(String userId) {
        List<ProductRole> sysRoles = this.list(new QueryWrapper<ProductRole>()
                .inSql("role_code", "select role_code from sys_user_role where user_code = '" + userId + "'"));

        return sysRoles;
    }
}
package com.platform.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.admin.mapper.ProductUserMapper;
import com.platform.admin.service.ProductUserService;
import com.platform.product.entity.admin.ProductUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户表(ProductUser)表服务实现类
 *
 * @author zengzheng
 * @since 2021-06-20 09:13:22
 */
@Service
public class ProductUserServiceImpl extends ServiceImpl<ProductUserMapper, ProductUser> implements ProductUserService {
    
    @Override
    public IPage<ProductUser> queryPage(IPage page, ProductUser productUser) {
        String userName = productUser.getUserName();
        LambdaQueryWrapper<ProductUser> wrapper = new QueryWrapper<ProductUser>().lambda();
        wrapper.like(StringUtils.isNotBlank(userName), ProductUser::getUserName, userName);
        return baseMapper.selectPage(page, wrapper);
    }

    @Override
    public List<ProductUser> queryList(ProductUser productUser) {
        LambdaQueryWrapper<ProductUser> wrapper = new QueryWrapper<ProductUser>().lambda();
        return baseMapper.selectList(wrapper);
    }

}
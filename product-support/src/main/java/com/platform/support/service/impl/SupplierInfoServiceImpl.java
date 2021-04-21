package com.platform.support.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.support.mapper.SupplierInfoMapper;
import com.platform.support.entity.SupplierInfo;
import com.platform.support.service.SupplierInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 供应商信息表(SupplierInfo)表服务实现类
 *
 * @author zengzheng
 * @since 2021-04-21 13:36:39
 */
@Service
public class SupplierInfoServiceImpl extends ServiceImpl<SupplierInfoMapper, SupplierInfo> implements SupplierInfoService {
    
    @Override
    public IPage<SupplierInfo> queryPage(IPage page, SupplierInfo supplierInfo) {
        LambdaQueryWrapper<SupplierInfo> wrapper = new QueryWrapper<SupplierInfo>().lambda();
        return baseMapper.selectPage(page, wrapper);
    }

    @Override
    public List<SupplierInfo> queryList(SupplierInfo supplierInfo) {
        LambdaQueryWrapper<SupplierInfo> wrapper = new QueryWrapper<SupplierInfo>().lambda();
        return baseMapper.selectList(wrapper);
    }
}
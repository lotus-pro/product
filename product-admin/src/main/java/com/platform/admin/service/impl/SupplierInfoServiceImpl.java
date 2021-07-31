package com.platform.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.admin.mapper.SupplierInfoMapper;
import com.platform.admin.service.SupplierInfoService;
import com.platform.product.entity.SupplierInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 供应商信息表(SupplierInfo)表服务实现类
 *
 * @author zengzheng
 * @since 2021-07-27 09:57:59
 */
@Service
public class SupplierInfoServiceImpl extends ServiceImpl<SupplierInfoMapper, SupplierInfo> implements SupplierInfoService {

    @Override
    public IPage<SupplierInfo> queryPage(IPage page, SupplierInfo supplierInfo) {
        return baseMapper.queryPage(page, supplierInfo);
    }

    @Override
    public int exist(SupplierInfo supplierInfo) {
        return baseMapper.exist(supplierInfo);
    }

    @Override
    public Integer getMaxSupplierNo() {
        return baseMapper.getMaxSupplierNo();
    }
}
package com.platform.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.product.entity.admin.SupplierInfo;

/**
 * 供应商信息表(SupplierInfo)表服务接口
 *
 * @author zengzheng
 * @since 2021-07-27 09:57:59
 */
public interface SupplierInfoService extends IService<SupplierInfo> {

    IPage<SupplierInfo> queryPage(IPage page, SupplierInfo supplierInfo);

    int exist(SupplierInfo supplierInfo);

    Integer getMaxSupplierNo();
}
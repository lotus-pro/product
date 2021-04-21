package com.platform.support.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.support.entity.SupplierInfo;

import java.util.List;

/**
 * 供应商信息表(SupplierInfo)表服务接口
 *
 * @author zengzheng
 * @since 2021-04-21 13:36:39
 */
public interface SupplierInfoService extends IService<SupplierInfo> {
    
        IPage<SupplierInfo> queryPage(IPage page, SupplierInfo supplierInfo);

        List<SupplierInfo> queryList(SupplierInfo supplierInfo);
}
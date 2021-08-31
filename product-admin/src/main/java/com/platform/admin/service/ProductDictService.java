package com.platform.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.product.entity.admin.ProductDict;

import java.util.List;

/**
 * 系统字典表(ProductDict)表服务接口
 *
 * @author zengzheng
 * @since 2021-08-25 15:54:29
 */
public interface ProductDictService extends IService<ProductDict> {
    
    IPage<ProductDict> queryPage(IPage<ProductDict> page, ProductDict productDict);

    void saveDataInfo(ProductDict dict);

    void updateDataInfo(ProductDict productDict);
}
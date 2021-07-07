package com.platform.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.common.pojo.admin.ProductUser;

import java.util.List;

/**
 * 用户表(ProductUser)表服务接口
 *
 * @author zengzheng
 * @since 2021-06-20 09:13:21
 */
public interface ProductUserService extends IService<ProductUser> {
    
    IPage<ProductUser> queryPage(IPage page, ProductUser productUser);

    List<ProductUser> queryList(ProductUser productUser);

}
package com.platform.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.product.entity.admin.ProductRole;

import java.util.List;

/**
 * (SysRole)表服务接口
 *
 * @author zengzheng
 * @since 2021-05-14 10:22:41
 */
public interface ProductRoleService extends IService<ProductRole> {
    
        IPage<ProductRole> queryPage(IPage page, ProductRole sysRole);

        List<ProductRole> queryList(ProductRole sysRole);

        List<ProductRole> listRolesByUserId(String userId);
}
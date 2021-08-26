package com.platform.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.product.entity.admin.ProductUser;
import org.springframework.stereotype.Component;

/**
 * 用户表(ProductUser)表数据库访问层
 *
 * @author zengzheng
 * @since 2021-06-20 09:13:22
 */
@Component
public interface ProductUserMapper extends BaseMapper<ProductUser> {

}
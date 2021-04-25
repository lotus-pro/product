package com.platform.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.common.pojo.admin.ProductUser;
import org.springframework.stereotype.Component;

/**
 * 用户表(ProductUser)表数据库访问层
 *
 * @author zengzheng
 * @since 2021-04-23 08:33:41
 */
@Component
public interface ProductUserMapper extends BaseMapper<ProductUser> {

}
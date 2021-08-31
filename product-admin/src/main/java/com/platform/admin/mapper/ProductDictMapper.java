package com.platform.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.product.entity.admin.ProductDict;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 系统字典表(ProductDict)表数据库访问层
 *
 * @author zengzheng
 * @since 2021-08-25 15:54:29
 */
@Component
public interface ProductDictMapper extends BaseMapper<ProductDict> {

    List<ProductDict> qryChildren(ProductDict productDict);

}
package com.platform.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.product.entity.admin.SupplierInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * 供应商信息表(SupplierInfo)表数据库访问层
 *
 * @author zengzheng
 * @since 2021-07-27 10:08:44
 */
@Component
public interface SupplierInfoMapper extends BaseMapper<SupplierInfo> {

    int exist(SupplierInfo supplierInfo);

    IPage<SupplierInfo> queryPage(IPage page, @Param("info") SupplierInfo supplierInfo);

    Integer getMaxSupplierNo();
}
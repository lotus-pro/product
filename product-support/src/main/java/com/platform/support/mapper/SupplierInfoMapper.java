package com.platform.support.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;
import com.platform.support.entity.SupplierInfo;

/**
 * 供应商信息表(SupplierInfo)表数据库访问层
 *
 * @author zengzheng
 * @since 2021-04-21 13:36:39
 */
@Component
public interface SupplierInfoMapper extends BaseMapper<SupplierInfo> {

}
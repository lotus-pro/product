package com.platform.common.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @description: 分页工具类
 * @author: zengzheng
 * @create: 2021-01-27 10:46
 */
public class PageUtil {

    public static <T> IPage<T> getIPage(Map<String, Object> requestParam) {
        Object currentPage = requestParam.get("currentPage");
        Object pageSize = requestParam.get("pageSize");
        if (null == currentPage) {
            currentPage = "1";
        }

        if (null == pageSize) {
            pageSize = "10";
        }
        BigDecimal pageNumBigDecimal = new BigDecimal(currentPage.toString());
        BigDecimal pageSizeBigDecimal = new BigDecimal(pageSize.toString());
        return pageNumBigDecimal.compareTo(new BigDecimal(9223372036854775807L)) <= 0 && pageNumBigDecimal.compareTo(new BigDecimal(0)) >= 0 && pageSizeBigDecimal.compareTo(new BigDecimal(9223372036854775807L)) <= 0 && pageSizeBigDecimal.compareTo(new BigDecimal(0)) >= 0 ? new Page(pageNumBigDecimal.longValue(), pageSizeBigDecimal.longValue()) : new Page(0L, 0L);
    }
}

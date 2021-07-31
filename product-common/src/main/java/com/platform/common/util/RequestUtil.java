package com.platform.common.util;

import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.LocaleUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class RequestUtil {
    private static final String I18N_LANGUAGE = "x-edsp-language";

    private RequestUtil() {
    }

    public static <T> List<T> getObjectList(Map<String, Object> param, String propName, Class<T> clazz) {
        Object obj = param.get(propName);
        if (null == obj) {
            return Lists.newArrayList();
        } else {
            JSONArray jsonArray = (JSONArray)obj;
            return jsonArray.toJavaList(clazz);
        }
    }

    public static JSONArray getJSONArray(Map<String, Object> param, String propName) {
        Object obj = param.get(propName);
        return null == obj ? new JSONArray() : (JSONArray)obj;
    }

    public static <T> List<T> getAddObjectList(Map<String, Object> param, Class<T> clazz) {
        return getObjectList(param, "addList", clazz);
    }

    public static <T> List<T> getUpdateObjectList(Map<String, Object> param, Class<T> clazz) {
        return getObjectList(param, "updateList", clazz);
    }

    public static <T> List<T> getDeleteObjectList(Map<String, Object> param, Class<T> clazz) {
        return getObjectList(param, "deleteList", clazz);
    }

    public static List<String> getSelectedTree(Map<String, Object> param) {
        return getObjectList(param, "selectedTree", String.class);
    }

    public static String getStringTrim(Map<String, Object> param, String propName) {
        return getStringTrim(param, propName, (String)null);
    }

    public static String getStringTrim(Map<String, Object> param, String propName, String defaultValue) {
        String value = MapUtil.getStr(param, propName, defaultValue);
        return null == value ? null : value.trim();
    }

    public static HttpServletRequest getRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return null == attributes ? null : attributes.getRequest();
    }

    public static Locale getLocale(HttpServletRequest httpServletRequest) {
        String i18nLanguage = httpServletRequest.getHeader("x-edsp-language");
        Locale locale = Locale.SIMPLIFIED_CHINESE;
        return StringUtils.isNotBlank(i18nLanguage) ? LocaleUtils.toLocale(i18nLanguage) : locale;
    }

    public static <T> IPage<T> getIPage(Map<String, Object> requestParam) {
        Object pageNumberParam = requestParam.get("currentPage");
        Object pageSizeNumParam = requestParam.get("pageSize");
        if (null == pageNumberParam) {
            pageNumberParam = "1";
        }

        if (null == pageSizeNumParam) {
            pageSizeNumParam = "10";
        }

        BigDecimal pageNumBigDecimal = new BigDecimal(pageNumberParam.toString());
        BigDecimal pageSizeBigDecimal = new BigDecimal(pageSizeNumParam.toString());
        return pageNumBigDecimal.compareTo(new BigDecimal(9223372036854775807L)) <= 0 && pageNumBigDecimal.compareTo(new BigDecimal(0)) >= 0 && pageSizeBigDecimal.compareTo(new BigDecimal(9223372036854775807L)) <= 0 && pageSizeBigDecimal.compareTo(new BigDecimal(0)) >= 0 ? new Page(pageNumBigDecimal.longValue(), pageSizeBigDecimal.longValue()) : new Page(0L, 0L);
    }

    public static void stringToLikeString(Map<String, Object> paraMap, String propName) {
        String propStr = MapUtil.getStr(paraMap, propName);
        if (StringUtils.isNotEmpty(propStr)) {
            paraMap.put(propName, "%" + propStr + "%");
        }

    }

    public static Map<String, Object> requestParamMapToMap(HttpServletRequest request) {
        Map<String, String[]> requestParamMap = request.getParameterMap();
        if (null == requestParamMap) {
            return Maps.newHashMap();
        } else {
            Map<String, Object> paraMap = Maps.newHashMap();

            Map.Entry entry;
            String value;
            for(Iterator var3 = requestParamMap.entrySet().iterator(); var3.hasNext(); paraMap.put((String) entry.getKey(), value)) {
                entry = (Map.Entry)var3.next();
                String[] values = (String[])entry.getValue();
                value = null;
                if (ArrayUtils.isNotEmpty(values)) {
                    value = values[0];
                }
            }

            return paraMap;
        }
    }
}

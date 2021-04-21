package com.platform.common.util;

import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BeanUtil {

    //忽略空值
    private static String[] getNullPropertyNames(Object source){
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    /**
     * 将原对象拷贝到目标对象，忽略空值
     * @param src
     * @param target
     */
    public static void copyProperties(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    public static <T> T mapToBean(Map<String, Object> map, Class<T> clazz) {
        try {
            T bean = newInstance(clazz);
            org.apache.commons.beanutils.BeanUtils.populate(bean, map);
            return bean;
        } catch (InvocationTargetException | IllegalAccessException var3) {
            throw ExceptionUtils.mpe("map convert to bean error,", var3, new Object[]{map, clazz.getName()});
        }
    }

    public static <T> T newInstance(Class<T> clazz) {
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            return constructor.newInstance();
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException var2) {
            throw ExceptionUtils.mpe("new instance error,please try add no args constructor", var2, new Object[]{clazz.getName()});
        }
    }
}

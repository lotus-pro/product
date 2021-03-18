package com.platform.common.cache;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: zengzheng
 * @create: 2021-01-21 16:31
 */
@Component("cache")
public interface Cache {
    <V> void set(final String key, final V value);

    <V> void set(final String key, final V value, int expire, TimeUnit timeUnit);

    <V> V get(final String key, final Class<V> clazz);

    <V> List<V> getList(final String key);

    <V> List<V> getList(final String key, final Class<V> clazz);

    <V> void setList(final String key, final List<V> value);

    <V> Set<V> getSet(final String key);

    <V> void setSet(final String key, final Set<V> value);

    void del(final String... keys);

    void delsByKeyPattern(final String keyPattern);

    Boolean exists(final String key);

    Long incr(final String key);

    void incr(final String key, Long num);

    boolean offerBlockingDeque(final String key, final Object value);

    <K, V> Map<K, V> getMap(final String key);

    <K, V> void setMap(final String key, Map<K, V> tMap);

    <K, V> void setMap(final String key, Map<K, V> tMap, long expireTimestamp);
}

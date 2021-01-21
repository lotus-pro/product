package com.platform.cache.redis;

import com.platform.cache.Cache;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: zengzheng
 * @create: 2021-01-21 16:33
 */
public class RedissionCache implements Cache {
    @Autowired(
            required = false
    )
    private RedissonClient redissonClient;

    public RedissionCache() {
    }

    public final <V> void set(final String key, final V value) {
        RBucket<V> rBucket = this.redissonClient.getBucket(key);
        rBucket.set(value);
    }

    public final <V> void set(final String key, final V value, int expire, TimeUnit timeUnit) {
        RBucket<V> rBucket = this.redissonClient.getBucket(key);
        rBucket.set(value, (long)expire, TimeUnit.SECONDS);
    }

    public final <V> V get(final String key, final Class<V> clazz) {
        RBucket<V> rBucket = this.redissonClient.getBucket(key);
        return rBucket.get();
    }

    public final <V> List<V> getList(final String key) {
        RList<V> rList = this.redissonClient.getList(key);
        return rList.readAll();
    }

    public final <V> List<V> getList(final String key, final Class<V> clazz) {
        RList<V> rList = this.redissonClient.getList(key);
        return rList.readAll();
    }

    public final <V> void setList(final String key, final List<V> value) {
        RList<V> rList = this.redissonClient.getList(key);
        rList.clear();
        rList.addAll(value);
    }

    public <V> Set<V> getSet(String key) {
        RSet<V> rSet = this.redissonClient.getSet(key);
        return rSet.readAll();
    }

    public <V> void setSet(String key, Set<V> value) {
        RSet<V> rSet = this.redissonClient.getSet(key);
        rSet.clear();
        rSet.addAll(value);
    }

    public final void del(final String... keys) {
        String[] var2 = keys;
        int var3 = keys.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            String key = var2[var4];
            this.redissonClient.getBucket(key).delete();
        }

    }

    public final void delsByKeyPattern(final String keyPattern) {
        RKeys rKeys = this.redissonClient.getKeys();
        Iterable<String> patternKeys = rKeys.getKeysByPattern("*".concat(keyPattern).concat("*"));
        patternKeys.forEach((key) -> {
            this.redissonClient.getBucket(key).delete();
        });
    }

    public final Boolean exists(final String key) {
        return this.redissonClient.getBucket(key).isExists();
    }

    public final Long incr(final String key) {
        RAtomicLong rAtomicLong = this.redissonClient.getAtomicLong(key);
        return rAtomicLong.incrementAndGet();
    }

    public final void incr(final String key, Long num) {
        RAtomicLong rAtomicLong = this.redissonClient.getAtomicLong(key);
        rAtomicLong.set(num);
    }

    public boolean offerBlockingDeque(String key, Object value) {
        return this.redissonClient.getBlockingDeque(key).offer(value);
    }

    public <K, V> Map<K, V> getMap(String key) {
        RMap<K, V> rMap = this.redissonClient.getMap(key);
        return rMap.readAllMap();
    }

    public <K, V> void setMap(final String key, Map<K, V> tMap) {
        RMap<K, V> rMap = this.redissonClient.getMap(key);
        rMap.clear();
        rMap.putAll(tMap);
    }

    public <K, V> void setMap(final String key, Map<K, V> tMap, long timestamp) {
        RMap<K, V> rMap = this.redissonClient.getMap(key);
        rMap.clear();
        rMap.putAll(tMap);
        rMap.expireAt(timestamp);
    }
}

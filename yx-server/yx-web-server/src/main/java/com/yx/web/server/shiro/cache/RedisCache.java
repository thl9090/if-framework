package com.yx.web.server.shiro.cache;

import cn.hutool.core.collection.CollUtil;
import com.yx.common.core.Constants;
import com.yx.common.redis.util.CacheUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

import java.io.Serializable;
import java.util.*;

/**
 * Shiro缓存之Redis实现类
 *
 * @author TangHuaLiang
 * @date 2018-07-01
 */
@Slf4j
public class RedisCache<K, V> implements Cache<K, V> {

    @Override
    public V get(K key) throws CacheException {
        String cacheKey = getCacheKey(key);
        log.debug("获取缓存，key:{}", cacheKey);
        @SuppressWarnings("unchecked")
        V value = (V) CacheUtil.getCache().get(cacheKey);
        return value;
    }

    @Override
    public V put(K key, V value) throws CacheException {
        String cacheKey = getCacheKey(key);
        log.debug("放入缓存，key:{},value:{}", cacheKey, value);
        CacheUtil.getCache().set(cacheKey, (Serializable) value, 30 * 60);
        return value;
    }

    @Override
    public V remove(K key) throws CacheException {
        String cacheKey = getCacheKey(key);
        log.debug("清除缓存，key:{}", cacheKey);
        V value = (V) get(key);
        CacheUtil.getCache().del(cacheKey);
        return value;
    }

    @Override
    public void clear() throws CacheException {
        log.debug("清空缓存，pattern:{}", getKeyPrefix() + "*");
        CacheUtil.getCache().delAll(getKeyPrefix() + "*");
    }

    @Override
    public int size() {
        return CacheUtil.getCache().getAll(getKeyPrefix() + "*").size();
    }

    @Override
    public Set<K> keys() {
        Set<Object> keys = CacheUtil.getCache().getAll(getKeyPrefix() + "*");
        if (CollUtil.isEmpty(keys)) {
            return Collections.emptySet();
        }
        Set<K> newKeys = new HashSet<K>();
        for (Object key : keys) {
            newKeys.add((K) key);
        }
        return newKeys;
    }

    @Override
    public Collection<V> values() {
        Set<Object> keys = CacheUtil.getCache().getAll(getKeyPrefix() + "*");
        if (CollUtil.isEmpty(keys)) {
            return Collections.emptyList();
        }
        List<V> values = new ArrayList<V>(keys.size());
        for (Object key : keys) {
            V value = get((K) key);
            if (value != null) {
                values.add(value);
            }
        }
        return Collections.unmodifiableList(values);
    }

    /**
     * 获取缓存KEY
     */
    private String getCacheKey(K key) {
        return getKeyPrefix() + key;
    }

    /**
     * 获取缓存key前缀
     */
    private String getKeyPrefix() {
        return Constants.CacheNamespaceEnum.SHIRO.value();
    }
}

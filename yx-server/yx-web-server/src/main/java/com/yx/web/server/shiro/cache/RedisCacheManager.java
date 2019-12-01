package com.yx.web.server.shiro.cache;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Shiro缓存管理器（Redis实现）
 *
 * @author TangHuaLiang
 * @date 2018-07-01
 */
@Slf4j
@Component
@SuppressWarnings("unchecked")
public class RedisCacheManager implements CacheManager {

    private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap();

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        // return redisCache;
        log.debug("获取名称为: " + name + " 的RedisCache实例");
        Cache<K, V> cache = caches.get(name);
        if (cache == null) {
            // create a new cache instance, add it to the cache collection
            caches.put(name, new RedisCache<K, V>());
        }
        return cache;
    }
}

package com.yx.common.redis.manager;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 缓存管理接口
 *
 * @author TangHuaLiang
 * @date 2018-07-01
 */
public interface CacheManager {

    /**
     * 获取存储在 key(键) 中的 value(数据值) ，如果 key 不存在，则返回空
     *
     * @param key
     * @return Object
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    Object get(final String key);

    /**
     * 根据键模式获取所有匹配的缓存
     *
     * @param pattern
     * @return Set<Object>
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    Set<Object> getAll(final String pattern);

    /**
     * 匹配满足条件的key集合
     *
     * @param pattern
     * @return
     */
    Set<String> keys(String pattern);

    /**
     * 放入缓存，并设置有效期
     *
     * @param key
     * @param value
     * @param seconds
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    void set(final String key, final Serializable value, int seconds);

    /**
     * 设置给定 key 的值。如果 key 已经存储其他值， SET 就覆写旧值，且无视类型
     *
     * @param key
     * @param value
     * @return void
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    void set(final String key, final Serializable value);

    /**
     * 检查给定 key 是否存在
     *
     * @param key
     * @return java.lang.Boolean
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    Boolean exists(final String key);

    /**
     * 删除已存在的键。不存在的 key 会被忽略
     *
     * @param key
     * @return void
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    void del(final String key);

    /**
     * 根据键模式删除所有缓存
     *
     * @param pattern
     * @return void
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    void delAll(final String pattern);

    /**
     * 获取指定文件或文件夹的类型
     *
     * @param key
     * @return
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    String type(final String key);

    /**
     * 设置 key 的过期时间
     *
     * @param key
     * @param seconds
     * @return
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    Boolean expire(final String key, final int seconds);

    /**
     * 以 UNIX 时间戳(unix timestamp)格式设置 key 的过期时间
     *
     * @param key
     * @param unixTime
     * @return java.lang.Boolean
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    Boolean expireAt(final String key, final long unixTime);

    /**
     * 以秒为单位返回 key 的剩余过期时间
     *
     * @param key
     * @return java.lang.Long 当 key 不存在时，返回 -2 。 当 key 存在但没有设置剩余生存时间时，返回 -1 。 否则，以秒为单位，返回 key 的剩余生存时间。
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    Long ttl(final String key);

    /**
     * 设置指定 key 的值，并返回 key 的旧值
     *
     * @param key
     * @param value
     * @return java.lang.Object
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    Object getSet(final String key, final Serializable value);

    /**
     * 获取指定名称的分布式锁
     *
     * @param lockName 分布式锁名称
     * @return java.lang.String 如果获取锁成功则返回锁键对应值，否则返回null
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    String lock(String lockName);

    /**
     * 获取指定名称的分布式锁
     *
     * @param lockName    锁名称
     * @param waitTimeOut 等待获取锁的超时时间（秒）
     * @param lockTimeOut 锁的生存时间（秒）
     * @return java.lang.String 如果获取锁成功则返回锁键对应值，否则返回null
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    String lock(String lockName, long waitTimeOut, int lockTimeOut);

    /**
     * 解锁，将判断锁键对应值是否是给定的值，防止误解锁。分布式锁原则之一：每个锁只能被获得锁的客户端删除，或者自动过期释放锁
     *
     * @param lockName  锁名称
     * @param lockValue 锁键对应值
     * @return boolean  解锁成功返回true，否则返回false
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    boolean unlock(String lockName, String lockValue);

    /**
     * 为哈希表中的字段赋值 。 如果哈希表不存在，一个新的哈希表被创建并进行 HSET 操作。 如果字段已经存在于哈希表中，旧值将被覆盖
     *
     * @param key
     * @param field
     * @param value
     * @return void
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    void hset(String key, Serializable field, Serializable value);

    /**
     * 为哈希表中的字段赋值
     *
     * @param key
     * @param params
     */
    void hmset(String key, Map<Serializable, Serializable> params);

    /**
     * 获取哈希表中指定字段的值
     *
     * @param key
     * @param field
     * @return java.lang.Object
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    Object hget(String key, Serializable field);

    /**
     * 删除哈希表 key 中的一个或多个指定字段,不存在的字段将被忽略
     *
     * @param key
     * @param field
     * @return void
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    void hdel(String key, Serializable field);

    /**
     * 在指定的 key 不存在时,为 key 设置指定的值
     *
     * @param key
     * @param value
     * @return boolean
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    boolean setnx(String key, Serializable value);

    /**
     * 将 key 中储存的数字值增一
     *
     * @param key
     * @return java.lang.Long
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    Long incr(String key);

    /**
     * 用指定的字符串覆盖给定 key 所储存的字符串值,覆盖的位置从偏移量 offset 开始
     *
     * @param key
     * @param offset
     * @param value
     * @return void
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    void setrange(String key, long offset, String value);

    /**
     * 获取存储在键的字符串值的子字符串
     *
     * @param key
     * @param startOffset
     * @param endOffset
     * @return java.lang.String
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    String getrange(String key, long startOffset, long endOffset);

    /**
     * 放入集合缓存一个值
     *
     * @param key
     * @param value
     * @return void
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    void sadd(String key, Serializable value);

    /**
     * 获取key对应集合的所有元素
     *
     * @param key
     * @return Set
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    Set<?> sall(String key);

    /**
     * 验证set集合中的值是否删除
     *
     * @param key
     * @param value
     * @return boolean
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    boolean sdel(String key, Serializable value);

    /**
     * 向list的左边添加项
     *
     * @param key
     * @param value
     * @return
     */
    Long lpush(String key, Serializable value);

    /**
     * 获取list的数据
     *
     * @param key
     * @param startIndex
     * @param endIndex
     * @return
     */
    List<Serializable> lrange(String key, Long startIndex, Long endIndex);

    /**
     * 向list的右边添加项
     *
     * @param key
     * @param value
     * @return
     */
    Long rpush(String key, Serializable value);

    /**
     * 获取list长度
     *
     * @param key
     * @return
     */
    Long llen(String key);

    /**
     * 在list固定位置放入新数据
     *
     * @param key
     * @param index
     * @param value
     */
    void lset(String key, Long index, Serializable value);

    Long lrem(String key, Long count, Serializable value);

    Serializable lindex(String key, Long index);

    /**
     * 存放有序的set集合
     *
     * @param key
     * @param index
     * @param value
     */
    void zadd(String key, double index, String value);

    /**
     * 获取升序的集合
     *
     * @param key
     * @param startIndex
     * @param endIndex
     * @return
     */
    Set<String> zrange(String key, long startIndex, long endIndex);

    /**
     * 获取降序的集合
     *
     * @param key
     * @param startIndex
     * @param endIndex
     * @return
     */
    Set<String> zrevrange(String key, long startIndex, long endIndex);

    /**
     * 获取有序集合的数量
     *
     * @param key
     * @return
     */
    long zcard(String key);

    /**
     * 根据key移除值
     *
     * @param key
     * @param values
     */
    void zrem(String key, String... values);

    /**
     * 获取值在集合的index
     *
     * @param key
     * @param value
     * @return
     */
    double zscore(String key, String value);

}

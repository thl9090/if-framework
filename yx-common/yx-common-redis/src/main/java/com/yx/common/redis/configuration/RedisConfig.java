package com.yx.common.redis.configuration;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yx.common.core.Constants;
import com.yx.common.core.base.BaseModel;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.*;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author TangHuaLiang
 * @description: Redis缓存配置类
 * @date 2018-07-01
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    /**
     * <p>
     * 自定义KEY生成器，格式： yx:data:[包名 + 类名 + 方法名+ 参数]
     * 如：yx:data:com.yx.common.redis.RedisConfig:queryList_param1_param2
     * </p>
     *
     * @return KeyGenerator
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                CacheConfig cacheConfig = target.getClass().getAnnotation(CacheConfig.class);
                Cacheable cacheable = method.getAnnotation(Cacheable.class);
                CachePut cachePut = method.getAnnotation(CachePut.class);
                CacheEvict cacheEvict = method.getAnnotation(CacheEvict.class);
                String cacheName = "";
                if (cacheable != null) {
                    String[] cacheNames = cacheable.value();
                    if (ArrayUtil.isNotEmpty(cacheNames)) {
                        cacheName = cacheNames[0];
                    }
                } else if (cachePut != null) {
                    String[] cacheNames = cachePut.value();
                    if (ArrayUtil.isNotEmpty(cacheNames)) {
                        cacheName = cacheNames[0];
                    }
                } else if (cacheEvict != null) {
                    String[] cacheNames = cacheEvict.value();
                    if (ArrayUtil.isNotEmpty(cacheNames)) {
                        cacheName = cacheNames[0];
                    }
                }
                if (cacheConfig != null && StrUtil.isBlank(cacheName)) {
                    String[] cacheNames = cacheConfig.cacheNames();
                    if (ArrayUtil.isNotEmpty(cacheNames)) {
                        cacheName = cacheNames[0];
                    }
                }
                if (StrUtil.isBlank(cacheName)) {
                    cacheName = "default";
                }
                String paramStr = getParamStr(params);
                StringBuilder sb = new StringBuilder();
                sb.append(Constants.CacheNamespaceEnum.DATA.value())
                        .append(cacheName).append(":")
                        .append(target.getClass().getName())
                        .append(":").append(method.getName())
                        .append("_").append(paramStr);
                return sb.toString();
            }

            /** 获取参数串（BaseModel取ID），以下划线连线 */
            private String getParamStr(Object[] params) {
                if (ArrayUtil.isEmpty(params)) {
                    return "";
                }
                String paramStr = "";
                for (Object param : params) {
                    //BaseModel类型，取ID
                    if (param instanceof BaseModel) {
                        BaseModel model = (BaseModel) param;
                        paramStr = String.join("_", paramStr, String.valueOf(model.getId()));
                    } else if (ArrayUtil.isArray(param)) {
                        //数组类型，将各元素序列化为字符串
                        Object[] arrs = (Object[]) param;
                        paramStr = Arrays.stream(arrs).map(JSON::toJSONString).collect(Collectors.joining("_"));
                    } else {
                        //其它类型，直接序列化为字符串
                        paramStr = String.join("_", paramStr, JSON.toJSONString(param));
                    }
                }
                return paramStr;
            }
        };
    }


    /**
     * @param redisTemplate
     * @return org.springframework.cache.CacheManager
     * @description: 初始化缓存管理类
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    @SuppressWarnings("rawtypes")
    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate) {
        RedisCacheManager rcm = new RedisCacheManager(redisTemplate);
        return rcm;
    }

    /**
     * @return RedisTemplate
     * @description: RedisTemplate序列化方式设置，并初始化
     * @param: factory
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    @Bean
    public RedisTemplate<String, Serializable> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Serializable> template = new RedisTemplate<String, Serializable>();
        template.setConnectionFactory(factory);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        //KEY 为String方式
        template.setKeySerializer(template.getStringSerializer());
        //VALUE 使用 jackson进行序列化
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

}

package com.yx.common.core.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 分布式锁注解
 *
 * @author TangHuaLiang
 * @date 2018-07-01
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DistributedLock {
    /**
     * Alias for {@link #key}.
     * @see #key
     */
    @AliasFor("key")
    String value() default "";

    /**
     * 分布式锁key值
     * Spring Expression Language (SpEL) expression for computing the key dynamically.
     * <p>Default is {@code ""}, meaning all method parameters are considered as a key
     */
    String key() default "";
}

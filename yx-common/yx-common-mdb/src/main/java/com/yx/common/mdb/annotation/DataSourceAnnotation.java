package com.yx.common.mdb.annotation;

import com.yx.common.core.Constants;

import java.lang.annotation.*;

/**
 * 数据源注解
 *
 * @author TangHuaLiang
 * @date 2018-07-01
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSourceAnnotation {
    Constants.DataSourceEnum value() default Constants.DataSourceEnum.MASTER;
}

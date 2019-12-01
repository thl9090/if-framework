package com.yx.mobile.server.annotation;

import com.yx.common.core.Constants;

import java.lang.annotation.*;

/**
 * 日志注解
 *
 * @author TangHuaLiang
 * @date 2018-07-01
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLogOpt {
    String value();
    String module()  default "";  //模块名称 系统管理-用户管理－列表页面
    String description()  default "";  //描述
    Constants.LogOptEnum operationType() default Constants.LogOptEnum.UNKNOW;//操作类型
}

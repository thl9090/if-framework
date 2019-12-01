package com.yx.mobile.server.druid.filter;

import com.alibaba.druid.support.http.WebStatFilter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

/**
 * druid过滤器
 *
 * @author TangHuaLiang
 * @date 2018-07-01
 */
@WebFilter(filterName="druidWebStatFilter",urlPatterns="/*",
        initParams={
                @WebInitParam(name="exclusions",value="*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*"),//忽略资源
                @WebInitParam(name="sessionStatMaxCount",value = "1000"),
                @WebInitParam(name="sessionStatEnable",value = "true"),//是否开启session统计功能
                @WebInitParam(name="principalSessionName",value = "CURRENT_USER"),//提供给druid知道当前的session的用户，value为用户在session中的sessionName
//                @WebInitParam(name="principalCookieName",value = "cookie.user"), //提供给druid知道当前的session的用户，value为用户在cookie中的name
                @WebInitParam(name="profileEnable",value = "true") //监控单个url调用的sql列表
        }
)
public class DruidStatFilter extends WebStatFilter {

}

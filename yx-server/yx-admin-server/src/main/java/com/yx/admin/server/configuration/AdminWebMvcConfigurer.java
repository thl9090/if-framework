package com.yx.admin.server.configuration;

import com.yx.admin.server.interceptor.SessionControlInterceptor;
import com.yx.common.web.configuration.PlatformMethodArgumentResolver;
import com.yx.common.web.interceptor.AuthorizationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * TangHuaLiang
 */
@Configuration
public class AdminWebMvcConfigurer extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessionControlInterceptor())
                .addPathPatterns("/**");    // 拦截所有请求，通过判断是否有 @Authorization 注解 决定是否需要登录
        super.addInterceptors(registry);
    }

    @Bean
    public SessionControlInterceptor sessionControlInterceptor() {
        return new SessionControlInterceptor();
    }




}


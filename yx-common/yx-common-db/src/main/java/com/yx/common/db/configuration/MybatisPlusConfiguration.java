package com.yx.common.db.configuration;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.plugins.PerformanceInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mybatisplus配置
 *
 * @author TangHuaLiang
 * @date 2018-07-01
 */
@Configuration
public class MybatisPlusConfiguration {

    /**
     * SQL执行效率插件【生产环境可以关闭】
     *
     * @return PerformanceInterceptor
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    @Bean
    public PerformanceInterceptor performanceInterceptor() {
        return new PerformanceInterceptor();
    }

    /**
     * 分页插件
     *
     * @return PaginationInterceptor
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // 开启 PageHelper 的支持
        paginationInterceptor.setLocalPage(true);
        return paginationInterceptor;
    }
}

package com.yx.admin.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

/**
 * 启动类
 *
 * @author TangHuaLiang
 * @date 2018-07-01
 */
@Slf4j
@SpringBootApplication
@ComponentScan(basePackages = {"com.yx.admin.server","com.yx.common.core", "com.yx.common.web", "com.yx.common.dsession", "com.yx.common.log.web", "com.yx.common.redis","com.yx.common.utils","com.yx.common.oss"})
@ServletComponentScan({"com.yx.common.web.filter", "com.yx.admin.server.druid"})
@ImportResource(value = {"classpath:dubbo/consumers.xml"})
public class ServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
        log.info("========== ServerApplication启动成功 ==========");
    }

/*    @Bean
    public FilterRegistrationBean webAppForIndexFilterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setName("webAppForIndexFilter");
        XssFilter xssFilter = new XssFilter();
        registrationBean.setFilter(xssFilter);
        registrationBean.setOrder(1);
        List<String> urlList = new ArrayList<String>();
        urlList.add("/*");
        registrationBean.setUrlPatterns(urlList);
        return registrationBean;
    }*/
}

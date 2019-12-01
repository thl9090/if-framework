package com.yx.sys.rpc.service;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

/**
 * @author TangHuaLiang
 * @description: 启动类
 * @date 2018-07-01
 */
@Slf4j
@SpringBootApplication
@ComponentScan(basePackages = {"com.yx.sys.rpc.service.impl", "com.yx.common.core", "com.yx.common.db", "com.yx.common.mdb", "com.yx.common.redis","com.yx.common.log.rpc"})
@ImportResource(value = {"classpath:dubbo/providers.xml"})
@MapperScan(basePackages = {"com.yx.sys.dao.mapper"})
public class ServiceApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(ServiceApplication.class);
        springApplication.run(args);
        log.info("========== ServiceApplication启动成功 ==========");
    }
}

package com.yx.common.dsession.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * HttpSession缓存配置
 *
 * @author TangHuaLiang
 * @date 2018-07-01
 */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 60 * 30)
public class SessionConfiguration {

}

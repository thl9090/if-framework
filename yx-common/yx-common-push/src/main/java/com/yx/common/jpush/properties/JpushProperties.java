package com.yx.common.jpush.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * jpush极光推送配置信息
 *
 * @author TangHuaLiang
 * @date 2019-02-23
 **/
@ConfigurationProperties(prefix = JpushProperties.JPUSH_PREFIX)
public class JpushProperties {

    public static final String JPUSH_PREFIX = "jpush";

    @Getter
    @Setter
    private String appKey;

    @Getter
    @Setter
    private String masterSecret;


}

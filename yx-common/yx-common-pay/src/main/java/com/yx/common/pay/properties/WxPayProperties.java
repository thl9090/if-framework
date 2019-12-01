package com.yx.common.pay.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 微信支付配置
 *
 * @author TangHuaLiang
 * @date 2018-07-01
 */
@Component
@ConfigurationProperties(prefix = "weixin")
public class WxPayProperties {

    @Setter@Getter
    private String appid;
    @Setter@Getter
    private String secret;
    @Setter@Getter
    private String mchId;
    @Setter@Getter
    private String secretKey;
    @Setter@Getter
    private String keyPublic;
    @Setter@Getter
    private String notifyUrl;
    @Setter@Getter
    private String signType;

}

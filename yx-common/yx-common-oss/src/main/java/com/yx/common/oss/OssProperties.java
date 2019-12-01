package com.yx.common.oss;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * oss配置信息
 *
 * @author TangHuaLiang
 * @date 2018-07-01
 **/
@ConfigurationProperties(prefix = OssProperties.OSS_PREFIX)
public class OssProperties {

    public static final String OSS_PREFIX = "oss";

    /**
     * 类型 1：阿里云  2：腾讯云  3：七牛云
     */
    @Getter
    @Setter
    private Integer type;

    /**
     * 绑定的域名
     */
    @Getter
    @Setter
    private String domain;

    /**
     * 路径前缀
     */
    @Getter
    @Setter
    private String prefix;

    /**
     * AccessKeyId(对应腾讯云：SecretId)
     */
    @Getter
    @Setter
    private String accessKeyId;

    /**
     * AccessKeySecret(对应腾讯云：SecretKey)
     */
    @Getter
    @Setter
    private String accessKeySecret;

    /**
     * bucketName
     */
    @Getter
    @Setter
    private String bucketName;

    /**
     * endPoint(对应腾讯云：Region)
     */
    @Getter
    @Setter
    private String endPoint;


}

package com.yx.common.mq.propties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * MQ配置文件
 *
 * @author TangHuaLiang
 * @date 18/1/1 15:11:47
 */
@Component
@ConfigurationProperties(prefix = "spring.activemq")
public class MqPropties {

    @Getter@Setter private String defaultQueueName;

    @Getter@Setter private Integer queuePrefetch;
}

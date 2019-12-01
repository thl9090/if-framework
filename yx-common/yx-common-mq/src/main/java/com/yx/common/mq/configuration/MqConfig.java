package com.yx.common.mq.configuration;

import com.yx.common.mq.propties.MqPropties;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQPrefetchPolicy;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.Queue;

/**
 * MQ配置
 *
 * @author TangHuaLiang
 * @date 17/11/24 15:16:50
 */
@Configuration
@EnableJms
@EnableConfigurationProperties(ActiveMQProperties.class)
public class MqConfig {

    @Autowired
    private ActiveMQProperties activeMQProperties;

    @Autowired
    private MqPropties mqPropties;

    @Bean
    public Queue queue() {
        return new ActiveMQQueue();
    }

    @Bean
    public RedeliveryPolicy redeliveryPolicy() {
        RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
        //是否在每次尝试重新发送失败后,增长这个等待时间
        redeliveryPolicy.setUseExponentialBackOff(true);
        //重发次数,默认为6次   这里设置为1次
        redeliveryPolicy.setMaximumRedeliveries(1);
        //重发时间间隔,默认为1秒
        redeliveryPolicy.setInitialRedeliveryDelay(1);
        //第一次失败后重新发送之前等待500毫秒,第二次失败再等待500 * 2毫秒,这里的2就是value
        redeliveryPolicy.setBackOffMultiplier(2);
        //是否避免消息碰撞
        redeliveryPolicy.setUseCollisionAvoidance(false);
        //设置重发最大拖延时间-1 表示没有拖延只有UseExponentialBackOff(true)为true时生效
        redeliveryPolicy.setMaximumRedeliveryDelay(-1);
        return redeliveryPolicy;
    }

    @Bean
    public ActiveMQPrefetchPolicy prefetchPolicy() {
        ActiveMQPrefetchPolicy activeMQPrefetchPolicy = new ActiveMQPrefetchPolicy();
        //预处理条数
        if (mqPropties.getQueuePrefetch() != null) {
            activeMQPrefetchPolicy.setQueuePrefetch(mqPropties.getQueuePrefetch());
        }
        return activeMQPrefetchPolicy;
    }

    @Bean
    public CachingConnectionFactory cachingConnectionFactory(RedeliveryPolicy redeliveryPolicy, ActiveMQPrefetchPolicy activeMQPrefetchPolicy) {
        ActiveMQConnectionFactory activeMQConnectionFactory =
                new ActiveMQConnectionFactory(activeMQProperties.getUser(),
                        activeMQProperties.getPassword(),
                        activeMQProperties.getBrokerUrl());
        activeMQConnectionFactory.setRedeliveryPolicy(redeliveryPolicy);
        activeMQConnectionFactory.setPrefetchPolicy(activeMQPrefetchPolicy);
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(activeMQConnectionFactory);
        cachingConnectionFactory.setSessionCacheSize(10);
        return cachingConnectionFactory;
    }

    @Bean
    public JmsTemplate jmsTemplate(CachingConnectionFactory cachingConnectionFactory, Queue queue) {
        JmsTemplate jmsTemplate = new JmsTemplate();
        //进行持久化配置 1表示非持久化，2表示持久化
        jmsTemplate.setDeliveryMode(2);
        jmsTemplate.setSessionTransacted(true);
        jmsTemplate.setConnectionFactory(cachingConnectionFactory);
        //此处可不设置默认，在发送消息时也可设置队列
        jmsTemplate.setDefaultDestination(queue);
//        jmsTemplate.setSessionAcknowledgeMode(4);//客户端签收模式
//        jmsTemplate.setReceiveTimeout(3000);//默认0永不超时
        return jmsTemplate;
    }

    @Bean
    public JmsMessagingTemplate jmsMessagingTemplate(JmsTemplate jmsTemplate) {
        JmsMessagingTemplate jmsMessagingTemplate = new JmsMessagingTemplate();
        jmsMessagingTemplate.setJmsTemplate(jmsTemplate);
        return jmsMessagingTemplate;
    }

    /**
     * 定义一个消息监听器连接工厂，这里定义的是点对点模式的监听器连接工厂
     *
     * @param cachingConnectionFactory
     * @return DefaultJmsListenerContainerFactory
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    @Bean(name = "jmsQueueListener")
    public DefaultJmsListenerContainerFactory jmsQueueListenerContainerFactory(CachingConnectionFactory cachingConnectionFactory) {
        DefaultJmsListenerContainerFactory factory =
                new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(cachingConnectionFactory);
        //设置连接数
        factory.setConcurrency("1-10");
        //重连间隔时间
        factory.setRecoveryInterval(1000L);
        return factory;
    }

}

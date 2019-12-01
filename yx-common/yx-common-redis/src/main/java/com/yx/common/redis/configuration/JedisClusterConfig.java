/*
package com.yx.common.redis.configuration;

import com.alibaba.fastjson.JSON;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

*/
/**
 * REDIS集群配置类
 *//*

@Configuration
@ConfigurationProperties(prefix = "spring.redis")
@EnableConfigurationProperties(RedisProperties.class)
public class JedisClusterConfig {

    private static final Logger log = LoggerFactory.getLogger(JedisClusterConfig.class);

    @Autowired
    private RedisProperties redisProperties;


    */
/**
     * jedis集群配置
     * @return
     *//*

    @Bean
    @Primary
    public RedisConnectionFactory connectionFactory() {

        RedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory(
                new RedisClusterConfiguration(redisProperties.getCluster().getNodes()));

        return redisConnectionFactory;
    }



    */
/**
     * 注意：
     * 这里返回的JedisCluster是单例的，并且可以直接注入到其他类中去使用
     * @return
     *//*

   */
/* @Bean
    public JedisCluster getJedisCluster() {
        log.info("进入redis集群初始化方法：访问集群地址为："+ JSON.toJSONString(redisProperties.getCluster().getNodes()));
        List<String> serverArray = redisProperties.getCluster().getNodes();//获取服务器数组(这里要相信自己的输入，所以没有考虑空指针问题)
        Set<HostAndPort> nodes = new HashSet<>();
        for (String ipPort : serverArray) {
            String[] ipPortPair = ipPort.split(":");
            nodes.add(new HostAndPort(ipPortPair[0].trim(), Integer.valueOf(ipPortPair[1].trim())));
        }
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxTotal(redisProperties.getPool().getMaxActive());
        config.setMaxIdle(redisProperties.getPool().getMaxIdle());
        config.setMinIdle(redisProperties.getPool().getMinIdle());//设置最小空闲数
        config.setMaxWaitMillis(redisProperties.getPool().getMaxWait());
        //在获取Jedis连接时，自动检验连接是否可用
        config.setTestOnBorrow(true);
        //在将连接放回池中前，自动检验连接是否有效
        config.setTestOnReturn(true);
        //自动测试池中的空闲连接是否都是可用连接
        config.setTestWhileIdle(true);
        //连接耗尽时是否阻塞, false报异常,ture阻塞直到超时,默认true
        config.setBlockWhenExhausted(false);
        //表示idle object evitor两次扫描之间要sleep的毫秒数
        config.setTimeBetweenEvictionRunsMillis(30000);
        //表示idle object evitor每次扫描的最多的对象数
        config.setNumTestsPerEvictionRun(10);
        //表示一个对象至少停留在idle状态的最短时间，然后才能被idle object evitor扫描并驱逐；这一项只有在timeBetweenEvictionRunsMillis大于0时才有意义
        config.setMinEvictableIdleTimeMillis(60000);
        //需要密码连接的创建对象方式
        //参数依次是：集群地址，链接超时时间，返回值的超时时间，链接尝试次数，密码和配置文件
        return new JedisCluster(nodes,10000,1000,3,redisProperties.getPassword(),config);
    }*//*




}
*/

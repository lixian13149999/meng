//package com.bcdbook.meng.common.config;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import redis.clients.jedis.JedisPoolConfig;
//
///**
// * @Author summer
// * @Date 2017/8/15 下午3:57
// * 日志使用redis进行记录, 且使用不同的数据库
// * 这里是专门针对日志的redis数据库做相关配置的文件
// */
//@Configuration
//@EnableAutoConfiguration
//@Slf4j
//public class LogRedisConfig {
//
//    /**
//     * @author summer
//     * @date 2017/8/15 下午3:59
//     * @param
//     * @return redis.clients.jedis.JedisPoolConfig
//     * @description 创建JedisPoolConfig对象
//     * 注意扫描的配置是log-redis
//     */
//    @Bean
//    @ConfigurationProperties(prefix="spring.log-redis")
//    public JedisPoolConfig getRedisConfig(){
//        JedisPoolConfig config = new JedisPoolConfig();
//        return config;
//    }
//
//    /**
//     * @author summer
//     * @date 2017/8/15 下午4:00
//     * @param
//     * @return org.springframework.data.redis.connection.jedis.JedisConnectionFactory
//     * @description 初始化JedisConnectionFactory,用于创建LogRedisTemplate
//     */
//    @Bean
//    @ConfigurationProperties(prefix="spring.log-redis")
//    public JedisConnectionFactory getConnectionFactory(){
//
//        JedisConnectionFactory factory = new JedisConnectionFactory();
//        JedisPoolConfig config = getRedisConfig();
//        factory.setPoolConfig(config);
//        log.info("JedisConnectionFactory 初始化成功.");
//        return factory;
//    }
//
//    /**
//     * @author summer
//     * @date 2017/8/15 下午4:02
//     * @param
//     * @return org.springframework.data.redis.core.RedisTemplate<?,?>
//     * @description 初始化 RedisTemplate
//     */
//    @Bean(name="logRedisTemplate")
//    public StringRedisTemplate getRedisTemplate() {
//
//        StringRedisTemplate template = new StringRedisTemplate(getConnectionFactory());
//        return template;
//    }
//}

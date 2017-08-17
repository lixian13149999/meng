//package com.bcdbook.meng.common.factory;
//
//import com.bcdbook.meng.common.config.RedisConfig;
//import com.bcdbook.meng.common.constant.RedisTemplateConstent;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.util.StringUtils;
//import redis.clients.jedis.JedisPoolConfig;
//
///**
// * @Author summer
// * @Date 2017/8/15 下午7:23
// */
//@Configuration
//@Slf4j
//public class RedisFactory {
//
//    @Autowired
//    private RedisConfig redisConfig;
//
//    public JedisPoolConfig getJedisPoolConfig(){
//
//        JedisPoolConfig poolCofig = new JedisPoolConfig();
//        poolCofig.setMaxWaitMillis(redisConfig.getMaxWait());
//        poolCofig.setMaxIdle(redisConfig.getMaxIdle());
//        poolCofig.setMinIdle(redisConfig.getMinIdle());
//
//        return poolCofig;
//    }
//
//    public JedisConnectionFactory getJedisConnectionFactory(Integer dbIndex){
//
//        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
//        JedisPoolConfig jedisPoolConfig = getJedisPoolConfig();
//
//        jedisConnectionFactory.setHostName(redisConfig.getHostName());
//        jedisConnectionFactory.setPort(redisConfig.getPort());
//        if(StringUtils.isEmpty(redisConfig.getPassword())){
//            jedisConnectionFactory.setPassword(redisConfig.getPassword());
//        }
//        if(dbIndex>0){
//            jedisConnectionFactory.setDatabase(dbIndex);
//        }
//
//        jedisConnectionFactory.setPoolConfig(jedisPoolConfig);
//
//        jedisConnectionFactory.afterPropertiesSet();
//
//        log.info("JedisConnectionFactory 初始化成功. dbIndex={}",dbIndex);
//
//        return jedisConnectionFactory;
//    }
//
//    @Bean(name= RedisTemplateConstent.USER_AUTHORIZE_REDIS_TEMPLATE)
//    public StringRedisTemplate getUserAuthorizeRedisTemplate(){
//
//        StringRedisTemplate template = new StringRedisTemplate();
//        JedisConnectionFactory jedisConnectionFactory = getJedisConnectionFactory(RedisTemplateConstent.USER_AUTHORIZE_DB_INDEX);
//        template.setConnectionFactory(jedisConnectionFactory);
//
//        return template;
//    }
//
//    @Bean(name= RedisTemplateConstent.LOG_REDIS_TEMPLATE)
//    public StringRedisTemplate getLogRedisTemplate(){
//
//        StringRedisTemplate template = new StringRedisTemplate();
//        JedisConnectionFactory jedisConnectionFactory = getJedisConnectionFactory(RedisTemplateConstent.LOG_DB_INDEX);
//        template.setConnectionFactory(jedisConnectionFactory);
//
//        return template;
//    }
//}

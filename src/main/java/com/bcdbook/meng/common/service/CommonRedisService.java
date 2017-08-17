package com.bcdbook.meng.common.service;

import com.bcdbook.meng.common.constant.CookieConstant;
import com.bcdbook.meng.common.constant.RedisConstant;
import com.bcdbook.meng.common.enums.ResultEnum;
import com.bcdbook.meng.common.exception.CommonException;
import com.bcdbook.meng.common.util.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * @Author summer
 * @Date 2017/8/16 下午12:41
 */
@Component
@Slf4j
public class CommonRedisService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    /**
     * @author summer
     * @date 2017/8/16 下午2:27
     * @param key
     * @return java.lang.String
     * @description 根据key值,获取redis数据库中的value
     */
    public String get(String key){

        if (StringUtils.isEmpty(key)){
            return null;
        }
        //获取redis数据库中对应key的value值
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * @author summer
     * @date 2017/8/16 下午2:14
     * @param request
     * @param key
     * @return java.lang.String
     * @description 根据相应的key,自动拼接cookie中的token并获取redis中的value
     */
    public String autoGet(HttpServletRequest request, String key) {
        if(request==null||StringUtils.isEmpty(key)){
            return null;
        }

        //获取token值
        Cookie tokenCookieValue = CookieUtil.get(request, CookieConstant.TOKEN_NAME);
        if(StringUtils.isEmpty(tokenCookieValue)){
            return null;
        }

        String redisKey = String.format(key,tokenCookieValue);
        return get(redisKey);
    }

    /**
     * @author summer
     * @date 2017/8/16 下午2:41
     * @param key
     * @param value
     * @param expire
     * @return void
     * @description 保存数据到redis数据库
     */
    public void set(String key, String value, long expire, TimeUnit timeUnit) {

        if (StringUtils.isEmpty(key)) {
            log.error("[保存数据到redis] 必要参数为空 key={}, value={}, expire={}", key, value, expire);
            throw new CommonException(ResultEnum.PARAM_IS_EMPTY);

        }
        stringRedisTemplate.opsForValue().set(key, value, expire, timeUnit);
    }

    /**
     * @author summer
     * @date 2017/8/16 下午2:43
     * @param request
     * @param key
     * @param value
     * @param expire
     * @param timeUnit
     * @return void
     * @description 保存数据到redis数据库, 保存时key值会自动拼接cookie中的token
     */
    public void autoSet(HttpServletRequest request, String key, String value, long expire, TimeUnit timeUnit){
        if (request ==null
                || StringUtils.isEmpty(key)) {
            log.error("[保存数据到redis] 必要参数为空 key={}, value={}, expire={}", key, value, expire);
            throw new CommonException(ResultEnum.PARAM_IS_EMPTY);
        }

        //获取token值
        String tokenCookieValue = CookieUtil.getValue(request, CookieConstant.TOKEN_NAME);
        if (StringUtils.isEmpty(tokenCookieValue)) {
            log.error("[保存数据到redis] 必要参数为空 key={}, value={}, expire={}", key, value, expire);
            throw new CommonException(ResultEnum.PARAM_IS_EMPTY);
        }

        String redisKey = String.format(key,tokenCookieValue);
        set(redisKey,value,expire,timeUnit);
    }

    /**
     * @author summer
     * @date 2017/8/16 下午7:39
     * @param key
     * @param timeOut
     * @param timeUnit
     * @return java.lang.Boolean
     * @description 刷新redis中的数据有效时长
     */
    public Boolean expire(String key,long timeOut,TimeUnit timeUnit){

        return stringRedisTemplate.expire(key,timeOut,timeUnit);
    }

    /**
     * @author summer
     * @date 2017/8/16 下午7:49
     * @param request
     * @param prefix
     * @return java.lang.Boolean
     * @description 自动刷新redis数据库中数据的方法
     * 其中内置了一些默认值
     * tokenCookieValue--用户的token值
     * RedisConstant.EXPIRE--数据的有效时长(常量值)
     * TimeUnit.SECONDS--时长的单位(秒)
     */
    public Boolean autoExpire(HttpServletRequest request,String prefix){

        //获取token值
        String tokenCookieValue = CookieUtil.getValue(request, CookieConstant.TOKEN_NAME);
        if (StringUtils.isEmpty(tokenCookieValue)) {
            log.error("[保存数据到redis] 必要参数为空 tokenCookieValue={}",tokenCookieValue);
            throw new CommonException(ResultEnum.PARAM_IS_EMPTY);
        }
        return stringRedisTemplate.expire(String.format(prefix, tokenCookieValue),
                RedisConstant.EXPIRE,
                TimeUnit.SECONDS);
    }


}

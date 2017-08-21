package com.bcdbook.meng.common.aspect;

import com.bcdbook.meng.common.config.CommonConfig;
import com.bcdbook.meng.common.constant.CookieConstant;
import com.bcdbook.meng.common.constant.RedisConstant;
import com.bcdbook.meng.common.constant.SessionResourceConstant;
import com.bcdbook.meng.common.enums.ResultEnum;
import com.bcdbook.meng.common.exception.AuthorizeException;
import com.bcdbook.meng.common.service.CommonRedisService;
import com.bcdbook.meng.common.util.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * @Author summer
 * @Date 2017/8/14 下午4:37
 * 全局的权限拦截器
 */
//表明这是一个切面类
@Aspect
//放入spring容器
@Component
//lombok的注解,用于简化日志的使用
@Slf4j
public class AuthorizeAspect {

    //@Resource(name = RedisTemplateConstent.USER_AUTHORIZE_REDIS_TEMPLATE)
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private CommonRedisService commonRedisService;

    @Autowired
    private CommonConfig commonConfig;

//    @Autowired
//    private RedisTemplate redisTemplate;

    /**
     * @author summer
     * @date 2017/8/14 下午4:46
     * @param
     * @return void
     * @description 定义一个切面
     */
    //过滤所有的Controller,中的所有方法
    @Pointcut("execution(public * com.bcdbook.meng.*.controller.*.*(..))" +
            "&& !execution(public * com.bcdbook.meng.system.controller.CommonController.*(..))")
    public void verify() {

    }

    /**
     * @author summer
     * @date 2017/8/14 下午4:46
     * @param
     * @return void
     * @description 执行校验操作
     */
    @Before("verify()")
    public void doVerify() {
        //从配置文件中获取,是否执行登录权限的拦截
        if(commonConfig.isCheckLoginAuthorize()){
            //log.error("[过滤器] 将要执行权限校验 isCheckLoginAuthorize={}",commonConfig.isCheckLoginAuthorize());

            //获取request对象
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            HttpServletResponse response = attributes.getResponse();

            /*
             * 验证用户登录状态
             */
            //查询cookie
            Cookie tokenCookie = CookieUtil.get(request, CookieConstant.TOKEN_NAME);
            //校验cookie中的token
            if (tokenCookie == null) {
                log.warn("[登录校验]Cookie中token已过期");
                throw new AuthorizeException(ResultEnum.COOKIE_EXPIRED);
            }

            //查询redis获取redis的值
            //校验redis中的登录状态
            String redisTokenValue = commonRedisService.get(String.format(RedisConstant.TOKEN_PREFIX, tokenCookie.getValue()));
            if (StringUtils.isEmpty(redisTokenValue)) {
                log.warn("[登录校验]Redis中token已过期");
                throw new AuthorizeException(ResultEnum.REDIS_EXPIRED);
            }

            /**
             * 刷新用户相关资源
             */
            //如果当前有效时长小于默认时长的一半,则执行刷新操作
            String cookieTokenValue = tokenCookie.getValue();
            //查询有效时长
            Long expire = stringRedisTemplate.getExpire(String.format(RedisConstant.TOKEN_PREFIX, cookieTokenValue),
                    TimeUnit.SECONDS);
            //当有效时间小于最小可用时间时
            if(expire <= RedisConstant.EXPIRE/2){
                //刷新Token
                CookieUtil.set(response, CookieConstant.TOKEN_NAME, cookieTokenValue, CookieConstant.MAX_AGE);
                //刷新否长效登录状态
                CookieUtil.set(response,
                        CookieConstant.KEEP_ONLINE,
                        CookieUtil.get(request, CookieConstant.KEEP_ONLINE).getValue().toString(),
                        CookieConstant.MAX_AGE);

//            stringRedisTemplate.expire(String.format(RedisConstant.TOKEN_PREFIX, cookieTokenValue),
//                    RedisConstant.EXPIRE,
//                    TimeUnit.SECONDS);

                //刷新redis中的Token
                commonRedisService.autoExpire(request, RedisConstant.TOKEN_PREFIX);
                //刷新redis中的用户
                commonRedisService.autoExpire(request, SessionResourceConstant.ONLINE_USER);
                //刷新redis中的用户名
                commonRedisService.autoExpire(request, SessionResourceConstant.ONLINE_USER_NAME);

                log.info("[权限拦截] 刷新登录状态");
            }
        }
    }
}

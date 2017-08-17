package com.bcdbook.meng.common.aspect;

import com.alibaba.fastjson.JSON;
import com.bcdbook.meng.common.constant.SessionResourceConstant;
import com.bcdbook.meng.common.service.CommonRedisService;
import com.bcdbook.meng.common.util.LoggerUtils;
import com.bcdbook.meng.tools.enums.LogTypeEnums;
import com.bcdbook.meng.tools.model.Log;
import com.bcdbook.meng.tools.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author summer
 * @Date 2017/8/15 上午8:17
 */
//表明这是一个切面类
@Aspect
//放入spring容器
@Component
//lombok的注解,用于简化日志的使用
@Slf4j
public class LogAspect {

    @Autowired
    private LogService loggerService;

    @Autowired
    private CommonRedisService commonRedisService;

    @Pointcut("execution(public * com.bcdbook.meng.*.controller.*.*(..))")
    public void loggerPointcut() {}

    /**
     * @author summer
     * @date 2017/8/15 下午2:36
     * @param
     * @return void
     * @description 正常日志的拦截和记录
     */
    @After("loggerPointcut()")
    public void recordLog(){

        try{
            /*
             * 获取request和response
             */
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = servletRequestAttributes.getRequest();
            HttpServletResponse response = servletRequestAttributes.getResponse();

            /*
             * 获取相应的参数
             */
            Integer loggerType = LogTypeEnums.INFO.getCode();//日志类型
            //- TODO 这里的用户需要从redis数据库中获取,之后会写一个公共方法来处理
            String username = commonRedisService.autoGet(request,SessionResourceConstant.ONLINE_USER_NAME);//用户名
            String method = request.getMethod();//方法名
            String requestParams = JSON.toJSONString(request.getParameterMap());//请求参数
            String requestIp = LoggerUtils.getCliectIp(request);//获取请求的ip地址
            String requestUri = request.getRequestURI();//获取请求的路径

            String exception = null;//这是正常情况的请求,所以没有异常信息

            Integer responseStatus = response.getStatus();//返回的状态码
            String ajaxType = LoggerUtils.getRequestType(request);//请求方式(是否是ajax方式请求)

            //封装对象
            Log logger = new Log();
            logger.setLogType(loggerType);
            logger.setUsername(username);
            logger.setMethod(method);
            logger.setRequestParams(requestParams);
            logger.setRequestIp(requestIp);
            logger.setRequestUri(requestUri);
            logger.setException(exception);
            logger.setResponseStatus(responseStatus);
            logger.setAjaxType(ajaxType);

            //执行保存
            //- TODO 这里暂时关闭的日志记录的保存,上线前需要开启
//            loggerService.save(logger);
            log.info("[LogAspect] Info {}",logger.toString());
        }catch (Exception e){
            log.error("[记录日志] 正常日志日志记录时出现错误 exception={}",e);
            log.error(e.getMessage());
        }
    }

    /**
     * @author summer
     * @date 2017/8/15 下午2:35
     * @param e
     * @return void
     * @description 异常日志的拦截和记录
     */
    @AfterThrowing(pointcut = "loggerPointcut()",throwing = "e")
    public void recordExceptionLog(Exception e){

        try{
            /*
             * 获取request和response
             */
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = servletRequestAttributes.getRequest();
            HttpServletResponse response = servletRequestAttributes.getResponse();

            /*
             * 获取相应的参数
             */
            Integer loggerType = LogTypeEnums.ERROR.getCode();//日志类型
            //- TODO 这里的用户需要从redis数据库中获取,之后会写一个公共方法来处理
            String username = commonRedisService.autoGet(request,SessionResourceConstant.ONLINE_USER_NAME);//用户名
            String method = request.getMethod();//方法名
            String requestParams = JSON.toJSONString(request.getParameterMap());//请求参数
            String requestIp = LoggerUtils.getCliectIp(request);//获取请求的ip地址
            String requestUri = request.getRequestURI();//获取请求的路径

            String exception = e.toString();

            Integer responseStatus = response.getStatus();//返回的状态码
            String ajaxType = LoggerUtils.getRequestType(request);//请求方式(是否是ajax方式请求)

            //封装对象
            Log logger = new Log();
            logger.setLogType(loggerType);
            logger.setUsername(username);
            logger.setMethod(method);
            logger.setRequestParams(requestParams);
            logger.setRequestIp(requestIp);
            logger.setRequestUri(requestUri);
            logger.setException(exception);
            logger.setResponseStatus(responseStatus);
            logger.setAjaxType(ajaxType);

            //执行保存
            //- TODO 这里暂时关闭的日志记录的保存,上线前需要开启
//            loggerService.save(logger);
            log.info("[LogAspect] ExceptionLog {}",logger.toString());


        }catch (Exception excepiton){
            log.error("[记录日志] 错误日志记录时出出现错误 excepiton={}",excepiton);
            log.error(excepiton.getMessage());
        }
    }
}

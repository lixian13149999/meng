package com.bcdbook.meng.common.handler;

import com.bcdbook.meng.common.enums.ResultEnum;
import com.bcdbook.meng.common.exception.AuthorizeException;
import com.bcdbook.meng.common.exception.CommonException;
import com.bcdbook.meng.common.result.Result;
import com.bcdbook.meng.common.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author summer
 * @Date 2017/8/14 上午10:47
 * 公共异常类的过滤处理器
 */
@ControllerAdvice
@Slf4j
public class CommonExceptionHandler {

    /**
     * 异常统一拦截的过滤器
     * @param e
     * @return
     */
    /*
     * 异常监听把手,value用于定义异常的监听类型
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result handle(Exception e) {
        if (e instanceof CommonException) {
            CommonException commonException = (CommonException) e;
            return ResultUtil.error(commonException.getCode(),commonException.getMessage());
        }else if (e instanceof AuthorizeException) {
            AuthorizeException authorizeException = (AuthorizeException) e;
            return ResultUtil.error(authorizeException.getCode(),authorizeException.getMessage());
        }else {
            log.error("【系统异常】{}", e);
            return ResultUtil.error(ResultEnum.ERROR);
        }
    }
}

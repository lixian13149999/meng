package com.bcdbook.meng.common.util;

import com.bcdbook.meng.common.enums.ResultEnum;
import com.bcdbook.meng.common.result.Result;

/**
 * @Author summer
 * @Date 2017/8/14 上午8:29
 * 统一返回值处理的封装工具类
 */
public class ResultUtil {

    /**
     * 返回成功的方法(有需要返回的数据)
     * @param data
     * @return
     */
    public static Result success(Object data){
        return getResult(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(),data);
    }

    /**
     * 返回成功的方法,不需要返回数据
     * @return
     */
    public static Result success(){
        return success(null);
    }

    /**
     * 返回错误的方法,不需要返回值
     * @param code
     * @param message
     * @return
     */
    public static Result error(Integer code, String message){
        return getResult(code,message,null);
    }

    /**
     * 错误返回值的封装方法
     * @param resultEnum
     * @return
     */
    public static Result error(ResultEnum resultEnum){
        return error(resultEnum.getCode(),resultEnum.getMessage());
    }

    /**
     * 用于获取统一返回值对象的封装方法
     * @param code
     * @param message
     * @param data
     * @return
     */
    private static Result getResult(Integer code, String message, Object data){
        return new Result(code,message,data);
    }
}

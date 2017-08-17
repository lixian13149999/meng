package com.bcdbook.meng.common.exception;

import com.bcdbook.meng.common.enums.ResultEnum;
import lombok.Getter;

/**
 * @Author summer
 * @Date 2017/8/14 上午8:59
 * 公共的异常类
 */
@Getter
public class CommonException extends RuntimeException {

    /*
     * 错误码
     */
    private Integer code;

    /**
     * 通过返回值枚举, 封装的异常构造方法
     * @param resultEnum
     */
    public CommonException(ResultEnum resultEnum){
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    /**
     * 通过错误码和提示信息封装的异常构造方法
     * @param code
     * @param message
     */
    public CommonException(Integer code,String message){
        super(message);
        this.code = code;
    }
}

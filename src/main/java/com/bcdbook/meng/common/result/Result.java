package com.bcdbook.meng.common.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author summer
 * @aate 2017/8/14 上午8:25
 * 统一返回值处理的返回数据类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 提示信息
     */
    private String message;

    /**
     * 返回的具体内容
     */
    private T data;
}

package com.bcdbook.meng.tools.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author summer
 * @Date 2017/8/15 上午10:04
 * 日志类型枚举
 */
@Getter
@AllArgsConstructor
public enum LogTypeEnums {

    INFO(1,"正常日志"),
    ERROR(2,"错误日志")
    ;

    private Integer code;
    private String message;
}

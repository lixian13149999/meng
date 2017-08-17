package com.bcdbook.meng.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author summer
 * @Date 2017/8/14 上午8:34
 * 统一返回值,返回码的枚举类
 */
@Getter
@AllArgsConstructor
public enum ResultEnum {

    SUCCESS(200,"成功"),


    ERROR(300,"系统错误"),
    USERNAME_OR_PASSWORD_NOT_MATCHING(301,"用户名或密码不正确"),


    PARAM_ERROR(501,"参数不正确"),
    PARAM_IS_EMPTY(502,"必要参数为空"),

    PERMISSION_DENIED(510,"没有权限"),
    COOKIE_EXPIRED(511,"cookie已过期或被禁用"),
    REDIS_EXPIRED(512,"登录已过期请重新登录")

    ;

    private Integer code;
    private String message;
}

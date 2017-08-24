package com.bcdbook.meng.system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author summer
 * @Date 2017/8/12 下午5:30
 * 用户类型枚举类
 */
@Getter
@AllArgsConstructor
public enum UserTypeEnum {
    ORDINARY_USER(1,"普通用户"),
    MERCHANT(2,"商家"),
    ADMIN(10,"普通管理员"),
    SYSADMIN(11,"超级管理员"),
    ;

    private Integer code;
    private String message;
}

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
    SYSADMIN(10,"超级管理员"),
    ADMIN(11,"普通管理员"),
    ;

    private Integer code;
    private String message;
}

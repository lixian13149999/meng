package com.bcdbook.meng.system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author summer
 * @Date 2017/8/12 下午5:30
 * 用户状态枚举类
 */
@Getter
@AllArgsConstructor
public enum UserStatusEnum {
    NORMALITY(1,"正常状态"),
    BLOCK_UP(2,"停用"),
    LOCKED(3,"锁定")
    ;

    private Integer code;
    private String message;

}

package com.bcdbook.meng.system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author summer
 * @Date 2017/8/12 下午5:30
 * 认证状态枚举类
 */
@Getter
@AllArgsConstructor
public enum CertificationStatusEnum {
    UNCERTIFIED(1,"未认证"),
    CERTIFIED(2,"已认证")
    ;

    private Integer code;
    private String message;
}

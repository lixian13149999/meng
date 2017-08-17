package com.bcdbook.meng.system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author summer
 * @Date 2017/8/12 下午5:30
 * 资源类型枚举类
 */
@Getter
@AllArgsConstructor
public enum IResourceTypeEnum {
    /**
     * 菜单资源
     */
    MENU(1, "菜单资源"),
    /**
     * 枚举资源
     */
    BUTTON(2,"按钮资源")
    ;

    private Integer code;
    private String message;

}

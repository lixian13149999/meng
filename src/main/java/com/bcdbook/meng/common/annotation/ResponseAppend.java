package com.bcdbook.meng.common.annotation;

import java.lang.annotation.*;

/**
 * @Author summer
 * @Date 2017/9/13 上午10:28
 * 定义返回时需要添加值的内容注解
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ResponseAppend {

    String[] appendItems();
}

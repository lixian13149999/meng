package com.bcdbook.meng.common.constant;

/**
 * redis常量
 * Created by summer
 * 2017-08-13 16:22
 */
public interface RedisConstant {

    /**
     * Token的前缀
     */
    String TOKEN_PREFIX = "token_%s";
    /**
     * log的前缀
     */
//    String LOG_PREFIX = "log_%s";

    /**
     * 普通Token保存的时长(2小时)
     */
    Integer EXPIRE = 7200;//正常失效时间(2小时)

    /**
     * 长效Token保存的时长(一周)
     */
    Integer LONG_EXPIRE = 604800;//长效登录时间(一周)
    /**
     * 日志保存的时长(一年)
     */
//    Integer LOG_EXPIRE = 31536000;//日志数据保存的时长
}

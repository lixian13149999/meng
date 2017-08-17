package com.bcdbook.meng.common.constant;

/**
 * cookie常量
 * Created by summer
 * 2017-08-13 16:35
 */
public interface CookieConstant {

    /**
     * Token的名字
     */
    String TOKEN_NAME = "token";

    /**
     * 是否保持长效在线的标注
     */
    String KEEP_ONLINE = "keepOnline";//是否保持长时间在线

    /**
     * 默认Token的有效时长(2小时)
     */
    Integer MAX_AGE = 7200;//正常失效时间(2小时)

    /**
     * 长效Token的有效时长(一周)
     */
    Integer LONG_MAX_AGE = 604800;//长效登录时间(一周)
}

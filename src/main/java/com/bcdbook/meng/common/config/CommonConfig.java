package com.bcdbook.meng.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author summer
 * @Date 2017/8/21 上午10:28
 */
@Configuration
@ConfigurationProperties(prefix = "common.parameters")
@Data
public class CommonConfig {

    /**
     * 是否执行登录拦截
     */
    private boolean checkLoginAuthorize;

    /**
     * 请求的sql记录
     */
    private boolean takeRequestSqlLog;

    /**
     * 请求的logback记录
     */
    private boolean takeRequestLogBack;

}

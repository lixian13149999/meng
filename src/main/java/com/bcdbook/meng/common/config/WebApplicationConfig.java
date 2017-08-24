package com.bcdbook.meng.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Author summer
 * @Date 2017/8/23 下午4:38
 * web访问的配置文件
 */
@Configuration
public class WebApplicationConfig extends WebMvcConfigurerAdapter {

    /**
     * @author summer
     * @date 2017/8/23 下午4:41
     * @param registry
     * @return void
     * @description 重新定义静态资源的访问路径和映射地址
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        super.addResourceHandlers(registry);
    }
}

package com.bcdbook.meng.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author summer
 * @Date 2017/8/13 下午9:24
 */
/*
 * @Configuration注解,表明这是一个配置类
 */
@Configuration
/*
 * @EnableSwagger2开启swagger2
 */
@EnableSwagger2
public class Swagger2Config {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())//配置一些基本信息
                .select()
                //指定的包会生成API文档
                .apis(RequestHandlerSelectors.basePackage("com.bcdbook.meng.system"))
                .paths(PathSelectors.any())
                .build();
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("SpringBoot 基础项目meng的API文档")
                .description("本文档通过swagger2生成")
                .termsOfServiceUrl("www.baidu.com")
                .version("0.0.1")
                .build();
    }
}

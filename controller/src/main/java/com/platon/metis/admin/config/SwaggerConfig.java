package com.platon.metis.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author houz
 * swagger配置类
 */
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                //是否开启 (true 开启  false隐藏。生产环境建议隐藏)
//                .enable(false)
                .select()
                //扫描的路径包,设置basePackage会将包下的所有被@Api标记类的所有方法作为api
                .apis(RequestHandlerSelectors.basePackage("com.platon.metis.admin.controller"))
                //配置如何通过path过滤，指定路径处理PathSelectors.any()代表所有的路径
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //设置文档标题(API名称)
                .title("metis-admin项目 API 文档")
                //文档描述
                .description("此API文档使用于前后端分离开发，后端进行了接口变更，不需要更新接口文档，前端可以实时查看接口变更！")
//                .contact(new Contact("联系人名字", "http://xxx.xxx.com/联系人访问链接", "联系人邮箱"))
                //版本号
                .version("2.0.0")
                .build();
    }
}

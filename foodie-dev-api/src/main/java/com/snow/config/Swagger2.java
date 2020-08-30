package com.snow.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Desc: Swagger2 Api 文档配置
 */
@Configuration
@EnableSwagger2
public class Swagger2 {

//    http://localhost:8088/swagger-ui.html     // 原路径
//    http://localhost:8088/doc.html            // 第三方 api 路径

    // 配置 swagger2 核心配置 docket
    @Bean
    public Docket createRestApi() {


        return new Docket(DocumentationType.SWAGGER_2)  // 指定 api 类型为 swagger2
                    .apiInfo(apiInfo())                 // 用于定义 api 文档汇总信息
                    .select()
                    .apis(RequestHandlerSelectors
                            .basePackage("com.snow.controller"))    // 指定 controller
                    .paths(PathSelectors.any())         // 所有 controller
                    .build();

    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("天天吃货 电商平台接口 api")        // 文档页标题
                .contact(new Contact("snow",
                        "https://www.imooc.com",
                        "wjl7123093@163.com"))        // 联系人信息
                .description("转为天天吃货提供的 api 文档")    // 详细信息
                .version("1.0.1")                           // 文档版本号
                .termsOfServiceUrl("https://www.imooc.com")
                .build();
    }

}

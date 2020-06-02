package com.pyb.config;

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

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /*swagger二个固定配置*/
    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(true)
                .apiInfo(apiInfo())
                .select()
                //这里配置要扫描的包，接口在哪个包就配置哪个包
                .apis(RequestHandlerSelectors.basePackage("com.pyb"))
                .paths(PathSelectors.any())
                .build();
    }

    public ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("谷粒学院")
                .description("谷粒学院接口")
                .termsOfServiceUrl("pybCoding")
                .contact(new Contact("pyb","pyb.cn","pyb1997@qq.com"))
                .version("1.0")
                .build();
    }
}

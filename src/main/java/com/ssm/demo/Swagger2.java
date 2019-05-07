package com.ssm.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @Description
 * @Author Roge
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2019/4/26
 */
@Configuration
@EnableSwagger2
public class Swagger2 {

    public Docket createRestApi() {
        Parameter parameter = new ParameterBuilder().name("token").defaultValue("").description("token")
                .modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        return new Docket(DocumentationType.SWAGGER_2)
                .globalOperationParameters(new ArrayList<>(Arrays.asList(parameter)))
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ssm.demo.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("demo接口")
                .description("swagger接口文档")
                .termsOfServiceUrl("")
                .version("1.0")
                .build();
    }

}

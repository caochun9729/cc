package com.example.demo.swagger;

import com.example.demo.constant.Constants;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
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
import java.util.List;


@Configuration
@EnableSwagger2
//@ConditionalOnProperty(prefix = "swagger", name = "button-open", havingValue = "true")
public class SwaggerConfig {

    /**
     * 创建获取api应用
     *
     * @return
     */
    @Bean
    public Docket createRestApi() {
        //添加head参数配置start
        ParameterBuilder tokenPar = new ParameterBuilder();
        ParameterBuilder tokenPar1 = new ParameterBuilder();
        ParameterBuilder tokenPar2 = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        tokenPar.name(Constants.USER_NAME).description("用户").modelRef(new ModelRef("string"))
                .parameterType("header").required(true).build();
        tokenPar1.name(Constants.USER_ID).description("用户编号").modelRef(new ModelRef("integer"))
                 .parameterType("header").required(false).build();
        tokenPar2.name(Constants.token).description("用户token").modelRef(new ModelRef("String"))
                .parameterType("header").required(false).build();
        pars.add(tokenPar.build());
        pars.add(tokenPar1.build());
        pars.add(tokenPar2.build());
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())
            .select()
            //这里采用包含注解的方式来确定要显示的接口(建议使用这种)
            .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
            //.apis(RequestHandlerSelectors.basePackage("com.scw.springboot.swagger.controller"))    //这里采用包扫描的方式来确定要显示的接口
            .paths(PathSelectors.any())
            .build()
            .globalOperationParameters(pars);

    }

    /**
     * 配置swagger文档显示的相关内容标识(信息会显示到swagger页面)
     *
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("swagger使用")
            .version("1.0")
            .build();
    }

}

package com.platform.common.config;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;
import java.util.Objects;

/**
 * @description:
 * @author: zengzheng
 * @create: 2021-01-11 14:03
 */

//注解标示,这是一个配置类,@Configuation注解包含了@Component注解
//可以不用在使用@Component注解标记这是个bean了
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Value("${spring.application.name}")
    private String applicationName;
    @Value("${swagger.enable:true}")
    private boolean enable;
    @Value("${server.port}")
    private Integer serverPort;
    @Value("${spring.profiles.active:}")
    private String profileActive;

    private static final String TOKEN = "token";

    public SwaggerConfiguration() {
    }

    @Bean
    public Docket createRestApi() {
        return (new Docket(DocumentationType.SWAGGER_2)).
                groupName(applicationName + "接口文档")
                .enable(this.enable()).select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any()).build()
                .securitySchemes(this.securitySchemes())
                .securityContexts(this.securityContexts()).apiInfo(apiInfo());
    }

    private boolean enable() {
        if (!Objects.isNull(enable) && !profileActive.equals("prd")) {
            return true;
        }
        return !profileActive.equals("prd");
    }

    private List<SecurityContext> securityContexts() {
        return Lists.newArrayList(new SecurityContext[]{SecurityContext.builder().securityReferences(this.defaultAuth())
                .forPaths(PathSelectors.regex("^(?!auth).*$")).build()});
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[]{authorizationScope};
        return Lists.newArrayList(new SecurityReference[]{new SecurityReference(TOKEN, authorizationScopes)});
    }

    private List<SecurityScheme> securitySchemes() {
        return Lists.newArrayList(new ApiKey[]{new ApiKey(TOKEN, TOKEN, "header")});
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //页面标题
                .title(applicationName+"服务接口文档")
                //设置作者联系方式,可有可无
                .contact(new Contact("zengzheng", "https://github.com/", "xxxxxx@163.com"))
                //版本号
                .version("1.0")
                //描述
                .description("接口文档 good good study！day day up！")
                .build();
    }


}

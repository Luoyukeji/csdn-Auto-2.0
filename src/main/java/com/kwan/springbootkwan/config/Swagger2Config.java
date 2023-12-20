package com.kwan.springbootkwan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 通过@Configuration注解，表明它是一个配置类 @EnableSwagger2开启swagger2。 apiINfo()配置一些基本的信息。apis()指定扫描的包会生成文档。
 *
 * @author : qinyingjie
 * @version : 2.2.0
 * @date : 2022/12/19 17:23
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket createRestApi(Environment environment) {
        // 配置swagger的docket的bean实例
        Profiles profiles = Profiles.of("dev", "test", "local");
        // 通过environment.acceptsProfiles()判断是否指定的环境中，是，则为true
        boolean flag = environment.acceptsProfiles(profiles);
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("SpringBootStudy")
                .pathMapping("/")
                .enable(flag)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.kwan.springbootkwan.controller"))//需要生成接口文档的包名
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("SpringBoot学习")
                .description("最怕一生碌碌无为，还安慰自己平凡可贵。")
                .termsOfServiceUrl("http://qinyingjie.top/")
                .contact(new Contact("kwan", "http://qinyingjie.top/", "3327782001@qq.com"))
                .version("Apache 2.0")
                .build();
    }
}
package com.customer.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket createRestApi() {
		/*添加接口请求头参数配置 没有的话 可以忽略*/
		return (new Docket(DocumentationType.SWAGGER_2)).apiInfo(apiInfo()).enable(
				true)
				.globalOperationParameters(
                        Arrays.asList(
                                new ParameterBuilder()
                                .name("Authorization")
                                .description("token")
                                .modelRef(new ModelRef("string"))
                                .defaultValue(JWTConfig.tokenPrefix+"token")
                                .parameterType("header")
                                .required(false)
                                .build()
                        )
                )
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.customer"))
				.paths(PathSelectors.any())
				.build();
	}

	private ApiInfo apiInfo() {
	    return (new ApiInfoBuilder()).title("接口说明").version("1.0").build();
	  }
}

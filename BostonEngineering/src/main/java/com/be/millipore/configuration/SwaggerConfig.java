package com.be.millipore.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.be.millipore.apiconstant.APIConstant;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {
	private static final String SWAGGER_API_VERSION = "1.0";
	private static final String LICENSE_TEXT = "License";
	private static final String title = "Boston Engineering RESTFul API";
	private static final String description = "RESTful APIs for Boston Engineering";

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title(title).description(description).license(LICENSE_TEXT)
				.version(SWAGGER_API_VERSION).build();
	}

	@Bean
	public Docket bostonApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
				.tags(new Tag(APIConstant.USER_CONTROLLER_TAG, APIConstant.USER_CONTROLLER_DESCRIPTON),
						new Tag(APIConstant.USER_ROLE_CONTROLLER_TAG, APIConstant.USER_ROLE_CONTROLLER_DESCRIPTON))
				.select().apis(RequestHandlerSelectors.basePackage("com.be.millipore.controller"))
				.paths(PathSelectors.regex(APIConstant.REST_BASE_URL + ".*")).build();
	}

}

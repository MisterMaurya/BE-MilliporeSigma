package com.be.millipore.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.be.millipore.constant.APIConstant;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {
	private static final String SWAGGER_API_VERSION = "1.0";
	private static final String LICENSE_TEXT = "License";
	private static final String TITLE = "Boston Engineering RESTFul API";
	private static final String DESCRIPTION = "RESTful APIs for Boston Engineering";
	public static final Contact DEFAULT_CONTACT = new Contact("Pawan Maurya", "http://github.com/mistermaurya",
			"pawank@thelattice.in");

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title(TITLE).description(DESCRIPTION).license(LICENSE_TEXT).contact(DEFAULT_CONTACT)
				.version(SWAGGER_API_VERSION).build();
	}

	@Bean
	public Docket bostonApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
				.tags(new Tag(APIConstant.USER_CONTROLLER_TAG, APIConstant.USER_CONTROLLER_DESCRIPTION),
						new Tag(APIConstant.USER_ROLE_CONTROLLER_TAG, APIConstant.USER_ROLE_CONTROLLER_DESCRIPTION),
						new Tag(APIConstant.USER_LOGIN_TAG, APIConstant.USER_LOGIN_DESCRIPTION),
						new Tag(APIConstant.TASK_TEMPLATE_TAG, APIConstant.TASK_TEMPLATE_DESCRIPTION),
						new Tag(APIConstant.TEMPLATE_CONTROLLER_TAG, APIConstant.TEMPLATE_CONTROLLER_DESCRIPTION))
				.select().apis(RequestHandlerSelectors.basePackage("com.be.millipore")).build()
				.enableUrlTemplating(true).securitySchemes(Arrays.asList(apiKey()));
	}

	private ApiKey apiKey() {
		return new ApiKey("Authorization", "Authorization", "header");
	}

	@Bean
	public SecurityConfiguration securityInfo() {
		return new SecurityConfiguration(null, null, null, null, "", ApiKeyVehicle.HEADER, "Authorization", "");
	}

}

package com.nokia.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.annotations.Api;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author jaisw
 * 
 * Swagger Docs.
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerDocumentationConfig {
	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.withClassAnnotation(Api.class)).build().useDefaultResponseMessages(false)
				.apiInfo(metaData());
	}

	/**
	 * @return ApiInfo
	 */
	private ApiInfo metaData() {
		return new ApiInfoBuilder().title("Nokia Api").description("Upload & Download files").termsOfServiceUrl("")
				.version("1.0.0").build();
	}
}

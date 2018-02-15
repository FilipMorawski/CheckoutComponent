package com.filipmorawski.checkoutcomponent;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Bean
	public Docket checkoutComponentApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select().apis(RequestHandlerSelectors.basePackage("com.filipmorawski.checkoutcomponent.controllers"))
				.build()
				.apiInfo(metaData());
				
	}
	 private ApiInfo metaData() {
	        ApiInfo apiInfo = new ApiInfo(
	                "Checkout Component REST API",
	                "API for Online Shop Apps, managing shopping carts and products stored in shop.",
	                "1.0",
	                "Terms of service",
	                new Contact("Filip Morawski", "https://github.com/FilipMorawski", "morawski.filip@o2.pl"),
	               "Apache License Version 2.0",
	                "https://www.apache.org/licenses/LICENSE-2.0");
	        return apiInfo;
	    }
	
}

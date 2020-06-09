package com.karthickram.redditclone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@SpringBootApplication
@EnableAsync
@EnableSwagger2
public class RedditcloneApplication {

	private static final Logger log = LoggerFactory.getLogger(RedditcloneApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(RedditcloneApplication.class, args);
		System.out.println("Welcome to Reddit clone application!");
	}

	@Bean
	public Docket swaggerConfiguration() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.karthickram.redditclone"))
				.build()
				.apiInfo(getSwaggerApiInfo());
	}

	private ApiInfo getSwaggerApiInfo() {
		return new ApiInfo(
				"Reddit clone API",
				"API Documentation for reddit clone application",
				"1.0",
				"Free to use",
				new Contact("Karthick Ram", "https://karthickram.in", "karthik.28696@gmail.com"),
				"MIT License",
				"https://karthickram.in",
				Collections.emptyList());
	}
}

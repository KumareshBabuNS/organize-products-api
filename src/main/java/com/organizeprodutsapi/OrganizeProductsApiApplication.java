package com.organizeprodutsapi;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class OrganizeProductsApiApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		new OrganizeProductsApiApplication().configure(new SpringApplicationBuilder(OrganizeProductsApiApplication.class)).run(args);
		//SpringApplication.run(OrganizeProductsApiApplication.class, args);
	}
}

package com.organizeprodutsapi.jersey;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import com.organizeprodutsapi.ProductResource;

@Component
public class JerseyConfig extends ResourceConfig{
	
	public JerseyConfig() {
		register(ProductResource.class);
	}

}

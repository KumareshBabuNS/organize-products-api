package com.organizeprodutsapi.jersey;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.organizeprodutsapi.dto.ProductDto;
import com.organizeprodutsapi.product.Product;

/**
 * This class can be used to simulate requests to resources like a frontend.
 * @author Danilo
 *
 */
public class JerseyClient {
	
	private Client client;
	private WebTarget target;
	
	public JerseyClient() {
		client = ClientBuilder.newClient();
		target = client.target("http://localhost:8080/organize-products-api");
	}
	
	public ProductDto organize(List<Product> unorganizedProducts, String filter, String order) {
		Response response = target.path("v1/products/organize")
				.queryParam("filter", filter)
				.queryParam("order_by", order)
				.request(MediaType.APPLICATION_JSON)				
				.post(Entity.json(unorganizedProducts)); 
		
		if (response.getStatus() != 200) {
			throw new RuntimeException(response.getStatus() + ": request failed!");
		}
		
		return response.readEntity(ProductDto.class);
	}

}

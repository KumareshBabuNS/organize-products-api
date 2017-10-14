package com.organizeprodutsapi.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import com.organizeprodutsapi.dto.GroupResult;
import com.organizeprodutsapi.dto.ProductDto;
import com.organizeprodutsapi.product.Product;
import com.organizeprodutsapi.service.ProductService;

@Path("organize-products-api")
public class ProductResource {
	
	@Autowired
	private ProductService service;
	
	@POST
	@Path("v1/products/organize")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)	
	public ProductDto organizeProducts(List<Product> products, @QueryParam("filter") String filter, @QueryParam("order_by") String orderBy) {
		List<GroupResult> groupResult = service.organize(products, filter, orderBy);
		
		ProductDto productDto = new ProductDto(groupResult);
		
		return productDto;
	}

}

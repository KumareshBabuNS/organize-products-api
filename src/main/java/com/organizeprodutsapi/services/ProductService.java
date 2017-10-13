package com.organizeprodutsapi.services;

import java.util.List;

import com.organizeprodutsapi.product.Product;

public interface ProductService {
	
	List<Product> organize(List<Product> unorganizedProducts, String filter, String order);

	String prepareQuery(String filter, String order);
	
	boolean validateFilterOrderField(String filterOrderField);
}

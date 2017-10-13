package com.organizeprodutsapi.services;

import java.util.List;

import com.organizeprodutsapi.product.Product;

public interface ProductService {
	
	List<Product> findAll();
	
	List<Product> filterAndOrderDefault();

	Product save(Product product);

	Product findById(String string);

	void delete(String string);
	
	String prepareQuery(String filter, String order);
	
	boolean validateFilterOrderField(String filterOrderField);
}

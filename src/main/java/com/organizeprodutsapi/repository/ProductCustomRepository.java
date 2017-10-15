package com.organizeprodutsapi.repository;

import java.util.List;

import com.organizeprodutsapi.product.Product;

public interface ProductCustomRepository{
	
	List<String> loadGroups(String group, String filter, String order);
	
	List<Product> findProducts(String groupTitle, String groupDescription, String filter, String order);

}

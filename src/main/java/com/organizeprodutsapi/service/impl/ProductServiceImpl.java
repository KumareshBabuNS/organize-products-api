package com.organizeprodutsapi.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.organizeprodutsapi.dto.GroupResult;
import com.organizeprodutsapi.product.Product;
import com.organizeprodutsapi.repository.ProductRepository;
import com.organizeprodutsapi.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepository repository;
	
	/**
	 * This method organizes a list of products according to the filter and order specified.
	 * @param unorganizedProducts The unorganized list of products to be organized.
	 * @param filter The filter to be applied to the unorganized list of products.
	 * @param order The order to be applied to the unorganized list of products.
	 * @return The list of products organized according to the filter and order. 
	 * If no order is specified, the default order is applied: order by stock desc, price asc
	 */
	@Override
	public List<GroupResult> organize(List<Product> unorganizedProducts, String filter, String order) {
		List<GroupResult> groupResults = new ArrayList<GroupResult>();
		
		setupProductsList(unorganizedProducts, filter, order);
		
		if (unorganizedProducts != null) {
			List<String> descriptions = repository.loadGroups("title", filter, order);
			
			for (String description : descriptions) {
				GroupResult groupResult = new GroupResult(description);
				
				groupResult.setItems(repository.findProducts("title", description, filter, order));
				
				//Add group to the list of groups
				groupResults.add(groupResult);
			}
			
			List<String> brands = repository.loadGroups("brand", filter, order);
			
			for (String brand : brands) {
				GroupResult groupResult = new GroupResult(brand);
				
				groupResult.setItems(repository.findProducts("brand", brand, filter, order));
				
				//Add group to the list of groups
				groupResults.add(groupResult);
			}
		}
		
		return groupResults;
	}
	
	private void setupProductsList(List<Product> unorganizedProducts, String filter, String order) {
		
		repository.deleteAll();
		
		if (unorganizedProducts != null) {
			for (Product product : unorganizedProducts) {
				repository.save(product);
			}
		}
		
	}

}

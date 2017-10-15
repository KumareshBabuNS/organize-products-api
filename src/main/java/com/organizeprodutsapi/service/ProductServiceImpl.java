package com.organizeprodutsapi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.organizeprodutsapi.dto.GroupResult;
import com.organizeprodutsapi.product.Product;
import com.organizeprodutsapi.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepository repository;
	
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

	@Override
	public Product save(Product product) {
		return repository.save(product);
		
	}

	@Override
	public void deleteAll() {
		repository.deleteAll();
		
	}

	@Override
	public Product findById(String id) {
		return repository.findById(id);
	}

	@Override
	public boolean validateFields(String[] fields) {
		boolean allValidated = true;
		
		for (String field: fields) {
			if (field != null && !repository.validateField(field.split(":")[0])) {
				allValidated = false;
			}
		}
		
		return allValidated;
	}

}

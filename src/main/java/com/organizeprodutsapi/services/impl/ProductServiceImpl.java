package com.organizeprodutsapi.services.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.organizeprodutsapi.product.Product;
import com.organizeprodutsapi.repositories.ProductRepository;
import com.organizeprodutsapi.services.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	
	private static final String[] PRODUCT_FIELDS = {"id","ean","title","brand","price","stock"};
	private static final String DEFAULT_QUERY_ORDER = "order by stock desc, price asc";

	@Autowired
	private ProductRepository repository;
	
	@Override
	public List<Product> findAll() {
		return repository.findAll();
	}
	
	@Override
	public Product save(Product product) {
		return repository.save(product);
	}
	
	@Override
	public List<Product> filterAndOrderDefault() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product findById(String id) {
		return repository.findById(id);
	}

	@Override
	public void delete(String id) {
		repository.delete(id);
		
	}
	
	public String prepareQuery(String filter, String order) {
		
		StringBuffer query = new StringBuffer(); 
		query.append("select prod from Product prod ");
		
		if (filter != null && !filter.isEmpty()) {
			String filterStatement = "";
			
			//Split filter parameter expecting that rawFilter[0] becomes the filter field 
			//and rawFilter[1] the filter value.  
			String[] rawFilter = filter.split(":");
			String filterField = rawFilter[0];
						
			//If filterValue[0] is validated by validateFilterOrder then append WHERE clause in the query.
			if (validateFilterOrderField(filterField)) {
				String filterValue = "";
				
				//To the query, strings must contains "", but numerics not.
				if (rawFilter[1] != null) {
					if (filterField.equals("price") || filterField.equals("stock")) {
						filterValue = rawFilter[1] + " ";
					} else {
						filterValue = "\"" + rawFilter[1]+"\" ";
					}					
				}
				
				//Even if filter field is a valid field, field value can be empty. In this case the filter wont be applied.
				if (!filterValue.isEmpty()) {
					filterStatement = "where " + filterField + " = " + filterValue;
				}
				
				query.append(filterStatement);
			} else {
				throw new RuntimeException("Field " + filterField + " is not a valid filter field for Product.");
			}
		}
		
		if (order == null || order.isEmpty()) {
			query.append(DEFAULT_QUERY_ORDER);
		} else {
			//Split order parameter expecting that rawOrder[0] becomes the order field 
			//and rawOrder[1] has asc or desc value.  
			String[] rawOrder = order.split(":");
			String orderField = rawOrder[0];
			
			if (validateFilterOrderField(orderField)) {
				String orderAscDesc = "";
				
				//To the query, strings must contains "", but numerics not.
				if (rawOrder[1] != null) {
					orderAscDesc = rawOrder[1];
				}
				
				orderAscDesc = orderAscDesc.toLowerCase();
				
				//If orderAscDesc is not asc nor desc, it is considered as desc.
				if (!orderAscDesc.equals("desc") && !orderAscDesc.equals("asc")) {
					orderAscDesc = "desc";
				}
				
				query.append("order by " + orderField + " " + orderAscDesc);
			} else {
				throw new RuntimeException("Field " + orderField + " is not a valid order field for Product.");
			}
		}
		
		return query.toString();
	}
	
	/**
	 * This method validates if filterOrder parameter corresponds to a Product's field.
	 * @param filterOrderField must be one value that is in the array PRODUCT_FIELDS.
	 * @return true if filterOrder is one Product's field and false if not.
	 */
	public boolean validateFilterOrderField(String filterOrderField) {
		return Arrays.asList(PRODUCT_FIELDS).contains(filterOrderField.toLowerCase());
	}

}
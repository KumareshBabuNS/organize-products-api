package com.organizeprodutsapi.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.organizeprodutsapi.product.Product;
import com.organizeprodutsapi.repository.ProductRepository;
import com.organizeprodutsapi.result.GroupResult;
import com.organizeprodutsapi.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	
	private static final String[] PRODUCT_FIELDS = {"id","ean","title","brand","price","stock"};
	private static final String DEFAULT_QUERY_ORDER = "order by stock desc, price asc";
	//private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);
	
	@PersistenceContext
    private EntityManager entityManager;
	
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
	@SuppressWarnings("unchecked")
	@Override
	public List<GroupResult> organize(List<Product> unorganizedProducts, String filter, String order) {
		List<GroupResult> groupResults = new ArrayList<GroupResult>();
		
		repository.deleteAll();
		
		if (unorganizedProducts != null) {
			for (Product product : unorganizedProducts) {
				repository.save(product);
			}
			
			//Create query to return distinct description to organize description groups
			String descriptionQueryString = prepareQuery(filter, order, "title", null);
			
			Query descriptionQuery = entityManager.createQuery(descriptionQueryString);
			List<String> descriptions = descriptionQuery.getResultList();
			
			for (String description : descriptions) {
				GroupResult groupResult = new GroupResult(description);
				
				//Create query to return the list of products filtered and ordered
				String descriptionFilterQueryString = prepareQuery(filter, order, null, "and title = '" + description + "'");
				
				Query descriptionFilterQuery = entityManager.createQuery(descriptionFilterQueryString);
				
				//Set filtered items to the group
				groupResult.setItems(descriptionFilterQuery.getResultList());
				
				//Add group to the list of groups
				groupResults.add(groupResult);
			}
			
			//Create query to return distinct brand to organize brand groups
			String brandQueryString = prepareQuery(filter, order, "brand", null);
			
			Query brandQuery = entityManager.createQuery(brandQueryString);
			List<String> brands = brandQuery.getResultList();
			
			for (String brand : brands) {
				GroupResult groupResult = new GroupResult(brand);
				
				//Create query to return the list of products filtered and ordered
				String brandFilterQueryString = prepareQuery(filter, order, null, "and brand = '" + brand + "'");
				
				Query brandFilterQuery = entityManager.createQuery(brandFilterQueryString);
				
				//Set filtered items to the group
				groupResult.setItems(brandFilterQuery.getResultList());
				
				//Add group to the list of groups
				groupResults.add(groupResult);
			}
			
			repository.deleteAll();
		}
		
		return groupResults;
	}
	
	/**
	 * This method creates the JQL statement to filter and order a product list.
	 * @param filter The filter field and the value to be applied to the filter e.g.: brand:nikana.
	 * This example results in a where clause: where brand = 'nikana'. 
	 * @param order The order field and the type of order, asc or desc e.g: title:asc.
	 * This example results in an order by clause: order by title asc.
	 * @param distinctField The distinct field, if specified, changes the select clause to return distinct values of the specified field.
	 * @param extraAndClause The extraAndClause, if specified, adds an and clause to the query.
	 * @return The JQL statement.
	 */
	private String prepareQuery(String filter, String order, String distinctField, String extraAndClause) {
		
		StringBuffer query = new StringBuffer(); 
		
		if (distinctField != null && !distinctField.isEmpty()) {
			query.append("select distinct prod." + distinctField + " from Product prod ");
		} else {
			query.append("select prod from Product prod ");
		}
		
		query.append("where 0 = 0");
		
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
						filterValue = "'" + rawFilter[1]+"' ";
					}					
				}
				
				//Even if filter field is a valid field, field value can be empty. In this case the filter wont be applied.
				if (!filterValue.isEmpty()) {
					filterStatement = "and " + filterField + " = " + filterValue;
				}
				
				query.append(filterStatement);
			} else {
				throw new RuntimeException("Field " + filterField + " is not a valid filter field for Product.");
			}
		}
		
		if (extraAndClause != null) {
			query.append(extraAndClause);
		}
		
		if (distinctField != null) {
			query.append("order by prod." + distinctField);
		} else if (order == null || order.isEmpty()) {
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

package com.organizeprodutsapi.repository;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.organizeprodutsapi.product.Product;

public class ProductRepositoryImpl implements ProductCustomRepository {

	private static final String[] PRODUCT_FIELDS = {"id","ean","title","brand","price","stock"};
	private static final String DEFAULT_QUERY_ORDER = "order by stock desc, price asc";
	
	@PersistenceContext
    private EntityManager entityManager;
	
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
		
		query.append(" where 0 = 0 ");
		
		if (filter != null && !filter.isEmpty()) {
			String filterStatement = "";
			
			//Split filter parameter expecting that rawFilter[0] becomes the filter field 
			//and rawFilter[1] the filter value.  
			String[] rawFilter = filter.split(":");
			String filterField = rawFilter[0];
						
			//If filterValue[0] is validated by validateFilterOrder then append WHERE clause in the query.
			if (validateField(filterField)) {
				String filterValue = "";
				
				//To the query, strings must contains "", but numerics not.
				if (rawFilter[1] != null) {
					if (filterField.equals("price") || filterField.equals("stock")) {
						filterValue = rawFilter[1] + " ";
					} else {
						filterValue = " '" + rawFilter[1]+"' ";
					}					
				}
				
				//Even if filter field is a valid field, field value can be empty. In this case the filter wont be applied.
				if (!filterValue.isEmpty()) {
					filterStatement = " and " + filterField + " = " + filterValue + " ";
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
			
			if (validateField(orderField)) {
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
	
	@Override
	public boolean validateField(String field) {
		if (field == null) {
			return true;
		}
		
		return Arrays.asList(PRODUCT_FIELDS).contains(field.toLowerCase());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> loadGroups(String group, String filter, String order) {
		//Create query to return distinct description to organize description groups
		String groupQueryString = prepareQuery(filter, order, group, null);
		
		Query groupQuery = entityManager.createQuery(groupQueryString);
		return groupQuery.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Product> findProducts(String groupTitle, String groupDescription, String filter, String order) {
		//Create query to return the list of products filtered and ordered
		String groupFilterQueryString = "";
		
		if (groupDescription != null) {
			groupFilterQueryString = prepareQuery(filter, order, null, " and " + groupTitle + " = '" + groupDescription + "' ");
		} else {
			groupFilterQueryString = prepareQuery(filter, order, null, " and " + groupTitle + " is null ");
		}
		
		Query groupFilterQuery = entityManager.createQuery(groupFilterQueryString);
		
		//Set filtered items to the group
		return groupFilterQuery.getResultList();
	}

}

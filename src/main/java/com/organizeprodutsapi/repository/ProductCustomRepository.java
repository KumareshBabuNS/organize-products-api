package com.organizeprodutsapi.repository;

import java.util.List;

import com.organizeprodutsapi.product.Product;

/**
 * Product Respository with customized methods.
 * @author Danilo
 *
 */
public interface ProductCustomRepository{
	
	/**
	 * Loads distinct information from database based on group, filter and order.
	 * @param group The field to return distinct information. e. g. title. This produces a query like: select distinct title ... 
	 * @param filter The filter field and the value to be applied to the filter e.g.: brand:nikana.
	 * This example results in a where clause: where brand = 'nikana'.
	 * @param The order field and the type of order, asc or desc e.g: title:asc.
	 * This example results in an order by clause: order by title asc.
	 * @return List of string with distinct information.
	 */
	List<String> loadGroups(String group, String filter, String order);
	
	/**
	 * Load products according to the group field and description.
	 * @param groupTitle The field to filter group. 
	 * @param groupDescription The value of the filter.
	 * @param filter The original filter of the request.
	 * @param order The original order og the request.
	 * @return
	 */
	List<Product> findProducts(String groupTitle, String groupDescription, String filter, String order);

	/**
	 * This method validates if filterOrder parameter corresponds to a Product's field.
	 * @param filterOrderField must be one value that is in the array PRODUCT_FIELDS.
	 * @return true if filterOrder is one Product's field and false if not. Null fields are considered valid.
	 */
	boolean validateField(String field);

}

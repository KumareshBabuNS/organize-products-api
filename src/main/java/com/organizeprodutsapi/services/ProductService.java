package com.organizeprodutsapi.services;

import java.util.List;

import com.organizeprodutsapi.product.Product;

/**
 * This class does the core work to organize the product list.
 * @author Danilo
 *
 */
public interface ProductService {
	
	/**
	 * This method organizes a list of products according to the filter and order specified.
	 * @param unorganizedProducts The unorganized list of products to be organized.
	 * @param filter The filter to be applied to the unorganized list of products.
	 * @param order The order to be applied to the unorganized list of products.
	 * @return The list of products organized according to the filter and order. 
	 * If no order is specified, the default order is applied: order by stock desc, price asc
	 */
	List<Product> organize(List<Product> unorganizedProducts, String filter, String order);

	/**
	 * This method creates the JQL statement to filter and order a product list.
	 * @param filter The filter field and the value to be applied to the filter e.g.: brand:nikana.
	 * This example results in a where clause: where brand = 'nikana'. 
	 * @param order The order field and the type of order, asc or desc e.g: title:asc.
	 * This example results in an order by clause: order by title asc.
	 * @return The JQL statement.
	 */
	String prepareQuery(String filter, String order);
	
	/**
	 * This method validates if filterOrder parameter corresponds to a Product's field.
	 * @param filterOrderField must be one value that is in the array PRODUCT_FIELDS.
	 * @return true if filterOrder is one Product's field and false if not.
	 */
	boolean validateFilterOrderField(String filterOrderField);
}

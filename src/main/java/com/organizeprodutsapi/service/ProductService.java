package com.organizeprodutsapi.service;

import java.util.List;

import com.organizeprodutsapi.dto.GroupResult;
import com.organizeprodutsapi.product.Product;

/**
 * Product Service does the core work to organize the product list.
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
	List<GroupResult> organize(List<Product> unorganizedProducts, String filter, String order);

	Product save(Product product);

	void deleteAll();

	Product findById(String id);

	/**
	 * This method validates if filterOrder parameter corresponds to a Product's field.
	 * @param filterOrderField must be one value that is in the array PRODUCT_FIELDS.
	 * @return true if filterOrder is one Product's field and false if not. Null fields are considered valid.
	 */
	boolean validateFields(String[] strings);
}

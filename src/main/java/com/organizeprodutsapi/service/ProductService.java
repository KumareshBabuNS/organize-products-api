package com.organizeprodutsapi.service;

import java.util.List;

import com.organizeprodutsapi.product.Product;
import com.organizeprodutsapi.result.GroupResult;

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
	List<GroupResult> organize(List<Product> unorganizedProducts, String filter, String order);
}

package com.organizeprodutsapi.result;

import java.util.List;

import com.organizeprodutsapi.product.Product;

/**
 * The object of this type carries grouped information about each description.
 * @author Danilo
 *
 */
public class GroupResult {
	
	private String description;	
	private List<Product> items;
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<Product> getItems() {
		return items;
	}
	public void setItems(List<Product> items) {
		this.items = items;
	}

}
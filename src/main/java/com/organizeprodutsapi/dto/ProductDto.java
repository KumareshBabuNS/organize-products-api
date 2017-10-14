package com.organizeprodutsapi.dto;

import java.util.List;

/**
 * The object of this type transports the information in a format according to the domain requirements.
 * @author Danilo
 *
 */
public class ProductDto {
	
	private List<GroupResult> data;
	
	public ProductDto() {
		super();
	}
	
	public ProductDto(List<GroupResult> groupResult) {
		this.data = groupResult;
	}

	public List<GroupResult> getData() {
		return data;
	}

	public void setData(List<GroupResult> data) {
		this.data = data;
	}

}

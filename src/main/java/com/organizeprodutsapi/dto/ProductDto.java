package com.organizeprodutsapi.dto;

import java.util.List;

import com.organizeprodutsapi.result.GroupResult;

/**
 * The object of this type transports the information in a format according to the domain requirements.
 * @author Danilo
 *
 */
public class ProductDto {
	
	private List<GroupResult> data;

	public List<GroupResult> getData() {
		return data;
	}

	public void setData(List<GroupResult> data) {
		this.data = data;
	}

}

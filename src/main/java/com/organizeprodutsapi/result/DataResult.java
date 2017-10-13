package com.organizeprodutsapi.result;

import java.util.List;

/**
 * The object of this type transports the information in a format according to the requirements.
 * @author Danilo
 *
 */
public class DataResult {
	
	private List<GroupResult> data;

	public List<GroupResult> getData() {
		return data;
	}

	public void setData(List<GroupResult> data) {
		this.data = data;
	}

}

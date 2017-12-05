package com.whir.ht.cms;

import java.io.Serializable;
import java.util.List;

public class MaterialResponse extends Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List data;

	public List getData() {
		return data;
	}

	public void setData(List data) {
		this.data = data;
	}
	

}

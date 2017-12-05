package com.whir.ht.webservice.model.order;

import java.util.List;

import com.whir.ht.webservice.model.BaseResult;

public class OrderSerResponse extends BaseResult {
	private List<OrderSerBean> data;//  接口调用的返回的网点信息列表 (String/Ojbect类型)

	public List<OrderSerBean> getReturnlist() {
		return data;
	}

	public void setReturnlist(List<OrderSerBean> data) {
		this.data = data;
	}
	
}

package com.whir.ht.webservice.model.order;

import java.util.List;

import com.whir.ht.webservice.model.BaseResult;

public class WebOrderDetailsResponse extends BaseResult {
	private List<WebOrderDetailsBean> data;//  接口调用的返回的订单详情列表 (String/Ojbect类型)

	public List<WebOrderDetailsBean> getReturnlist() {
		return data;
	}

	public void setReturnlist(List<WebOrderDetailsBean> data) {
		this.data = data;
	}
}

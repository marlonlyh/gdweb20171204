package com.whir.ht.webservice.model.order;

import java.util.List;

import com.whir.ht.webservice.model.BaseResult;

public class WebOrderSerResponse extends BaseResult {
	private List<WebOrderSerBean> data;//  接口调用的返回的订单列表 (String/Ojbect类型)

	public List<WebOrderSerBean> getReturnlist() {
		return data;
	}

	public void setReturnlist(List<WebOrderSerBean> data) {
		this.data = data;
	}
}

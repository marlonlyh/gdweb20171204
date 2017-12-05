package com.whir.ht.webservice.model.gps;

import java.util.List;

import com.whir.ht.webservice.model.BaseResult;

public class GpsInfosResponse extends BaseResult {
	private List<GpsInfoBean> data;//  接口调用的返回的订单列表 (String/Ojbect类型)

	public List<GpsInfoBean> getData() {
		return data;
	}

	public void setData(List<GpsInfoBean> data) {
		this.data = data;
	}
}

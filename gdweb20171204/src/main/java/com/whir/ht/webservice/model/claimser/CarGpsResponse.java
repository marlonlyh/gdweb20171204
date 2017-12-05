package com.whir.ht.webservice.model.claimser;

import java.util.List;

import com.whir.ht.webservice.model.BaseResult;

/**
 * 指定车辆的GPS信息结果
 * @author chenshaofeng
 *
 */
public class CarGpsResponse extends BaseResult {
	private List<CarGpsBean> data;//  接口调用的返回指定车辆的GPS信息列表 (String/Ojbect类型)

	public List<CarGpsBean> getReturnlist() {
		return data;
	}

	public void setReturnlist(List<CarGpsBean> data) {
		this.data = data;
	}
	
}

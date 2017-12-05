package com.whir.ht.webservice.model.claimser;

import java.util.List;

import com.whir.ht.webservice.model.BaseResult;

/**
 * 线路费用查询返回结果
 * @author chenshaofeng
 *
 */
public class WebLinePricesResponse extends BaseResult {
	private List<WebLinePricesBean> data;//  接口调用的返回的线路价格详情列表 (String/Ojbect类型)

	public List<WebLinePricesBean> getReturnlist() {
		return data;
	}

	public void setReturnlist(List<WebLinePricesBean> data) {
		this.data = data;
	}
	
}

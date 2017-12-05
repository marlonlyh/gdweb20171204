package com.whir.ht.webservice.model.claimser;

import com.whir.ht.webservice.model.BaseParameter;

/**
 *  线路价格查询参数
 * @author chenshaofeng
 *
 */
public class WebLinePricesRequest extends BaseParameter {
	
	private WebLinePricesBusiness data;// JSON格式的字符串,即需要提交的具体接口中的应用参数

	public WebLinePricesBusiness getData() {
		return data;
	}

	public void setData(WebLinePricesBusiness data) {
		this.data = data;
	}
	
	
}

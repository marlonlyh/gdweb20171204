package com.whir.ht.webservice.model.order;

import com.whir.ht.webservice.model.BaseParameter;

/**
 *  订单查询接口参数
 * @author chenshaofeng
 *
 */
public class WebOrderSerRequest extends BaseParameter {
	
	private WebOrderSerBusiness data;// JSON格式的字符串,即需要提交的具体接口中的应用参数

	public WebOrderSerBusiness getData() {
		return data;
	}

	public void setData(WebOrderSerBusiness data) {
		this.data = data;
	}
	
}

package com.whir.ht.webservice.model.order;

import com.whir.ht.webservice.model.BaseParameter;

/**
 *  订单详情参数
 * @author chenshaofeng
 *
 */
public class WebOrderDetailsRequest extends BaseParameter {
	
	private WebOrderDetailsBusiness data;// JSON格式的字符串,即需要提交的具体接口中的应用参数

	public WebOrderDetailsBusiness getData() {
		return data;
	}

	public void setData(WebOrderDetailsBusiness data) {
		this.data = data;
	}
	
}

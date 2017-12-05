package com.whir.ht.webservice.model.order;

import com.whir.ht.webservice.model.BaseParameter;

/**
 * 订单下单, 修改, 取消接口参数
 * @author chenshaofeng
 *  
 */
public class WebOrderCtlRequest extends BaseParameter {
	private WebOrderCtlBusiness data;// JSON格式的字符串,即需要提交的具体接口中的应用参数

	public WebOrderCtlBusiness getData() {
		return data;
	}

	public void setData(WebOrderCtlBusiness data) {
		this.data = data;
	}
	
	
}

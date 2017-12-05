package com.whir.ht.webservice.model.order;

import com.whir.ht.webservice.model.BaseParameter;

/**
 *  运单查询参数
 * @author chenshaofeng
 *
 */
public class OrderSerRequest extends BaseParameter{

	private OrderSerBusiness data;// JSON格式的字符串,即需要提交的具体接口中的应用参数

	public OrderSerBusiness getData() {
		return data;
	}

	public void setData(OrderSerBusiness data) {
		this.data = data;
	}
	
}

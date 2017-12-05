package com.whir.ht.webservice.model.order;

import java.util.List;

import com.whir.ht.webservice.model.BaseResult;

/**
 * 订单下单, 修改, 取消接口返回结果
 * @author chenshaofeng
 * 注意:接口返回结只有"result", 且并没有提供数据类型. 这里实现为String
 */
public class WebOrderCtlResponse extends BaseResult {
	private List<WebOrderCtlBean> data;//  接口调用的返回的网点信息列表 (String/Ojbect类型)

	public List<WebOrderCtlBean> getReturnlist() {
		return data;
	}

	public void setReturnlist(List<WebOrderCtlBean> data) {
		this.data = data;
	}
	
}

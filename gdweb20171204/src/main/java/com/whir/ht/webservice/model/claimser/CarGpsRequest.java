package com.whir.ht.webservice.model.claimser;

import com.whir.ht.webservice.model.BaseParameter;

/**
 * 请求车辆GPS信息参数
 * @author chenshaofeng
 * 注意:"3.7 获取指定车辆GPS信息"与描述不一致
 */
public class CarGpsRequest extends BaseParameter {
	
	private CarGpsBusiness data;// JSON格式的字符串,即需要提交的具体接口中的应用参数

	public CarGpsBusiness getData() {
		return data;
	}

	public void setData(CarGpsBusiness data) {
		this.data = data;
	}
	
}

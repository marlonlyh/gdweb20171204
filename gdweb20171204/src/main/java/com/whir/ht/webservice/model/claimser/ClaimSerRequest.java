package com.whir.ht.webservice.model.claimser;

import com.whir.ht.webservice.model.BaseParameter;
/**
 * 理赔查询参数
 * @author chenshaofeng
 *
 */
public class ClaimSerRequest extends BaseParameter {
	
	private ClaimSerBusiness data;// JSON格式的字符串,即需要提交的具体接口中的应用参数

	public ClaimSerBusiness getData() {
		return data;
	}

	public void setData(ClaimSerBusiness data) {
		this.data = data;
	}
	
}

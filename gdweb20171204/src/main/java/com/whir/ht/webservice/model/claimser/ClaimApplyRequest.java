package com.whir.ht.webservice.model.claimser;

import com.whir.ht.webservice.model.BaseParameter;

/**
 *  理赔申请参数
 * @author chenshaofeng
 *
 */
public class ClaimApplyRequest extends BaseParameter {
	
	private ClaimApplyBusiness data;// JSON格式的字符串,即需要提交的具体接口中的应用参数

	public ClaimApplyBusiness getData() {
		return data;
	}

	public void setData(ClaimApplyBusiness data) {
		this.data = data;
	}
	
}

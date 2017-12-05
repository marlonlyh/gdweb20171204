package com.whir.ht.webservice.model.claimser;

import java.util.List;

import com.whir.ht.webservice.model.BaseResult;

/**
 * 理赔申请结果
 * @author chenshaofeng
 *
 */
public class ClaimApplyResponse extends BaseResult {
	private List<ClaimApplyBean> data;//  接口调用的返回的网点信息列表 (String/Ojbect类型)

	public List<ClaimApplyBean> getReturnlist() {
		return data;
	}

	public void setReturnlist(List<ClaimApplyBean> data) {
		this.data = data;
	}
}

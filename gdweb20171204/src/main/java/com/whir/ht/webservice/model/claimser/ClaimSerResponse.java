package com.whir.ht.webservice.model.claimser;

import java.util.List;

import com.whir.ht.webservice.model.BaseResult;

/** 
 * 理赔查询结果
 * @author chenshaofeng
 *
 */
public class ClaimSerResponse extends BaseResult {
	private List<ClaimSerBean> data;//  接口调用的返回理赔查询详情列表 (String/Ojbect类型)

	public List<ClaimSerBean> getReturnlist() {
		return data;
	}

	public void setReturnlist(List<ClaimSerBean> data) {
		this.data = data;
	}
	
}

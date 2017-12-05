package com.whir.ht.webservice.model.branch;

import java.util.List;

import com.whir.ht.webservice.model.BaseResult;

/**
 * 网点查询返回结果
 * @author chenshaofeng
 *
 */
public class BranchInfosResponse extends BaseResult {
	private List<BranchInfosBean> data;//  接口调用的返回的网点信息列表 (String/Ojbect类型)

	public List<BranchInfosBean> getReturnlist() {
		return data;
	}

	public void setReturnlist(List<BranchInfosBean> data) {
		this.data = data;
	}
}

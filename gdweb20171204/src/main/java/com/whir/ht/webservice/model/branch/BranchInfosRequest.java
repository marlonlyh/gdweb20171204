package com.whir.ht.webservice.model.branch;

import com.whir.ht.webservice.model.BaseParameter;

/**
 * 网点查询参数,系统参数从BaseParameter继承
 * @author chenshaofeng
 *
 */
public class BranchInfosRequest extends BaseParameter {
	private BranchInfosBusiness data;// JSON格式的字符串,即需要提交的具体接口中的应用参数

	public BranchInfosBusiness getData() {
		return data;
	}

	public void setData(BranchInfosBusiness data) {
		this.data = data;
	}
	
	
}


package com.whir.ht.webservice.model.order;

/**
 * 订单查询接口业务参数
 * @author chenshaofeng
 *
 */
public class WebOrderSerBusiness {

	private String webordertime;// 订单日期
	private String weborderstatus;// 订单状态
	private String weborderorderid;// 订单号码
	private String webordertel;// 手机号码
	
	public String getWebordertime() {
		return webordertime;
	}
	public void setWebordertime(String webordertime) {
		this.webordertime = webordertime;
	}
	public String getWeborderstatus() {
		return weborderstatus;
	}
	public void setWeborderstatus(String weborderstatus) {
		this.weborderstatus = weborderstatus;
	}
	public String getWeborderorderid() {
		return weborderorderid;
	}
	public void setWeborderorderid(String weborderid) {
		this.weborderorderid = weborderid;
	}
	public String getWebordertel() {
		return webordertel;
	}
	public void setWebordertel(String webordertel) {
		this.webordertel = webordertel;
	}
	
}

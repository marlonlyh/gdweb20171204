package com.whir.ht.webservice.model.order;

/**
 *  订单信息
 * @author chenshaofeng
 *
 */
public class WebOrderSerBean {
	private String weborderorderid;// 订单号
	private String webordertime;// 订单日期
	private String weborderstatus;// 订单状态
	private String orderid;// 运单号
	private String shipperdepartment;// 承运部门
	private String orderstatus;// 运单状态
	
	public String getWeborderorderid() {
		return weborderorderid;
	}
	public void setWeborderorderid(String weborderorderid) {
		this.weborderorderid = weborderorderid;
	}
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
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getShipperdepartment() {
		return shipperdepartment;
	}
	public void setShipperdepartment(String shipperdepartment) {
		this.shipperdepartment = shipperdepartment;
	}
	public String getOrderstatus() {
		return orderstatus;
	}
	public void setOrderstatus(String orderstatus) {
		this.orderstatus = orderstatus;
	}
	
	
}

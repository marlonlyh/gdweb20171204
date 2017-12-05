package com.whir.ht.webservice.model.order;

import com.whir.ht.webservice.model.BaseResult;
/**
 * 运单详情
 * @author chenshaofeng
 *
 */
public class OrderSerBean {
	private String orderid;// 运单号
	private String billingtime;// 制单时间
	private String orderstatus;// 运单状态
	private String paymentstatus;// 货款状态
	private String backreceiptstatus;// 回单状态
	
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getBillingtime() {
		return billingtime;
	}
	public void setBillingtime(String billingtime) {
		this.billingtime = billingtime;
	}
	public String getOrderstatus() {
		return orderstatus;
	}
	public void setOrderstatus(String orderstatus) {
		this.orderstatus = orderstatus;
	}
	public String getPaymentstatus() {
		return paymentstatus;
	}
	public void setPaymentstatus(String paymentstatus) {
		this.paymentstatus = paymentstatus;
	}
	public String getBackreceiptstatus() {
		return backreceiptstatus;
	}
	public void setBackreceiptstatus(String backreceiptstatus) {
		this.backreceiptstatus = backreceiptstatus;
	}
	
	
}

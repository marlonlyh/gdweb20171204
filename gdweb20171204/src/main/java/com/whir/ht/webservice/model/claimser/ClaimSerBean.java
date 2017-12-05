package com.whir.ht.webservice.model.claimser;

/**
 * 理赔查询结果详情
 * @author chenshaofeng
 *
 */
public class ClaimSerBean {
	private String orderid;// 订单号
	private String claimappdate;// 理赔申请日期
	private String claimstatus;// 理赔状态
	
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getClaimappdate() {
		return claimappdate;
	}
	public void setClaimappdate(String claimappdate) {
		this.claimappdate = claimappdate;
	}
	public String getClaimstatus() {
		return claimstatus;
	}
	public void setClaimstatus(String claimstatus) {
		this.claimstatus = claimstatus;
	}
	
	
}

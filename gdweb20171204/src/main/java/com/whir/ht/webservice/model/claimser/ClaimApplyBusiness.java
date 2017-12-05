package com.whir.ht.webservice.model.claimser;

/**
 *  理赔申请业务参数
 * @author chenshaofeng
 *
 */
public class ClaimApplyBusiness {

	private String orderid;// 运单号
	private String identification;// 身份证
	
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getIdentification() {
		return identification;
	}
	public void setIdentification(String identification) {
		this.identification = identification;
	}
	
}

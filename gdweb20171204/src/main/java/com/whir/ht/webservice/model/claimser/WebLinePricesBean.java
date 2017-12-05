package com.whir.ht.webservice.model.claimser;

/**
 * 线路费用
 * @author chenshaofeng
 *
 */
public class WebLinePricesBean {
	private String startbanch;// 始发地
	private String destinationbranch;// 目的地
	private String linename;// 线路名称
	private String prescriptionstandard;// 时效标准
	private String weightrate;// 重量费率
	private String volumerate;// 体积费率
	private String baroquerate;// 异形费率
	private String agencyfundrate;// 代收货款费率
	private String insurancefeerate;// 保价费率
	private String backreceiptrate;// 回单费用
	private String notifyfee;// 等通知放货费用
	
	public String getStartbanch() {
		return startbanch;
	}
	public void setStartbanch(String startbanch) {
		this.startbanch = startbanch;
	}
	public String getDestinationbranch() {
		return destinationbranch;
	}
	public void setDestinationbranch(String destinationbranch) {
		this.destinationbranch = destinationbranch;
	}
	public String getLinename() {
		return linename;
	}
	public void setLinename(String linename) {
		this.linename = linename;
	}
	public String getPrescriptionstandard() {
		return prescriptionstandard;
	}
	public void setPrescriptionstandard(String prescriptionstandard) {
		this.prescriptionstandard = prescriptionstandard;
	}
	public String getWeightrate() {
		return weightrate;
	}
	public void setWeightrate(String weightrate) {
		this.weightrate = weightrate;
	}
	public String getVolumerate() {
		return volumerate;
	}
	public void setVolumerate(String volumerate) {
		this.volumerate = volumerate;
	}
	public String getBaroquerate() {
		return baroquerate;
	}
	public void setBaroquerate(String baroquerate) {
		this.baroquerate = baroquerate;
	}
	public String getAgencyfundrate() {
		return agencyfundrate;
	}
	public void setAgencyfundrate(String agencyfundrate) {
		this.agencyfundrate = agencyfundrate;
	}
	public String getInsurancefeerate() {
		return insurancefeerate;
	}
	public void setInsurancefeerate(String insurancefeerate) {
		this.insurancefeerate = insurancefeerate;
	}
	public String getBackreceiptrate() {
		return backreceiptrate;
	}
	public void setBackreceiptrate(String backreceiptrate) {
		this.backreceiptrate = backreceiptrate;
	}
	public String getNotifyfee() {
		return notifyfee;
	}
	public void setNotifyfee(String notifyfee) {
		this.notifyfee = notifyfee;
	}
	
	
}

package com.whir.ht.webservice.model.order;

/**
 *  订单详情对象
 * @author chenshaofeng
 *  注意: 文档中定义的返回订单详情列有中重复定义了orderstatus
 */
public class WebOrderDetailsBean {
	private String weborderorderid; // 订单号
	private String webordertime;// 订单日期
	private String weborderstatus;// 订单状态
	private String orderid;// 运单号
	private String shipperdepartment;// 承运部门
	private String orderstatus;// 运单状态
	private String receiver;// 收货人姓名
	private String receivertel;// 收货人电话
	private String receivermobile;// 收货人手机
	private String receiveraddress;// 收货人地址
	private String cargoname;// 品名
	private String quantity;//件数
	private String cargopackage;// 包装
	private String weight;// 重量
	private String volume;// 体积
	private String insurancefee;// 声明保价金额
	private String agencyfundamount;// 代收货款
	private String pickupmode;// 提货方式
	private String receipt;// 签收单
	private String clearingform;// 付款方式
	private String freight;// 运费(所有运费明细都给以一个JSON的形式提供)返回结果为:{"getfee":"10", "startfee":"10", "destinationfees":"10", "longdistancefreight":"10", "deliverfee":"10","sumfee":"50"}
								   	// 描述:
									// 接货费  getfee
									// 起运地其它费用 startfee
									// 目的地其它费用 destinationfees
									// 长途运费 longdistancefreight
									// 送货费 deliverfee
									// 运费合计 sumfee
	private String smsnotify;// 短信通知
	private String isd2dget;// 上门接货
	private String backreceiptstatus;// 回单状态
	private String paymentstatus;// 货款状态
	
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
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getReceivertel() {
		return receivertel;
	}
	public void setReceivertel(String receivertel) {
		this.receivertel = receivertel;
	}
	public String getReceivermobile() {
		return receivermobile;
	}
	public void setReceivermobile(String receivermobile) {
		this.receivermobile = receivermobile;
	}
	public String getReceiveraddress() {
		return receiveraddress;
	}
	public void setReceiveraddress(String receiveraddress) {
		this.receiveraddress = receiveraddress;
	}
	public String getCargoname() {
		return cargoname;
	}
	public void setCargoname(String cargoname) {
		this.cargoname = cargoname;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getCargopackage() {
		return cargopackage;
	}
	public void setCargopackage(String cargopackage) {
		this.cargopackage = cargopackage;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getVolume() {
		return volume;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	public String getInsurancefee() {
		return insurancefee;
	}
	public void setInsurancefee(String insurancefee) {
		this.insurancefee = insurancefee;
	}
	public String getAgencyfundamount() {
		return agencyfundamount;
	}
	public void setAgencyfundamount(String agencyfundamount) {
		this.agencyfundamount = agencyfundamount;
	}
	public String getPickupmode() {
		return pickupmode;
	}
	public void setPickupmode(String pickupmode) {
		this.pickupmode = pickupmode;
	}
	public String getReceipt() {
		return receipt;
	}
	public void setReceipt(String receipt) {
		this.receipt = receipt;
	}
	public String getClearingform() {
		return clearingform;
	}
	public void setClearingform(String clearingform) {
		this.clearingform = clearingform;
	}
	public String getFreight() {
		return freight;
	}
	public void setFreight(String freight) {
		this.freight = freight;
	}
	public String getSmsnotify() {
		return smsnotify;
	}
	public void setSmsnotify(String smsnotify) {
		this.smsnotify = smsnotify;
	}
	public String getIsd2dget() {
		return isd2dget;
	}
	public void setIsd2dget(String isd2dget) {
		this.isd2dget = isd2dget;
	}
	public String getBackreceiptstatus() {
		return backreceiptstatus;
	}
	public void setBackreceiptstatus(String backreceiptstatus) {
		this.backreceiptstatus = backreceiptstatus;
	}
	public String getPaymentstatus() {
		return paymentstatus;
	}
	public void setPaymentstatus(String paymentstatus) {
		this.paymentstatus = paymentstatus;
	}
	
	
}

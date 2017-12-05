package com.whir.ht.webservice.model.order;

/**
 *  订单下单, 修改, 取消接口业务参数
 * @author chenshaofeng
 *  注意:接口文档中把"件数"定义为"package", 跟关键字有冲突,这里改成"packages"
 *  
 */
public class WebOrderCtlBusiness {

	private String shipper;// 发货人姓名
	private String shippermobile;// 发货人电话
	private String shippertel;// 发货人手机
	private String shipperaddress;// 发货人地址
	private String weborderid;// 订单号
	private String webordertime;// 订单日期
	private String receiver;// 收货人姓名
	private String receivermobile;// 收货人手机
	private String receivertel;// 收货人电话
	private String receiveraddress;// 收货人地址
	private String cargoname;// 品名
	private String packages;// 包装
	private String quantity;
	private String wight;// 重量
	private String volume;// 体积
	private String insurancefee;// 声明保价金额
	private String agencyfundamount;// 代收货款
	private String pickupmode;// 提货方式
	private String receipt;// 签收单
	private String clearingform;// 付款方式
	private String smsnotify;// 短信通知
	private String isd2dget;// 上门接货
	private String opmark;// 操作标记
	private String cancelreason;// 取消原因
	private String canceldate;// 取消日期
	private String getbranchno;// 接货网点编号
	private String getbranchname;// 接货网点名称
	private String optype;// 操作类型(0 新增, 1 修改, 2 删除)
	
	public String getShipper() {
		return shipper;
	}
	public void setShipper(String shipper) {
		this.shipper = shipper;
	}
	public String getShippermobile() {
		return shippermobile;
	}
	public void setShippermobile(String shippermobile) {
		this.shippermobile = shippermobile;
	}
	public String getShippertel() {
		return shippertel;
	}
	public void setShippertel(String shippertel) {
		this.shippertel = shippertel;
	}
	public String getShipperaddress() {
		return shipperaddress;
	}
	public void setShipperaddress(String shipperaddress) {
		this.shipperaddress = shipperaddress;
	}
	public String getWeborderid() {
		return weborderid;
	}
	public void setWeborderid(String weborderid) {
		this.weborderid = weborderid;
	}
	public String getWebordertime() {
		return webordertime;
	}
	public void setWebordertime(String webordertime) {
		this.webordertime = webordertime;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getReceivermobile() {
		return receivermobile;
	}
	public void setReceivermobile(String receivermobile) {
		this.receivermobile = receivermobile;
	}
	public String getReceivertel() {
		return receivertel;
	}
	public void setReceivertel(String receivertel) {
		this.receivertel = receivertel;
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
	public String getPackages() {
		return packages;
	}
	public void setPackages(String packages) {
		this.packages = packages;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getWight() {
		return wight;
	}
	public void setWight(String wight) {
		this.wight = wight;
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
	public String getOpmark() {
		return opmark;
	}
	public void setOpmark(String opmark) {
		this.opmark = opmark;
	}
	public String getCancelreason() {
		return cancelreason;
	}
	public void setCancelreason(String cancelreason) {
		this.cancelreason = cancelreason;
	}
	public String getCanceldate() {
		return canceldate;
	}
	public void setCanceldate(String canceldate) {
		this.canceldate = canceldate;
	}
	public String getGetbranchno() {
		return getbranchno;
	}
	public void setGetbranchno(String getbranchno) {
		this.getbranchno = getbranchno;
	}
	public String getGetbranchname() {
		return getbranchname;
	}
	public void setGetbranchname(String getbranchname) {
		this.getbranchname = getbranchname;
	}
	public String getOptype() {
		return optype;
	}
	public void setOptype(String optype) {
		this.optype = optype;
	}
	
	
}

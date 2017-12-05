package com.whir.ht.webservice.model.claimser;

/**
 *  线路价格查询业务参数
 * @author chenshaofeng
 *
 */
public class WebLinePricesBusiness {

	private String shipperprovince;// 发货人所在省
	private String shippercity;// 发货人所在市
	private String shipperarea;// 发货人所在区
	private String receiverprovince;// 收货人所在省
	private String receivercity;//收货人所在市
	private String receiverarea;//收货人所在区
	
	public String getShipperprovince() {
		return shipperprovince;
	}
	public void setShipperprovince(String shipperprovince) {
		this.shipperprovince = shipperprovince;
	}
	public String getShippercity() {
		return shippercity;
	}
	public void setShippercity(String shippercity) {
		this.shippercity = shippercity;
	}
	public String getShipperarea() {
		return shipperarea;
	}
	public void setShipperarea(String shipperarea) {
		this.shipperarea = shipperarea;
	}
	public String getReceiverprovince() {
		return receiverprovince;
	}
	public void setReceiverprovince(String receiverprovince) {
		this.receiverprovince = receiverprovince;
	}
	public String getReceivercity() {
		return receivercity;
	}
	public void setReceivercity(String receivercity) {
		this.receivercity = receivercity;
	}
	public String getReceiverarea() {
		return receiverarea;
	}
	public void setReceiverarea(String receiverarea) {
		this.receiverarea = receiverarea;
	}
	
}

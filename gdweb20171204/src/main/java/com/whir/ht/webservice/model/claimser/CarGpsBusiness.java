package com.whir.ht.webservice.model.claimser;

/**
 * 请求车辆GPS信息参数
 * @author chenshaofeng
 * 注意:"3.7 获取指定车辆GPS信息"与描述不一致
 */
public class CarGpsBusiness {
	
	private String vehicleno; // 车辆编号

	public String getVehicleno() {
		return vehicleno;
	}

	public void setVehicleno(String vehicleno) {
		this.vehicleno = vehicleno;
	}
	
}

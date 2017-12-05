package com.whir.ht.webservice.model.branch;

/**
 * 网点查询业务参数
 * @author chenshaofeng
 *
 */
public class BranchInfosBusiness {
	private String province;// 省份
	private String city;// 城市
	private String area;// 区域
	private String address;// 地址

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}

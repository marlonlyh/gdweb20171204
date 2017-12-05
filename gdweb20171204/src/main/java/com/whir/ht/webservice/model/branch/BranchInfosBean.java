package com.whir.ht.webservice.model.branch;

/**
 *  网点信息说明
 * @author chenshaofeng
 *
 */
public class BranchInfosBean {
	private String branchno;// 网点编号
	private String branchname;// 网点名称
	private String manager;// 联系人
	private String tel;// 联系电话
	private String lnt;// 网点经度
	private String lat;// 网点维度
	private String address;//网点地址
	private String service;// 网点提供服务如: 接货, 送货, 提货, 代收款服务描述
	
	public String getBranchno() {
		return branchno;
	}
	public void setBranchno(String branchno) {
		this.branchno = branchno;
	}
	public String getBranchname() {
		return branchname;
	}
	public void setBranchname(String branchname) {
		this.branchname = branchname;
	}
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getLnt() {
		return lnt;
	}
	public void setLnt(String lnt) {
		this.lnt = lnt;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	
	
}

package com.whir.ht.webservice.model;

/**
 * 系统级参数
 * @author chenshaofeng
 *  
 */
public class BaseParameter {
	private String method;// 指具体API接口的名称, 必须指定
	private String timestamp;//时间戳,格式为yyyy-mm-dd hh:mm:ss, 例如:2014-09-18 20:23:30
	private String app_key;// 指盛辉方接口平台分配的授权码
	private String sign;// 指API输入参数的签名摘要
	private String format;// 指接口返回的数据格式,默认JSON, 目前只支持json
	
	public BaseParameter() {
		this.setFormat("JSON");
	}
	
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getApp_key() {
		return app_key;
	}
	public void setApp_key(String app_key) {
		this.app_key = app_key;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	
	
}

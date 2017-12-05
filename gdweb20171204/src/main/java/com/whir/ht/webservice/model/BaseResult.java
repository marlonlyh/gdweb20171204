package com.whir.ht.webservice.model;

/**
 * 接口返回数据格式
 * @author chenshaofeng
 * 注意: data为字符串或JSON对象类型
 */
public class BaseResult {
	private String code;// 接口状态码, 0:代表成功, 非0:有错误
	private String message;// 接口调用不成功时的状态描述, 当code=0时,为空

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	

}

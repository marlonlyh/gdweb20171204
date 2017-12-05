package com.whir.ht.cms.web.app.model;

import java.io.Serializable;

public class CustomResponse<T> implements Serializable {

	private static final long serialVersionUID = 5213230387175987834L;

	public int code;
	public String msg;
	public T data;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}

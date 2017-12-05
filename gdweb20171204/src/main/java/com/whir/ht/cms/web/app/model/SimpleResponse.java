package com.whir.ht.cms.web.app.model;

import java.io.Serializable;

public class SimpleResponse implements Serializable {

	private static final long serialVersionUID = 5213230387175987834L;

	public int code;
	public String msg;


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

}

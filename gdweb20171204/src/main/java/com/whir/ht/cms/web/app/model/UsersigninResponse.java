package com.whir.ht.cms.web.app.model;

public class UsersigninResponse {
	public STATUS status;
	public SIGNIN_DATA data;

	public STATUS getStatus() {
		return status;
	}

	public void setStatus(STATUS status) {
		this.status = status;
	}

	public SIGNIN_DATA getData() {
		return data;
	}

	public void setData(SIGNIN_DATA data) {
		this.data = data;
	}

}

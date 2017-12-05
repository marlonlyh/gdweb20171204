package com.whir.ht.cms.web.app.model;

public class WorkItemResponse {
	
	private static final long serialVersionUID = 3L;
	
	private String id;
	
	private String attach;
	
	private String fileName;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
}

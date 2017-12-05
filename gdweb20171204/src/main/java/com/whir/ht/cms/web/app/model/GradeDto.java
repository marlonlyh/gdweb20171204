package com.whir.ht.cms.web.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GradeDto  {
	
	private static final long serialVersionUID = 3L;
	private String id;
	/**年级名称*/
	private String gradeName;
	
	/**
	 * 获取年级名称
	 * @return
	 */
	@JsonProperty("value")
	public String getGradeName() {
		return gradeName;
	}

	/**
	 * 设置年级名称
	 * @param gradeName
	 */
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	

}
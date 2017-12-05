package com.whir.ht.cms.web.app.model;
public class Banner {
	private String title;	// 链接名称
	private String thref;	// 链接地址
	private Integer index;  // 序号
	private String image;	// 链接图片
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getThref() {
		return thref;
	}
	public void setThref(String thref) {
		this.thref = thref;
	}
   
}
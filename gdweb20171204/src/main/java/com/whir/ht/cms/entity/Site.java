/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/whir/ht">ezSite</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.whir.ht.cms.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import com.whir.ht.common.persistence.IdEntity;
import com.whir.ht.sys.utils.UserUtils;

/**
 * 站点Entity
 * @author Elvin
 * @version 2013-05-15
 */
@Entity
@Table(name = "cms_site")
@DynamicInsert @DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Site extends IdEntity<Category> {
	
	private static final long serialVersionUID = 1L;
	private String name;	// 站点名称
	private String title;	// 站点标题
	private String logo;	// 站点logo
	private String description;// 描述，填写有助于搜索引擎优化
	private String keywords;// 关键字，填写有助于搜索引擎优化
	private String theme;	// 主题
	private String copyright;// 版权信息
	private String customIndexView;// 自定义首页视图文件
	private String uploadFileFormat;// 上传文件格式
	private String convertFileFormat;// 转换文件格式
	private String uploadImageFormat;//上传图片格式
	private String convertImageFormat;//转换图片格式

	public Site() {
		super();
	}
	
	public Site(String id){
		this();
		this.id = id;
	}

	@Length(min=1, max=100)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Length(min=1, max=100)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	@Length(min=0, max=255)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Length(min=0, max=255)
	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	@Length(min=1, max=255)
	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getCopyright() {
		return copyright;
	}

	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}

	public String getCustomIndexView() {
		return customIndexView;
	}

	public void setCustomIndexView(String customIndexView) {
		this.customIndexView = customIndexView;
	}

	/**
	 * 获取默认站点ID
	 */
	@Transient
	public static String defaultSiteId(){
		return "1";
	}
	
	/**
	 * 判断是否为默认（主站）站点
	 */
	@Transient
	public static boolean isDefault(String id){
		return id != null && id.equals(defaultSiteId());
	}
	
	/**
	 * 获取当前编辑的站点编号
	 */
	@Transient
	public static String getCurrentSiteId(){
		String siteId = (String)UserUtils.getCache("siteId");
		return StringUtils.isNotBlank(siteId)?siteId:defaultSiteId();
	}

    /**
   	 * 模板路径
   	 */
   	public static final String TPL_BASE = "/WEB-INF/views/cms/front/themes";

    /**
   	 * 获得模板方案路径。如：/WEB-INF/views/cms/front/themes/gdweb
   	 *
   	 * @return
   	 */
    @Transient
   	public String getSolutionPath() {
   		return TPL_BASE + "/" + getTheme();
   	}

	public String getUploadFileFormat() {
		return uploadFileFormat;
	}

	public void setUploadFileFormat(String uploadFileFormat) {
		this.uploadFileFormat = uploadFileFormat;
	}

	public String getConvertFileFormat() {
		return convertFileFormat;
	}

	public void setConvertFileFormat(String convertFileFormat) {
		this.convertFileFormat = convertFileFormat;
	}

	public String getUploadImageFormat() {
		return uploadImageFormat;
	}

	public void setUploadImageFormat(String uploadImageFormat) {
		this.uploadImageFormat = uploadImageFormat;
	}

	public String getConvertImageFormat() {
		return convertImageFormat;
	}

	public void setConvertImageFormat(String convertImageFormat) {
		this.convertImageFormat = convertImageFormat;
	}
	
	
}
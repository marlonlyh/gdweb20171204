package com.whir.ht.cms.web.app.model;

import java.util.Date;

import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.whir.ht.cms.utils.CmsUtils;

/**
 * 作业
 * 
 * @author liuchunyi
 *
 */
public class WorkResponse  {

	private static final long serialVersionUID = 3L;

	/** 作业名称 */
	private String workName;

	/** 布置时间 */
	private Date publishDate;

	/** 完成时间 */
	private Date finishDate;

	/** 附件 */
	private String attach;

	/** 作业详情 */
	private String content;

	/** 图片 */
	private String image;


	/** 原始文件名 */
	private String fileName;

	/** 原始图片名 */
	private String photoName;

	/** 学生是否查看（1：已查看；0：未查看） */
	private String isView;
	
	/** 教师是否查阅（1：已阅；0：未阅） */
	private String isRead;
	
	/** 作业是否已分配（1：已分配；0：未分配） */
	private String isAssign;
	
	/** content赋值无效，作业详情 */
	private String detail;
	


	/**
	 * 获取作业名称
	 * 
	 * @return
	 */
	public String getWorkName() {
		return workName;
	}

	/**
	 * 设置作业名称
	 * 
	 * @param workName
	 */
	public void setWorkName(String workName) {
		this.workName = workName;
	}

	/**
	 * 获取布置时间
	 * 
	 * @return
	 */
	public Date getPublishDate() {
		return publishDate;
	}

	/**
	 * 设置布置时间
	 * 
	 * @param publishDate
	 */
	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	/**
	 * 获取完成时间
	 * 
	 * @return
	 */
	public Date getFinishDate() {
		return finishDate;
	}

	/**
	 * 设置完成时间
	 * 
	 * @param finishDate
	 */
	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	// @NotBlank
	@JsonIgnore
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = CmsUtils.formatImageSrcToDb(image);
	}

	@Transient
	public String getImageSrc() {
		return CmsUtils.formatImageSrcToWeb(this.image);
	}

	/**
	 * 原始文件名
	 * 
	 * @return
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * 原始文件名
	 * 
	 * @param fileName
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * 原始图片名
	 * 
	 * @return
	 */
	public String getPhotoName() {
		return photoName;
	}

	/**
	 * 原始图片名
	 * 
	 * @param photoName
	 */
	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}
	
	
	public String getIsView() {
		return isView;
	}

	public void setIsView(String isView) {
		this.isView = isView;
	}

	public String getIsRead() {
		return isRead;
	}

	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}

	public String getIsAssign() {
		return isAssign;
	}

	public void setIsAssign(String isAssign) {
		this.isAssign = isAssign;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}


}

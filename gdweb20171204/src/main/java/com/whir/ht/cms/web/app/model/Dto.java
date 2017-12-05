package com.whir.ht.cms.web.app.model;

import java.util.Date;
import java.util.List;

import com.whir.ht.cms.entity.ArticleImage;
import com.whir.ht.cms.entity.Classes;
import com.whir.ht.cms.entity.Grade;
import com.whir.ht.cms.entity.Major;

public class Dto {

	/**
	 * 内容
	 */
	private String title;
	private String content;
	private Integer hits;
	private Date publishDate;
	private List<ArticleImage> articleImages;
	private List<Object[]> relationList;
	private String image; // 文章图片
	private String keywords;// 关键字
	private String summary;// 描述、摘要
	private String attach;// 附件
	private Date startDate;// 任职时间
	private Date terminalDate;// 离任时间
	private Date paperTime;// 论文时间
	private String counselor;// 指导老师
	private String author;// 作者
	private String company;// 公司名称
	private String jobAddress;// 工作地点
	private Grade grade;// 年级
	private Classes classes;// 班级
	private Major major;// 专业
	private String categroyId;
	private String categroyName;
	private String copyfrom;// 来源
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getHits() {
		return hits;
	}

	public void setHits(Integer hits) {
		this.hits = hits;
	}
	
	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public List<Object[]> getRelationList() {
		return relationList;
	}

	public void setRelationList(List<Object[]> relationList) {
		this.relationList = relationList;
	}

	public List<ArticleImage> getArticleImages() {
		return articleImages;
	}

	public void setArticleImages(List<ArticleImage> articleImages) {
		this.articleImages = articleImages;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getTerminalDate() {
		return terminalDate;
	}

	public void setTerminalDate(Date terminalDate) {
		this.terminalDate = terminalDate;
	}

	public Date getPaperTime() {
		return paperTime;
	}

	public void setPaperTime(Date paperTime) {
		this.paperTime = paperTime;
	}

	public String getCounselor() {
		return counselor;
	}

	public void setCounselor(String counselor) {
		this.counselor = counselor;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getJobAddress() {
		return jobAddress;
	}

	public void setJobAddress(String jobAddress) {
		this.jobAddress = jobAddress;
	}

	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	public Classes getClasses() {
		return classes;
	}

	public void setClasses(Classes classes) {
		this.classes = classes;
	}

	public Major getMajor() {
		return major;
	}

	public void setMajor(Major major) {
		this.major = major;
	}

	public String getCategroyId() {
		return categroyId;
	}

	public void setCategroyId(String categroyId) {
		this.categroyId = categroyId;
	}

	public String getCategroyName() {
		return categroyName;
	}

	public void setCategroyName(String categroyName) {
		this.categroyName = categroyName;
	}

	public String getCopyfrom() {
		return copyfrom;
	}

	public void setCopyfrom(String copyfrom) {
		this.copyfrom = copyfrom;
	}

}
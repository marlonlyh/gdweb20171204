/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/whir/ht">ezSite</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.whir.ht.cms.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;
import org.hibernate.validator.constraints.Length;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.whir.ht.cms.utils.CmsUtils;
import com.whir.ht.common.persistence.IdEntity;

/**
 * 文章Entity
 * 
 * @author Elvin
 * @version 2013-05-15
 */
@Entity
@Table(name = "cms_article")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Indexed
@Analyzer(impl = IKAnalyzer.class)
public class Article extends IdEntity<Article> {

	public static final String DEFAULT_TEMPLATE = "frontViewArticle";

	private static final long serialVersionUID = 1L;
	private Category category;// 分类编号
	private String title; // 标题
	private String link; // 外部链接
	private String color; // 标题颜色（red：红色；green：绿色；blue：蓝色；yellow：黄色；orange：橙色）
	private String image; // 文章图片
	private String keywords;// 关键字
	private String description;// 描述、摘要
	private Integer weight; // 权重，越大越靠前
	private Date weightDate;// 权重期限，超过期限，将weight设置为0
	private Integer hits; // 点击数
	private String posid; // 推荐位，多选（1：首页焦点图；2：栏目页文章推荐；）
	private String customContentView; // 自定义内容视图
	private String viewConfig; // 视图参数
	private String attach;// 附件
	private String attachType;// 附件类型
	private Date publishDate = new Date();// 发布时间
	private String isRecommend;// 是否推荐

	/** 文章图片 */
	private List<ArticleImage> articleImages = new ArrayList<ArticleImage>();
	// 以下冗余字段为专业负责人字段
	private Date startDate;// 任职时间
	private Date terminalDate;// 离任时间

	// 以下冗余字段为毕业论文资源字段
	private Date paperTime;// 论文时间
	private String counselor;// 指导老师
	private String author;// 论文作者

	// 以下冗余字段为招聘信息字段
	private String company;// 公司名称
	private String jobAddress;// 工作地点

	// 以下冗余字段为班级风采字段
	private Grade grade;// 年级
	private Classes classes;// 班级
	private Major major;// 专业

	private ArticleData articleData; // 文章副表

	public Article() {
		super();
		this.weight = 0;
		this.hits = 0;
		this.posid = "";
	}

	public Article(String id) {
		this();
		this.id = id;
	}

	public Article(Category category) {
		this();
		this.category = category;
	}

	@PrePersist
	public void prePersist() {
		super.prePersist();
		articleData.setId(this.id);
	}

	@ManyToOne
	@JoinColumn(name = "category_id")
	@NotFound(action = NotFoundAction.IGNORE)
	@NotNull
	@IndexedEmbedded
	@JsonIgnore
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Length(min = 1, max = 255)
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Length(min = 0, max = 255)
	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	@Length(min = 0, max = 50)
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Length(min = 0, max = 255)
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = CmsUtils.formatImageSrcToDb(image);
	}

	@Length(min = 0, max = 255)
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	@Length(min = 0, max = 255)
	@JsonIgnore
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@JsonIgnore
	@NotNull
	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Date getWeightDate() {
		return weightDate;
	}

	public void setWeightDate(Date weightDate) {
		this.weightDate = weightDate;
	}

	public Integer getHits() {
		return hits;
	}

	public void setHits(Integer hits) {
		this.hits = hits;
	}

	@Length(min = 0, max = 10)
	public String getPosid() {
		return posid;
	}

	public String getAttach() {
		return attach;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public String getAttachType() {
		return attachType;
	}

	public void setAttachType(String attachType) {
		this.attachType = attachType;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public void setPosid(String posid) {
		this.posid = posid;
	}

	public String getCustomContentView() {
		return customContentView;
	}

	public void setCustomContentView(String customContentView) {
		this.customContentView = customContentView;
	}

	public String getViewConfig() {
		return viewConfig;
	}

	public void setViewConfig(String viewConfig) {
		this.viewConfig = viewConfig;
	}

	@OneToOne(mappedBy = "article", cascade = CascadeType.ALL, optional = false)
	@Valid
	@IndexedEmbedded
	@JsonIgnore
	public ArticleData getArticleData() {
		return articleData;
	}

	public void setArticleData(ArticleData articleData) {
		this.articleData = articleData;
	}

	@JsonIgnore
	@Transient
	public List<String> getPosidList() {
		List<String> list = Lists.newArrayList();
		if (posid != null) {
			for (String s : StringUtils.split(posid, ",")) {
				list.add(s);
			}
		}
		return list;
	}

	@Transient
	public void setPosidList(List<Long> list) {
		posid = "," + StringUtils.join(list, ",") + ",";
	}

	@Transient
	@JsonIgnore
	public String getUrl() {
		return CmsUtils.getUrlDynamic(this);
	}

	@Transient
	@JsonIgnore
	public String getImageSrc() {
		return CmsUtils.formatImageSrcToWeb(this.image);
	}

	@Length(min = 1, max = 1)
	@JsonIgnore
	public String getIsRecommend() {
		return isRecommend;
	}

	public void setIsRecommend(String isRecommend) {

		this.isRecommend = isRecommend;
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

	@JsonIgnore
	@OneToMany(mappedBy = "article", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@Where(clause = "del_flag=0")
	public List<ArticleImage> getArticleImages() {
		return articleImages;
	}

	public void setArticleImages(List<ArticleImage> articleImages) {
		this.articleImages = articleImages;
	}
	
	@Transient
	public String getGradeName() {
		if (null!=grade) {
			return grade.getGradeName();
		} 
		return "";
	}
	
	@Transient
	public String getClassName(){
		if(null!=classes){
			return classes.getClassName();
		}
		return "";
	}
	
	@Transient
	public String getMajorName(){
		if(null!=major){
			return major.getMajorName();
		}
		return "";
	}

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	public Classes getClasses() {
		return classes;
	}

	public void setClasses(Classes classes) {
		this.classes = classes;
	}

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	public Major getMajor() {
		return major;
	}

	public void setMajor(Major major) {
		this.major = major;
	}
	
/*	@Transient
    public String getSummary() {
    	return StringUtils.substring(getArticleData().getContent(), 0, 20);
    }*/
	
	@Transient
	public String getSummary() {
		return description;
	}

	@Transient
	public String getCategoryId() {
		return category.getId();
	}
}

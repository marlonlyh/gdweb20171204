package com.whir.ht.cms.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.whir.ht.common.persistence.IdEntity;

/**
 * 专业
 * @author liuchunyi
 *
 */
@Entity
@Table(name="cms_major")
@DynamicInsert @DynamicUpdate
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
public class Major extends IdEntity<Major> {
	
	private static final long serialVersionUID = 3L;
	
	/**专业名称*/
	private String majorName;
	
	/**排序（升序）*/
	private Integer sort;
	
	/**文章*/
	private Set<Article> articles = new HashSet<Article>();

	/**
	 * 获取专业名称
	 * @return
	 */
	public String getMajorName() {
		return majorName;
	}

	/**
	 * 设置专业名称
	 * @param majorName
	 */
	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}

	/**
	 * 获取排序
	 * @return
	 */
	public Integer getSort() {
		return sort;
	}

	/**
	 * 设置排序
	 * @param sort
	 */
	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@OneToMany(mappedBy = "major", fetch = FetchType.LAZY,cascade=CascadeType.ALL)
	public Set<Article> getArticles() {
		return articles;
	}

	public void setArticles(Set<Article> articles) {
		this.articles = articles;
	}
	
}
	
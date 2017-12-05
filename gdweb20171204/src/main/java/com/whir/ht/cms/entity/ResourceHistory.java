package com.whir.ht.cms.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.whir.ht.common.persistence.IdEntity;

/**
 * 原材料历史表
 * @author wangqing
 *
 */
@Entity
@Table(name = "cms_resource_history")
@DynamicInsert @DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ResourceHistory  extends IdEntity <ResourceHistory> {
	
	private static final long serialVersionUID = 3L;
	/**原材料唯一标记*/
	private String uid;	
	/**序号*/
	private Integer sort;
	/**材料分类*/
	private String type;
	/**材料名称*/
	private String name;
	/**材料型号*/
	private String model;
	/**材料规格*/
	private String standard;
	/**材料单价*/
	private BigDecimal price;
	/**含税价格*/
	private BigDecimal taxPrice;
	/**上次采集日期*/
	private Date collectDate;
	
	/**
	 * 
	 * @return
	 */
	public Integer getSort() {
		return sort;
	}
	/**
	 * 
	 * @param sort
	 */
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	/**
	 * 
	 * @return
	 */
	public String getType() {
		return type;
	}
	/**
	 * 
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}
	/**
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 
	 * @return
	 */
	public String getModel() {
		return model;
	}
	/**
	 * 
	 * @param model
	 */
	public void setModel(String model) {
		this.model = model;
	}
	/**
	 * 
	 * @return
	 */
	public String getStandard() {
		return standard;
	}
	/**
	 * 
	 * @param standard
	 */
	public void setStandard(String standard) {
		this.standard = standard;
	}
	/**
	 * 
	 * @return
	 */
	@Column(precision = 21, scale = 2)
	public BigDecimal getPrice() {
		return price;
	}
	/**
	 * 
	 * @param price
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	/**
	 * 
	 * @return
	 */
	@Column(precision = 21, scale = 2)
	public BigDecimal getTaxPrice() {
		return taxPrice;
	}
	/**
	 * 
	 * @param taxPrice
	 */
	public void setTaxPrice(BigDecimal taxPrice) {
		this.taxPrice = taxPrice;
	}
	/**
	 * 
	 * @return
	 */
	public Date getCollectDate() {
		return collectDate;
	}
	/**
	 * 
	 * @param collectDate
	 */
	public void setCollectDate(Date collectDate) {
		this.collectDate = collectDate;
	}
	/**
	 * 
	 * @return
	 */
	public String getUid() {
		return uid;
	}
	/**
	 * 
	 * @param uid
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}

}

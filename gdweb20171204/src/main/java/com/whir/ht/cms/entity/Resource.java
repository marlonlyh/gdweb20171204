package com.whir.ht.cms.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import com.whir.ht.common.persistence.IdEntity;

/**
 * 原材料
 * @author wangqing1
 *
 */
@Entity
@Table(name = "cms_resource")
@DynamicInsert @DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Resource  extends IdEntity<Resource>{
	
	private static final long serialVersionUID = 3L;
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
	/**涨跌*/
	private BigDecimal upAndDown;
	/**涨跌幅*/
	private BigDecimal ranges;
	/**唯一标记*/
	private String uid;
	/**是否推荐*/
	private String isRecommend;
	
	/**
	 * 
	 * @return
	 */
	@Column(precision = 21, scale = 2)
	public BigDecimal getUpAndDown() {
		return upAndDown;
	}
	/**
	 * 
	 * @param upAndDown
	 */
	public void setUpAndDown(BigDecimal upAndDown) {
		this.upAndDown = upAndDown;
	}
	/**
	 * 
	 * @return
	 */
	@Column(precision = 21, scale = 2)
	public BigDecimal getRanges() {
		return ranges;
	}
	/**
	 * 
	 * @param ranges
	 */
	public void setRanges(BigDecimal ranges) {
		this.ranges = ranges;
	}

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
	public BigDecimal getTaxPrice() {
		return taxPrice;
	}
	/**
	 * 
	 * @param taxPirce
	 */
	public void setTaxPrice(BigDecimal taxPrice) {
		this.taxPrice = taxPrice;
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

	@PrePersist
	public void prePersist(){
		super.prePersist();
		if (getUid()==null){
			setUid(getId());
		}	
		if (getSort()==null){
			setSort(0);
		}
		if (getIsRecommend()==null){
			setIsRecommend("0");
		}
	}
	
	/**修改后处理*/
	@PreUpdate
	public void preUpdate(){
		super.preUpdate();
		if (getSort()==null){
			setSort(0);
		}
		if (getIsRecommend()==null){
			setIsRecommend("0");
		}
	}
	
	/**
	 * 获取是否推荐
	 * @return
	 */
	@Length(min=1, max=1)
	public String getIsRecommend() {
		return isRecommend;
	}
	
	/**
	 * 设置是否推荐
	 * @param isRecommend
	 */
	public void setIsRecommend(String isRecommend) {
		this.isRecommend = isRecommend;
	}
}

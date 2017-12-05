package com.whir.ht.cms.entity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

import com.whir.ht.common.persistence.IdEntity;

/**
 * 产品
 * @author wangqing1
 *
 */
@Entity
@Table(name = "cms_product")
@DynamicInsert @DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Product extends IdEntity<Product>{
	
	private static final long serialVersionUID = 3L;
	/**产品分类*/
	private ProductType type;
	/**型号*/
	private String model;
	/**电压*/
	private String voltage;
	/**符合标准*/
	private String standard;
	/**产品名称*/
	private String name;
	/**单位*/
	private String unit;
	/**摘要*/
	private String summary;
	/**产品简介*/
	private String information;
	/**红本价格*/
	private BigDecimal redPrice;
	/**检测价格*/
	private BigDecimal testPrice;	
	/**备注*/
	private String memo;
	/**型号介绍*/
	private String modelDescript;
	/**试验规范*/
	private String standardDescript;
	/**排序*/
	private Integer sort;
	/**获取规格*/
	private Set<Specificate> specificates=new HashSet<Specificate>();
	
	public Product(){
		super();
		
		
	}
	
	public Product(ProductType productType){
		this();
		if (productType!=null){
			this.type=productType;
		}		
	}
	
	
	/**
	 * 获取产品分类
	 * @return
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	public ProductType getType() {
		return type;
	}
	/**
	 * 设置产品分类
	 * @param type
	 */
	public void setType(ProductType type) {
		this.type = type;
	}
	/**
	 * 获取型号
	 * @return
	 */
	public String getModel() {
		return model;
	}
	/**
	 * 设置型号
	 * @param model
	 */
	public void setModel(String model) {
		this.model = model;
	}
	/**
	 * 获取电压
	 * @return
	 */
	public String getVoltage() {
		return voltage;
	}
	/**
	 * 设置电压
	 * @param voltage
	 */
	public void setVoltage(String voltage) {
		this.voltage = voltage;
	}
	/**
	 * 获取符合标准
	 * @return
	 */
	public String getStandard() {
		return standard;
	}
	/**
	 * 设置符合标准
	 * @param standard
	 */
	public void setStandard(String standard) {
		this.standard = standard;
	}
	/**
	 * 获取产品名称
	 * @return
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置产品名称
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取单位
	 * @return
	 */
	public String getUnit() {
		return unit;
	}
	/**
	 * 设置单位
	 * @param unit
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}
	/**
	 * 获取摘要
	 * @return
	 */
	public String getSummary() {
		return summary;
	}
	/**
	 * 设置摘要
	 * @param summary
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}
	/**
	 * 获取产品简介
	 * @return
	 */
	public String getInformation() {
		return information;
	}
	/**
	 * 设置产品简介
	 * @param information
	 */
	public void setInformation(String information) {
		this.information = information;
	}
	/**
	 * 获取红本价格
	 * @return
	 */
	@Column(precision = 21, scale = 2)
	public BigDecimal getRedPrice() {
		return redPrice;
	}
	/**
	 * 设置红本价格
	 * @param redPrice
	 */
	public void setRedPrice(BigDecimal redPrice) {
		this.redPrice = redPrice;
	}
	/**
	 * 获取检测价格
	 * @return
	 */
	@Column(precision = 21, scale = 2)
	public BigDecimal getTestPrice() {
		return testPrice;
	}
	/**
	 * 设置检测价格
	 * @param testPrice
	 */
	public void setTestPrice(BigDecimal testPrice) {
		this.testPrice = testPrice;
	}
	
	
	/**
	 * 获取备注
	 * @return
	 */
	public String getMemo() {
		return memo;
	}
	/**
	 * 设置备注
	 * @param memo
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}
	/**
	 * 获取型号介绍
	 * @return
	 */
	public String getModelDescript() {
		return modelDescript;
	}
	/**
	 * 设置型号介绍
	 * @param modelDescript
	 */
	public void setModelDescript(String modelDescript) {
		this.modelDescript = modelDescript;
	}
	/**
	 * 获取试验标准
	 * @return
	 */
	public String getStandardDescript() {
		return standardDescript;
	}
	/**
	 * 设置试验标准
	 * @param standardDescript
	 */
	public void setStandardDescript(String standardDescript) {
		this.standardDescript = standardDescript;
	}
	/**
	 * 
	 * @return
	 */
	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
	@OrderBy("sort asc")
	@Where(clause="del_flag='0'")
	public Set<Specificate> getSpecificates() {
		return specificates;
	}
	/**
	 * 
	 * @param specificates
	 */
	public void setSpecificates(Set<Specificate> specificates) {
		this.specificates = specificates;
	}
	
     /**获取排序*/
	public Integer getSort() {
		return sort;
	}
	 /**设置排序*/
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	/**修改前处理*/
	@PrePersist
	public void prePersist(){
		super.prePersist();
		if (getSort()==null){
			setSort(0);
		}
	}
	
	/**修改后处理*/
	@PreUpdate
	public void preUpdate(){
		super.preUpdate();
		if (getSort()==null){
			setSort(0);
		}
	}
		
}

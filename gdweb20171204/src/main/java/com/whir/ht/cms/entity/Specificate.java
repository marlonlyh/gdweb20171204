package com.whir.ht.cms.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;

import com.whir.ht.common.persistence.IdEntity;

/**
 * 产品规格表
 * @author wangqing1
 *
 */
@Entity
@Table(name = "cms_specificate")
@DynamicInsert @DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Specificate  extends IdEntity<Specificate> {
	
	private static final long serialVersionUID = 3L;
	/**产品*/
	private Product product;
	/**规格*/
	private String name;
	/**材料总价*/
	private BigDecimal resourcePrice;
	/**市场参考价*/
	private BigDecimal guidePrice;
	/**导体结构*/
	private String conductorStruct;
	/**导体外径*/
	private String externalSize;
	/**绝缘厚度*/
	private String insulateThick;
	/**内护套厚度*/
	private String innerThick;
	/**外护套厚度*/
	private String externalThick;
	/**护套厚度*/
	private String jacketThick;
	/**参考外径范围*/
	private String externalRange;
	/**参考重量*/
	private String weight;
	/**20度导体最大直流电阻 镀金*/
	private String resistance;
	/**20度导体最大直流电阻 不镀金*/
	private String notResistance;
	/**参考载流量*/
	private String electricCurrent;
	/**参考最大拉力*/
	private String pull;
	/**铜杆加工*/
	private BigDecimal copperProcess;
	/**材料消耗*/
	private BigDecimal resourceConsume;
	/**工艺成本*/
	private BigDecimal resourceCost;
	/**财务成本*/
	private BigDecimal financeCost;
	/**固定成本*/
	private BigDecimal fixedCost;
	/**人工费用*/
	private BigDecimal personCost;
	/**销售费用*/
	private BigDecimal otherCost;
	/**利润*/
	private BigDecimal profit;	
	/**参考总价*/
	private BigDecimal referPrice;
	/**规格图片1*/
	private String image01;
	/**规格图片2*/
	private String image02;
	/**规格图片3*/
	private String image03;
	/**是否推荐*/
	private String isRecommend;
	/**排序*/
	private Integer sort;
	/**规格价格表*/
	private List<SpecificatePrice> prices= new ArrayList<SpecificatePrice>();
	/**获取排序号*/
	public Integer getSort() {
		return sort;
	}
	/**获取排序号*/
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public BigDecimal getCopperProcess() {
		return copperProcess;
	}
	public void setCopperProcess(BigDecimal copperProcess) {
		this.copperProcess = copperProcess;
	}
	public String getNotResistance() {
		return notResistance;
	}
	public void setNotResistance(String notResistance) {
		this.notResistance = notResistance;
	}
	/**
	 * 获取产品
	 * @return
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	public Product getProduct() {
		return product;
	}
	/**
	 * 设置产品
	 * @param product
	 */
	public void setProduct(Product product) {
		this.product = product;
	}
	/**
	 * 获取规格
	 * @return
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置规格
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取材料价格
	 * @return
	 */
	@Column(precision = 21, scale = 2)
	public BigDecimal getResourcePrice() {
		return resourcePrice;
	}
	/**
	 * 设置材料价格
	 * @param resourcePrice
	 */
	public void setResourcePrice(BigDecimal resourcePrice) {
		this.resourcePrice = resourcePrice;
	}
	/**
	 * 获取指导价格
	 * @return
	 */
	@Column(precision = 21, scale = 2)
	public BigDecimal getGuidePrice() {
		return guidePrice;
	}
	/**
	 * 设置指导价格
	 * @param guidePrice
	 */
	public void setGuidePrice(BigDecimal guidePrice) {
		this.guidePrice = guidePrice;
	}
	/**
	 * 获取导体结构
	 * @return
	 */
	public String getConductorStruct() {
		return conductorStruct;
	}
	/**
	 * 设置导体结构
	 * @param conductorStruct
	 */
	public void setConductorStruct(String conductorStruct) {
		this.conductorStruct = conductorStruct;
	}
	/**
	 * 获取导体外径
	 * @return
	 */
	public String getExternalSize() {
		return externalSize;
	}
	/**
	 * 设置导体外径
	 * @param externalSize
	 */
	public void setExternalSize(String externalSize) {
		this.externalSize = externalSize;
	}
	/**
	 * 
	 * @return
	 */
	public String getInsulateThick() {
		return insulateThick;
	}
	/**
	 * 
	 * @param insulateThick
	 */
	public void setInsulateThick(String insulateThick) {
		this.insulateThick = insulateThick;
	}
	/**
	 * 
	 * @return
	 */
	public String getInnerThick() {
		return innerThick;
	}
	/**
	 * 
	 * @param innerThick
	 */
	public void setInnerThick(String innerThick) {
		this.innerThick = innerThick;
	}
	/**
	 * 
	 * @return
	 */
	public String getExternalThick() {
		return externalThick;
	}
	/**
	 * 
	 * @param externalThick
	 */
	public void setExternalThick(String externalThick) {
		this.externalThick = externalThick;
	}
	/**
	 * 
	 * @return
	 */
	public String getJacketThick() {
		return jacketThick;
	}
	/**
	 * 
	 * @param jacketThick
	 */
	public void setJacketThick(String jacketThick) {
		this.jacketThick = jacketThick;
	}
	/**
	 * 
	 * @return
	 */
	public String getExternalRange() {
		return externalRange;
	}
	/**
	 * 
	 * @param externalRange
	 */
	public void setExternalRange(String externalRange) {
		this.externalRange = externalRange;
	}
	/**
	 * 
	 * @return
	 */
	public String getWeight() {
		return weight;
	}
	/**
	 * 
	 * @param weight
	 */
	public void setWeight(String weight) {
		this.weight = weight;
	}
	/**
	 * 
	 * @return
	 */
	public String getResistance() {
		return resistance;
	}
	/**
	 * 
	 * @param resistance
	 */
	public void setResistance(String resistance) {
		this.resistance = resistance;
	}
	/**
	 * 
	 * @return
	 */
	public String getElectricCurrent() {
		return electricCurrent;
	}
	/**
	 * 
	 * @param electricCurrent
	 */
	public void setElectricCurrent(String electricCurrent) {
		this.electricCurrent = electricCurrent;
	}
	/**
	 * 
	 * @return
	 */
	public String getPull() {
		return pull;
	}
	/**
	 * 
	 * @param pull
	 */
	public void setPull(String pull) {
		this.pull = pull;
	}
	/**
	 * 
	 * @return
	 */
	@Column(precision = 21, scale = 2)
	public BigDecimal getResourceConsume() {
		return resourceConsume;
	}
	/**
	 * 
	 * @param resourceConsume
	 */
	public void setResourceConsume(BigDecimal resourceConsume) {
		this.resourceConsume = resourceConsume;
	}
	/**
	 * 
	 * @return
	 */
	@Column(precision = 21, scale = 2)
	public BigDecimal getResourceCost() {
		return resourceCost;
	}
	/**
	 * 
	 * @param resourceCost
	 */
	public void setResourceCost(BigDecimal resourceCost) {
		this.resourceCost = resourceCost;
	}
	/**
	 * 
	 * @return
	 */
	@Column(precision = 21, scale = 2)
	public BigDecimal getFinanceCost() {
		return financeCost;
	}
	/**
	 * 
	 * @param financeCost
	 */
	public void setFinanceCost(BigDecimal financeCost) {
		this.financeCost = financeCost;
	}
	/**
	 * 
	 * @return
	 */
	@Column(precision = 21, scale = 2)
	public BigDecimal getFixedCost() {
		return fixedCost;
	}
	/**
	 * 
	 * @param fixedCost
	 */
	public void setFixedCost(BigDecimal fixedCost) {
		this.fixedCost = fixedCost;
	}
	/**
	 * 
	 * @return
	 */
	@Column(precision = 21, scale = 2)
	public BigDecimal getPersonCost() {
		return personCost;
	}
	/**
	 * 
	 * @param personCost
	 */
	public void setPersonCost(BigDecimal personCost) {
		this.personCost = personCost;
	}
	/**
	 * 
	 * @return
	 */
	@Column(precision = 21, scale = 2)
	public BigDecimal getProfit() {
		return profit;
	}
	/**
	 * 
	 * @param profit
	 */
	public void setProfit(BigDecimal profit) {
		this.profit = profit;
	}
	/**
	 * 
	 * @return
	 */
	@Column(precision = 21, scale = 2)
	public BigDecimal getOtherCost() {
		return otherCost;
	}
	/**
	 * 
	 * @param otherCost
	 */
	public void setOtherCost(BigDecimal otherCost) {
		this.otherCost = otherCost;
	}
	/**
	 * 
	 * @return
	 */
	@Column(precision = 21, scale = 2)
	public BigDecimal getReferPrice() {
		return referPrice;
	}
	/**
	 * 
	 * @param referPrice
	 */
	public void setReferPrice(BigDecimal referPrice) {
		this.referPrice = referPrice;
	}
	/**
	 * 
	 * @return
	 */
	@OneToMany(mappedBy = "specificate", fetch = FetchType.LAZY,cascade=CascadeType.ALL)
	@Where(clause="del_flag='0'")
	public List<SpecificatePrice> getPrices() {
		return prices;
	}
	/**
	 * 
	 * @param prices
	 */
	public void setPrices(List<SpecificatePrice> prices) {
		this.prices = prices;
	}	
	
	/**
	 * 获取图片01
	 * @return
	 */
	public String getImage01() {
		return image01;
	}
	/**
	 * 设置图片01
	 * @param image01
	 */
	public void setImage01(String image01) {
		this.image01 = image01;
	}
	/**
	 * 获取图片02
	 * @return
	 */
	public String getImage02() {
		return image02;
	}
	/**
	 * 设置图片02
	 * @param image02
	 */
	public void setImage02(String image02) {
		this.image02 = image02;
	}
	/**
	 * 获取图片03
	 * @return
	 */
	public String getImage03() {
		return image03;
	}
	/**
	 * 设置图片03
	 * @param image03
	 */
	public void setImage03(String image03) {
		this.image03 = image03;
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
	
	@PrePersist
	public void prePersist(){
		super.prePersist();
		if (getSort()==null){
			setSort(0);
		}
	}
	
	@PreUpdate
	public void preUpdate(){
		super.preUpdate();
		if (getSort()==null){
			setSort(0);
		}
	}
}

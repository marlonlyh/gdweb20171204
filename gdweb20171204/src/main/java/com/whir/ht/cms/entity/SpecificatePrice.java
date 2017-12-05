package com.whir.ht.cms.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.whir.ht.common.persistence.IdEntity;

/**
 * 产品规格价格表
 * @author wangqing
 *
 */
@Entity
@Table(name = "cms_specificate_price")
@DynamicInsert @DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SpecificatePrice extends IdEntity<SpecificatePrice>{
	
	private static final long serialVersionUID = 1L;
	/**规格*/
	private Specificate specificate;
	
	/**原材料*/
	private Resource resource;
	
	/**材料定额*/
	private BigDecimal price;
	
	/**标记*/
	private String flag;
	
	/**排序*/
	private Integer  specSort;

	/**获取排序*/
	public Integer getSpecSort() {
		return specSort;
	}
	/**设置排序*/
	public void setSpecSort(Integer specSort) {
		this.specSort = specSort;
	}
	/**
	 * 
	 * @return
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	public Specificate getSpecificate() {
		return specificate;
	}
	/**
	 * 
	 * @param specificate
	 */
	public void setSpecificate(Specificate specificate) {
		this.specificate = specificate;
	}
	
	/**
	 * 
	 * @return
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	public Resource getResource() {
		return resource;
	}
	/**
	 * 
	 * @param resource
	 */
	public void setResource(Resource resource) {
		this.resource = resource;
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
	public String getFlag() {
		return flag;
	}
	/**
	 * 
	 * @param flag
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	public SpecificatePrice(){
		super();
		this.flag="1";
	}
	
	@Transient
	public BigDecimal getSubtotal() {
		if(resource.getTaxPrice() !=null && getPrice() != null){
			return resource.getTaxPrice().multiply(getPrice()).setScale(2, RoundingMode.HALF_UP);
		}else{
			return new BigDecimal(0);
		}
	}
	
}

/*
 * Copyright 2005-2013 ezworks.com. All rights reserved.
 * Support: http://www.ezworks.com
 * License: http://www.ezworks.com/license
 */
package com.whir.ht.cms.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.whir.ht.common.persistence.IdEntity;

/**
 * Entity - 会员等级
 * 
 * @author 万户网络
 * @version 1.0
 */
@Entity
@Table(name = "cms_member_rank")
@DynamicInsert @DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MemberRank extends IdEntity<MemberRank> {

	private static final long serialVersionUID = 3599029355500655209L;

	/** 名称 */
	private String name;

	/** 优惠比例 */
	private Double scale;

	/** 消费金额 */
	private BigDecimal amount;
	
	/**达标积分*/
	private Integer point;
	
	/**注册时长*/
	private Integer registerDays;
	
	/**购买商品个数*/
	private Integer qty;

	/** 是否默认 */
	private Boolean isDefault;

	/** 是否特殊 */
	private Boolean isSpecial;

	
	/**
	 * 获取名称
	 * 
	 * @return 名称
	 */
	@NotEmpty
	@Length(max = 100)
	@Column(nullable = false, unique = true, length = 100)
	public String getName() {
		return name;
	}

	/**
	 * 设置名称
	 * 
	 * @param name
	 *            名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取优惠比例
	 * 
	 * @return 优惠比例
	 */
	
	@Min(0)
	@Digits(integer = 3, fraction = 3)
	@Column(precision = 12, scale = 6)
	public Double getScale() {
		return scale;
	}

	/**
	 * 设置优惠比例
	 * 
	 * @param scale
	 *            优惠比例
	 */
	public void setScale(Double scale) {
		this.scale = scale;
	}

	/**
	 * 获取消费金额
	 * 
	 * @return 消费金额
	 */
	@Min(0)
	@Digits(integer = 12, fraction = 3)
	@Column(unique = true, precision = 21, scale = 6)
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * 设置消费金额
	 * 
	 * @param amount
	 *            消费金额
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * 获取是否默认
	 * 
	 * @return 是否默认
	 */
	@NotNull
	@Column(nullable = false)
	public Boolean getIsDefault() {
		return isDefault;
	}

	/**
	 * 设置是否默认
	 * 
	 * @param isDefault
	 *            是否默认
	 */
	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	/**
	 * 获取是否特殊
	 * 
	 * @return 是否特殊
	 */
	public Boolean getIsSpecial() {
		return isSpecial;
	}

	/**
	 * 设置是否特殊
	 * 
	 * @param isSpecial
	 *            是否特殊
	 */
	public void setIsSpecial(Boolean isSpecial) {
		this.isSpecial = isSpecial;
	}

	/**
	 * 获取达标积分
	 * @return
	 */
	public Integer getPoint() {
		return point;
	}
	/**
	 * 设置达标积分
	 * @param point
	 */
	public void setPoint(Integer point) {
		this.point = point;
	}
	/**
	 * 获取注册时长
	 * @return
	 */
	public Integer getRegisterDays() {
		return registerDays;
	}
	/**
	 * 设置注册时长
	 * @param registerDays
	 */
	public void setRegisterDays(Integer registerDays) {
		this.registerDays = registerDays;
	}
	/**
	 * 获取购买商品个数
	 * @return
	 */
	public Integer getQty() {
		return qty;
	}
	/**
	 * 设置购买商品个数
	 * @param qty
	 */
	public void setQty(Integer qty) {
		this.qty = qty;
	}

}
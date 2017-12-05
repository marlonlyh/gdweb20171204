package com.whir.ht.cms.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.whir.ht.cms.utils.CmsUtils;
import com.whir.ht.common.persistence.IdEntity;
import com.whir.ht.common.utils.excel.annotation.ExcelField;

@Entity
@Table(name="cms_member")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="flag",discriminatorType=DiscriminatorType.STRING)
@DynamicInsert @DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class  FrontUser  extends IdEntity<FrontUser>{

	private static final long serialVersionUID = -5940806545728377068L;
	
	/**用户名*/
	private String userName;
	
	/**密码*/
	private String password;
	
	/**手机串号*/
	private String serialNo;
	
	/** 个人昵称 */
	private String nickname;
	
	/** 个人头像 */
	private String head;
	
	/**密码提示问题*/
	private String problem;
	
	/**密码提示答案*/
	private String answer;
	
	/**协议账户*/
	private String account;
	
	/**真实姓名*/
	private String name;
	
	/** 性别 */
	private String gender;
	
	/** 手机 */
	private String mobile;
	
	/** 单位名称 */
	private String tCName;
	
	/** 单位地址 */
	private String unitAddress;

	/** 详细地址 */
	private String address;
	
	/** 电话 */
	private String phone;

	/** E-mail */
	private String email;
	
	/** 最后登陆IP */
	private String loginIp;	
	
	/** 最后登陆日期 */
	private Date loginDate;	
	
	/**过期日期*/
	private Date expireDate;
	
	/**随机种子*/
	private String randomSeed;
	
	/**注册ID*/
	private String registrationId;
	
	/**app个人反馈*/
	private String feedback;
	
	/**
	 * 获取用户名
	 * @return
	 */
	@NotEmpty
//	@Pattern(regexp = "^[0-9a-z_A-Z\\u4e00-\\u9fa5]+$")
	@Column(name="username", nullable = false, unique = false,length = 50)
	@ExcelField(title="用户名", align=2, sort=10)
	public String getUserName() {
		return userName;
	}

	/**
	 * 设置用户名
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * 获取密码
	 * @return
	 */
//	@NotEmpty
//	@Pattern(regexp = "^[^\\s&\"<>]+$")
	@Column(name="userpwd",length=200)
	@ExcelField(title="密码", align=2, sort=15)
	public String getPassword() {
		return password;
	}

	/**
	 * 设置密码
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 获取手机串号
	 * @return
	 */
	@Length(max = 200)
	@ExcelField(title="手机串号", align=2, sort=20)
	public String getSerialNo() {
		return serialNo;
	}
	/**
	 * 设置手机串号
	 * @param serialNo
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	/**
	 * 获取个人昵称
	 * @return
	 */
	public String getNickname() {
		return nickname;
	}
	/**
	 * 设置个人昵称
	 * @param nickname
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/**
	 * 获取个人头像
	 * @return
	 */
	@Length(min = 0, max = 255)
	public String getHead() {
		return head;
	}
	/**
	 * 设置个人头像
	 * @param head
	 */
	public void setHead(String head) {
		this.head = head;
	}
	
	/**
	 * 获取密码提示问题
	 * @return
	 */
//	@NotEmpty
	@Column(name="proble",nullable = false , length = 500)
	public String getProblem() {
		return problem;
	}

	/**
	 * 设置密码提示问题
	 * @param problem
	 */
	public void setProblem(String problem) {
		this.problem = problem;
	}

	/**
	 * 获取密码提示答案
	 * @return
	 */
//	@NotEmpty 
	@Column(name = "answer",nullable = false,length = 500)
	public String getAnswer() {
		return answer;
	}

	/**
	 * 设置密码提示问题
	 * @param answer
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
	}

	/**
	 * 获取协议帐户
	 * @return
	 */
	@Column(name = "account" , nullable = false,length = 50)
	public String getAccount() {
		return account;
	}

	/**
	 * 设置协议帐户
	 * @param account
	 */
	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * 获取真实姓名
	 * @return
	 */
	//@NotEmpty
	@Column(name="name",nullable = false,length = 200)
	public String getName() {
		return name;
	}

	/**
	 * 设置真实姓名
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取性别
	 * @return
	 */
	//@NotEmpty
	@Column(name = "gender",nullable = false, length = 50)
	public String getGender() {
		return gender;
	}

	/**
	 * 设置性别
	 * @param gender
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * 获取手机号
	 * @return
	 */
	@Length(max = 200)
	public String getMobile() {
		return mobile;
	}

	/**
	 * 设置手机号
	 * @param mobile
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * 获取单位名称
	 * @return
	 */
	@Length(max = 500)
	public String gettCName() {
		return tCName;
	}

	/**
	 * 设置单位名称
	 * @param tCName
	 */
	public void settCName(String tCName) {
		this.tCName = tCName;
	}

	/**
	 * 获取单位地址
	 * @return
	 */
	@Length(max = 500)
	public String getUnitAddress() {
		return unitAddress;
	}

	/**
	 * 设置单位地址
	 * @param unitAddress
	 */
	public void setUnitAddress(String unitAddress) {
		this.unitAddress = unitAddress;
	}

	/**
	 * 获取详细地址
	 * @return
	 */
	@Length(max = 500)
	public String getAddress() {
		return address;
	}

	/**
	 * 设置详细地址
	 * @param address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * 获取固定电话
	 * @return
	 */
	@Column(name = "phone", length = 50)
	public String getPhone() {
		return phone;
	}

	/**
	 * 设置固定电话
	 * @param phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * 获取E-mail
	 * @return
	 */
	@Email
	@Length(max = 500)
	@Column(name = "email")
	public String getEmail() {
		return email;
	}

	/**
	 * 设置E-mail
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 获取最后登陆IP
	 * @return
	 */
	@Column(name = "loginoip")
	public String getLoginIp() {
		return loginIp;
	}

	/**
	 * 设置最后登陆IP
	 * @param loginIp
	 */
	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	/**
	 * 获取最后登陆日期
	 * @return
	 */
	public Date getLoginDate() {
		return loginDate;
	}

	/**
	 * 设置最后登陆日期
	 * @param loginDate
	 */
	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}
	/**
	 * 获取过期日期
	 * @return
	 */
	public Date getExpireDate() {
		return expireDate;
	}
	/**
	 * 设置过期日期
	 * @param expireDate
	 */
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	/**
	 * 获取随机种子
	 * @return
	 */
	public String getRandomSeed() {
		return randomSeed;
	}
	/**
	 * 设置随机种子
	 * @param randomSeed
	 */
	public void setRandomSeed(String randomSeed) {
		this.randomSeed = randomSeed;
	}
	/**
	 * 获取注册ID
	 * @return
	 */
	public String getRegistrationId() {
		return registrationId;
	}
	/**
	 * 设置注册ID
	 * @param registrationId
	 */
	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}
	
	/**
	 * 获取app个人反馈
	 * @return
	 */
	@Length(min = 0, max = 255)
	public String getFeedback() {
		return feedback;
	}

	/**
	 * 设置app个人反馈
	 * @param feedback
	 */
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	
	@Transient
	@JsonIgnore
	public String getImageSrc() {
		return CmsUtils.formatImageSrcToWeb(this.head);
	}

	
}

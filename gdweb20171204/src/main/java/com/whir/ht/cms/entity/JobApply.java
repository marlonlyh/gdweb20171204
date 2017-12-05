package com.whir.ht.cms.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.whir.ht.common.persistence.IdEntity;

@Entity
@Table(name="cms_jobs")
@DynamicInsert @DynamicUpdate
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class JobApply extends IdEntity<JobApply>{
	
	private static final long serialVersionUID = 3L;
	
	private String name;//应聘者姓名
	private String sex;//性别
	private Date birthDate;//出生日期
	private String nation;//民族
	private String origin;//籍贯
	private String houseHolds;//户口所在地
	private String institutions;//毕业院校
	private String graduate;//学历
	private String specialty;//专业
	private String documentType;//证件类型
	private String certificate;//证件号码
	private String mobile;//移动电话
	private String email;//电子邮箱
	private Date beginDate;//教育经历开始时间
	private Date endDate;//教育经历结束时间
	private String school;//学校名称
	private String college;//教育经历的学历
	private String discipline;//教育经历的专业
	private Date startDate;//工作经历的开始时间
	private Date terminalDate;//工作经历的结束时间
	private String enterprise;//企业名称
	private String jobName;//职位名称
	private String department;//所在部门
	private String detail;//工作描述
	
	/**
	 * 获取应聘者姓名
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 设置应聘者姓名
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 获取性别
	 * @return
	 */
	public String getSex() {
		return sex;
	}
	
	/**
	 * 设置性别
	 * @param sex
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	/**
	 * 获取生日
	 * @return
	 */
	public Date getBirthDate() {
		return birthDate;
	}
	
	/**
	 * 设置生日
	 * @return
	 */
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	
	/**
	 * 获取民族
	 * @return
	 */
	public String getNation() {
		return nation;
	}
	
	/**
	 * 设置民族
	 * @param nation
	 */
	public void setNation(String nation) {
		this.nation = nation;
	}
	
	/**
	 * 获取籍贯
	 * @return
	 */
	public String getOrigin() {
		return origin;
	}
	
	/**
	 *设置籍贯
	 * @param origin
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	
	/**
	 * 获取户口所在地
	 * @return
	 */
	public String getHouseHolds() {
		return houseHolds;
	}
	
	/**
	 * 设置户口所在地
	 * @param houseHolds
	 */
	public void setHouseHolds(String houseHolds) {
		this.houseHolds = houseHolds;
	}
	
	/**
	 * 获取毕业院校
	 * @return
	 */
	public String getInstitutions() {
		return institutions;
	}
	
	/**
	 * 设置毕业院校
	 * @param institutions
	 */
	public void setInstitutions(String institutions) {
		this.institutions = institutions;
	}
	
	/**
	 * 获取学历
	 * @return
	 */
	public String getGraduate() {
		return graduate;
	}
	
	/**
	 * 设置学历
	 * @param graduate
	 */
	public void setGraduate(String graduate) {
		this.graduate = graduate;
	}
	
	/**
	 * 获取专业
	 * @return
	 */
	public String getSpecialty() {
		return specialty;
	}
	
	/**
	 * 设置专业
	 * @param specialty
	 */
	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}
	
	/**
	 * 获取证件类型
	 * @return
	 */
	public String getDocumentType() {
		return documentType;
	}
	
	/**
	 * 设置证件类型
	 * @param documentType
	 */
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	
	/**
	 * 获取证件号码
	 * @return
	 */
	public String getCertificate() {
		return certificate;
	}
	
	/**
	 * 设置证件号码
	 * @param certificate
	 */
	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}
	
	/**
	 * 获取移动电话
	 * @return
	 */
	public String getMobile() {
		return mobile;
	}
	
	/**
	 * 设置移动电话
	 * @param mobile
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	/**
	 * 获取email
	 * @return
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * 设置email
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * 获取教育经历开始时间
	 * @return
	 */
	public Date getBeginDate() {
		return beginDate;
	}
	
	/**
	 * 设置教育经历开始时间
	 * @return
	 */
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	
	/**
	 * 设置教育经历开始时间
	 * @return
	 */
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getCollege() {
		return college;
	}
	public void setCollege(String college) {
		this.college = college;
	}
	public String getDiscipline() {
		return discipline;
	}
	public void setDiscipline(String discipline) {
		this.discipline = discipline;
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
	public String getEnterprise() {
		return enterprise;
	}
	public void setEnterprise(String enterprise) {
		this.enterprise = enterprise;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
}

package com.whir.ht.cms.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.whir.ht.common.persistence.IdEntity;

@Entity
@Table(name="cms_job")
@DynamicInsert @DynamicUpdate
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Job extends IdEntity<Job> {
	
	private static final long serialVersionUID = 3L;
	
	/**职位名称*/
	private String jobName;
	
	/**所属部门*/
	private String departMent;
	
	/**工作地点*/
	private String address;
	
	/**所属公司*/
	private String company;
	
	/**发布时间*/
	private Date publishDate;
	
	/**职位类型*/
	private String jobType;
	
	/**学历*/
	private String school;
	
	/**工作年限*/
	private String workYear;
	
	/**职务描述*/
	private String jobDesc;
	
	/**入职要求*/
	private String joinReqiure;
	
	/**薪资待遇*/
	private String salary;
	
	/**福利待遇*/
	private String welfare;
	
	/**
	 * 获取职位名称
	 * @return
	 */
	@NotNull
	public String getJobName() {
		return jobName;
	}
	/**
	 * 设置职位名称
	 * @param jobName
	 */
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	/**
	 * 获取所属部门
	 * @return
	 */
	public String getDepartMent() {
		return departMent;
	}
	/**
	 * 设置所属部门
	 * @param departMent
	 */
	public void setDepartMent(String departMent) {
		this.departMent = departMent;
	}
	/**
	 * 获取工作地点
	 * @return
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * 设置工作地点
	 * @param address
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * 获取所属公司
	 * @return
	 */
	public String getCompany() {
		return company;
	}
	/**
	 * 设置所属公司
	 * @param company
	 */
	public void setCompany(String company) {
		this.company = company;
	}
	/**
	 * 获取发布时间
	 * @return
	 */
	public Date getPublishDate() {
		return publishDate;
	}
	/**
	 * 设置发布时间
	 * @param publishDate
	 */
	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}
	/**
	 * 获取职位类型
	 * @return
	 */
	public String getJobType() {
		return jobType;
	}
	/**
	 * 设置职位类型
	 * @param jobType
	 */
	public void setJobType(String jobType) {
		this.jobType = jobType;
	}
	/**
	 * 获取学历
	 * @return
	 */
	public String getSchool() {
		return school;
	}
	/**
	 * 设置学历
	 * @param school
	 */
	public void setSchool(String school) {
		this.school = school;
	}
	/**
	 * 获取工作年限
	 * @return
	 */
	public String getWorkYear() {
		return workYear;
	}
	/**
	 * 设置工作年限
	 * @param workYear
	 */
	public void setWorkYear(String workYear) {
		this.workYear = workYear;
	}
	/**
	 * 获取职位描述
	 * @return
	 */
	@Lob
	public String getJobDesc() {
		return jobDesc;
	}
	/**
	 * 设置职位描述
	 * @param jobDesc
	 */
	public void setJobDesc(String jobDesc) {
		this.jobDesc = jobDesc;
	}
	/**
	 * 获取入职要求
	 * @return
	 */
	@Lob
	public String getJoinReqiure() {
		return joinReqiure;
	}
	/**
	 * 设置入职要求
	 * @param joinReqiure
	 */
	public void setJoinReqiure(String joinReqiure) {
		this.joinReqiure = joinReqiure;
	}
	/**
	 * 获取薪资待遇
	 * @return
	 */
	public String getSalary() {
		return salary;
	}
	/**
	 * 设置薪资待遇
	 * @param salary
	 */
	public void setSalary(String salary) {
		this.salary = salary;
	}
	/**
	 * 获取福利待遇
	 * @return
	 */
	public String getWelfare() {
		return welfare;
	}
	/**
	 * 设置福利待遇
	 * @param welfare
	 */
	public void setWelfare(String welfare) {
		this.welfare = welfare;
	}

}

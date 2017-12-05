package com.whir.ht.cms.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.whir.ht.cms.interceptor.TeacherInterceptor;


/**
 * 教师Entity
 * @author wuxiaoyuan
 *
 */
@Entity
@DiscriminatorValue("T")
public class Teacher extends FrontUser {
	
	private static final long serialVersionUID = 2L;
	
	/** "身份信息"参数名称 */
	public static final String PRINCIPAL_ATTRIBUTE_NAME = TeacherInterceptor.class.getName() + ".PRINCIPAL";
	
	/** "用户名"Cookie名称 */
	public static final String USERNAME_COOKIE_NAME = "username";
	
	/**教师编号*/
	private String teacherNumber;
	
	/**年级*/
	//private Grade grade;
	private Set<Grade> grades = new HashSet<Grade>();
	
	/**班级*/
	private Set<Classes> classess = new HashSet<Classes>();
	
	/**作业*/
	private Set<Work> works = new HashSet<Work>();
	
	/**作业项*/
	private Set<WorkItem> workitems = new HashSet<WorkItem>();
	
	/**
	 * 获取教师编号
	 * @return
	 */
	@Column(length=255)
	public String getTeacherNumber() {
		return teacherNumber;
	}

	/**
	 * 设置教师编号
	 * @param teacherNumber
	 */
	public void setTeacherNumber(String teacherNumber) {
		this.teacherNumber = teacherNumber;
	}
	
//	/**
//	 * 获取年级
//	 * @return
//	 */
//	@ManyToOne(fetch = FetchType.LAZY)
//	public Grade getGrade() {
//		return grade;
//	}
//	/**
//	 * 设置年级
//	 * @param grade
//	 */
//	public void setGrade(Grade grade) {
//		this.grade = grade;
//	}
	
	/**
	 * 获取班级
	 * @return
	 */
	@JsonIgnore
	@JoinTable(name = "teacher_classes", joinColumns = { @JoinColumn(name = "teacher_id") }, inverseJoinColumns = { @JoinColumn(name = "classes_id") })
	@ManyToMany(fetch = FetchType.LAZY)
	@OrderBy(value="sort")
	@Where(clause="del_flag='"+DEL_FLAG_NORMAL+"'")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public Set<Classes> getClassess() {
		return classess;
	}

	/**
	 * 设置班级
	 * @param classes
	 */
	public void setClassess(Set<Classes> classess) {
		this.classess = classess;
	}

	/**
	 * 获取作业
	 * @return
	 */
	@OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY,cascade=CascadeType.ALL)
	@Where(clause="del_flag='"+DEL_FLAG_NORMAL+"'")
	public Set<Work> getWorks() {
		return works;
	}

	/**设置作业*/
	public void setWorks(Set<Work> works) {
		this.works = works;
	}
	
	/**
	 * 获取作业项
	 * @return
	 */
	@OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY)
	public Set<WorkItem> getWorkitems() {
		return workitems;
	}
	/**设置作业*/
	public void setWorkitems(Set<WorkItem> workitems) {
		this.workitems = workitems;
	}

	@JsonIgnore
	@JoinTable(name = "teacher_grade", joinColumns = { @JoinColumn(name = "teacher_id") }, inverseJoinColumns = { @JoinColumn(name = "grade_id") })
	@ManyToMany(fetch = FetchType.LAZY)
	@Where(clause="del_flag='"+DEL_FLAG_NORMAL+"'")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public Set<Grade> getGrades() {
		return grades;
	}

	public void setGrades(Set<Grade> grades) {
		this.grades = grades;
	}
	
}

package com.whir.ht.cms.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.whir.ht.cms.interceptor.StudentInterceptor;


/**
 * 学生Entity
 * @author wuxiaoyuan
 *
 */
@Entity
@DiscriminatorValue("S")
public class Student extends FrontUser {
	
	private static final long serialVersionUID = 3L;
	
	/** "身份信息"参数名称 */
	public static final String PRINCIPAL_ATTRIBUTE_NAME = StudentInterceptor.class.getName() + ".PRINCIPAL";
	
	/** "用户名"Cookie名称 */
	public static final String USERNAME_COOKIE_NAME = "username";
	
	/**学生编号*/
	private String studentNumber;
	
	/**年级*/
	private Grade grade;
	
	/**班级*/
	private Classes classes;
	
	/**班组*/
	private ClassGroup classGroup;
	
	/**作业项*/
	private Set<WorkItem> workitems = new HashSet<WorkItem>();
	
	/**
	 * 获取学号
	 * @return
	 */
	@Column(length=255)
	public String getStudentNumber() {
		return studentNumber;
	}

	/**
	 * 设置学生学号
	 * @param studentNumber
	 */
	public void setStudentNumber(String studentNumber) {
		this.studentNumber = studentNumber;
	}
	
	@Transient
	public String getGradeName() {
		if (null!=grade) {
			return grade.getGradeName();
		} 
		return "";
	}
	
	@Transient
	public String getClassName(){
		if(null!=classes){
			return classes.getClassName();
		}
		return "";
	}
	
	/**
	 * 获取年级
	 * @return
	 */
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	public Grade getGrade() {
		return grade;
	}
	/**
	 * 设置年级
	 * @param grade
	 */
	public void setGrade(Grade grade) {
		this.grade = grade;
	}
	/**
	 * 获取班级
	 * @return
	 */
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	public Classes getClasses() {
		return classes;
	}

	/**
	 * 设置班级
	 * @param classes
	 */
	public void setClasses(Classes classes) {
		this.classes = classes;
	}

	/**
	 * 获取班组
	 * @return
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	public ClassGroup getClassGroup() {
		return classGroup;
	}
    /**
     * 设置班组
     * @param classGroup
     */
	public void setClassGroup(ClassGroup classGroup) {
		this.classGroup = classGroup;
	}
	
	@Transient
	public List<String> getStudentNumberList(){
		List<String> list=new ArrayList<String>();
		if (getStudentNumber()!=null){
			String[] tem=getStudentNumber().split(",");
			for (int i = 0; i < tem.length; i++) {
				list.add(tem[i]);
			}		
		}
		return list;
	}

	/**
	 * 获取作业项
	 * @return
	 */
	@OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
	public Set<WorkItem> getWorkitems() {
		return workitems;
	}
	/**设置作业*/
	public void setWorkitems(Set<WorkItem> workitems) {
		this.workitems = workitems;
	}
	
}

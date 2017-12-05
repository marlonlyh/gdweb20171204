package com.whir.ht.cms.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.whir.ht.common.persistence.IdEntity;

/**
 * 班组
 * @author liuchunyi
 *
 */
@Entity
@Table(name="cms_class_group")
@DynamicInsert @DynamicUpdate
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
public class ClassGroup extends IdEntity<ClassGroup> {
	
	private static final long serialVersionUID = 3L;
	
	/**年级*/
	private Grade grade;
	
	/**班级*/
	private Classes classes;
	
	/**班组名称*/
	private String groupName;
	
	/**作业*/
	private Set<Work> works = new HashSet<Work>();
	
	/**学生 */
	private Set<Student> students = new HashSet<Student>();
	
	/**排序（升序）*/
	private Integer sort;
	
	/**
	 * 获取年级
	 * @return
	 */
	@JsonIgnore
	@NotNull
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(nullable = false)
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
	@NotNull	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(nullable = false)
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
	 * 获取班组名称
	 * @return
	 */
	@NotNull
	public String getGroupName() {
		return groupName;
	}

	/**
	 * 设置班组名称
	 * @param className
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@JsonIgnore
	@OneToMany(mappedBy = "classGroup", fetch = FetchType.LAZY)
	public Set<Work> getWorks() {
		return works;
	}

	public void setWorks(Set<Work> works) {
		this.works = works;
	}

	@JsonIgnore
	@OneToMany(mappedBy = "classGroup", fetch = FetchType.LAZY)
	public Set<Student> getStudents() {
		return students;
	}

	public void setStudents(Set<Student> students) {
		this.students = students;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	@Transient
	public String getValue() {
		return this.groupName;
	}
}

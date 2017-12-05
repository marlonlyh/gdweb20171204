package com.whir.ht.cms.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.whir.ht.common.persistence.IdEntity;

/**
 * 年级
 * @author liuchunyi
 *
 */
@Entity
@Table(name="cms_grade")
@DynamicInsert @DynamicUpdate
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
public class Grade extends IdEntity<Grade> {
	
	private static final long serialVersionUID = 3L;
	
	/**年级名称*/
	private String gradeName;
	
	/**排序（升序）*/
	private Integer sort;
	
	/**班级*/
	private Set<Classes> classess = new HashSet<Classes>();
	
	/**学生 */
	private Set<Student> students = new HashSet<Student>();
	
	/**教师*/
	private Set<Teacher> teachers = new HashSet<Teacher>();
	
	/**作业*/
	private Set<Work> works = new HashSet<Work>();
	
	/**文章*/
	private Set<Article> articles = new HashSet<Article>();

	/**
	 * 获取年级名称
	 * @return
	 */
	public String getGradeName() {
		return gradeName;
	}

	/**
	 * 设置年级名称
	 * @param gradeName
	 */
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
	@JsonIgnore
	@OneToMany(mappedBy = "grade",fetch=FetchType.LAZY)
	public Set<Classes> getClassess() {
		return classess;
	}

	public void setClassess(Set<Classes> classess) {
		this.classess = classess;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	@JsonIgnore
	@OneToMany(mappedBy = "grade", fetch = FetchType.LAZY)
	public Set<Student> getStudents() {
		return students;
	}

	public void setStudents(Set<Student> students) {
		this.students = students;
	}
	@JsonIgnore
	@ManyToMany(mappedBy = "grades", fetch = FetchType.LAZY)
	@Where(clause="del_flag='"+DEL_FLAG_NORMAL+"'")
	public Set<Teacher> getTeachers() {
		return teachers;
	}

	public void setTeachers(Set<Teacher> teachers) {
		this.teachers = teachers;
	}
	@JsonIgnore
	@OneToMany(mappedBy = "grade", fetch = FetchType.LAZY)
	public Set<Work> getWorks() {
		return works;
	}

	public void setWorks(Set<Work> works) {
		this.works = works;
	}
	@JsonIgnore
	@OneToMany(mappedBy = "grade", fetch = FetchType.LAZY,cascade=CascadeType.ALL)
	public Set<Article> getArticles() {
		return articles;
	}

	public void setArticles(Set<Article> articles) {
		this.articles = articles;
	}

	@Transient
	public String getValue() {
		return this.gradeName;
	}
}

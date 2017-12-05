package com.whir.ht.cms.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.whir.ht.common.persistence.IdEntity;

/**
 * 班级
 * @author liuchunyi
 *
 */
@Entity
@Table(name="cms_classes")
@DynamicInsert @DynamicUpdate
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
public class Classes extends IdEntity<Classes> {
	
	private static final long serialVersionUID = 3L;
	
	/**年级*/
	private Grade grade;
	
	/**班级名称*/
	private String className;
	
	/**排序（升序）*/
	private Integer sort;
	
	/**班组*/
	private Set<ClassGroup> classGroups = new HashSet<ClassGroup>();
	
	/**学生 */
	private Set<Student> students = new HashSet<Student>();
	
	/**教师*/
	private Set<Teacher> teachers = new HashSet<Teacher>();
	
	/**年级班级*/
	private String gradeClass;
	
	/**作业*/
/*	private Set<Work> works = new HashSet<Work>();*/
	
	/**文章*/
	private Set<Article> articles = new HashSet<Article>();
	
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
	 * 获取班级名称
	 * @return
	 */
	@NotNull
	public String getClassName() {
		return className;
	}

	/**
	 * 设置班级名称
	 * @param className
	 */
	public void setClassName(String className) {
		this.className = className;
	}
	@JsonIgnore
	@OneToMany(mappedBy = "classes",fetch=FetchType.LAZY)
	public Set<ClassGroup> getClassGroups() {
		return classGroups;
	}

	public void setClassGroups(Set<ClassGroup> classGroups) {
		this.classGroups = classGroups;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	@JsonIgnore
	@OneToMany(mappedBy = "classes", fetch = FetchType.LAZY)
	public Set<Student> getStudents() {
		return students;
	}

	public void setStudents(Set<Student> students) {
		this.students = students;
	}
	@JsonIgnore
	@ManyToMany(mappedBy = "classess", fetch = FetchType.LAZY)
	@Where(clause="del_flag='"+DEL_FLAG_NORMAL+"'")
	public Set<Teacher> getTeachers() {
		return teachers;
	}

	public void setTeachers(Set<Teacher> teachers) {
		this.teachers = teachers;
	}

	public String getGradeClass() {
		return gradeClass;
	}

	public void setGradeClass(String gradeClass) {
		this.gradeClass = gradeClass;
	}
	@JsonIgnore
	@OneToMany(mappedBy = "classes", fetch = FetchType.LAZY,cascade=CascadeType.ALL)
	@Where(clause="del_flag='0'")
	@OrderBy(value="sort")
	public Set<Article> getArticles() {
		return articles;
	}

	public void setArticles(Set<Article> articles) {
		this.articles = articles;
	}

/*	@ManyToMany(mappedBy = "classess",fetch = FetchType.LAZY)
	public Set<Work> getWorks() {
		return works;
	}

	public void setWorks(Set<Work> works) {
		this.works = works;
	}*/
	@Transient
	public String getValue() {
		return this.className;
	}
}

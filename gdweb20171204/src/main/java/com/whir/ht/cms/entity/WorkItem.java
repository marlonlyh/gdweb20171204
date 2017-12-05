package com.whir.ht.cms.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.whir.ht.common.persistence.IdEntity;

/**
 * 每个学生提交的作业
 * @author liuchunyi
 *
 */
@Entity
@Table(name="cms_work_item")
@DynamicInsert @DynamicUpdate
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class WorkItem extends IdEntity<WorkItem> {
	
	private static final long serialVersionUID = 3L;
	
	/**作业*/
	private Work work;

	/**教师*/
	private Teacher teacher;
	
	/**学生*/
	private Student student;
	
	/**附件*/
	private String attach;
	
	/**原始文件名*/
	private String fileName;
	
	/**原始图片名*/
	private String photoName;
	
	/**
	 * 获取作业
	 * @return
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "works")
	public Work getWork() {
		return work;
	}

	/**
	 * 设置作业
	 * @param work
	 */
	public void setWork(Work work) {
		this.work = work;
	}
	
	/**获取教师*/
	@ManyToOne(fetch = FetchType.LAZY)
	public Teacher getTeacher() {
		return teacher;
	}

	/**设置教师 * */
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	
	/**获取学生*/
	@ManyToOne(fetch = FetchType.LAZY)
	public Student getStudent() {
		return student;
	}

	/**设置学生 * */
	public void setStudent(Student student) {
		this.student = student;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}
	
	/**
	 * 原始文件名
	 * @return
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * 原始文件名
	 * @param fileName
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * 原始图片名
	 * @return
	 */
	public String getPhotoName() {
		return photoName;
	}

	/**
	 * 原始图片名
	 * @param photoName
	 */
	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}
	
}

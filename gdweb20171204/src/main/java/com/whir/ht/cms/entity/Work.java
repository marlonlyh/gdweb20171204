package com.whir.ht.cms.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.whir.ht.cms.utils.CmsUtils;
import com.whir.ht.common.persistence.IdEntity;

/**
 * 作业
 * 
 * @author liuchunyi
 *
 */
@Entity
@Table(name = "cms_work")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Work extends IdEntity<Work> {

	private static final long serialVersionUID = 3L;

	/** 作业名称 */
	private String workName;

	/** 年级 */
	private Grade grade;

	/** 班级列表 */
/*	private Set<Classes> classess = new HashSet<Classes>();*/

	/** 班级 */
	private Classes classes;

	/** 班组 */
	private ClassGroup classGroup;

	/** 布置时间 */
	private Date publishDate;

	/** 完成时间 */
	private Date finishDate;

	/** 附件 */
	private String attach;

	/** 作业详情 */
	private String content;

	/** 图片 */
	private String image;

	/** 教师 */
	private Teacher teacher;

	/** 原始文件名 */
	private String fileName;

	/** 原始图片名 */
	private String photoName;

	/** 学生是否查看（1：已查看；0：未查看） */
	private String isView;
	
	/** 教师是否查阅（1：已阅；0：未阅） */
	private String isRead;
	
	/** 作业是否已分配（1：已分配；0：未分配） */
	private String isAssign;

	/** 作业项 */
	private List<WorkItem> workItems = new ArrayList<WorkItem>();

	/**
	 * 获取作业名称
	 * 
	 * @return
	 */
	public String getWorkName() {
		return workName;
	}

	/**
	 * 设置作业名称
	 * 
	 * @param workName
	 */
	public void setWorkName(String workName) {
		this.workName = workName;
	}

	/**
	 * 获取布置时间
	 * 
	 * @return
	 */
	public Date getPublishDate() {
		return publishDate;
	}

	/**
	 * 设置布置时间
	 * 
	 * @param publishDate
	 */
	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	/**
	 * 获取完成时间
	 * 
	 * @return
	 */
	public Date getFinishDate() {
		return finishDate;
	}

	/**
	 * 设置完成时间
	 * 
	 * @param finishDate
	 */
	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	// @NotBlank
	@JsonIgnore
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Length(min = 0, max = 255)
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = CmsUtils.formatImageSrcToDb(image);
	}

	@Transient
	public String getImageSrc() {
		return CmsUtils.formatImageSrcToWeb(this.image);
	}

/*	@JoinTable(name = "work_classes", joinColumns = { @JoinColumn(name = "work_id") }, inverseJoinColumns = {
			@JoinColumn(name = "classes_id") })
	@ManyToMany(fetch = FetchType.LAZY)
	@Where(clause = "del_flag='" + DEL_FLAG_NORMAL + "'")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	@JsonIgnore
	public Set<Classes> getClassess() {
		return classess;
	}

	public void setClassess(Set<Classes> classess) {
		this.classess = classess;
	}*/

	/** 获取班级 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	public Classes getClasses() {
		return classes;
	}

	/** 设置班级 * */
	public void setClasses(Classes classes) {
		this.classes = classes;
	}

	/** 获取教师 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	public Teacher getTeacher() {
		return teacher;
	}

	/** 设置教师 * */
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	/**
	 * 原始文件名
	 * 
	 * @return
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * 原始文件名
	 * 
	 * @param fileName
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * 原始图片名
	 * 
	 * @return
	 */
	public String getPhotoName() {
		return photoName;
	}

	/**
	 * 原始图片名
	 * 
	 * @param photoName
	 */
	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	public ClassGroup getClassGroup() {
		return classGroup;
	}

	public void setClassGroup(ClassGroup classGroup) {
		this.classGroup = classGroup;
	}

	@Length(min = 1, max = 1)
	public String getIsView() {
		return isView;
	}

	public void setIsView(String isView) {
		this.isView = isView;
	}

	/** 获取年级 */
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	public Grade getGrade() {
		return grade;
	}

	/** 设置年级 * */
	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	/**
	 * 获取作业项
	 * 
	 * @return 作业项
	 */
	@OneToMany(mappedBy = "work", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	public List<WorkItem> getWorkItems() {
		return workItems;
	}

	public void setWorkItems(List<WorkItem> workItems) {
		this.workItems = workItems;
	}
	
	/**
	 * 获取教师是否查阅
	 * @return
	 */
	@Length(min = 1, max = 1)
	public String getIsRead() {
		return isRead;
	}

	/**
	 * 设置教师是否查阅
	 * @param isRead
	 */
	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}
	
	/**
	 * 获取作业是否已分配
	 * @return
	 */
	@Length(min = 1, max = 1)
	public String getIsAssign() {
		return isAssign;
	}

	/**
	 * 设置作业是否已分配
	 * @param isAssign
	 */
	public void setIsAssign(String isAssign) {
		this.isAssign = isAssign;
	}
	
	@Transient
	public String getGradeId() {
		if (null!=this.grade) {
			return this.grade.getId();
		}
		else {
			return "";
		}
	}

	@Transient
	public String getClassId() {
		if (null!=this.classes) {
			return this.classes.getId();
		}
		else {
			return "";
		}
		
	}
	
	@Transient
	public String getGroupId() {
		if (null!=this.classGroup) {
			return this.classGroup.getId();
		}
		else {
			return "";
		}
	}
	
	@Transient
	public String getGroupName() {
		if (null!=this.classGroup) {
			return this.classGroup.getGroupName();
		}
		else {
			return "";
		}
	}
	
	@Transient
	public String getTeacherName() {
		if (null!=this.teacher) {
			return this.getTeacher().getName();
		}
		else {
			return "";
		}
 		
	}

}

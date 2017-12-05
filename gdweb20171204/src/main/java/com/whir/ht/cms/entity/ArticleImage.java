package com.whir.ht.cms.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.whir.ht.common.persistence.IdEntity;

/**
 * 文章图片
 * @author liuchunyi
 *
 */
@Entity
@Table(name="cms_article_image")
@DynamicInsert @DynamicUpdate
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class ArticleImage extends IdEntity<ArticleImage> {
	
	private static final long serialVersionUID = 3L;
	
	/**文章*/
	private Article article;
	
	/**图片保存路径*/
	private String url;
	
	/**原图片名*/
	private String imageName;
	
	/**标记*/
	private String flag;
	
	/** 文件 */
	private MultipartFile file;

	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	@Length(min=0, max=255)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Length(min=0, max=255)
	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	/**
	 * 
	 * @return
	 */
	public String getFlag() {
		return flag;
	}
	/**
	 * 
	 * @param flag
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	public ArticleImage(){
		super();
		this.flag="1";
	}
	
	/**
	 * 获取文件
	 * 
	 * @return 文件
	 */
	@Transient
	@JsonIgnore
	public MultipartFile getFile() {
		return file;
	}

	/**
	 * 设置文件
	 * 
	 * @param file
	 *            文件
	 */
	public void setFile(MultipartFile file) {
		this.file = file;
	}

}
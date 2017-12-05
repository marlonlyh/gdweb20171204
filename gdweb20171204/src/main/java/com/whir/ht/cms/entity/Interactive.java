package com.whir.ht.cms.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;
import org.jsoup.Jsoup;

import com.whir.ht.common.persistence.IdEntity;
/**
 * 师生互动
 * @author liuchunyi
 *
 */
@Entity
@Table(name = "cms_interactive")
@DynamicInsert @DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Interactive extends IdEntity<Interactive> {
	
	private static final long serialVersionUID = 3L;
	
	/**标题*/
	private String title;
	
	/**描述*/
	private String description;
	
	/**补充说明*/
	private String instruction;
	
	/**是否显示*/
	private String isShow;
	
	/**是否置顶*/
	private String isTop;
	
	/**答复*/
	private String reply;
	
	public Interactive(){
		super();
		this.isShow= YES;
		this.isTop= NO;
	}
	
	/**
	 * 获取标题
	 * @return
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 设置标题
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * 获取问题
	 * @return
	 */
	@Length(min=1, max=255)
	public String getDescription() {
		return description;
	}
	/**
	 * 设置问题
	 * @param question
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 获取补充说明
	 * @return
	 */
	@Length(min=0, max=255)
	public String getInstruction() {
		return instruction;
	}
	/**
	 * 设置补充说明
	 * @param instruction
	 */
	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}
	/**
	 * 获取是否显示
	 * @return
	 */
	@Length(min=1, max=1)
	public String getIsShow() {
		return isShow;
	}
	/**
	 * 设置是否显示
	 * @return
	 */
	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}
	/**
	 * 获取回复
	 * @return
	 */
	public String getReply() {
		return reply;
	}
	/**
	 * 设置回复
	 * @return
	 */
	public void setReply(String reply) {
		this.reply = reply;
	}

	/**
	 * 设置是否置顶
	 * @return
	 */
	public String getIsTop() {
		return isTop;
	}

	/**
	 * 获取是否置顶
	 * @param isTop
	 */
	public void setIsTop(String isTop) {
		this.isTop = isTop;
	}
	
	@Transient
	public String getText() {
		if (getReply() != null) {
			return Jsoup.parse(getReply()).text();
		}
		return null;
	}

}

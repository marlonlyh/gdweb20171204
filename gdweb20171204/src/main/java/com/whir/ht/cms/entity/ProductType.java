package com.whir.ht.cms.entity;


import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;

import com.google.common.collect.Lists;
import com.whir.ht.common.persistence.IdEntity;

/**
 * 产品分类
 * @author wangqing1
 *
 */
@Entity
@Table(name = "cms_product_type")
@DynamicInsert @DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProductType extends IdEntity<ProductType> {
	
	private static final long serialVersionUID = 3L;
	
	/**分类名称*/
	private String name;
	
	/**上级分类*/
	private ProductType parent;
	
	private String parentIds;// 所有父级编号
	
	/**层级*/
	private Integer grade;
	
//	/**获取下级分类*/
//	private Set<ProductType> children=new HashSet<ProductType>();
	private List<ProductType> childList = Lists.newArrayList(); 	// 拥有子分类列表
	/** 排序 */
//	private Integer order;
	private Integer sort; 		// 排序（升序）
	/**
	 *获取分类名称
	 * @return
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置分类名称
	 * @return
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取上级分类
	 * @return
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="parent")
	@NotFound(action = NotFoundAction.IGNORE)
	public ProductType getParent() {
		return parent;
	}
	/**
	 * 设置上级分类
	 * @return
	 */
	public void setParent(ProductType parent) {
		this.parent = parent;
	}
	

	/**
	 * 获取所有父级编号
	 * @return
	 */
	public String getParentIds() {
		return parentIds;
	}

	/**
	 * 设置所有父级编号
	 * @param parentIds
	 */
	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	/**
	 * 获取层级
	 * @return
	 */
	public Integer getGrade() {
		return grade;
	}
	/**
	 * 设置层级
	 * @return
	 */
	public void setGrade(Integer grade) {
		this.grade = grade;
	}
//	/**
//	 * 获取下级分类
//	 * @return
//	 */
//	@OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
//	@OrderBy("order asc")
//	public Set<ProductType> getChildren() {
//		return children;
//	}
//	/**
//	 * 设置下级分类
//	 * @return
//	 */
//	public void setChildren(Set<ProductType> children) {
//		this.children = children;
//	}
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="parent")
	@Where(clause="del_flag='"+DEL_FLAG_NORMAL+"'")
	@OrderBy(value="sort")
	@NotFound(action = NotFoundAction.IGNORE)	
	public List<ProductType> getChildList() {
		return childList;
	}

	
	public void setChildList(List<ProductType> childList) {
		this.childList = childList;
	}
	
//	/**
//	 * 获取排序
//	 * @return
//	 */
//	public Integer getOrder() {
//		return order;
//	}
//	/**
//	 * 设置排序
//	 * @param order
//	 */
//	public void setOrder(Integer order) {
//		this.order = order;
//	}
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	public ProductType(){
		super();
	
	}
	
	public ProductType(String id){
		this();
		this.id = id;
	}
	
	@Transient
	public static boolean isRoot(String id){
		return id != null && id.equals("1");
	}
	
	@Transient
	public static void sortList(List<ProductType> list, List<ProductType> sourcelist, String parentId){
		for (int i=0; i<sourcelist.size(); i++){
			ProductType e = sourcelist.get(i);
			if (e.getParent()!=null && e.getParent().getId()!=null
					&& e.getParent().getId().equals(parentId)){
				list.add(e);
				// 判断是否还有子节点, 有则继续获取子节点
				for (int j=0; j<sourcelist.size(); j++){
					ProductType child = sourcelist.get(j);
					if (child.getParent()!=null && child.getParent().getId()!=null
							&& child.getParent().getId().equals(e.getId())){
						sortList(list, sourcelist, e.getId());
						break;
					}
				}
			}
		}
	}
	
	@Transient
	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	public String getIds() {
		return (this.getParentIds() !=null ? this.getParentIds().replaceAll(",", " ") : "") 
				+ (this.getId() != null ? this.getId() : "");
	}
	
    @Transient
    public ProductType getTopProductType() {
    	ProductType cat = null;
    	if (StringUtils.isNotBlank(getParentIds())) {
    		String[] ids = StringUtils.split(parentIds,",");
    		if (ids.length<=2) {
    			cat = this;
    		} else {
    			cat = new ProductType(ids[2]);
    		}
    	}
    	return cat;
    }
    
    @Transient
    public List<ProductType> getValidList() {
    	List<ProductType> vlist = Lists.newArrayList();
    	
    	for (ProductType cat: this.childList) {
    		if ( StringUtils.equals(cat.getDelFlag(), ProductType.DEL_FLAG_NORMAL)) {
    			vlist.add(cat);
    		}
    	}
    	
    	return vlist;
    }
}

/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/whir/ht">ezSite</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.whir.ht.cms.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.whir.ht.cms.entity.Category;
import com.whir.ht.common.persistence.BaseDao;
import com.whir.ht.common.persistence.Parameter;

/**
 * 栏目DAO接口
 * @author Elvin
 * @version 2013-8-23
 */
@Repository
public class CategoryDao extends BaseDao<Category> {
	
	public List<Category> findByParentIdsLike(String parentIds){
		return find("from Category where parentIds like :p1", new Parameter(parentIds));
	}

	public List<Category> findByModule(String module){
		return find("from Category where delFlag=:p1 and (module='' or module=:p2) order by site.id, sort", 
				new Parameter(Category.DEL_FLAG_NORMAL, module));
	}
	
	public List<Category> findByParentId(String parentId, String isMenu){
		return find("from Category where delFlag=:p1 and parent.id=:p2 and inMenu=:p3 order by site.id, sort", 
				new Parameter(Category.DEL_FLAG_NORMAL, parentId, isMenu));
	}

	public List<Category> findByParentIdAndSiteId(String parentId, String siteId){
		return find("from Category where delFlag=:p1 and parent.id=:p2 and site.id=:p3 order by site.id, sort", 
				new Parameter(Category.DEL_FLAG_NORMAL, parentId, siteId));
	}
	
	public List<Category> findByParentIdAndSiteIdAndApp(String parentId, String siteId, String appinMenu){
		return find("from Category where delFlag=:p1 and parent.id=:p2 and site.id=:p3 and appinMenu=:p4 order by site.id, sort", 
				new Parameter(Category.DEL_FLAG_NORMAL, parentId, siteId,appinMenu));
	}
	
	public List<Category> findByIdIn(String[] ids){
		return find("from Category where id in (:p1)", new Parameter(new Object[]{ids}));
	}

	public List<Category> findByCategorys(String appinMenu){
		return find("from Category where delFlag=:p1 and appinMenu=:p2 order by site.id, sort", 
				new Parameter(Category.DEL_FLAG_NORMAL,appinMenu));
	}
//	@Query("select distinct c from Category c, Role r, User u where c in elements (r.categoryList) and r in elements (u.roleList)" +
//			" and c.delFlag='" + Category.DEL_FLAG_NORMAL + "' and r.delFlag='" + Role.DEL_FLAG_NORMAL + 
//			"' and u.delFlag='" + User.DEL_FLAG_NORMAL + "' and u.id=?1 or (c.user.id=?1 and c.delFlag='" + Category.DEL_FLAG_NORMAL +
//			"') order by c.site.id, c.sort")
//	public List<Category> findByUserId(Long userId);
	
}
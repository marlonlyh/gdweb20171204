package com.whir.ht.cms.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.whir.ht.cms.entity.Resource;
import com.whir.ht.common.persistence.BaseDao;
import com.whir.ht.common.persistence.Parameter;

/**
 * Dao 原材料
 * @author wuxiaoyuan
 *
 */
@Repository
public class ResourceDao extends BaseDao<Resource> {

	public Resource findBySort(Integer sort){
		List<Resource> list=find("from Resource where sort = (:p1) and delFlag=0", new Parameter(sort));
		if (list.size()==0){
			return null;
		}else {
			return list.get(0);
		}
	}
	
	/**
	 * 根据列号查询
	 * @param sort
	 * @return
	 */
	public Resource findSort(Integer sort){
		List<Resource> list=find("from Resource where sort = :p1 and delFlag = :p2", new Parameter(sort,Resource.DEL_FLAG_NORMAL));     
		if ((list ==null) || (list.size()==0)){
			return new Resource();
		}else {
			return list.get(0);
		}
	}
	
}

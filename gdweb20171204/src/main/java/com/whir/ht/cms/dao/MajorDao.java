package com.whir.ht.cms.dao;

import org.springframework.stereotype.Repository;

import com.whir.ht.cms.entity.Major;
import com.whir.ht.common.persistence.BaseDao;
import com.whir.ht.common.persistence.Parameter;

@Repository
public class MajorDao extends BaseDao<Major> {
	/**
	 * 检测专业名称是否存在
	 * @param gradeName
	 * @return
	 */
	public Major findName(String majorName){
	
		return getByHql("from Major where majorName = :p1 and delFlag = :p2", new Parameter(majorName,Major.DEL_FLAG_NORMAL));
	}

}

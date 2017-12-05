package com.whir.ht.cms.dao;

import org.springframework.stereotype.Repository;

import com.whir.ht.cms.entity.Grade;
import com.whir.ht.common.persistence.BaseDao;
import com.whir.ht.common.persistence.Parameter;

@Repository
public class GradeDao extends BaseDao<Grade> {
	/**
	 * 检测年级名称是否存在
	 * @param gradeName
	 * @return
	 */
	public Grade findName(String gradeName){
	
		return getByHql("from Grade where gradeName = :p1 and delFlag = :p2", new Parameter(gradeName,Grade.DEL_FLAG_NORMAL));
	}

}

package com.whir.ht.cms.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.whir.ht.cms.entity.Classes;
import com.whir.ht.common.persistence.BaseDao;
import com.whir.ht.common.persistence.Parameter;

@Repository
public class ClassesDao extends BaseDao<Classes> {
	public Classes findName(String className,String gradeId){
		Classes classess = getByHql("from Classes where className = :p1 and delFlag = :p2 and grade.id= :p3", new Parameter(className,Classes.DEL_FLAG_NORMAL,gradeId));
		 return classess;
	}
}

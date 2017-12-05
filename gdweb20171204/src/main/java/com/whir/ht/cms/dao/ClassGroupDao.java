package com.whir.ht.cms.dao;

import org.springframework.stereotype.Repository;

import com.whir.ht.cms.entity.ClassGroup;
import com.whir.ht.cms.entity.Classes;
import com.whir.ht.common.persistence.BaseDao;
import com.whir.ht.common.persistence.Parameter;

@Repository
public class ClassGroupDao extends BaseDao<ClassGroup> {
	public ClassGroup findName(String groupName,String gradeId,String classesId){
	ClassGroup classgroups = getByHql("from ClassGroup where groupName = :p1 and delFlag = :p2 and grade.id= :p3 and classes.id= :p4", new Parameter(groupName,ClassGroup.DEL_FLAG_NORMAL,gradeId,classesId));
	 return classgroups;
	}
}

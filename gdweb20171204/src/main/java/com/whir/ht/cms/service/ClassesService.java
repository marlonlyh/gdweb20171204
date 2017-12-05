package com.whir.ht.cms.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.whir.ht.cms.dao.ClassesDao;
import com.whir.ht.cms.entity.Classes;
import com.whir.ht.cms.entity.Grade;
import com.whir.ht.common.persistence.Page;
import com.whir.ht.common.persistence.Parameter;
import com.whir.ht.common.service.BaseService;

/**
 * 班级
 * @author liuchunyi
 *
 */

@Service
@Transactional(readOnly = true)
public class ClassesService extends BaseService {

	@Autowired
	private ClassesDao classesDao;
	
	public Classes get(String id){
		return classesDao.get(id);
	}
	
	@Transactional(readOnly = false)
	public Page<Classes> find(Page<Classes> page,Classes classes,boolean isDataScopeFilter){
		DetachedCriteria dc = classesDao.createDetachedCriteria();
		dc.createAlias("grade", "grade");
		if (StringUtils.isNotEmpty(classes.getClassName())){
			dc.add(Restrictions.like("className", "%"+classes.getClassName()+"%"));
		}
		dc.add(Restrictions.eq(Classes.FIELD_DEL_FLAG, classes.getDelFlag()));
		dc.addOrder(Order.asc("grade.sort"));
		dc.addOrder(Order.asc("sort"));
		return classesDao.find(page, dc);
	}
	
	/**根据年级查询班级 */
	@Transactional(readOnly = false)
	public List<Classes> findList(Grade grade){
		DetachedCriteria dc = classesDao.createDetachedCriteria();
		dc.add(Restrictions.eq("grade", grade));
		dc.add(Restrictions.eq(Classes.FIELD_DEL_FLAG, Classes.DEL_FLAG_NORMAL));
		dc.addOrder(Order.asc("sort"));
		return classesDao.find(dc);
	}
	
	/**根据年级和教师查询班级 */
	@Transactional(readOnly = false)
	public List<Classes> findList(Grade grade,String teacherid){
		DetachedCriteria dc = classesDao.createDetachedCriteria();
		dc.createAlias("teachers", "teachers");
		dc.add(Restrictions.eq("grade", grade));
		dc.add(Restrictions.eq("teachers.id", teacherid));
		dc.add(Restrictions.eq(Classes.FIELD_DEL_FLAG, Classes.DEL_FLAG_NORMAL));
		dc.addOrder(Order.asc("sort"));
		return classesDao.find(dc);
	}
	
	/**根据班级id查询 */
	@Transactional(readOnly = false)
	public List<Classes> findList(String classesId){
		DetachedCriteria dc = classesDao.createDetachedCriteria();
		dc.createAlias("grade", "grade");
		dc.add(Restrictions.eq("id", classesId));
		dc.add(Restrictions.eq(Classes.FIELD_DEL_FLAG, Classes.DEL_FLAG_NORMAL));
		dc.addOrder(Order.asc("grade.sort"));
		dc.addOrder(Order.asc("sort"));
		return classesDao.find(dc);
	}
	
	/**
	 * 检测班级名称是否存在
	 * 
	 * @param className
	 * @return
	 */
	
	public Classes getClassName(String className,String gradeId) {
		
		return classesDao.findName(className,gradeId);
	}
	
	/**获取班级*/
	public List<Classes> getClasses(){
		return  classesDao.find("select c from Classes c,Grade g where c.delFlag=0 and c.grade=g.id order by g.sort,c.sort");
	}
	
	/**获取不同名称班级*/
	public List<Classes> getClassName(){
		return  classesDao.find("select className from Classes where delFlag=0 group by className order by max(sort)");
	}
	
	/**根据教师id查询班级 */
	public List<Classes> findByClassesId(String teacherid){
		return classesDao.find("select distinct c from Classes c,Teacher t where c in elements (t.classess)" +
				" and c.delFlag=:p1 and t.delFlag=:p1 and t.id=:p2" +  
				" order by c.grade.sort,c.sort", new Parameter(Classes.DEL_FLAG_NORMAL, teacherid));
	}
	
	@Transactional(readOnly = false)
	public void save(Classes classes){
		classesDao.clear();
		classesDao.save(classes);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		Classes classes = classesDao.get(id);
		if(classes!=null){
			classesDao.deleteById(id);
		}
	}
}

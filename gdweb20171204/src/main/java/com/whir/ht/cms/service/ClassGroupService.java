package com.whir.ht.cms.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.whir.ht.cms.dao.ClassGroupDao;
import com.whir.ht.cms.entity.ClassGroup;
import com.whir.ht.cms.entity.Classes;
import com.whir.ht.common.persistence.Page;
import com.whir.ht.common.service.BaseService;

/**
 * 班组
 * @author liuchunyi
 *
 */

@Service
@Transactional(readOnly = true)
public class ClassGroupService extends BaseService {

	@Autowired
	private ClassGroupDao classGroupDao;
	
	public ClassGroup get(String id){
		return classGroupDao.get(id);
	}
	
	@Transactional(readOnly = false)
	public Page<ClassGroup> find(Page<ClassGroup> page,ClassGroup classGroup,boolean isDataScopeFilter){
		DetachedCriteria dc = classGroupDao.createDetachedCriteria();
		dc.createAlias("grade", "grade");
		dc.createAlias("classes", "classes");
		if (StringUtils.isNotEmpty(classGroup.getGroupName())){
			dc.add(Restrictions.like("groupName", "%"+classGroup.getGroupName()+"%"));
		}
		dc.add(Restrictions.eq(ClassGroup.FIELD_DEL_FLAG, classGroup.getDelFlag()));
		dc.addOrder(Order.asc("grade.sort"));
		dc.addOrder(Order.asc("classes.sort"));
		dc.addOrder(Order.asc("sort"));
		return classGroupDao.find(page, dc);
	}
	
	/**根据班级查找班组*/
	@Transactional(readOnly = false)
	public List<ClassGroup> findList(Classes classes){
		DetachedCriteria dc = classGroupDao.createDetachedCriteria();
		dc.add(Restrictions.eq("classes", classes));
		dc.add(Restrictions.eq(ClassGroup.FIELD_DEL_FLAG,ClassGroup.DEL_FLAG_NORMAL));
		dc.addOrder(Order.asc("sort"));
		return classGroupDao.find(dc);
	}
	
	@Transactional(readOnly = false)
	public void save(ClassGroup classGroup){
		classGroupDao.clear();
		classGroupDao.save(classGroup);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		ClassGroup classGroup = classGroupDao.get(id);
		if(classGroup!=null){
			classGroupDao.deleteById(id);
		}
	}
	/**
	 * 检测班组名称是否存在
	 * 
	 * @param groupName
	 * @return
	 */
	
	public ClassGroup getGroupName(String groupName,String gradeId,String classesId) {
		
		return classGroupDao.findName(groupName,gradeId,classesId);
	}
}

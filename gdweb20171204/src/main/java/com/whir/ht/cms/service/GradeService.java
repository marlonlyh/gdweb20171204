package com.whir.ht.cms.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.whir.ht.cms.dao.GradeDao;
import com.whir.ht.cms.entity.Classes;
import com.whir.ht.cms.entity.Grade;
import com.whir.ht.common.persistence.Page;
import com.whir.ht.common.persistence.Parameter;
import com.whir.ht.common.service.BaseService;

/**
 * 年级
 * @author liuchunyi
 *
 */

@Service
@Transactional(readOnly = true)
public class GradeService extends BaseService {

	@Autowired
	private GradeDao gradeDao;
	
	public Grade get(String id){
		return gradeDao.get(id);
	}
	
	@Transactional(readOnly = false)
	public Page<Grade> find(Page<Grade> page,Grade grade,boolean isDataScopeFilter){
		DetachedCriteria dc = gradeDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(grade.getGradeName())){
			dc.add(Restrictions.like("gradeName", "%"+grade.getGradeName()+"%"));
		}
		dc.add(Restrictions.eq(Grade.FIELD_DEL_FLAG, grade.getDelFlag()));
		dc.addOrder(Order.asc("sort"));
		return gradeDao.find(page, dc);
	}
	
	/**
	 * 检测年级名称是否存在
	 * 
	 * @param gradeName
	 * @return
	 */
	public Grade getGradeName(String gradeName) {
		return gradeDao.findName(gradeName);
	}
	
	/**获取年级*/
	public List<Grade> getGrade(){
		return  gradeDao.find("from Grade where delFlag=0 order by sort");
	}
	
	/**根据教师id查询年级 */
	public List<Grade> findByGradeId(String teacherid){
		return gradeDao.find("select distinct g from Grade g,Teacher t where g in elements (t.grades)" +
				" and g.delFlag=:p1 and t.delFlag=:p1 and t.id=:p2" +  
				" order by g.sort", new Parameter(Grade.DEL_FLAG_NORMAL, teacherid));
	}
	
	@Transactional(readOnly = false)
	public void save(Grade grade){
		gradeDao.clear();
		gradeDao.save(grade);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		Grade grade = gradeDao.get(id);
		if(grade!=null){
			gradeDao.deleteById(id);
		}
	}
}

package com.whir.ht.cms.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.whir.ht.cms.dao.FrontUserDao;
import com.whir.ht.cms.dao.WorkDao;
import com.whir.ht.cms.entity.ClassGroup;
import com.whir.ht.cms.entity.Classes;
import com.whir.ht.cms.entity.FrontUser;
import com.whir.ht.cms.entity.Teacher;
import com.whir.ht.cms.entity.Work;
import com.whir.ht.common.persistence.Page;
import com.whir.ht.common.service.BaseService;

/**
 * 作业
 * @author liuchunyi
 *
 */

@Service
@Transactional(readOnly = true)
public class WorkService extends BaseService {

	@Autowired
	private WorkDao workDao;
	
	@Autowired
	private FrontUserDao userDao;
	
	public Work get(String id){
		return workDao.get(id);
	}
	
	@Transactional(readOnly = false)
	public Page<Work> find(Page<Work> page,Work work,boolean isDataScopeFilter){
		DetachedCriteria dc = workDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(work.getWorkName())){
			dc.add(Restrictions.like("workName", "%"+work.getWorkName()+"%"));
		}
		if (StringUtils.isNotEmpty(work.getContent())){
			dc.add(Restrictions.like("content", "%"+work.getContent()+"%"));
		}
		dc.add(Restrictions.eq(Work.FIELD_DEL_FLAG, work.getDelFlag()));
		dc.addOrder(Order.desc("publishDate"));
		return workDao.find(page, dc);
	}
	
	/**用于APP*/
	@Transactional(readOnly = false)
	public Page<Work> findPage(Page<Work> page,Teacher teacher,Classes classes){
		DetachedCriteria dc = workDao.createDetachedCriteria();
		if (null!=teacher){
			dc.add(Restrictions.eq("teacher", teacher));
		}
		
		if (null!=classes){
			dc.add(Restrictions.eq("classes", classes));
		}
		dc.add(Restrictions.eq(Work.FIELD_DEL_FLAG, Work.DEL_FLAG_NORMAL));
		dc.add(Restrictions.gt("finishDate",new Date()));
		dc.addOrder(Order.desc("publishDate"));
		return workDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public List<Work> findteacherWork(Teacher teacher,String classId){
		DetachedCriteria dc = workDao.createDetachedCriteria();
		dc.add(Restrictions.eq("teacher", teacher));
		if(StringUtils.isNotBlank(classId)){
			dc.add(Restrictions.eq("classes.id", classId));
		}
		dc.add(Restrictions.eq(Work.FIELD_DEL_FLAG, Work.DEL_FLAG_NORMAL));
		dc.add(Restrictions.gt("finishDate",new Date()));
		dc.addOrder(Order.desc("publishDate"));
		return workDao.find(dc);
	}
	
	/**前台教师分配作业根据关键字查询**/
	@Transactional(readOnly = false)
	public List<Work> findteacherWork(Teacher teacher,String classId,String keyword){
		DetachedCriteria dc = workDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(keyword)){
			dc.add(Restrictions.or(Restrictions.like("workName", "%"+keyword+"%"),
					 Restrictions.like("content", "%"+keyword+"%")));
		}
		dc.add(Restrictions.eq("teacher", teacher));
		if(StringUtils.isNotBlank(classId)){
			dc.add(Restrictions.eq("classes.id", classId));
		}
		dc.add(Restrictions.eq(Work.FIELD_DEL_FLAG, Work.DEL_FLAG_NORMAL));
		dc.add(Restrictions.gt("finishDate",new Date()));
		dc.addOrder(Order.desc("publishDate"));
		return workDao.find(dc);
	}
	
	/**前台关键字搜索*/
	@Transactional(readOnly = false)
	public Page<Work> searchteacherWork(Page<Work> page,String keyword,Teacher teacher,String classId){
		DetachedCriteria dc = workDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(keyword)){
			dc.add(Restrictions.or(Restrictions.like("workName", "%"+keyword+"%"),
			Restrictions.like("content", "%"+keyword+"%")));
		}
		dc.add(Restrictions.eq("teacher", teacher));
		if(StringUtils.isNotBlank(classId)){
			dc.add(Restrictions.eq("classes.id", classId));
		}
		dc.add(Restrictions.eq(Work.FIELD_DEL_FLAG, Work.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("publishDate"));
		return workDao.find(page,dc);
	}
	
	@Transactional(readOnly = false)
	public List<Work> findteacherWork(String keyword,Teacher teacher,String classId,String classGroupId){
		DetachedCriteria dc = workDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(keyword)){
			dc.add(Restrictions.or(Restrictions.like("workName", "%"+keyword+"%"),
					 Restrictions.like("content", "%"+keyword+"%")));
		}
		dc.add(Restrictions.eq("teacher", teacher));
		if(StringUtils.isNotBlank(classId)){
			dc.add(Restrictions.eq("classes.id", classId));
		}
		if(StringUtils.isNotBlank(classGroupId)){
			dc.add(Restrictions.eq("classGroup.id", classGroupId));
		}
		dc.add(Restrictions.eq(Work.FIELD_DEL_FLAG, Work.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("publishDate"));
		return workDao.find(dc);
	}
	
	/**前台关键字搜索，学生分页查询教师布置作业*/
	@Transactional(readOnly = false)
	public Page<Work> findteacherWork(Page<Work> page,String keyword,List<Teacher> teacherList,String classId,String classGroupId){	
		DetachedCriteria dc = workDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(keyword)){
			dc.add(Restrictions.or(Restrictions.like("workName", "%"+keyword+"%"),
					 Restrictions.like("content", "%"+keyword+"%")));
		}
		dc.add(Restrictions.in("teacher", teacherList));
		//dc.createAlias("classess", "classess");
		if(StringUtils.isNotBlank(classId)){
			dc.add(Restrictions.eq("classes.id", classId));
		}
		if(StringUtils.isNotBlank(classGroupId)){
			dc.add(Restrictions.eq("classGroup.id", classGroupId));
		}
		dc.add(Restrictions.eq(Work.FIELD_DEL_FLAG, Work.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("publishDate"));
		return workDao.find(page,dc);
	}
	
	@Transactional(readOnly = false)
	public void save(Work work){
		if (work.getContent()!=null){
			String temp=StringEscapeUtils.unescapeHtml4(work.getContent());
			temp=StringUtils.replace(temp, "<!--[if !supportLineBreakNewLine]-->", "");
			temp=StringUtils.replace(temp, "<!--[endif]-->", "");
			work.setContent(temp);
		}
		workDao.clear();
		workDao.save(work);
	}
	
	@Transactional(readOnly = false)
	public void saveWorkandUser(Work work,FrontUser user,String registrationId){
		if (work.getContent()!=null){
			String temp=StringEscapeUtils.unescapeHtml4(work.getContent());
			temp=StringUtils.replace(temp, "<!--[if !supportLineBreakNewLine]-->", "");
			temp=StringUtils.replace(temp, "<!--[endif]-->", "");
			work.setContent(temp);
		}
		workDao.clear();
		workDao.save(work);
		//查询到对应的用户并将registrationid保存（如果registrationid为空的情况才保存）
		if(user.getRegistrationId()==null || user.getRegistrationId().equals("")){
		user.setRegistrationId(registrationId);
		userDao.clear();
		userDao.save(user);
		}
		
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		Work work = workDao.get(id);
		if(work!=null){
			workDao.deleteById(id);
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(String id, Boolean isRe) {
		// 使用下面方法，以便更新索引。
		Work work = workDao.get(id);
		work.setDelFlag(isRe!=null&&isRe?Work.DEL_FLAG_NORMAL:Work.DEL_FLAG_DELETE);
		workDao.save(work);
	}
	
}

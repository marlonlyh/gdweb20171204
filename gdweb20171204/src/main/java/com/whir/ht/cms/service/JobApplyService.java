package com.whir.ht.cms.service;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.whir.ht.cms.dao.JobApplyDao;
import com.whir.ht.cms.entity.JobApply;
import com.whir.ht.common.persistence.Page;
import com.whir.ht.common.service.BaseService;

/**
 * 在线应聘 service
 * @author tcl
 *
 */
@Service
@Transactional(readOnly = true)
public class JobApplyService extends BaseService{
	
	@Autowired
	private JobApplyDao jobApplyDao;
	
	public JobApply get(String id){
		return jobApplyDao.get(id);
	}
	
	@Transactional(readOnly = false)
	public Page<JobApply> find(Page<JobApply> page,JobApply jobApply,boolean isDataScopeFilter){
		DetachedCriteria dc = jobApplyDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(jobApply.getName())){
			dc.add(Restrictions.like("name", "%"+jobApply.getName()+"%"));
		}
		dc.add(Restrictions.eq(JobApply.FIELD_DEL_FLAG, jobApply.getDelFlag()));
		return jobApplyDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(JobApply jobApply){
		jobApplyDao.clear();
		jobApplyDao.save(jobApply);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		JobApply jobApply = jobApplyDao.get(id);
		if(jobApply!=null){
			jobApplyDao.deleteById(id);
		}
	}
}

package com.whir.ht.cms.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.whir.ht.cms.dao.JobDao;
import com.whir.ht.cms.entity.Job;
import com.whir.ht.common.persistence.Page;
import com.whir.ht.common.service.BaseService;

/**
 * 发布职位
 * @author tcl
 *
 */

@Service
@Transactional(readOnly = true)
public class JobService extends BaseService {

	@Autowired
	private JobDao jobDao;
	
	public Job get(String id){
		return jobDao.get(id);
	}
	
	@Transactional(readOnly = false)
	public Page<Job> find(Page<Job> page,Job job,boolean isDataScopeFilter){
		DetachedCriteria dc = jobDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(job.getJobName())){
			dc.add(Restrictions.like("jobName", "%"+job.getJobName()+"%"));
		}
		if (StringUtils.isNotEmpty(job.getDepartMent())){
			dc.add(Restrictions.eq("departMent",job.getDepartMent()));
		}
		dc.add(Restrictions.eq(Job.FIELD_DEL_FLAG, job.getDelFlag()));
		return jobDao.find(page, dc);
	}
	/**根据职位类型查找工作*/
	@Transactional(readOnly = false)
	public Page<Job> findSocial(Page<Job> page,Job job,boolean isDataScopeFilter){
		DetachedCriteria dc = jobDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(job.getJobName())){
			dc.add(Restrictions.like("jobName", "%"+job.getJobName()+"%"));
		}
		if (StringUtils.isNotEmpty(job.getDepartMent())){
			dc.add(Restrictions.eq("departMent",job.getDepartMent()));
		}
		dc.add(Restrictions.eq(Job.FIELD_DEL_FLAG, job.getDelFlag()));
		dc.add(Restrictions.eq("jobType", job.getJobType()));
		return jobDao.find(page, dc);
	}
	/**查找所有部门，过滤掉相同的，在前台遍历*/
	@Transactional(readOnly = false)
	public List<String> findAllDepartMent(){
		return jobDao.find("select distinct departMent from Job");
	}
	@Transactional(readOnly = false)
	public void save(Job job){
		jobDao.clear();
		jobDao.save(job);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		Job job = jobDao.get(id);
		if(job!=null){
			jobDao.deleteById(id);
		}
	}
}

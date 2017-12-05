package com.whir.ht.cms.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.whir.ht.cms.dao.WorkItemDao;
import com.whir.ht.cms.entity.WorkItem;
import com.whir.ht.common.persistence.Page;
import com.whir.ht.common.persistence.Parameter;
import com.whir.ht.common.service.BaseService;

/**
 * 作业项
 * @author liuchunyi
 *
 */

@Service
@Transactional(readOnly = true)
public class WorkItemService extends BaseService {

	@Autowired
	private WorkItemDao workItemDao;
	
	public WorkItem get(String id){
		return workItemDao.get(id);
	}
	
	@Transactional(readOnly = false)
	public Page<WorkItem> find(Page<WorkItem> page,WorkItem workItem,boolean isDataScopeFilter){
		DetachedCriteria dc = workItemDao.createDetachedCriteria();
		dc.createAlias("work", "work");
		if(null!=workItem.getWork()){
			String workName= workItem.getWork().getWorkName();
			if (StringUtils.isNotEmpty(workName)){
				dc.add(Restrictions.like("work.workName", "%"+workName+"%"));
			}
		}
		if (StringUtils.isNotEmpty(workItem.getFileName())){
			dc.add(Restrictions.like("fileName", "%"+workItem.getFileName()+"%"));
		}
		dc.add(Restrictions.eq(WorkItem.FIELD_DEL_FLAG, workItem.getDelFlag()));
		return workItemDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(WorkItem workItem){
		workItemDao.clear();
		workItemDao.save(workItem);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		WorkItem workItem = workItemDao.get(id);
		if(workItem!=null){
			workItemDao.deleteById(id);
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(String id, Boolean isRe) {
		// 使用下面方法，以便更新索引。
		WorkItem workItem = workItemDao.get(id);
		workItem.setDelFlag(isRe!=null&&isRe?WorkItem.DEL_FLAG_NORMAL:WorkItem.DEL_FLAG_DELETE);
		workItemDao.save(workItem);
	}
	
	/**根据作业id查询文件 */
	public List<WorkItem> findByWorkId(String workId){
		return workItemDao.find("select distinct t from WorkItem t,Work c where t in elements (c.workItems)" +  
				" and t.delFlag=:p1 and c.delFlag=:p1 and c.id=:p2" , new Parameter(WorkItem.DEL_FLAG_NORMAL, workId));
	}
}

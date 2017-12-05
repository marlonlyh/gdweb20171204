package com.whir.ht.cms.service;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.whir.ht.cms.dao.InteractiveDao;
import com.whir.ht.cms.entity.Interactive;
import com.whir.ht.common.persistence.Page;
import com.whir.ht.common.service.BaseService;
import com.whir.ht.common.utils.StringUtils;


/**
 * Service 师生互动
 * @author liuchunyi
 *
 */
@Service
@Transactional(readOnly = true)
public class InteractiveService extends BaseService {
	
	@Autowired
	private InteractiveDao interactiveDao;

	public Interactive get(String id){
      	return interactiveDao.get(id);
	}
	
	/**
	 * 后台分页查找所有师生互动问题
	 * 
	 * @param page
	 * @return
	 */
	public Page<Interactive> findInteractive(Page<Interactive> page,Interactive interactive,String status,String isShow) {
		DetachedCriteria dc = interactiveDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(interactive.getTitle())){
			dc.add(Restrictions.like("title", "%"+interactive.getTitle()+"%"));
		}
		if (StringUtils.isNotEmpty(interactive.getDescription())){
			dc.add(Restrictions.like("description", "%"+interactive.getDescription()+"%"));
		}
		if(status !=null && status.equals("0")){
			dc.add(Restrictions.eq("reply", ""));
		}
		if(status !=null && status.equals("1")){
			dc.add(Restrictions.ne("reply", ""));
		}
		/*if(isShow!=null&&isShow.equals("0")){	
		}else{
			dc.add(Restrictions.eq("isShow","1"));
		}*/
		dc.add(Restrictions.eq(Interactive.FIELD_DEL_FLAG, Interactive.DEL_FLAG_NORMAL));
		if (StringUtils.isBlank(page.getOrderBy())){
			dc.addOrder(Order.desc("updateDate"));
		}
		return interactiveDao.find(page, dc);
	}
	
	/**
	 * 查找后台回复的师生互动问题
	 * @param page
	 * @param interactive
	 * @return
	 */
	@Transactional(readOnly = false)
	public Page<Interactive> find(Page<Interactive> page, Interactive interactive) {
		DetachedCriteria dc = interactiveDao.createDetachedCriteria();
		   dc.add(Restrictions.ne("reply", ""));
			
			dc.add(Restrictions.eq("isShow","1"));
			dc.add(Restrictions.eq(Interactive.FIELD_DEL_FLAG, Interactive.DEL_FLAG_NORMAL));
			if(interactive.getIsTop() != null){
				dc.add(Restrictions.eq("isTop","1"));	
			}	
		if (StringUtils.isBlank(page.getOrderBy())){
			dc.addOrder(Order.desc("updateDate"));
		}
		return interactiveDao.find(page, dc);
	}
	
	/**
	 * 查找未解决的师生互动问题
	 * @param page
	 * @param interactive
	 * @return
	 */
	@Transactional(readOnly = false)
	public Page<Interactive> findNoReply(Page<Interactive> page, Interactive interactive) {
		DetachedCriteria dc = interactiveDao.createDetachedCriteria();
		dc.add(Restrictions.eq("reply", ""));
		dc.add(Restrictions.eq("isShow","1"));
		dc.add(Restrictions.eq(Interactive.FIELD_DEL_FLAG, Interactive.DEL_FLAG_NORMAL));
		if (StringUtils.isBlank(page.getOrderBy())){
			dc.addOrder(Order.desc("updateDate"));
		}
		return interactiveDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(Interactive interactive){
		interactiveDao.clear();
		interactiveDao.save(interactive);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		Interactive interactive = interactiveDao.get(id);
		if(interactive!=null){
			interactiveDao.deleteById(id);
		}
	}
}

package com.whir.ht.cms.service;


import javax.servlet.http.HttpSession;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.whir.ht.cms.dao.ConsultDao;
import com.whir.ht.cms.entity.Consult;
import com.whir.ht.common.persistence.Page;
import com.whir.ht.common.service.BaseService;
import com.whir.ht.common.utils.StringUtils;


/**
 * Service 电缆咨询
 * @author wuxiaoyuan
 *
 */
@Service
@Transactional(readOnly = true)
public class ConsultService extends BaseService {
	
	@Autowired
	private ConsultDao consultDao;

	public Consult get(String id){
      	return 	consultDao.get(id);
	}
	
	/**
	 * 分页查找所有咨询
	 * 
	 * @param page
	 * @return
	 */
	public Page<Consult> findConsult(Page<Consult> page,Consult consult ,String status,String isShow) {
		DetachedCriteria dc = consultDao.createDetachedCriteria();		
		if(status !=null && status.equals("0")){
			dc.add(Restrictions.eq("reply", ""));
		}
		if(status !=null && status.equals("1")){
			dc.add(Restrictions.ne("reply", ""));
		}
		if(isShow!=null&&isShow.equals("0")){	
		}else{
			dc.add(Restrictions.eq("isShow","1"));
		}
		dc.add(Restrictions.eq(Consult.FIELD_DEL_FLAG, Consult.DEL_FLAG_NORMAL));
		if (StringUtils.isBlank(page.getOrderBy())){
			dc.addOrder(Order.desc("updateDate"));
		}
		return consultDao.find(page, dc);
	}
	
	/**
	 * 查找后台回复的电缆咨询
	 * @param page
	 * @param consult
	 * @return
	 */
	@Transactional(readOnly = false)
	public Page<Consult> find(Page<Consult> page, Consult consult) {
		DetachedCriteria dc = consultDao.createDetachedCriteria();
		   dc.add(Restrictions.ne("reply", ""));
			
			dc.add(Restrictions.eq("isShow","1"));
			dc.add(Restrictions.eq(Consult.FIELD_DEL_FLAG, Consult.DEL_FLAG_NORMAL));
			if(consult.getIsTop() != null){
				dc.add(Restrictions.eq("isTop","1"));	
			}	
		if (StringUtils.isBlank(page.getOrderBy())){
			dc.addOrder(Order.desc("updateDate"));
		}
		return consultDao.find(page, dc);
	}
	
	/**
	 * 查找未解决的电缆咨询
	 * @param page
	 * @param consult
	 * @return
	 */
	@Transactional(readOnly = false)
	public Page<Consult> findNoReply(Page<Consult> page, Consult consult) {
		DetachedCriteria dc = consultDao.createDetachedCriteria();
		dc.add(Restrictions.eq("reply", ""));
		dc.add(Restrictions.eq("isShow","1"));
		dc.add(Restrictions.eq(Consult.FIELD_DEL_FLAG, Consult.DEL_FLAG_NORMAL));
		if (StringUtils.isBlank(page.getOrderBy())){
			dc.addOrder(Order.desc("updateDate"));
		}
		return consultDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(Consult consult){
		consultDao.clear();
		consultDao.save(consult);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		Consult consult = consultDao.get(id);
		if(consult!=null){
			consultDao.deleteById(id);
		}
	}
}

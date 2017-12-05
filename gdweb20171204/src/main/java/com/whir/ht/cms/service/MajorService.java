package com.whir.ht.cms.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.whir.ht.cms.dao.MajorDao;
import com.whir.ht.cms.entity.Major;
import com.whir.ht.common.persistence.Page;
import com.whir.ht.common.service.BaseService;

/**
 * 专业
 * @author liuchunyi
 *
 */

@Service
@Transactional(readOnly = true)
public class MajorService extends BaseService {

	@Autowired
	private MajorDao majorDao;
	
	public Major get(String id){
		return majorDao.get(id);
	}
	
	@Transactional(readOnly = false)
	public Page<Major> find(Page<Major> page,Major major,boolean isDataScopeFilter){
		DetachedCriteria dc = majorDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(major.getMajorName())){
			dc.add(Restrictions.like("majorName", "%"+major.getMajorName()+"%"));
		}
		dc.add(Restrictions.eq(Major.FIELD_DEL_FLAG, major.getDelFlag()));
		dc.addOrder(Order.asc("sort"));
		return majorDao.find(page, dc);
	}
	
	/**
	 * 检测专业名称是否存在
	 * 
	 * @param majorName
	 * @return
	 */
	public Major getMajorName(String majorName) {
		return majorDao.findName(majorName);
	}
	
	/**获取专业*/
	public List<Major> getMajor(){
		return  majorDao.find("from Major where delFlag=0 order by sort");
	}
	
	
	@Transactional(readOnly = false)
	public void save(Major major){
		majorDao.clear();
		majorDao.save(major);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		Major major = majorDao.get(id);
		if(major!=null){
			majorDao.deleteById(id);
		}
	}
}

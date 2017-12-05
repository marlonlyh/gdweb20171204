package com.whir.ht.cms.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.whir.ht.cms.dao.ResourceDao;
import com.whir.ht.cms.entity.Resource;
import com.whir.ht.common.persistence.Page;
import com.whir.ht.common.service.BaseService;

/**
 * Service 原材料
 * @author wuxiaoyuan
 *
 */
@Service
@Transactional(readOnly = true)
public class ResourceService extends BaseService {

	@Autowired
	private ResourceDao resourceDao;
	
	public Resource get(String id){
		return resourceDao.get(id);
	}
	
	/**
	 * 根据列号查询
	 * @param sort
	 * @return
	 */
	public Resource getSort(Integer sort) {
		return resourceDao.findSort(sort);
	}
	
	/**
	 * 分页查找所有原材料
	 * 
	 * @param page
	 * @return
	 */
	public Page<Resource> findResource(Page<Resource> page) {
		DetachedCriteria dc = resourceDao.createDetachedCriteria();
		dc.add(Restrictions.eq(Resource.FIELD_DEL_FLAG, Resource.DEL_FLAG_NORMAL));
		
		dc.addOrder(Order.asc("sort"));
		return resourceDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(Resource resource){
		resourceDao.clear();
		resourceDao.save(resource);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		Resource resource = resourceDao.get(id);
		if(resource!=null){
			resourceDao.deleteById(id);
		}
	}
	
	
	public Resource findBySort(Integer sort){
		return resourceDao.findBySort(sort);
	}
	
	/**
	 * 查找所有原材料
	 * 
	 * @return
	 */
	public List<Resource> findAll() {
		DetachedCriteria dc = resourceDao.createDetachedCriteria();
		dc.add(Restrictions.eq(Resource.FIELD_DEL_FLAG, Resource.DEL_FLAG_NORMAL));
		dc.addOrder(Order.asc("sort"));
		return resourceDao.find(dc);
	}
	
	/**
	 * 分页查找推荐的原材料
	 * 
	 * @param page
	 * @return
	 */
	public Page<Resource> findIsRecommendResource(Page<Resource> page) {
		DetachedCriteria dc = resourceDao.createDetachedCriteria();
		dc.add(Restrictions.eq(Resource.FIELD_DEL_FLAG, Resource.DEL_FLAG_NORMAL));
		dc.add(Restrictions.eq("isRecommend", Resource.YES));
		dc.addOrder(Order.asc("sort"));
		return resourceDao.find(page, dc);
	}
}

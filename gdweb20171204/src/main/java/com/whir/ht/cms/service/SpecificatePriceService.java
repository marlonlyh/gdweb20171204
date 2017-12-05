package com.whir.ht.cms.service;

import java.util.List;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.whir.ht.cms.dao.SpecificatePriceDao;
import com.whir.ht.cms.entity.Specificate;
import com.whir.ht.cms.entity.SpecificatePrice;
import com.whir.ht.common.service.BaseService;

@Service
@Transactional(readOnly = true)
public class SpecificatePriceService extends BaseService {
	
	@Autowired
	private SpecificatePriceDao specificatePriceDao;
	
	
	public SpecificatePrice get(String id){
		return specificatePriceDao.get(id);
	}
	
	public List<SpecificatePrice> findList(Specificate specificate){
		DetachedCriteria dc = specificatePriceDao.createDetachedCriteria();
		dc.add(Restrictions.eq("specificate",specificate));
		dc.add(Restrictions.eq(SpecificatePrice.FIELD_DEL_FLAG, SpecificatePrice.DEL_FLAG_NORMAL));
		dc.addOrder(Order.asc("specSort"));
		return specificatePriceDao.find(dc);
	}
	@Transactional(readOnly = false)
	public void delete(String id) {
		SpecificatePrice specificatePrice = specificatePriceDao.get(id);
		if(specificatePrice!=null){
			specificatePriceDao.deleteById(id);
		}
	}
}

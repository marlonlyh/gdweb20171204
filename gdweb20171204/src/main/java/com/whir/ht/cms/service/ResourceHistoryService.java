package com.whir.ht.cms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.whir.ht.cms.dao.ResourceHistoryDao;
import com.whir.ht.cms.entity.ResourceHistory;
import com.whir.ht.common.service.BaseService;
/**
 *  原材料历史表
 * @author wangqing
 *
 */
@Service
@Transactional(readOnly = true)
public class ResourceHistoryService extends BaseService {

	
	@Autowired
	private ResourceHistoryDao resourceHistoryDao;
	
	
	@Transactional(readOnly = false)
	public void save(ResourceHistory resourceHistory){
		//插入历史记录
		if (resourceHistory!=null){
			resourceHistoryDao.clear();
			resourceHistoryDao.save(resourceHistory);
		}				
	}
	public List<ResourceHistory> findResourceHistory(String id){
		return resourceHistoryDao.findResourceHistory(id);
	}
}

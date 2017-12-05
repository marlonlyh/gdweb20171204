package com.whir.ht.cms.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.whir.ht.cms.entity.ResourceHistory;
import com.whir.ht.common.persistence.BaseDao;
import com.whir.ht.common.persistence.Parameter;
/**
 * 原材料历史表
 * @author wangqing
 *
 */
@Repository
public class ResourceHistoryDao extends BaseDao<ResourceHistory> {
	public List<ResourceHistory> findResourceHistory(String id){
		return find("from ResourceHistory r where r.uid=:p1 order by collectDate desc",new Parameter(id));
	}
}

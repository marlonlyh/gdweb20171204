package com.whir.ht.cms.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.whir.ht.cms.entity.ProductType;
import com.whir.ht.common.persistence.BaseDao;
import com.whir.ht.common.persistence.Parameter;

/**
 * Dao 产品分类
 * @author wuxiaoyuan
 *
 */
@Repository
public class ProductTypeDao extends BaseDao<ProductType> {
	
	public List<ProductType> findByParentIdsLike(String parentIds){
		return find("from ProductType where parentIds like :p1", new Parameter(parentIds));
	}
  

}

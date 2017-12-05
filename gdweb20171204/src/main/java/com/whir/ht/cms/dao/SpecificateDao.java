package com.whir.ht.cms.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.whir.ht.cms.entity.Specificate;
import com.whir.ht.common.persistence.BaseDao;
import com.whir.ht.common.persistence.Parameter;

/**
 * Dao 产品规格表
 * @author wuxiaoyuan
 *
 */
@Repository
public class SpecificateDao extends BaseDao<Specificate> {

	/**
	 * 根据产品规格查询
	 * @param sort
	 * @return
	 */
	public Specificate findSpecificate(String id,String name){
		List<Specificate> list=find("from Specificate where product.id= :p1 and name = :p2 and delFlag = :p3", new Parameter(id,name,Specificate.DEL_FLAG_NORMAL));     
		if ((list ==null) || (list.size()==0)){
			return new Specificate();
		}else {
			return list.get(0);
		}
	}
}

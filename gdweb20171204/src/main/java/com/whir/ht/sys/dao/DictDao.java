/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/whir/ht">ezSite</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.whir.ht.sys.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.whir.ht.common.persistence.BaseDao;
import com.whir.ht.common.persistence.Parameter;
import com.whir.ht.sys.entity.Dict;

/**
 * 字典DAO接口
 * @author Elvin
 * @version 2013-8-23
 */
@Repository
public class DictDao extends BaseDao<Dict> {

	public List<Dict> findAllList(){
		return find("from Dict where delFlag=:p1 order by sort", new Parameter(Dict.DEL_FLAG_NORMAL));
	}
	
	/**
	 * 根据标签名和类型查询
	 * @return
	 */
	public List<Dict> finDict(String label,String type){
		return find("from Dict where label=:p1 and type=:p2  and delFlag=:p3 ", new Parameter(label,type,Dict.DEL_FLAG_NORMAL));
	}

	public List<String> findTypeList(){
		return find("select type from Dict where delFlag=:p1 group by type", new Parameter(Dict.DEL_FLAG_NORMAL));
	}
}

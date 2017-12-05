/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/whir/ht">ezSite</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.whir.ht.cms.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.whir.ht.cms.entity.Link;
import com.whir.ht.common.persistence.BaseDao;
import com.whir.ht.common.persistence.Parameter;

/**
 * 链接DAO接口
 * @author Elvin
 * @version 2013-8-23
 */
@Repository
public class LinkDao extends BaseDao<Link> {
	
	public List<Link> findByIdIn(Long[] ids){
		return find("front Like where id in (:p1)", new Parameter(new Object[]{ids}));
	}
	
	public int updateExpiredWeight(){
		return update("update Link set weight=0 where weight > 0 and weightDate < current_timestamp()");
	}
}

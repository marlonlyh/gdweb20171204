/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/whir/ht">ezSite</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.whir.ht.sys.dao;

import java.util.List;

import com.whir.ht.common.persistence.annotation.MyBatisDao;
import com.whir.ht.sys.entity.Dict;

/**
 * MyBatis字典DAO接口
 * @author Elvin
 * @version 2013-8-23
 */
@MyBatisDao
public interface MyBatisDictDao {
	
    Dict get(String id);
    
    List<Dict> find(Dict dict);
    
}

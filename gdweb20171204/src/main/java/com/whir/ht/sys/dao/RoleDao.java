/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/whir/ht">ezSite</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.whir.ht.sys.dao;

import org.springframework.stereotype.Repository;

import com.whir.ht.common.persistence.BaseDao;
import com.whir.ht.common.persistence.Parameter;
import com.whir.ht.sys.entity.Role;

/**
 * 角色DAO接口
 * @author Elvin
 * @version 2013-8-23
 */
@Repository
public class RoleDao extends BaseDao<Role> {

	public Role findByName(String name){
		return getByHql("from Role where delFlag = :p1 and name = :p2", new Parameter(Role.DEL_FLAG_NORMAL, name));
	}

//	@Query("from Role where delFlag='" + Role.DEL_FLAG_NORMAL + "' order by name")
//	public List<Role> findAllList();
//
//	@Query("select distinct r from Role r, User u where r in elements (u.roleList) and r.delFlag='" + Role.DEL_FLAG_NORMAL +
//			"' and u.delFlag='" + User.DEL_FLAG_NORMAL + "' and u.id=?1 or (r.user.id=?1 and r.delFlag='" + Role.DEL_FLAG_NORMAL +
//			"') order by r.name")
//	public List<Role> findByUserId(Long userId);

}

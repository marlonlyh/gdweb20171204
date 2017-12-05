package com.whir.ht.cms.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.whir.ht.cms.entity.FrontUser;
import com.whir.ht.common.persistence.BaseDao;
import com.whir.ht.common.persistence.Parameter;

/**
 * 用户Dao
 * @author wuxiaoyuan
 *
 */
@Repository
public class FrontUserDao extends BaseDao<FrontUser> {
	/**
	 * 根据用户名和密码查询
	 * @param userName
	 * @return
	 */
	public List<FrontUser> findUserName(String userName){
		List<FrontUser> list=find("from FrontUser where userName = :p1 and delFlag = :p2 ", new Parameter(userName,FrontUser.DEL_FLAG_NORMAL));     
		if ((list ==null) || (list.size()==0)){
			return new ArrayList<FrontUser>();
		}else {
			return list;
		}
	}
}

package com.whir.ht.cms.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.whir.ht.cms.dao.FrontUserDao;
import com.whir.ht.cms.entity.FrontUser;
import com.whir.ht.cms.entity.Major;
import com.whir.ht.cms.entity.Member;
import com.whir.ht.common.persistence.Page;
import com.whir.ht.common.service.BaseService;

/**
 * 用户Service
 * 
 * @author wuxiaoyuan
 *
 */
@Service
@Transactional(readOnly = true)
public class UserService extends BaseService {

	@Autowired
	private FrontUserDao userDao;

	public FrontUser get(String id) {
		return userDao.get(id);
	}

	/**
	 * 根据用户名和密码查询
	 * 
	 * @param userName
	 * @return
	 */
	public List<FrontUser> getUserName(String userName) {
		return userDao.findUserName(userName);
	}
	
	/**
	 * 分页查找所有用户
	 * 
	 * @param page
	 * @return
	 */
	public Page<FrontUser> findFrontUser(Page<FrontUser> page, FrontUser frontUser) {
		DetachedCriteria dc = userDao.createDetachedCriteria();
		dc.add(Restrictions.eq(FrontUser.FIELD_DEL_FLAG, FrontUser.DEL_FLAG_NORMAL));
		return userDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(FrontUser user){
		userDao.clear();
		userDao.save(user);
	}
	
}

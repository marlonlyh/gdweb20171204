package com.whir.ht.cms.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.whir.ht.cms.Principal;
import com.whir.ht.cms.dao.MemberDao;
import com.whir.ht.cms.entity.Member;
import com.whir.ht.common.persistence.Page;
import com.whir.ht.common.service.BaseService;

/**
 * 会员Service
 * 
 * @author wuxiaoyuan
 *
 */
@Service
@Transactional(readOnly = true)
public class MemberService extends BaseService {

	@Autowired
	private MemberDao memberDao;

	/**
	 * 根据用户名查询
	 * 
	 * @param userName
	 * @return
	 */
	public Member getUserName(String userName) {
		return memberDao.findUserName(userName);
	}

	/**
	 * 根据邮箱是查询
	 * 
	 * @param email
	 * @return
	 */
	public Member getUserEmail(String email) {
		return memberDao.findUserEmai(email);
	}

	/**
	 * 检测用户名是否存在
	 * 
	 * @param userName
	 * @return
	 */
	public Member getName(String userName) {
		return memberDao.findName(userName);
	}

	/**
	 * 检测邮箱是否存在
	 * 
	 * @param email
	 * @return
	 */
	public Member getEmail(String email) {
		return memberDao.findEmai(email);
	}

	/**
	 * 根据id获取用户
	 * 
	 * @param id
	 * @return
	 */
	public Member getMember(String id) {
		return memberDao.get(id);
	}

	/**
	 * 分页查找所有用户
	 * 
	 * @param page
	 * @return
	 */
	public Page<Member> findMember(Page<Member> page, Member member) {
		DetachedCriteria dc = memberDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(member.getUserName())){
			dc.add(Restrictions.like("userName", "%"+member.getUserName()+"%"));
		}
		if (StringUtils.isNotEmpty(member.getSerialNo())){
			dc.add(Restrictions.like("serialNo", "%"+member.getSerialNo()+"%"));
		}
		dc.add(Restrictions.eq(Member.FIELD_DEL_FLAG, Member.DEL_FLAG_NORMAL));
		return memberDao.find(page, dc);
	}
	
	/**
	 * 分页查找app问题反馈的所有用户
	 * 
	 * @param page
	 * @return
	 */
	public Page<Member> findFeedback(Page<Member> page, Member member) {
		DetachedCriteria dc = memberDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(member.getUserName())){
			dc.add(Restrictions.like("userName", "%"+member.getUserName()+"%"));
		}
		dc.add(Restrictions.isNotNull("feedback"));
		dc.add(Restrictions.eq(Member.FIELD_DEL_FLAG, Member.DEL_FLAG_NORMAL));
		return memberDao.find(page, dc);
	}
	
	/**
	 * 查找所有会员
	 * @return
	 */
	public List<Member> findMember() {
		return memberDao.findAll();
	}

	/**
	 * 添加用户
	 * 
	 * @param member
	 */
	@Transactional(readOnly = false)
	public void save(Member member) {

		memberDao.clear();
		memberDao.save(member);
	}

	/**
	 * 删除用户
	 * 
	 * @param id
	 */
	@Transactional(readOnly = false)
	public void delete(String id) {
		memberDao.deleteById(id);
	}

	/**
	 * 根据session获取当前登录用户
	 * 
	 * @return
	 */
	public Member getCurrent() {
		RequestAttributes requestAttributes = RequestContextHolder
				.currentRequestAttributes();
		if (requestAttributes != null) {
			HttpServletRequest request = ((ServletRequestAttributes) requestAttributes)
					.getRequest();
			Principal principal = (Principal) request.getSession()
					.getAttribute(Member.PRINCIPAL_ATTRIBUTE_NAME);
			if (principal != null) {
				return memberDao.findId(principal.getId());
			}
		}
		return null;
	}

	/**
	 * 更新用户密码
	 * 
	 * @param newPassword
	 * @param id
	 */
	@Transactional(readOnly = false)
	public void updatePassword(String newPassword, String id) {
		memberDao.updatePasswordById(newPassword, id);
	}

	/**
	 * 更新手机号码
	 * 
	 * @param mobile
	 * @param id
	 */
	@Transactional(readOnly = false)
	public void updateMobile(String mobile, String id) {
		memberDao.updateMobile(mobile, id);
	}

	/**
	 * 更新用户资料
	 * 
	 * @param name
	 *            真实姓名
	 * @param gender
	 *            性别
	 * @param tCName
	 *            单位名姓
	 * @param address
	 *            详细地址
	 * @param id
	 */
	@Transactional(readOnly = false)
	public void updateMember(String name, String gender, String tCName,
			String address, String id) {
		memberDao.updateMemberd(name, gender, tCName, address, id);
	}

	/**
	 * 更新修改密码的过期时间和随机种子
	 * 
	 * @param expire
	 * @param rand
	 * @param id
	 */
	@Transactional(readOnly = false)
	public void updateMember(String expire, String rand, String id) {
		memberDao.updateExpireRand(expire, rand, id);
	}

}

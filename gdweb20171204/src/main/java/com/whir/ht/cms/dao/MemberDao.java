package com.whir.ht.cms.dao;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.whir.ht.cms.entity.Member;
import com.whir.ht.common.persistence.BaseDao;
import com.whir.ht.common.persistence.Parameter;

/**
 * 会员Dao
 * @author wuxiaoyuan
 *
 */
@Repository
public class MemberDao extends BaseDao<Member> {
	
	
	/**
	 * 根据ID查询
	 * @param userName
	 * @return
	 */
	public Member findId(String id){
		List<Member> list=find("from Member where id = :p1 and delFlag = :p2", new Parameter(id,Member.DEL_FLAG_NORMAL));     
		if ((list ==null) || (list.size()==0)){
			return new Member();
		}else {
			return list.get(0);
		}
	}
	
	/**
	 * 根据用户名查询
	 * @param userName
	 * @return
	 */
	public Member findUserName(String userName){
		List<Member> list=find("from Member where userName = :p1 and delFlag = :p2", new Parameter(userName,Member.DEL_FLAG_NORMAL));     
		if ((list ==null) || (list.size()==0)){
			return new Member();
		}else {
			return list.get(0);
		}
	}
	
	/**
	 * 根据邮箱查询
	 * @param userName
	 * @return
	 */
	public Member findUserEmai(String email){
		List<Member> list=find("from Member where email = :p1 and delFlag = :p2", new Parameter(email,Member.DEL_FLAG_NORMAL));     
		if ((list ==null) || (list.size()==0)){
			return new Member();
		}else {
			return list.get(0);
		}
	}

	/**
	 * 验证用户名是否存在
	 * @param userName
	 * @return
	 */
	public Member findName(String userName){
	
		return getByHql("from Member where userName = :p1 and delFlag = :p2", new Parameter(userName,Member.DEL_FLAG_NORMAL));
	}
	
	/**
	 * 验证邮箱是否存在
	 * @param userName
	 * @return
	 */
	public Member findEmai(String email){
	
		return getByHql("from Member where email = :p1 and delFlag = :p2", new Parameter(email,Member.DEL_FLAG_NORMAL));
	}
	/**
	 * 更新新密码
	 * @param newPassword
	 * @param id
	 * @return
	 */
	public int updatePasswordById(String newPassword,String id){
		return update("update Member set password=:p1 where id = :p2", new Parameter(newPassword,id));
	}
	

	/**
	 * 更新手机号码
	 * @param mobile
	 * @param id
	 * @return
	 */
	public int updateMobile(String mobile,String id){
		return update("update Member set mobile=:p1 where id = :p2", new Parameter(mobile,id));
	}
	
	/**
	 * 更新登录IP/时间
	 * @param loginIP
	 * @param loginDate
	 * @param id
	 * @return
	 */
	public int updateLonginInfo(String loginIP,Date loginDate,String id){
		return update("update Member set loginIp=:p1, loginDate=:p2 where id = :p3",new Parameter(loginIP,loginDate,id));
	}
	
	/**
	 * 更新用户资料
	 * @param name 真实姓名
	 * @param gender 性别
	 * @param tCName 单位名姓
	 * @param address 详细地址
	 * @param id
	 * @return
	 */
	public int updateMemberd(String name,String gender,String tCName,String address ,String id){
		return update("update Member set name=:p1 ,gender=:p2,tCName =:p3 ,address =:p4 where id = :p5", new Parameter(name,gender, tCName, address , id));
	}
	
	/**
	 * 更新过期时间和随机种子数
	 * @param expire 过期时间
	 *  @param rand 随机种子数
	 * @return
	 */
	public int updateExpireRand(String expire, String rand, String id){
		return update("update Member set expire_date=:p1, random_seed=:p2 where id = :p3", 
				new Parameter(expire, rand, id));
	}

}

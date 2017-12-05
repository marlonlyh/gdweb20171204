package com.whir.ht.cms.dao;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.whir.ht.cms.entity.Teacher;
import com.whir.ht.common.persistence.BaseDao;
import com.whir.ht.common.persistence.Parameter;

/**
 * 教师Dao
 * @author wuxiaoyuan
 *
 */
@Repository
public class TeacherDao extends BaseDao<Teacher> {
	
	
	/**
	 * 根据ID查询
	 * @param userName
	 * @return
	 */
	public Teacher findId(String id){
		List<Teacher> list=find("from Teacher where id = :p1", new Parameter(id));     
		if ((list ==null) || (list.size()==0)){
			return new Teacher();
		}else {
			return list.get(0);
		}
	}

	/**
	 * 根据用户名查询
	 * @param userName
	 * @return
	 */
	public Teacher findUserName(String userName){
		List<Teacher> list=find("from Teacher where userName = :p1 and delFlag = :p2", new Parameter(userName,Teacher.DEL_FLAG_NORMAL));     
		if ((list ==null) || (list.size()==0)){
			return new Teacher();
		}else {
			return list.get(0);
		}
	}
	
	/**
	 * 根据用户名和密码查询
	 * @param userName
	 * @return
	 */
	public Teacher findUserName(String userName,String password){
		List<Teacher> list=find("from Teacher where userName = :p1 and delFlag = :p2 and password = :p3", new Parameter(userName,Teacher.DEL_FLAG_NORMAL,password));     
		if ((list ==null) || (list.size()==0)){
			return new Teacher();
		}else {
			return list.get(0);
		}
	}
	
	/**
	 * 根据邮箱查询
	 * @param userName
	 * @return
	 */
	public Teacher findUserEmai(String email){
		List<Teacher> list=find("from Teacher where email = :p1 and delFlag = :p2", new Parameter(email,Teacher.DEL_FLAG_NORMAL));     
		if ((list ==null) || (list.size()==0)){
			return new Teacher();
		}else {
			return list.get(0);
		}
	}

	/**
	 * 验证用户名是否存在
	 * @param userName
	 * @return
	 */
	public Teacher findName(String userName){
		List<Teacher> list=find("from Teacher where userName = :p1 and delFlag = :p2", new Parameter(userName,Teacher.DEL_FLAG_NORMAL));     
		if ((list ==null) || (list.size()==0)){
			return null;
		}else {
			return (Teacher)list.get(0);
		}
	}
	
	/**
	 * 验证邮箱是否存在
	 * @param userName
	 * @return
	 */
	public Teacher findEmai(String email){
	
		return getByHql("from Teacher where email = :p1 and delFlag = :p2", new Parameter(email,Teacher.DEL_FLAG_NORMAL));
	}
	/**
	 * 更新新密码
	 * @param newPassword
	 * @param id
	 * @return
	 */
	public int updatePasswordById(String newPassword,String id){
		return update("update Teacher set password=:p1 where id = :p2", new Parameter(newPassword,id));
	}
	

	/**
	 * 更新手机号码
	 * @param mobile
	 * @param id
	 * @return
	 */
	public int updateMobile(String mobile,String id){
		return update("update Teacher set mobile=:p1 where id = :p2", new Parameter(mobile,id));
	}
	
	/**
	 * 更新登录IP/时间
	 * @param loginIP
	 * @param loginDate
	 * @param id
	 * @return
	 */
	public int updateLonginInfo(String loginIP,Date loginDate,String id){
		return update("update Teacher set loginIp=:p1, loginDate=:p2 where id = :p3",new Parameter(loginIP,loginDate,id));
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
	public int updateTeacherd(String name,String gender,String tCName,String address ,String id){
		return update("update Teacher set name=:p1 ,gender=:p2,tCName =:p3 ,address =:p4 where id = :p5", new Parameter(name,gender, tCName, address , id));
	}
	
	/**
	 * 更新过期时间和随机种子数
	 * @param expire 过期时间
	 *  @param rand 随机种子数
	 * @return
	 */
	public int updateExpireRand(String expire, String rand, String id){
		return update("update Teacher set expire_date=:p1, random_seed=:p2 where id = :p3", 
				new Parameter(expire, rand, id));
	}

}

package com.whir.ht.cms.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.whir.ht.cms.Principal;
import com.whir.ht.cms.dao.TeacherDao;
import com.whir.ht.cms.entity.Teacher;
import com.whir.ht.common.persistence.Page;
import com.whir.ht.common.persistence.Parameter;
import com.whir.ht.common.service.BaseService;
import com.whir.ht.common.utils.StringUtils;

/**
 * 教师Service
 * 
 * @author wuxiaoyuan
 *
 */
@Service
@Transactional(readOnly = true)
public class TeacherService extends BaseService {

	@Autowired
	private TeacherDao teacherDao;
	
	public Teacher get(String id) {
		return teacherDao.get(id);
	}

	/**
	 * 根据用户名查询
	 * 
	 * @param userName
	 * @return
	 */
	public Teacher getUserName(String userName) {
		return teacherDao.findUserName(userName);
	}
	
	/**
	 * 根据用户名和密码查询
	 * 
	 * @param userName
	 * @return
	 */
	public Teacher getUserName(String userName,String password) {
		return teacherDao.findUserName(userName,password);
	}

	/**
	 * 根据邮箱是查询
	 * 
	 * @param email
	 * @return
	 */
	public Teacher getUserEmail(String email) {
		return teacherDao.findUserEmai(email);
	}

	/**
	 * 检测用户名是否存在
	 * 
	 * @param userName
	 * @return
	 */
	public Teacher getName(String userName) {
		return teacherDao.findName(userName);
	}

	/**
	 * 检测邮箱是否存在
	 * 
	 * @param email
	 * @return
	 */
	public Teacher getEmail(String email) {
		return teacherDao.findEmai(email);
	}

	/**
	 * 根据id获取用户
	 * 
	 * @param id
	 * @return
	 */
	public Teacher getTeacher(String id) {
		return teacherDao.get(id);
	}

	/**
	 * 分页查找所有用户
	 * 
	 * @param page
	 * @return
	 */
	public Page<Teacher> findTeacher(Page<Teacher> page, Teacher teacher) {
		DetachedCriteria dc = teacherDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(teacher.getName())){
			dc.add(Restrictions.like("name", "%"+teacher.getName()+"%"));
		}
		dc.add(Restrictions.eq(Teacher.FIELD_DEL_FLAG, Teacher.DEL_FLAG_NORMAL));
		return teacherDao.find(page, dc);
	}
	
	/**根据班级id查询教师 */
	public List<Teacher> findByTeacherId(String classId){
		return teacherDao.find("select distinct t from Classes c,Teacher t where t in elements (c.teachers)" +
				" and t.delFlag=:p1 and c.delFlag=:p1 and c.id=:p2" +  
				" order by c.sort", new Parameter(Teacher.DEL_FLAG_NORMAL, classId));
	}
	
	/**
	 * 添加用户
	 * 
	 * @param teacher
	 */
	@Transactional(readOnly = false)
	public void save(Teacher teacher) {

		teacherDao.clear();
		teacherDao.save(teacher);
	}

	/**
	 * 删除用户
	 * 
	 * @param id
	 */
	@Transactional(readOnly = false)
	public void delete(String id) {
		teacherDao.deleteById(id);
	}

	/**
	 * 根据session获取当前登录用户
	 * 
	 * @return
	 */
	public Teacher getCurrent() {
		RequestAttributes requestAttributes = RequestContextHolder
				.currentRequestAttributes();
		if (requestAttributes != null) {
			HttpServletRequest request = ((ServletRequestAttributes) requestAttributes)
					.getRequest();
			Principal principal = (Principal) request.getSession()
					.getAttribute(Teacher.PRINCIPAL_ATTRIBUTE_NAME);
			if (principal != null) {
				return teacherDao.findId(principal.getId());
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
		teacherDao.updatePasswordById(newPassword, id);
	}

	/**
	 * 更新手机号码
	 * 
	 * @param mobile
	 * @param id
	 */
	@Transactional(readOnly = false)
	public void updateMobile(String mobile, String id) {
		teacherDao.updateMobile(mobile, id);
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
	public void updateTeacher(String name, String gender, String tCName,
			String address, String id) {
		teacherDao.updateTeacherd(name, gender, tCName, address, id);
	}

	/**
	 * 更新修改密码的过期时间和随机种子
	 * 
	 * @param expire
	 * @param rand
	 * @param id
	 */
	@Transactional(readOnly = false)
	public void updateTeacher(String expire, String rand, String id) {
		teacherDao.updateExpireRand(expire, rand, id);
	}

}

package com.whir.ht.cms.service;

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
import com.whir.ht.cms.dao.StudentDao;
import com.whir.ht.cms.entity.FrontUser;
import com.whir.ht.cms.entity.Student;
import com.whir.ht.common.persistence.Page;
import com.whir.ht.common.service.BaseService;
import com.whir.ht.common.utils.StringUtils;

/**
 * 学生Service
 * 
 * @author wuxiaoyuan
 *
 */
@Service
@Transactional(readOnly = true)
public class StudentService extends BaseService {

	@Autowired
	private StudentDao studentDao;

	/**
	 * 根据用户名查询
	 * 
	 * @param userName
	 * @return
	 */
	public Student getUserName(String userName) {
		return studentDao.findUserName(userName);
	}
	
	/**
	 * 根据用户名和密码查询
	 * 
	 * @param userName
	 * @return
	 */
	public Student getUserName(String userName,String password) {
		return studentDao.findUserName(userName,password);
	}

	/**
	 * 根据邮箱是查询
	 * 
	 * @param email
	 * @return
	 */
	public Student getUserEmail(String email) {
		return studentDao.findUserEmai(email);
	}

	/**
	 * 检测用户名是否存在
	 * 
	 * @param userName
	 * @return
	 */
	public Student getName(String userName) {
		return studentDao.findName(userName);
	}

	/**
	 * 检测邮箱是否存在
	 * 
	 * @param email
	 * @return
	 */
	public Student getEmail(String email) {
		return studentDao.findEmai(email);
	}

	/**
	 * 根据id获取用户
	 * 
	 * @param id
	 * @return
	 */
	public Student getStudent(String id) {
		return studentDao.get(id);
	}

	/**
	 * 分页查找所有用户
	 * 
	 * @param page
	 * @return
	 */
	public Page<Student> findStudent(Page<Student> page, Student student) {
		DetachedCriteria dc = studentDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(student.getName())){
			dc.add(Restrictions.like("name", "%"+student.getName()+"%"));
		}
		dc.add(Restrictions.eq(Student.FIELD_DEL_FLAG, Student.DEL_FLAG_NORMAL));
		return studentDao.find(page, dc);
	}

	/**
	 * 添加用户
	 * 
	 * @param student
	 */
	@Transactional(readOnly = false)
	public void save(Student student) {

		studentDao.clear();
		studentDao.save(student);
	}

	/**
	 * 删除用户
	 * 
	 * @param id
	 */
	@Transactional(readOnly = false)
	public void delete(String id) {
		studentDao.deleteById(id);
	}

	/**
	 * 根据session获取当前登录用户
	 * 
	 * @return
	 */
	public Student getCurrent() {
		RequestAttributes requestAttributes = RequestContextHolder
				.currentRequestAttributes();
		if (requestAttributes != null) {
			HttpServletRequest request = ((ServletRequestAttributes) requestAttributes)
					.getRequest();
			Principal principal = (Principal) request.getSession()
					.getAttribute(Student.PRINCIPAL_ATTRIBUTE_NAME);
			if (principal != null) {
				return studentDao.findId(principal.getId());
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
		studentDao.updatePasswordById(newPassword, id);
	}

	/**
	 * 更新手机号码
	 * 
	 * @param mobile
	 * @param id
	 */
	@Transactional(readOnly = false)
	public void updateMobile(String mobile, String id) {
		studentDao.updateMobile(mobile, id);
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
	public void updateStudent(String name, String gender, String tCName,
			String address, String id) {
		studentDao.updateStudentd(name, gender, tCName, address, id);
	}

	/**
	 * 更新修改密码的过期时间和随机种子
	 * 
	 * @param expire
	 * @param rand
	 * @param id
	 */
	@Transactional(readOnly = false)
	public void updateStudent(String expire, String rand, String id) {
		studentDao.updateExpireRand(expire, rand, id);
	}

}

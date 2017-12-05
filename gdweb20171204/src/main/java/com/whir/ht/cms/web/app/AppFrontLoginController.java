package com.whir.ht.cms.web.app;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whir.ht.cms.Principal;
import com.whir.ht.cms.entity.FrontUser;
import com.whir.ht.cms.entity.Member;
import com.whir.ht.cms.entity.Student;
import com.whir.ht.cms.entity.Teacher;
import com.whir.ht.cms.service.MemberService;
import com.whir.ht.cms.service.StudentService;
import com.whir.ht.cms.service.TeacherService;
import com.whir.ht.cms.service.UserService;
import com.whir.ht.cms.utils.WebUtils;
import com.whir.ht.cms.web.app.model.CustomResponse;
import com.whir.ht.cms.web.app.model.SESSION;
import com.whir.ht.cms.web.app.model.SIGNIN_DATA;
import com.whir.ht.cms.web.app.model.STATUS;
import com.whir.ht.cms.web.app.model.User;
import com.whir.ht.cms.web.app.model.UsersigninResponse;
import com.whir.ht.common.mapper.BeanMapper;
import com.whir.ht.common.web.BaseController;

import eu.bitwalker.useragentutils.DeviceType;
import eu.bitwalker.useragentutils.UserAgent;

@Controller
@RequestMapping(value = "${frontPath}/app")
public class AppFrontLoginController extends BaseController {
	@Autowired
	private MemberService memberService;
	@Autowired
	private TeacherService teacherService;
	@Autowired
	private StudentService studentService;
	@Autowired
	private UserService userService;

	/**
	 * 会员信息验证
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "auth")
	public @ResponseBody CustomResponse<UsersigninResponse> verify(String flag, String captchaId, String captcha,
			String userName, String password, String serialNo, String registrationId, Model model, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		// HttpSession session = request.getSession();
		CustomResponse<UsersigninResponse> data = new CustomResponse<UsersigninResponse>();
		if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password) || StringUtils.isEmpty(serialNo)) {
			UsersigninResponse ur = null;
			try {
				STATUS st = new STATUS();
				st.setError_code(6);
				st.setError_desc("用户名、密码或手机串号不能为空");
				ur = new UsersigninResponse();
				ur.setStatus(st);
				data.setData(ur);
			} catch (Exception e) {

			}
			return data;
		}

		UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent")); // 判断是否手机登录
		if (!DeviceType.MOBILE.equals(userAgent.getOperatingSystem().getDeviceType())) {
			UsersigninResponse ur = new UsersigninResponse();
			data.setData(ur);
			return data;
		}
 
		Member member =null;
		Teacher teacher = null;
		Student student = null;
		List<FrontUser> lFrontUsers = userService.getUserName(userName);
		FrontUser userp = null;
		for (FrontUser frontUser : lFrontUsers) {
			if (DigestUtils.md5Hex(password).equals(frontUser.getPassword())) {
				userp = frontUser;
				break;
			}
		}

		if (null == userp) {
			UsersigninResponse ur = null;
			try {
				STATUS st = new STATUS();
				st.setError_code(6);
				st.setError_desc("用户名或密码不正确");
				st.setSucceed(-1);
				ur = new UsersigninResponse();
				ur.setStatus(st);
				data.setData(ur);
			} catch (Exception e) {
				// TODO: handle exception
			}
			return data;
		}
		
		if (null != userp && !serialNo.equals(userp.getSerialNo())) {
			UsersigninResponse ur = null;
			try {
				STATUS st = new STATUS();
				st.setError_code(6);
				st.setError_desc("手机串号不正确");
				st.setSucceed(-1);
				ur = new UsersigninResponse();
				ur.setStatus(st);
				data.setData(ur);
			} catch (Exception e) {
				// TODO: handle exception
			}
			return data;
		}

		if (null != userp && StringUtils.isNotBlank(registrationId)) {
			userp.setRegistrationId(registrationId);
			userService.save(userp);// 统一为登录时提交registrationId
		}
		if (userp instanceof Member) {
			flag = "C";
			member = memberService.getMember(userp.getId());
			UsersigninResponse ur = null;
			try {
				session.setAttribute(Member.PRINCIPAL_ATTRIBUTE_NAME, new Principal(member.getId(), userName));
				WebUtils.addCookie(request, response, Teacher.USERNAME_COOKIE_NAME, userName);
				SIGNIN_DATA sd = new SIGNIN_DATA();
				SESSION ses = new SESSION();
				ses.setSid(session.getId());
				ses.setUid(member.getId());
				sd.setSession(ses);
				// 用dozer将member转换成user
				User user = new User();
				BeanMapper.copy(member, user);
				//user.setType("C");
				sd.setUser(user);
				STATUS st = new STATUS();
				st.setSucceed(0);
				ur = new UsersigninResponse();
				ur.setStatus(st);
				ur.setData(sd);
				data.setData(ur);
			} catch (Exception e) {
				// TODO: handle exception
			}
			return data;
		}else if (userp instanceof Teacher) {
			flag = "T";
			teacher = teacherService.get(userp.getId());
			UsersigninResponse ur = null;
			try {
				session.setAttribute(Teacher.PRINCIPAL_ATTRIBUTE_NAME, new Principal(teacher.getId(), userName));
				WebUtils.addCookie(request, response, Teacher.USERNAME_COOKIE_NAME, userName);
				SIGNIN_DATA sd = new SIGNIN_DATA();
				SESSION ses = new SESSION();
				ses.setSid(session.getId());
				ses.setUid(teacher.getId());
				sd.setSession(ses);
				// 用dozer将teacher转换成user
				User user = new User();
				BeanMapper.copy(teacher, user);
				//user.setType("T");
				sd.setUser(user);
				STATUS st = new STATUS();
				st.setSucceed(0);
				ur = new UsersigninResponse();
				ur.setStatus(st);
				ur.setData(sd);
				data.setData(ur);
			} catch (Exception e) {
				// TODO: handle exception
			}
			return data;
		} else if (userp instanceof Student) {
			flag = "S";
			student = studentService.getStudent(userp.getId());
			UsersigninResponse ur = null;
			try {
				session.setAttribute(Student.PRINCIPAL_ATTRIBUTE_NAME, new Principal(student.getId(), userName));
				WebUtils.addCookie(request, response, Student.USERNAME_COOKIE_NAME, userName);
				SIGNIN_DATA sd = new SIGNIN_DATA();
				SESSION ses = new SESSION();
				ses.setSid(session.getId());
				ses.setUid(student.getId());
				sd.setSession(ses);
				// 用dozer将student转换成user
				User user = new User();
				BeanMapper.copy(student, user);
				//user.setType("S");
				sd.setUser(user);
				STATUS st = new STATUS();
				st.setSucceed(0);
				ur = new UsersigninResponse();
				ur.setStatus(st);

				ur.setData(sd);
				data.setData(ur);
			} catch (Exception e) {
				// TODO: handle exception
			}
			return data;
		}

		STATUS st = new STATUS();
		st.setSucceed(-1);
		UsersigninResponse ur = new UsersigninResponse();
		ur.setStatus(st);
		data.setData(ur);
		return data;
	}

	/**
	 * 用户信息
	 * 
	 * @param uid
	 * @return
	 */
	@RequestMapping(value = "userinfor")
	public @ResponseBody UsersigninResponse userinfor(String uid, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {

		UsersigninResponse ur = null;
		try {
			FrontUser userp = userService.get(uid);
			String flag = "";
			if (userp instanceof Member) {
				flag = "C";
			}else if (userp instanceof Student) {
				flag = "S";
			} else if (userp instanceof Teacher) {
				flag = "T";
			}
			User user = new User();
			BeanMapper.copy(userp, user);
			//user.setType(flag);
			SIGNIN_DATA sd = new SIGNIN_DATA();
			sd.setUser(user);
			ur = new UsersigninResponse();
			ur.setData(sd);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return ur;
	}

}

package com.whir.ht.cms.web.app;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.whir.ht.cms.Principal;
import com.whir.ht.cms.entity.Member;
import com.whir.ht.cms.service.MemberService;
import com.whir.ht.cms.web.app.model.SimpleResponse;
import com.whir.ht.common.web.BaseController;

/**
 * 会员区
 *
 */
@Controller
@RequestMapping(value="${frontPath}/app/member")
public class AppFrontMemberController extends BaseController {
	
	@Autowired
	private MemberService memberService;
	@Autowired
	   ServletContext context;
	
	/**
	 * 修改个人图片
	 */
	@ResponseBody
	@RequestMapping(value = "updateHead", method=RequestMethod.POST)
	public SimpleResponse updateHead(String uid, @RequestParam(value = "file", required = false)MultipartFile file, HttpServletRequest request) {
		SimpleResponse result = new SimpleResponse();
		
		//判断是否登录
		HttpSession session = request.getSession(true);
		Principal principal = (Principal) session.getAttribute(Member.PRINCIPAL_ATTRIBUTE_NAME);
		if (null==principal) {
			result.setMsg("-999");
			return result;
		}
		try {
			if (StringUtils.isNotBlank(uid)) {
				Member member = memberService.getMember(uid);
				String url = upload(request, file);
				if (file != null && file.getSize() > 0) {
					if (!url.equals("")) {
						member.setHead(url);
					}
					memberService.save(member);
				}
			}
			result.setCode(0);
			result.setMsg("修改个人头像成功");
		} catch (Exception e) {
			result.setCode(-1);
			result.setMsg("修改个人头像失败");
		}
		return result;
	}
	
	public String upload(final HttpServletRequest request,MultipartFile multipartFile){
		if (multipartFile == null || multipartFile.getSize()==0) {
			return "";
		}
		try {
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("uuid", UUID.randomUUID().toString());
			String path = "/userfiles/1/user/";
			String destPath = path + UUID.randomUUID() + "." + FilenameUtils.getExtension(multipartFile.getOriginalFilename());
			File destFile = new File(context.getRealPath(destPath));
			if (!destFile.getParentFile().exists()) {
				destFile.getParentFile().mkdirs();
			}
			multipartFile.transferTo(destFile);
			return context.getContextPath()+destPath;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 更新个人昵称
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="updateProfile",method = RequestMethod.POST)
	public  SimpleResponse updateprofile(String uid,String nickname, HttpServletRequest request){
		SimpleResponse result = new SimpleResponse();
		//判断是否登录
		HttpSession session = request.getSession();
		Principal principal = (Principal) session.getAttribute(Member.PRINCIPAL_ATTRIBUTE_NAME);
		if (null==principal) {
			result.setMsg("-999");
			return result;
		}
		try {
			if (StringUtils.isNotBlank(uid)) {
				Member member = memberService.getMember(uid);
				if (StringUtils.isNotBlank(nickname)) {
					member.setNickname(nickname);
					memberService.save(member);
				}
			} 
			result.setCode(0);
			result.setMsg("修改个人昵称成功");
		} catch (Exception e) {
			result.setCode(-1);
			result.setMsg("修改个人昵称失败");
		}
		return result;
	}
	
	/**
	 * 更新密码
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="updatePassword",method = RequestMethod.POST)
	public SimpleResponse updatePassword(String uid,String password,String newpassword,HttpServletRequest request){
		SimpleResponse result = new SimpleResponse();
		//判断是否登录
		HttpSession session = request.getSession();
		Principal principal = (Principal) session.getAttribute(Member.PRINCIPAL_ATTRIBUTE_NAME);
		if (null==principal) {
			result.setMsg("-999");
			return result;
		}
		try {
			if (StringUtils.isNotBlank(uid)) {
				Member member = memberService.getMember(uid);
				if ((DigestUtils.md5Hex(password).equals(member.getPassword())) && (uid.equals(member.getId()))) {
					member.setPassword(DigestUtils.md5Hex(newpassword));
					memberService.save(member);
				}
			} 
			result.setCode(0);
			result.setMsg("修改密码成功");
		} catch (Exception e) {
			result.setCode(-1);
			result.setMsg("修改密码失败");
		}
		return result;
	}
	
	/**
	 * 修改app个人反馈
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="updateFeedback",method = RequestMethod.POST)
	public  SimpleResponse updatefeedback(String uid,String feedback, HttpServletRequest request){
		SimpleResponse result = new SimpleResponse();
		HttpSession session = request.getSession();
		Principal principal = (Principal) session.getAttribute(Member.PRINCIPAL_ATTRIBUTE_NAME);
		if (null==principal) {
			result.setMsg("-999");
			return result;
		}
		try {
			if (StringUtils.isNotBlank(uid)) {
				Member member = memberService.getMember(uid);
				if (StringUtils.isNotBlank(feedback)) {
					member.setFeedback(feedback);
					memberService.save(member);
				}
			} 
			result.setCode(0);
			result.setMsg("修改个人反馈成功");
		} catch (Exception e) {
			result.setCode(-1);
			result.setMsg("修改个人反馈失败");
		}
		return result;
	}
	
}

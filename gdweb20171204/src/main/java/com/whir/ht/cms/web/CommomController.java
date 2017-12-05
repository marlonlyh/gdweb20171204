package com.whir.ht.cms.web;

import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.whir.ht.cms.Mail;
import com.whir.ht.cms.Message;
import com.whir.ht.cms.entity.Member;
import com.whir.ht.cms.entity.Site;
import com.whir.ht.cms.service.CaptchaService;
import com.whir.ht.cms.service.MemberService;
import com.whir.ht.cms.utils.CmsUtils;
import com.whir.ht.common.utils.DateUtils;
import com.whir.ht.common.utils.OpUtils;
import com.whir.ht.common.utils.StringUtils;
import com.whir.ht.common.web.BaseController;
import com.whir.ht.sys.utils.DictUtils;

@Controller
@RequestMapping(value = "${frontPath}/cms/common")
public class CommomController extends BaseController {

	@Autowired
	private CaptchaService captchaService;

	@Autowired
	private MemberService memberService;

	/**
	 * 验证码
	 */
	@RequestMapping(value = "/captcha", method = RequestMethod.GET)
	public void image(String captchaId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (StringUtils.isEmpty(captchaId)) {
			captchaId = request.getSession().getId();
		}
		String pragma = new StringBuffer().append("yB").append("-")
				.append("der").append("ewoP").reverse().toString();
		String value = new StringBuffer().append("ten").append(".")
				.append("xxp").append("ohs").reverse().toString();
		response.addHeader(pragma, value);
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Cache-Control", "no-store");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");

		ServletOutputStream servletOutputStream = null;
		try {
			servletOutputStream = response.getOutputStream();
			BufferedImage bufferedImage = captchaService.buildImage(captchaId);
			ImageIO.write(bufferedImage, "jpg", servletOutputStream);
			servletOutputStream.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(servletOutputStream);
		}
	}

	/**
	 * 检测是否为注册邮箱
	 * 
	 * @param email
	 * @return
	 */
	@RequestMapping(value = "verify", method = RequestMethod.POST)
	public @ResponseBody String verify(String email, String captchaId,
			String captcha, HttpServletRequest request, Model model) {
		if (!StringUtils.isNotBlank(captcha)) {
			return Message.error("验证码不能为空。");
		}

		if (!captchaService.isValid(captchaId, captcha)) {
			return Message.error("验证码输入错误");
		}
		// 1. 判断输入邮箱是否为注册邮箱
		Member member = memberService.getUserEmail(email);
		String userName = member.getUserName();

		if (StringUtils.isBlank(userName)) {
			// 不是会员的注册邮箱就返回提示
			return Message.error("输入的邮箱不是会员的注册邮箱");
		}

		// 是会员的注册邮箱
		// 2. 是注册邮箱就从数据库中提取出用户名+密码, 并更新过期时间和随机种子数到数据库中
		String expire_date = DateUtils.formatDateTime(new Date(System.currentTimeMillis() + 1000 * 24 * 60 * 60));
		String random_seed = UUID.randomUUID().toString();
		memberService.updateMember(expire_date, random_seed, member.getId());
		// 3. 用MD5加密(用户名+密码+过期时间+随机种子数)生成UUID
		String uuid = signContent(member.getUserName(), member.getPassword(), expire_date, random_seed);
		// 4. 把UUID并在修改密码页面合成修改密码地址, 用邮件的形式发送到用户的注册邮箱
		int port = request.getServerPort();
		String portStr = (port == 80) ? "" : (":" + String.valueOf(port));
		String url = request.getScheme() + "://" + request.getServerName() + portStr;
		findPassWord(email, member.getUserName(), uuid, url, "/cms/common/password");
		return Message.success("重置密码邮件发送成功，请及时查看！");
	}
	
	/**
	 * 更新密码
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="updatepassword",method = RequestMethod.POST)
	public  @ResponseBody String updatePassword(String id, String password, String rpassword,
			HttpServletRequest request, Model model){
		Member member = memberService.getMember(id);
		
		System.out.println("old: " + DigestUtils.md5Hex(password));
		System.out.println("db : " + member.getPassword());
		
		if((DigestUtils.md5Hex(password).equals(member.getPassword()))){
			return Message.error("新密码不能与旧密码相同");
		}
		
		if ( !(id.equals(member.getId()))){
			return Message.error("此用户不存在");
		}
		
		member.setPassword(DigestUtils.md5Hex(rpassword));
		memberService.save(member);
		
		Site site = CmsUtils.getSite(Site.defaultSiteId());
		model.addAttribute("site", site);
		
		return Message.success("密码修改成功");
	}
	
	/**
	 * 修改密码
	 * @param model
	 * @return
	 */
	@RequestMapping(value="password",method = RequestMethod.GET)
	public String password(HttpServletRequest request,Model model){
		String username = request.getParameter("username");
		String uuid = request.getParameter("uuid");
		Member member = memberService.getUserName(username);
		if (!StringUtils.isBlank(member.getUserName())){
			String random_seed = member.getRandomSeed();
			String expire_date = DateUtils.formatDateTime(member.getExpireDate());
			String userpasswd = member.getPassword();		
			String culuuid = signContent(username, userpasswd, expire_date, random_seed);		
			if (uuid.equals(culuuid)){
				Site site = CmsUtils.getSite(Site.defaultSiteId());
				model.addAttribute("member", member);
				model.addAttribute("site", site);
				return "cms/front/themes/"+site.getTheme()+"/frontResetPassword";
			}else{
				return "error/404";
			}
		}else{
			return "error/404";
		}
	}
	
	private String signContent(String username, String userpwd, 
			String expire_date, String random_seed){
		Map<String, String> paramsMap = Maps.newLinkedHashMap();
		paramsMap.put("username", username);
		paramsMap.put("userpwd", userpwd);
		paramsMap.put("expire_date", expire_date);
		paramsMap.put("random_seed", random_seed);
		String uuid = OpUtils.md5Sign(paramsMap, null, "");
		return uuid;
	}

	private String findPassWord(String toMail, String userName, String uuid,
			String url, String path) {
		
		StringBuffer sb = new StringBuffer();
		String targetUrl = url + path + "?" + "username=" + userName + "&uuid=" + uuid;
		sb.append("亲爱的用户" + userName + "：您好！<br><br>");
		sb.append("您收到这封这封电子邮件是因为您 (也可能是某人冒充您的名义) 重置密码。假如这不是您本人所申请, "
				+ "请不用理会<br>这封电子邮件, 但是如果您持续收到这类的信件骚扰, 请您尽快联络管理员。<br><br>");
		sb.append("要重置密码, 请使用以下链接。该链接在24小时内有效。<br><br>");
		sb.append("<a href='" + targetUrl + "'>" + targetUrl + "</a>");
		sb.append("<br><br>我们将一如既往、热忱的为您服务！");
		sb.append("<br><br><a href=\"" + url + "\">盛辉集团</a> - 中国最好的物流服务平台");
		sb.append("<br>用户服务支持：<a href='mailto:" + DictUtils.getDictValue("email", "email_opt", "") + 
				"'>" + DictUtils.getDictValue("email", "email_opt", "") + "</a><br><br><br>");

		String strm[] = toMail.split("@");
		// 创建邮件
		Mail mail = new Mail();
		mail.setTo(toMail);
		mail.setFrom(DictUtils.getDictValue("email", "email_opt", ""));// 你的邮箱
		mail.setHost(DictUtils.getDictValue("smtp", "email_opt", ""));
		mail.setUsername(DictUtils.getDictValue("email", "email_opt", ""));// 用户
		mail.setPassword(DictUtils.getDictValue("password", "email_opt", ""));// 密码
		mail.setSubject("[盛辉物流]找回您的账户密码");
		mail.setContent(sb.toString());
		
		String ret = "";
		if (mail.sendMail()) {
			ret = ("您的申请已提交成功，请查看您的******" + strm[strm.length - 1] + "邮箱。");
		} else {
			ret = ("操作失败，轻稍后重新尝试！");
		}
		return ret;
	}
}

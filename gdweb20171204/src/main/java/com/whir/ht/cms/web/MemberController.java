package com.whir.ht.cms.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.whir.ht.cms.entity.FrontUser;
import com.whir.ht.cms.entity.Member;
import com.whir.ht.cms.service.MemberService;
import com.whir.ht.common.beanvalidator.BeanValidators;
import com.whir.ht.common.config.Global;
import com.whir.ht.common.persistence.Page;
import com.whir.ht.common.utils.DateUtils;
import com.whir.ht.common.utils.StringUtils;
import com.whir.ht.common.utils.excel.ExportExcel;
import com.whir.ht.common.utils.excel.ImportExcel;
import com.whir.ht.common.web.BaseController;
import com.whir.ht.sys.service.SystemService;

/**
 * 会员Controller
 * @author wuxiaoyuan
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/member")
public class MemberController extends BaseController {
	
	@Autowired
	private MemberService memberService;
	
	/**
	 * 检查用户名是否存在
	 * @param olduserName
	 * @param userName
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("cms:member:edit")
	@RequestMapping(value = "checkuserName")
	public String checkLoginName(String olduserName, String userName) {
		if (userName !=null && userName.equals(olduserName)) {
			return "true";
		} else if (userName !=null && memberService.getName(userName) == null) {
			return "true";
		}
		return "false";
	}
	
	/**
	 * 检查邮箱是否存在
	 * @param oldEmail
	 * @param email
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("cms:member:edit")
	@RequestMapping(value = "checkuserEmail")
	public String checkEmail(String oldEmail, String email) {
		if (email !=null && email.equals(oldEmail)) {
			return "true";
		} else if (email !=null && memberService.getEmail(email) == null) {
			return "true";
		}
		return "false";
	}
	
	
	@ModelAttribute
	public Member get(@RequestParam(required = false)String id){
		if(StringUtils.isNotBlank(id)){
			return memberService.getMember(id);
			
		}else{
			return new  Member();
		}
	}
	
	@RequiresPermissions("cms:member:view")
	@RequestMapping(value = {"list", ""})
	public String list(Member member, HttpServletRequest request, HttpServletResponse response, Model model){
		
		Page<Member> page = memberService.findMember(new Page<Member>(request,response),member);
		model.addAttribute("page",page);
		return "cms/memberList";
	}
	
	@RequiresPermissions("cms:feedback:view")
	@RequestMapping(value = {"feedbacklist"})
	public String feedbackList(Member member, HttpServletRequest request, HttpServletResponse response, Model model){
		
		Page<Member> page = memberService.findFeedback(new Page<Member>(request,response),member);
		model.addAttribute("page",page);
		return "cms/feedbackList";
	}
	
	@RequiresPermissions("cms:feedback:view")
	@RequestMapping(value="feedbackform")
	public String feedbackForm(Member member,Model model){
		
		model.addAttribute("member",member);
		return"cms/feedbackForm";
	}
	
	@RequiresPermissions("cms:member:view")
	@RequestMapping(value="form")
	public String form(Member member,Model model){
		
		model.addAttribute("member",member);
		return"cms/memberForm";
	}
	
	@RequiresPermissions("cms:member:edit")
	@RequestMapping(value="save")
	public String save(Member member,String newPassword,HttpServletRequest request, Model model,RedirectAttributes redirectAttributes){
		 Member pmember  = null;
		 if(member.getId() != null){
		 pmember = memberService.getMember(member.getId());
		 }
		if(StringUtils.isNotBlank(newPassword)){
			member.setPassword(DigestUtils.md5Hex(newPassword));
		}else{
			member.setPassword(pmember.getPassword());
		}

		
		memberService.save(member);
		addMessage(redirectAttributes, "保存用户"+member.getUserName()+"成功");
		return "redirect:"+Global.getAdminPath()+"/cms/member/?repage";
	}

	@RequiresPermissions("cms:member:edit")
	@RequestMapping(value="delete")
	public String delete(String id,RedirectAttributes redirectAttributes){
		memberService.delete(id);
		addMessage(redirectAttributes, "删除用户成功");
		return"redirect:"+Global.getAdminPath()+"/cms/member/?repage";
	}
	
	@RequiresPermissions("cms:member:view")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(Member member, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "会员数据"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx"; 
    		//Page<FrontUser> page = userService.findFrontUser(new Page<FrontUser>(request, response, -1), frontUser);
    		Page<Member> page = memberService.findMember(new Page<Member>(request, response, -1), member);
    		new ExportExcel("会员数据", FrontUser.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出会员失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cms/member/?repage";
    }

	@RequiresPermissions("cms:member:edit")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:"+Global.getAdminPath()+"/cms/member/?repage";
		}
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<FrontUser> list =ei.getDataList(FrontUser.class);
			for (FrontUser frontUser : list){
				Member member=new Member();
				member.setUserName(frontUser.getUserName());
				member.setSerialNo(frontUser.getSerialNo());
			/*	member.setMobile(frontUser.getMobile());
				member.setName(frontUser.getName());
				member.setGender(frontUser.getGender());
				member.settCName(frontUser.gettCName());
				member.setUnitAddress(frontUser.getUnitAddress());
				member.setAddress(frontUser.getAddress());
				member.setPhone(frontUser.getPhone());
				member.setEmail(frontUser.getEmail());*/
				try{
					if ("true".equals(checkLoginName("", member.getUserName()))){
						if(StringUtils.isNotBlank(frontUser.getPassword())){
							member.setPassword(SystemService.entryptPassword(frontUser.getPassword()));
						}else{
							member.setPassword(SystemService.entryptPassword("123456"));
						}
						BeanValidators.validateWithException(validator, frontUser);
						memberService.save(member);
						successNum++;
					}else{
						failureMsg.append("<br/>用户名 "+member.getUserName()+" 已存在; ");
						failureNum++;
					}
				}catch(ConstraintViolationException ex){
					failureMsg.append("<br/>用户名 "+frontUser.getUserName()+" 导入失败：");
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("<br/>用户名 "+frontUser.getUserName()+" 导入失败："+ex.getMessage());
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条会员信息，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条会员信息"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入会员失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cms/member/?repage";
    }
	
	@RequiresPermissions("cms:member:view")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "会员数据导入模板.xlsx";
    		List<FrontUser> list = Lists.newArrayList();
    		List<Member> member = memberService.findMember();
    		list.add(member.get(0));
    		new ExportExcel("会员数据", FrontUser.class, 2).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cms/member/?repage";
    }
}

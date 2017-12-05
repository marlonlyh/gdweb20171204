package com.whir.ht.cms.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.whir.ht.cms.Message;
import com.whir.ht.cms.entity.Interactive;
import com.whir.ht.cms.service.InteractiveService;
import com.whir.ht.common.config.Global;
import com.whir.ht.common.persistence.Page;
import com.whir.ht.common.utils.StringUtils;
import com.whir.ht.common.web.BaseController;

/**
 * Controller 师生互动
 * @author liuchunyi
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/interactive")
public class InteractiveController extends BaseController {

	@Autowired
	private InteractiveService interactiveService;
	
	@ModelAttribute
	public Interactive get(@RequestParam(required = false)String id){
		if(StringUtils.isNotBlank(id)){
			return interactiveService.get(id);
		}else{
		   return new Interactive();
		}
	}
	
	@RequiresPermissions("cms:article:view")
	@RequestMapping(value = {"list", ""})
	public String list(Interactive interactive,String status, HttpServletRequest request, HttpServletResponse response, Model model){
		
		Page<Interactive> page = interactiveService.findInteractive(new Page<Interactive>(request,response),interactive,status,"1");
		model.addAttribute("page",page);
		model.addAttribute("status",status);
		return "cms/interactiveList";
	}
	
	/**
	 *  未解决问题
	 * @param interactive
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("cms:article:view")	
	@RequestMapping(value="unresolved",method = RequestMethod.GET)
	public String Unresolved(Interactive interactive,HttpServletRequest request,HttpServletResponse response,Model model){
		Page<Interactive> unresolvedpage = interactiveService.findNoReply(new Page<Interactive>(request, response), interactive);	
		model.addAttribute("unresolvedpage", unresolvedpage);
		return "cms/interactiveList";
	}
	
	/**
	 *  已解决问题
	 * @param interactive
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("cms:article:view")	
	@RequestMapping(value="resolved",method = RequestMethod.GET)
	public String resolved(Interactive interactive,HttpServletRequest request,HttpServletResponse response,Model model){
		Page<Interactive> resolvedpage = interactiveService.find(new Page<Interactive>(request, response), interactive);	
		model.addAttribute("resolvedpage", resolvedpage);
		return "cms/interactiveList";
	}
	
	@RequiresPermissions("cms:article:view")
	@RequestMapping(value="form")
	public String form(Interactive interactive,Model model){
		
		model.addAttribute("interactive",interactive);
		return"cms/interactiveForm";
	}
	
	@RequiresPermissions("cms:article:edit")
	@RequestMapping(value="save")
	public String save(Interactive interactive,HttpServletRequest request, Model model,RedirectAttributes redirectAttributes){
	
		if(interactive.getReply() == null){
			interactive.setReply("");
		}else {
			if (interactive.getReply()!=null){
				String temp=StringEscapeUtils.unescapeHtml4(interactive.getReply());
				temp=StringUtils.replace(temp, "<!--[if !supportLineBreakNewLine]-->", "");
				temp=StringUtils.replace(temp, "<!--[endif]-->", "");
				interactive.setReply(temp);
			}
		}
		
		interactiveService.save(interactive);
		addMessage(redirectAttributes, "保存成功");
		return "redirect:"+Global.getAdminPath()+"/cms/interactive/?repage";
	}
	
	@RequiresPermissions("cms:article:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes){
		interactiveService.delete(id);
		addMessage(redirectAttributes, ("删除成功"));
		return "redirect:"+Global.getAdminPath()+"/cms/interactive/?repage";
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public @ResponseBody
	String delete(String[] ids) {
		try {		
		if (ids != null) {
			for (String id : ids) {
				interactiveService.delete(id);
			}			
		}	
		return Message.success("批量删除成功！");
	} catch (Exception e) {		
		return Message.warn("批量删除失败！");
		}
	}
	
}

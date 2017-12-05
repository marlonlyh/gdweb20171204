package com.whir.ht.cms.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.whir.ht.cms.entity.JobApply;
import com.whir.ht.cms.service.JobApplyService;
import com.whir.ht.common.config.Global;
import com.whir.ht.common.persistence.Page;
import com.whir.ht.common.utils.StringUtils;
import com.whir.ht.common.web.BaseController;

/**
 * 在线应聘 Controller
 * @author tcl
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/jobapply")
public class JobApplyController extends BaseController {
	
	@Autowired
	private JobApplyService jobApplyService;
	
	@ModelAttribute
	public JobApply get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return jobApplyService.get(id);
		}else{
			return new JobApply();
		}
	}
	
	@RequiresPermissions("cms:jobapply:view")
	@RequestMapping(value = {"list", ""})
	public String list(JobApply jobApply, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<JobApply> page = jobApplyService.find(new Page<JobApply>(request, response), jobApply, true); 
        model.addAttribute("page", page);
		return "cms/jobapplyList";
	}
	
	@RequiresPermissions("cms:jobapply:view")
	@RequestMapping(value = "form")
	public String form(JobApply jobApply, Model model) {
		model.addAttribute("jobapply", jobApply);
		return "cms/jobapplyForm";
	}
	
	@RequiresPermissions("cms:jobapply:edit")
	@RequestMapping(value = "save")
	public String save(JobApply jobApply,Model model, RedirectAttributes redirectAttributes){
		jobApplyService.save(jobApply);
		addMessage(redirectAttributes, "保存信息成功");
		return "redirect:"+Global.getAdminPath()+"/cms/jobapply/?repage";
	}
	
	@RequiresPermissions("cms:jobapply:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes){
		jobApplyService.delete(id);
		addMessage(redirectAttributes, ("删除成功"));
		return "redirect:"+Global.getAdminPath()+"/cms/jobapply/?repage";
	}
	
	/**
	 * 在线应聘选择列表
	 */
	@RequiresPermissions("cms:jobapply:view")
	@RequestMapping(value = "selectList")
	public String selectList(JobApply jobApply, HttpServletRequest request, HttpServletResponse response, Model model) {
        list(jobApply, request, response, model);
		return "cms/articleSelectList";
	}
}

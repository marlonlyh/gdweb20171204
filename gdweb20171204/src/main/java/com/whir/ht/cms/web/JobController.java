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

import com.whir.ht.cms.entity.Job;
import com.whir.ht.cms.service.JobService;
import com.whir.ht.common.config.Global;
import com.whir.ht.common.persistence.Page;
import com.whir.ht.common.utils.StringUtils;
import com.whir.ht.common.web.BaseController;

@Controller
@RequestMapping(value = "${adminPath}/cms/job")
public class JobController extends BaseController {

	@Autowired
	private JobService jobService;
	
	@ModelAttribute
	public Job get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return jobService.get(id);
		}else{
			return new Job();
		}
	}
	
	@RequiresPermissions("cms:job:view")
	@RequestMapping(value = {"list", ""})
	public String list(Job job, HttpServletRequest request, HttpServletResponse response, Model model) {       
		Page<Job> page = jobService.find(new Page<Job>(request, response), job, true); 
        model.addAttribute("page", page);
		return "cms/jobList";
	}
	
	@RequiresPermissions("cms:job:view")
	@RequestMapping(value = "form")
	public String form(Job job, Model model) {
		model.addAttribute("job", job);
		return "cms/jobForm";
	}
	
	@RequiresPermissions("cms:job:edit")
	@RequestMapping(value = "save")
	public String save(Job job,Model model, RedirectAttributes redirectAttributes){
		jobService.save(job);
		addMessage(redirectAttributes, "保存信息成功");
		return "redirect:"+Global.getAdminPath()+"/cms/job/?repage";
	}
	
	@RequiresPermissions("cms:job:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes){
		jobService.delete(id);
		addMessage(redirectAttributes, ("删除成功"));
		return "redirect:"+Global.getAdminPath()+"/cms/job/?repage";
	}
	
	/**
	 * 发布职位列表
	 */
	@RequiresPermissions("cms:job:view")
	@RequestMapping(value = "selectList")
	public String selectList(Job job, HttpServletRequest request, HttpServletResponse response, Model model) {
        list(job, request, response, model);
		return "cms/articleSelectList";
	}
}

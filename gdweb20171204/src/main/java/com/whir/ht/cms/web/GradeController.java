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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.whir.ht.cms.entity.Grade;
import com.whir.ht.cms.service.GradeService;
import com.whir.ht.common.config.Global;
import com.whir.ht.common.persistence.Page;
import com.whir.ht.common.utils.StringUtils;
import com.whir.ht.common.web.BaseController;

/**
 * 年级
 * @author liuchunyi
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/grade")
public class GradeController extends BaseController {

	@Autowired
	private GradeService gradeService;
	
	/**
	 * 检年级名称是否存在
	 * @param oldgradeName
	 * @param gradeName
	 * @return
	 *//*
	@ResponseBody
	@RequiresPermissions("cms:grade:edit")
	@RequestMapping(value = "checkgradeName")
	public String checkGradeName(String oldgradeName, String gradeName) {
		if (gradeName !=null && gradeName.equals(oldgradeName)) {
			return "true";
		} else if (gradeName !=null && gradeService.getGradeName(gradeName) == null) {
			return "true";
		}
		return "false";
	}*/
	
	@ModelAttribute
	public Grade get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return gradeService.get(id);
		}else{
			return new Grade();
		}
	}
	
	/**列表*/
	@RequiresPermissions("cms:grade:view")
	@RequestMapping(value = {"list", ""})
	public String list(Grade grade, HttpServletRequest request, HttpServletResponse response, Model model) {       
		Page<Grade> page = gradeService.find(new Page<Grade>(request, response), grade, true); 
        model.addAttribute("page", page);
		return "cms/gradeList";
	}
	
	/**添加、修改*/
	@RequiresPermissions("cms:grade:view")
	@RequestMapping(value = "form")
	public String form(Grade grade, Model model) {
		model.addAttribute("grade", grade);
		return "cms/gradeForm";
	}
	
	/**保存*/
	@RequiresPermissions("cms:grade:edit")
	@RequestMapping(value = "save")
	public String save(Grade grade,String oldgradeName, Model model, RedirectAttributes redirectAttributes){
		/*if (!"true".equals(checkgradeName(oldgradeName, grade.getGradeName()))){
			addMessage(model, "保存年级名称'" +  grade.getGradeName() + "'失败, 年级名称已存在");
			return form(grade, model);
		}*/
		gradeService.save(grade);
		addMessage(redirectAttributes, "保存信息成功");
		return "redirect:"+Global.getAdminPath()+"/cms/grade/?repage";
	}
	
	/**删除*/
	@RequiresPermissions("cms:grade:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes){
		gradeService.delete(id);
		addMessage(redirectAttributes, ("删除成功"));
		return "redirect:"+Global.getAdminPath()+"/cms/grade/?repage";
	}
	
	/**
	 * 检查年级名称是否存在
	 * @param oldgradeName
	 * @param gradeName
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("cms:grade:edit")
	@RequestMapping(value = "checkgradeName")
	public String checkgradeName(String oldgradeName, String gradeName) {
		if (gradeName !=null && gradeName.equals(oldgradeName)) {
			return "true";
		} else if (gradeName !=null && gradeService.getGradeName(gradeName)== null) {
			return "true";
		}
		return "false";
	}
	
}

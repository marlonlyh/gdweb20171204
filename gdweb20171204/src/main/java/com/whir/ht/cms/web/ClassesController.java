package com.whir.ht.cms.web;

import java.util.List;

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

import com.whir.ht.cms.entity.Classes;
import com.whir.ht.cms.entity.Grade;
import com.whir.ht.cms.service.ClassesService;
import com.whir.ht.cms.service.GradeService;
import com.whir.ht.common.config.Global;
import com.whir.ht.common.persistence.Page;
import com.whir.ht.common.utils.StringUtils;
import com.whir.ht.common.web.BaseController;

/**
 * 班级
 * @author liuchunyi
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/classes")
public class ClassesController extends BaseController {

	@Autowired
	private ClassesService classesService;
	
	@Autowired
	private GradeService gradeService;
	
	@ModelAttribute
	public Classes get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return classesService.get(id);
		}else{
			return new Classes();
		}
	}
	
	/**列表*/
	@RequiresPermissions("cms:classes:view")
	@RequestMapping(value = {"list", ""})
	public String list(Classes classes, HttpServletRequest request, HttpServletResponse response, Model model) {       
		Page<Classes> page = classesService.find(new Page<Classes>(request, response), classes, true); 
        model.addAttribute("page", page);
		return "cms/classesList";
	}
	
	/**添加、修改*/
	@RequiresPermissions("cms:classes:view")
	@RequestMapping(value = "form")
	public String form(Classes classes, Model model) {
		String id = classes.getId();
		if(StringUtils.isNotEmpty(id)){
			String gradeId = classes.getGrade().getId();
			model.addAttribute("gradeId", gradeId);
		}
		List<Grade> gradeList = gradeService.getGrade();
		model.addAttribute("gradeList", gradeList);
		model.addAttribute("classes", classes);
		return "cms/classesForm";
	}
	
	/**保存*/
	@RequiresPermissions("cms:classes:edit")
	@RequestMapping(value = "save")
	public String save(Classes classes,String gradeId,Model model, RedirectAttributes redirectAttributes){
		Grade grade= gradeService.get(gradeId);
		String gradeClass=grade.getGradeName()+classes.getClassName();  
		classes.setGradeClass(gradeClass);
		classes.setGrade(grade);
		classesService.save(classes);
		addMessage(redirectAttributes, "保存信息成功");
		return "redirect:"+Global.getAdminPath()+"/cms/classes/?repage";
	}
	
	/**删除*/
	@RequiresPermissions("cms:classes:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes){
		classesService.delete(id);
		addMessage(redirectAttributes, ("删除成功"));
		return "redirect:"+Global.getAdminPath()+"/cms/classes/?repage";
	}
	
	/**
	 * 检查班级名称是否存在
	 * @param oldclassName
	 * @param className
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("cms:classes:edit")
	@RequestMapping(value = "checkclassName")
	public String checkclassName(String oldclassName, String className,String gradeId) {
		if(gradeId==null){
			return "false";
		}
		if (className !=null && className.equals(oldclassName)) {
			return "true";
		} else if (className !=null && classesService.getClassName(className,gradeId)== null) {
			return "true";
		}
		return "false";
	}
	
}

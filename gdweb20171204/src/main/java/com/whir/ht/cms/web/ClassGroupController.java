package com.whir.ht.cms.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.whir.ht.cms.entity.ClassGroup;
import com.whir.ht.cms.entity.Classes;
import com.whir.ht.cms.entity.Grade;
import com.whir.ht.cms.service.ClassGroupService;
import com.whir.ht.cms.service.ClassesService;
import com.whir.ht.cms.service.GradeService;
import com.whir.ht.common.config.Global;
import com.whir.ht.common.persistence.Page;
import com.whir.ht.common.utils.StringUtils;
import com.whir.ht.common.web.BaseController;

/**
 * 班组
 * @author liuchunyi
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/classGroup")
public class ClassGroupController extends BaseController {

	@Autowired
	private ClassGroupService classGroupService;
	
	@Autowired
	private GradeService gradeService;
	
	@Autowired
	private ClassesService classesService;
	
	@ModelAttribute
	public ClassGroup get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return classGroupService.get(id);
		}else{
			return new ClassGroup();
		}
	}
	
	/**列表*/
	@RequiresPermissions("cms:classGroup:view")
	@RequestMapping(value = {"list", ""})
	public String list(ClassGroup classGroup, HttpServletRequest request, HttpServletResponse response, Model model) {       
		Page<ClassGroup> page = classGroupService.find(new Page<ClassGroup>(request, response), classGroup, true); 
        model.addAttribute("page", page);
		return "cms/classGroupList";
	}
	
	/**添加、修改*/
	@RequiresPermissions("cms:classGroup:view")
	@RequestMapping(value = "form")
	public String form(ClassGroup classGroup, Model model) {
		String id = classGroup.getId();
		if(StringUtils.isNotEmpty(id)){
			String gradeId = classGroup.getGrade().getId();
			String classesId = classGroup.getClasses().getId();
			Grade grade = gradeService.get(gradeId);
			List<Classes> classesList=classesService.findList(grade);
			model.addAttribute("gradeId", gradeId);
			model.addAttribute("classesId", classesId);
			model.addAttribute("classesList", classesList);
		}
		List<Grade> gradeList = gradeService.getGrade();
		model.addAttribute("gradeList", gradeList);
		model.addAttribute("classGroup", classGroup);
		return "cms/classGroupForm";
	}
	
	/**保存*/
	@RequiresPermissions("cms:classGroup:edit")
	@RequestMapping(value = "save")
	public String save(ClassGroup classGroup,String oldgroupName,String gradeId,String classesId,Model model, RedirectAttributes redirectAttributes){
		String id = classGroup.getId();
		if(StringUtils.isNotEmpty(id)){
			String groupName = classGroup.getGroupName();
			if(!groupName.equals(oldgroupName)){
				int sort = classGroup.getSort();
				classGroupService.delete(id);
				ClassGroup cgroup = new ClassGroup();
				Grade grade = gradeService.get(gradeId);
				Classes classes = classesService.get(classesId);
				cgroup.setGrade(grade);
				cgroup.setClasses(classes);
				cgroup.setGroupName(groupName);
				cgroup.setSort(sort);
				classGroupService.save(cgroup);
			}else{
				Grade grade = gradeService.get(gradeId);
				Classes classes = classesService.get(classesId);
				classGroup.setGrade(grade);
				classGroup.setClasses(classes);
				classGroupService.save(classGroup);
			}
		}else{
			Grade grade = gradeService.get(gradeId);
			Classes classes = classesService.get(classesId);
			classGroup.setGrade(grade);
			classGroup.setClasses(classes);
			classGroupService.save(classGroup);
		}
		addMessage(redirectAttributes, "保存信息成功");
		return "redirect:"+Global.getAdminPath()+"/cms/classGroup/?repage";
	}
	
	/**删除*/
	@RequiresPermissions("cms:classGroup:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes){
		classGroupService.delete(id);
		addMessage(redirectAttributes, ("删除成功"));
		return "redirect:"+Global.getAdminPath()+"/cms/classGroup/?repage";
	}
	
	/**
	 * 年级绑定班级
	 * @param brandId
	 * @return
	 */
	@RequiresPermissions("cms:classGroup:view")
	@RequestMapping(value = "searchclasses", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, String>> searchclasses(String gradeId) {
		Grade grade = gradeService.get(gradeId);
		List<Map<String, String>> list=new ArrayList<Map<String,String>>();
		if (grade!=null){		
			List<Classes> classesList = classesService.findList(grade);
			for(Classes classes:classesList){
				Map<String, String> map = new HashMap<String, String>();
				map.put("id", classes.getId().toString());
				map.put("className", classes.getClassName());
				list.add(map);
			}
		}
		return list;
	}
	/**
	 * 检查班组名称是否存在
	 * @param oldgroupName
	 * @param groupName
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("cms:classGroup:edit")
	@RequestMapping(value = "checkgroupName")
	public String checkgroupName(String oldgroupName, String groupName,String gradeId,String classesId) {
		if(gradeId== null||classesId == null){
			return "false";
		}
		if (groupName !=null && groupName.equals(oldgroupName)) {
			return "true";
		} else if (groupName !=null && classGroupService.getGroupName(groupName,gradeId,classesId)== null) {
			return "true";
		}
		return "false";
	}
}

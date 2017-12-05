package com.whir.ht.cms.web;

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

import com.whir.ht.cms.Message;
import com.whir.ht.cms.entity.Work;
import com.whir.ht.cms.service.WorkService;
import com.whir.ht.common.config.Global;
import com.whir.ht.common.persistence.Page;
import com.whir.ht.common.utils.StringUtils;
import com.whir.ht.common.web.BaseController;

/**
 * 作业
 * @author liuchunyi
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/work")
public class WorkController extends BaseController {

	@Autowired
	private WorkService workService;
	
	@ModelAttribute
	public Work get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return workService.get(id);
		}else{
			return new Work();
		}
	}
	
	/**列表*/
	@RequiresPermissions("cms:work:view")
	@RequestMapping(value = {"list", ""})
	public String list(Work work, HttpServletRequest request, HttpServletResponse response, Model model) {       
		Page<Work> page = workService.find(new Page<Work>(request, response), work, true); 
        model.addAttribute("page", page);
		return "cms/workList";
	}
	
	/**添加、修改*/
	@RequiresPermissions("cms:work:view")
	@RequestMapping(value = "form")
	public String form(String id, Model model) {
		Work work = workService.get(id);
		model.addAttribute("classes", work.getClasses());
		return "cms/workForm";
	}
	
	/**保存*/
	@RequiresPermissions("cms:work:edit")
	@RequestMapping(value = "save")
	public String save(Work work,String classesIds,Model model, RedirectAttributes redirectAttributes){
		/*Set<Classes> classess = new HashSet<Classes>();
		if (classesIds != null){
			String[] ids = StringUtils.split(classesIds, ",");
			for (String classesId : ids) {
				Classes classes = new Classes();
				classes.setId(classesId);
				classess.add(classes);
			}
		}*/
		if (work.getGrade().getId().equals("")){
			work.setGrade(null);
		}
		if (work.getClasses().getId().equals("")){
			work.setClasses(null);
		}
		if (work.getClassGroup().getId().equals("")){
			work.setClassGroup(null);
		}
		workService.save(work);
		addMessage(redirectAttributes, "保存信息成功");
		return "redirect:"+Global.getAdminPath()+"/cms/work/?repage";
	}
	
	/**删除*/
	@RequiresPermissions("cms:work:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes){
		workService.delete(id);
		addMessage(redirectAttributes, ("删除成功"));
		return "redirect:"+Global.getAdminPath()+"/cms/work/?repage";
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public @ResponseBody
	String delete(String[] ids) {
		try {
		Boolean isRe=false;
		if (ids != null) {
			for (String id : ids) {
				workService.delete(id, isRe);
			}			
		}	
		return Message.success("批量删除成功！");
	} catch (Exception e) {		
		return Message.warn("批量删除失败！");
		}
	}
	
}

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

import com.whir.ht.cms.entity.Major;
import com.whir.ht.cms.service.MajorService;
import com.whir.ht.common.config.Global;
import com.whir.ht.common.persistence.Page;
import com.whir.ht.common.utils.StringUtils;
import com.whir.ht.common.web.BaseController;

/**
 * 专业
 * @author liuchunyi
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/major")
public class MajorController extends BaseController {

	@Autowired
	private MajorService majorService;
	
	@ModelAttribute
	public Major get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return majorService.get(id);
		}else{
			return new Major();
		}
	}
	
	/**列表*/
	@RequiresPermissions("cms:major:view")
	@RequestMapping(value = {"list", ""})
	public String list(Major major, HttpServletRequest request, HttpServletResponse response, Model model) {       
		Page<Major> page = majorService.find(new Page<Major>(request, response), major, true); 
        model.addAttribute("page", page);
		return "cms/majorList";
	}
	
	/**添加、修改*/
	@RequiresPermissions("cms:major:view")
	@RequestMapping(value = "form")
	public String form(Major major, Model model) {
		model.addAttribute("major", major);
		return "cms/majorForm";
	}
	
	/**保存*/
	@RequiresPermissions("cms:major:edit")
	@RequestMapping(value = "save")
	public String save(Major major,String oldmajorName, Model model, RedirectAttributes redirectAttributes){
		majorService.save(major);
		addMessage(redirectAttributes, "保存信息成功");
		return "redirect:"+Global.getAdminPath()+"/cms/major/?repage";
	}
	
	/**删除*/
	@RequiresPermissions("cms:major:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes){
		majorService.delete(id);
		addMessage(redirectAttributes, ("删除成功"));
		return "redirect:"+Global.getAdminPath()+"/cms/major/?repage";
	}
	
	/**
	 * 检查专业名称是否存在
	 * @param oldmajorName
	 * @param majorName
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("cms:major:edit")
	@RequestMapping(value = "checkmajorName")
	public String checkmajorName(String oldmajorName, String majorName) {
		if (majorName !=null && majorName.equals(oldmajorName)) {
			return "true";
		} else if (majorName !=null && majorService.getMajorName(majorName)== null) {
			return "true";
		}
		return "false";
	}
	
}

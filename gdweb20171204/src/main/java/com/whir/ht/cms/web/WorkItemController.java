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
import com.whir.ht.cms.entity.WorkItem;
import com.whir.ht.cms.service.WorkItemService;
import com.whir.ht.common.config.Global;
import com.whir.ht.common.persistence.Page;
import com.whir.ht.common.utils.StringUtils;
import com.whir.ht.common.web.BaseController;

/**
 * 作业项
 * @author liuchunyi
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/workItem")
public class WorkItemController extends BaseController {

	@Autowired
	private WorkItemService workItemService;
	
	@ModelAttribute
	public WorkItem get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return workItemService.get(id);
		}else{
			return new WorkItem();
		}
	}
	
	/**列表*/
	@RequiresPermissions("cms:workItem:view")
	@RequestMapping(value = {"list", ""})
	public String list(WorkItem workItem, HttpServletRequest request, HttpServletResponse response, Model model) {       
		Page<WorkItem> page = workItemService.find(new Page<WorkItem>(request, response), workItem, true); 
        model.addAttribute("page", page);
		return "cms/workItemList";
	}
	
	/**添加、修改*/
	@RequiresPermissions("cms:WorkItem:view")
	@RequestMapping(value = "form")
	public String form(WorkItem workItem, Model model) {
		model.addAttribute("workItem", workItem);
		return "cms/workItemForm";
	}
	
	/**保存*/
	@RequiresPermissions("cms:workItem:edit")
	@RequestMapping(value = "save")
	public String save(WorkItem workItem,Model model, RedirectAttributes redirectAttributes){
		workItemService.save(workItem);
		addMessage(redirectAttributes, "保存信息成功");
		return "redirect:"+Global.getAdminPath()+"/cms/workItem/?repage";
	}
	
	/**删除*/
	@RequiresPermissions("cms:workItem:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes){
		workItemService.delete(id);
		addMessage(redirectAttributes, ("删除成功"));
		return "redirect:"+Global.getAdminPath()+"/cms/workItem/?repage";
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
				workItemService.delete(id, isRe);
			}			
		}	
		return Message.success("批量删除成功！");
	} catch (Exception e) {		
		return Message.warn("批量删除失败！");
		}
	}
	
}

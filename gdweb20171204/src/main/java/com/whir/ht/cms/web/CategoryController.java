/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/whir/ht">ezSite</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.whir.ht.cms.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.whir.ht.cms.entity.Article;
import com.whir.ht.cms.entity.Category;
import com.whir.ht.cms.entity.Site;
import com.whir.ht.cms.service.CategoryService;
import com.whir.ht.cms.service.FileTplService;
import com.whir.ht.cms.service.SiteService;
import com.whir.ht.cms.utils.TplUtils;
import com.whir.ht.common.config.Global;
import com.whir.ht.common.utils.StringUtils;
import com.whir.ht.common.web.BaseController;
import com.whir.ht.sys.entity.Office;

/**
 * 栏目Controller
 * @author Elvin
 * @version 2013-4-21
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/category")
public class CategoryController extends BaseController {

	@Autowired
	private CategoryService categoryService;
    @Autowired
   	private FileTplService fileTplService;
    @Autowired
   	private SiteService siteService;
	
	@ModelAttribute("category")
	public Category get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return categoryService.get(id);
		}else{
			return new Category();
		}
	}

	@RequiresPermissions("cms:category:view")
	@RequestMapping(value = {"list", ""})
	public String list(Model model) {
		List<Category> list = Lists.newArrayList();
		List<Category> sourcelist = categoryService.findByUser(true, null);
		Category.sortList(list, sourcelist, "1");
        model.addAttribute("list", list);
		return "cms/categoryList";
	}

	@RequiresPermissions("cms:category:view")
	@RequestMapping(value = "form")
	public String form(Category category, Model model) {
		if (category.getParent()==null||category.getParent().getId()==null){
			category.setParent(new Category("1"));
		}
		category.setParent(categoryService.get(category.getParent().getId()));
		if (category.getOffice()==null||category.getOffice().getId()==null){
			//category.setOffice(category.getParent().getOffice());
			category.setOffice(new Office("1"));
		}
        model.addAttribute("listViewList",getTplContent(Category.DEFAULT_TEMPLATE));
        model.addAttribute("category_DEFAULT_TEMPLATE",Category.DEFAULT_TEMPLATE);
        model.addAttribute("contentViewList",getTplContent(Article.DEFAULT_TEMPLATE));
        model.addAttribute("article_DEFAULT_TEMPLATE",Article.DEFAULT_TEMPLATE);
		model.addAttribute("office", category.getOffice());
		model.addAttribute("category", category);
		return "cms/categoryForm";
	}
	
	@RequiresPermissions("cms:category:edit")
	@RequestMapping(value = "save")
	public String save(Category category, Model model, RedirectAttributes redirectAttributes) {
		if (category.getOffice()==null||category.getOffice().getId()==null){
			category.setOffice(new Office("1"));
		}
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:"+Global.getAdminPath()+"/cms/category/";
		}
		if (!beanValidator(model, category)){
			return form(category, model);
		}
		categoryService.save(category);
		addMessage(redirectAttributes, "保存栏目'" + category.getName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/cms/category/";
	}
	
	@RequiresPermissions("cms:category:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:"+Global.getAdminPath()+"/cms/category/";
		}
		if (Category.isRoot(id)){
			addMessage(redirectAttributes, "删除栏目失败, 不允许删除顶级栏目或编号为空");
		}else{
			categoryService.delete(id);
			addMessage(redirectAttributes, "删除栏目成功");
		}
		return "redirect:"+Global.getAdminPath()+"/cms/category/";
	}

	/**
	 * 批量修改栏目排序
	 */
	@RequiresPermissions("cms:category:edit")
	@RequestMapping(value = "updateSort")
	public String updateSort(String[] ids, Integer[] sorts, RedirectAttributes redirectAttributes) {
    	int len = ids.length;
    	Category[] entitys = new Category[len];
    	for (int i = 0; i < len; i++) {
    		entitys[i] = categoryService.get(ids[i]);
    		entitys[i].setSort(sorts[i]);
    		categoryService.save(entitys[i]);
    	}
    	addMessage(redirectAttributes, "保存栏目排序成功!");
		return "redirect:"+Global.getAdminPath()+"/cms/category/";
	}
	
	@RequiresUser
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(String module, @RequestParam(required=false) String extId, HttpServletResponse response) {
		response.setContentType("application/json; charset=UTF-8");
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Category> list = categoryService.findByUser(true, module);
		for (int i=0; i<list.size(); i++){
			Category e = list.get(i);
			if (extId == null || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParent()!=null?e.getParent().getId():0);
				map.put("name", e.getName());
				map.put("module", e.getModule());
				mapList.add(map);
			}
		}
		return mapList;
	}

    private List<String> getTplContent(String prefix) {
   		List<String> tplList = fileTplService.getNameListByPrefix(siteService.get(Site.getCurrentSiteId()).getSolutionPath());
   		tplList = TplUtils.tplTrim(tplList, prefix, "");
   		return tplList;
   	}
}
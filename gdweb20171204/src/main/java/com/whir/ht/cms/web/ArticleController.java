/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/whir/ht">ezSite</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.whir.ht.cms.web;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.whir.ht.cms.Message;
import com.whir.ht.cms.entity.Article;
import com.whir.ht.cms.entity.ArticleImage;
import com.whir.ht.cms.entity.Category;
import com.whir.ht.cms.entity.Classes;
import com.whir.ht.cms.entity.Grade;
import com.whir.ht.cms.entity.Major;
import com.whir.ht.cms.entity.Site;
import com.whir.ht.cms.service.ArticleImageService;
import com.whir.ht.cms.service.ArticleService;
import com.whir.ht.cms.service.CategoryService;
import com.whir.ht.cms.service.ClassesService;
import com.whir.ht.cms.service.FileTplService;
import com.whir.ht.cms.service.GradeService;
import com.whir.ht.cms.service.MajorService;
import com.whir.ht.cms.service.SiteService;
import com.whir.ht.cms.utils.TplUtils;
import com.whir.ht.common.config.Global;
import com.whir.ht.common.mapper.JsonMapper;
import com.whir.ht.common.persistence.Page;
import com.whir.ht.common.utils.IdGen;
import com.whir.ht.common.utils.SpringContextHolder;
import com.whir.ht.common.utils.StringUtils;
import com.whir.ht.common.web.BaseController;
import com.whir.ht.sys.utils.DictUtils;

/**
 * 文章Controller
 * 
 * @author Elvin
 * @version 2013-3-23
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/article")
public class ArticleController extends BaseController {

	HttpClientBuilder hcBuilder = HttpClients.custom();
	private CloseableHttpClient httpClient = hcBuilder.build();
	// private HttpContext context = new BasicHttpContext();

	@Autowired
	private ArticleService articleService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private FileTplService fileTplService;
	@Autowired
	private SiteService siteService;
	@Autowired
	private ArticleImageService articleImageService;
	@Autowired
	private GradeService gradeService;
	@Autowired
	private ClassesService classesService;
	@Autowired
	private MajorService majorService;

	private static ServletContext context = SpringContextHolder.getBean(ServletContext.class);

	@ModelAttribute
	public Article get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return articleService.get(id);
		} else {
			return new Article();
		}
	}

	@RequiresPermissions("cms:article:view")
	@RequestMapping(value = { "list", "" })
	public String list(Article article, HttpServletRequest request, HttpServletResponse response, Model model) {
		article.setIsRecommend(null);
		Page<Article> page = articleService.find(new Page<Article>(request, response), article, true);
		model.addAttribute("page", page);

		return "cms/articleList";
	}

	@RequiresPermissions("cms:article:view")
	@RequestMapping(value = "form")
	public String form(Article article, Model model) {
		// 如果当前传参有子节点，则选择取消传参选择
		// if (article.getCategory()!=null &&
		// StringUtils.isNotBlank(article.getCategory().getId())){
		// List<Category> list =
		// categoryService.findByParentId(article.getCategory().getId(),
		// Site.getCurrentSiteId());
		// if (list.size() > 0){
		// article.setCategory(null);
		// }
		// }

		String id = article.getCategory().getId();
		article.setCategory(null);
		article.setCategory(categoryService.get(id));
		if (article.getIsRecommend() == null) {
			article.setIsRecommend("0");
		}
		model.addAttribute("contentViewList", getTplContent());
		model.addAttribute("article_DEFAULT_TEMPLATE", Article.DEFAULT_TEMPLATE);
		model.addAttribute("article", article);
		return "cms/articleForm";
	}

	@RequiresPermissions("cms:article:edit")
	@RequestMapping(value = "save")

	public String save(Article article,String gradeId,String classesId,String majorId, Model model, HttpServletRequest request,
			RedirectAttributes redirectAttributes) {

		if (!beanValidator(model, article)) {
			return form(article, model);
		}
		if (article.getTitle() != null) {
			String temp = StringEscapeUtils.unescapeHtml4(article.getTitle());
			temp = StringUtils.replace(temp, "<!--[if !supportLineBreakNewLine]-->", "");
			temp = StringUtils.replace(temp, "<!--[endif]-->", "");
			article.setTitle(temp);
		}
		if (article.getArticleData().getContent() != null) {
			String temp = StringEscapeUtils.unescapeHtml4(article.getArticleData().getContent());
			temp = StringUtils.replace(temp, "<!--[if !supportLineBreakNewLine]-->", "");
			temp = StringUtils.replace(temp, "<!--[endif]-->", "");
			article.getArticleData().setContent(temp);
		}

		addMessage(redirectAttributes, "保存文章'" + StringUtils.abbr(article.getTitle(), 50) + "'成功");
		String categoryId = article.getCategory() != null ? article.getCategory().getId() : null;
		return "redirect:" + Global.getAdminPath() + "/cms/article/?repage&category.id="
				+ (categoryId != null ? categoryId : "");
	}

	@RequiresPermissions("cms:article:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, String categoryId, @RequestParam(required = false) Boolean isRe,
			RedirectAttributes redirectAttributes) {
		// 如果没有审核权限，则不允许删除或发布。
		if (!SecurityUtils.getSubject().isPermitted("cms:article:audit")) {
			addMessage(redirectAttributes, "你没有删除或发布权限");
		}

		articleService.delete(id, isRe);
		addMessage(redirectAttributes, (isRe != null && isRe ? "发布" : "删除") + "文章成功");
		return "redirect:" + Global.getAdminPath() + "/cms/article/?repage&category.id="
				+ (categoryId != null ? categoryId : "");
	}

	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public @ResponseBody String delete(String[] ids) {
		try {
			Boolean isRe = false;
			if (ids != null) {
				for (String id : ids) {
					articleService.delete(id, isRe);
				}
			}
			return Message.success("批量删除成功！");
		} catch (Exception e) {
			return Message.warn("批量删除失败！");
		}
	}

	/**
	 * 文章选择列表
	 */
	@RequiresPermissions("cms:article:view")
	@RequestMapping(value = "selectList")
	public String selectList(Article article, HttpServletRequest request, HttpServletResponse response, Model model) {
		list(article, request, response, model);
		return "cms/articleSelectList";
	}

	/**
	 * 通过编号获取文章标题
	 */
	@RequiresPermissions("cms:article:view")
	@ResponseBody
	@RequestMapping(value = "findByIds")
	public String findByIds(String ids) {
		List<Object[]> list = articleService.findByIds(ids);
		return JsonMapper.nonDefaultMapper().toJson(list);
	}

	private List<String> getTplContent() {
		List<String> tplList = fileTplService
				.getNameListByPrefix(siteService.get(Site.getCurrentSiteId()).getSolutionPath());
		tplList = TplUtils.tplTrim(tplList, Article.DEFAULT_TEMPLATE, "");
		return tplList;
	}
	
}

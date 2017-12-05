/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/whir/ht">ezSite</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.whir.ht.cms.web.app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.whir.ht.cms.entity.Article;
import com.whir.ht.cms.entity.ArticleData;
import com.whir.ht.cms.entity.ArticleImage;
import com.whir.ht.cms.entity.Category;
import com.whir.ht.cms.entity.Link;
import com.whir.ht.cms.entity.Site;
import com.whir.ht.cms.service.ArticleService;
import com.whir.ht.cms.service.CategoryService;
import com.whir.ht.cms.service.LinkService;
import com.whir.ht.cms.utils.CmsUtils;
import com.whir.ht.cms.web.app.model.Banner;
import com.whir.ht.cms.web.app.model.CustomResponse;
import com.whir.ht.cms.web.app.model.Dto;
import com.whir.ht.common.mapper.JsonMapper;
import com.whir.ht.common.persistence.Page;
import com.whir.ht.common.utils.StringUtils;
import com.whir.ht.common.web.BaseController;
import com.whir.ht.sys.utils.DictUtils;

/**
 * 网站Controller
 * 
 * @author Elvin
 * @version 2013-5-29
 */
@Controller
@RequestMapping(value = "${frontPath}/app")
public class AppFrontController extends BaseController {
	private static Logger log = LoggerFactory.getLogger(AppFrontController.class);
	@Autowired
	private ArticleService articleService;
	@Autowired
	private LinkService linkService;
	@Autowired
	private CategoryService categoryService;

	/**
	 * 内容列表
	 */
	@RequestMapping(value = "list-{categoryId}")
	@ResponseBody
	public CustomResponse<List<Article>> list(@PathVariable String categoryId,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(required = false, defaultValue = "15") Integer pageSize, String jobAddress, String order,
			String keyword, String gradeName, String className, String majorName, HttpServletRequest request,
			Model model, @RequestParam(required = false) String issue) {
		
		CustomResponse<List<Article>> data = new CustomResponse<List<Article>>();
		Category category = categoryService.get(categoryId);
		if (category == null) {
			log.error("{}:栏目id不存在！",new Date().getTime());
			return data;
		}
		// 2：简介类栏目，栏目第一条内容
		if ("2".equals(category.getShowModes()) && "article".equals(category.getModule())) {
			// 如果没有子栏目，并父节点为跟节点的，栏目列表为当前栏目。
			List<Category> categoryList = Lists.newArrayList();
			if (category.getParent().getId().equals("1")) {
				categoryList.add(category);
			} else {
				categoryList = categoryService.findByParentId(category.getParent().getId(), category.getSite().getId(),category.getAppinMenu());
			}
			try {
				// 获取文章内容
				Page<Article> page = new Page<Article>(1, 1, 0);
				Article article = new Article(category);
				if (StringUtils.isNotBlank(keyword)) {
					article.setTitle(keyword);
					ArticleData prticleData = new ArticleData();
					prticleData.setContent(keyword);
					article.setArticleData(prticleData);
				}
				page = articleService.findFront(page, article, false);
				if (page.getList().size() > 0) {
					article = page.getList().get(0);
					articleService.updateHitsAddOne(article.getId());
				}
//				data.put("article", article);
				setTplModelAttribute(model, category);
				setTplModelAttribute(model, article.getViewConfig());
			} catch (Exception e) {
				// TODO: handle exception
			}
			return data;
		} else {
			List<Category> categoryList = categoryService.findByParentId(category.getId(), category.getSite().getId(),category.getAppinMenu());
			//当有多个子下级时，都展示，例如培养方案、毕业成果
			if (categoryList.size()>1){
				Page<Article> page1 = new Page<Article>();
				page1.setPageNo(pageNo);
				page1.setPageSize(pageSize);
				
				Article searchArticle = new Article(category);
				page1 = articleService.findFrontForPenYang(page1, searchArticle, false);
				data.setData(page1.getList());
				return data;	
			}
			// 展现方式为1 、无子栏目或公共模型，显示栏目内容列表
			if ("1".equals(category.getShowModes()) || categoryList.size() == 0) {
				// 有子栏目并展现方式为1，则获取第一个子栏目；无子栏目，则获取同级分类列表。
				if (categoryList.size() > 0) {
					category = categoryList.get(0);
					if (category.getId().equals(DictUtils.getDictValue("front.interactive", "comm_category_id", ""))) {
						category = categoryList.get(1);
						if (category.getId()
								.equals(DictUtils.getDictValue("front.onlineHomework", "comm_category_id", ""))) {
							try {
								String role = getCurrentRole();
								if (role.equals(TeacherRole)) {
									
									return data;

								} else if (role.equals(StudentRole)) {
									
									return data;
								} else {
									
									return data;
								}
							} catch (Exception e) {
								// TODO: handle exception
							}
						}
					}
				} else {
					// 如果没有子栏目，并父节点为根节点的，栏目列表为当前栏目。
					if (category.getParent().getId().equals("1")) {
						categoryList.add(category);
					} else {
						categoryList = categoryService.findByParentId(category.getParent().getId(),
								category.getSite().getId(),category.getAppinMenu());
					}
				}
				try {
					// 获取内容列表
					if ("article".equals(category.getModule())) {
						Page<Article> page1 = new Page<Article>();
						page1.setPageNo(pageNo);
						page1.setPageSize(pageSize);
						
						Article searchArticle = new Article(category);
						if (StringUtils.isNotBlank(keyword)) {
							searchArticle.setTitle(keyword);
							ArticleData prticleData = new ArticleData();
							prticleData.setContent(keyword);
							searchArticle.setArticleData(prticleData);
						}
						page1 = articleService.findFront(page1, searchArticle, false);
						data.setData(page1.getList());
						// 如果第一个子栏目为简介类栏目，则获取该栏目第一篇文章
						if ("2".equals(category.getShowModes())) {
							Article article = new Article(category);
							if (page1.getList().size() > 0) {
								article = page1.getList().get(0);
								articleService.updateHitsAddOne(article.getId());
							}
							setTplModelAttribute(model, category);
							setTplModelAttribute(model, article.getViewConfig());
							return data;
						}
					} else if ("link".equals(category.getModule())) {
						Page<Link> page = new Page<Link>(1, -1);
						page = linkService.find(page, new Link(category), false);
					}
					setTplModelAttribute(model, category);
				} catch (Exception e) {
					// TODO: handle exception
				}
				return data;
			}
			// 有子栏目：显示子栏目列表
			else {
				Page<Article> page = new Page<Article>(pageNo, pageSize);
				if(pageNo<=0 || pageNo>pageSize){
					return data;
				}
				page = articleService.findFront(page, new Article(category), false);
				data.setData(page.getList());
				setTplModelAttribute(model, category);
				return data;
			}
		}
	}

	
	/**
	 * 显示内容
	 */
	@RequestMapping(value = "view-{contentId}")
	@ResponseBody
	public CustomResponse<Dto> view(@PathVariable String contentId,
			HttpServletRequest request, Model model) {
		CustomResponse<Dto> data = new CustomResponse<Dto>();
		try {
				// 获取文章内容
				Article article = articleService.get(contentId);
				Dto dto = new Dto();
				
				String categroyId= article.getCategory().getId();
				String categroyName = article.getCategory().getName();
				StringBuffer url = request.getRequestURL();  
				String tempContextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).toString();  
				String content="";
				Document doc = Jsoup.parse(article.getArticleData().getContent());
				
			      Elements pngs = doc.select("img[src]");
			      for (Element element : pngs) {
			        String imgUrl = element.attr("src");
			        if (imgUrl.trim().startsWith("/")) {
			          imgUrl =tempContextUrl + imgUrl;
			          element.attr("src", imgUrl);
			        }
			      }
			      
			      Elements hrefs = doc.select("a[href]");
			      for (Element element : hrefs) {
			        String hrefUrl = element.attr("href");
			        if (hrefUrl.trim().startsWith("/")) {
			        	hrefUrl =tempContextUrl + hrefUrl;
			          element.attr("href", hrefUrl);
			        }
			      }
			      content = doc.body().toString();
				dto.setTitle(article.getTitle());
				dto.setContent(content);//获取文章内容数据dto.setContent(article.getArticleData().getContent());
				dto.setImage(article.getImage());
				dto.setAttach(article.getAttach());
				dto.setPublishDate(article.getPublishDate());
				dto.setPaperTime(article.getPaperTime());
				dto.setCounselor(article.getCounselor());
				dto.setAuthor(article.getArticleData().getAuthor());
				dto.setGrade(article.getGrade());
				dto.setClasses(article.getClasses());
				dto.setMajor(article.getMajor());
				dto.setCopyfrom(article.getArticleData().getCopyfrom());
				dto.setHits(article.getHits());	
				
				List<ArticleImage> articleImages = article.getArticleImages();
				dto.setArticleImages(articleImages);
				dto.setCategroyId(categroyId);
				dto.setCategroyName(categroyName);

				// 文章阅读次数+1
				articleService.updateHitsAddOne(contentId);
				data.setData(dto);
				return data;
			//}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return data;
		// return "error/404";
	}
	
	/**
	 * 从单页栏目获取文章内容
	 */
	@RequestMapping(value = "single-{channelId}")
	@ResponseBody
	public CustomResponse<Dto> viewSingle(@PathVariable String channelId,
			HttpServletRequest request, Model model) {
		CustomResponse<Dto> result = new CustomResponse<Dto>();
		try {
			Category category = categoryService.get(channelId);
			if (category.getShowflag().equals("2")) {
				Page<Article> page = new Page<Article>(1, 1, 0);
				Article article = new Article(category);
//				
				page = articleService.findFront(page, article, false);
				if (page.getList().size() > 0) {
					article = page.getList().get(0);
					Dto dto = new Dto();
					   dto.setTitle(article.getTitle());
						StringBuffer url = request.getRequestURL();  
						String tempContextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).toString();  
						String content="";
						Document doc = Jsoup.parse(article.getArticleData().getContent());
						String categroyId = article.getCategory().getId();
						String categroyName = article.getCategory().getName();
					      Elements pngs = doc.select("img[src]");
					      for (Element element : pngs) {
					        String imgUrl = element.attr("src");
					        if (imgUrl.trim().startsWith("/")) {
					          imgUrl =tempContextUrl + imgUrl;
					          element.attr("src", imgUrl);
					        }
					      }
					      Elements hrefs = doc.select("a[href]");
					      for (Element element : hrefs) {
					        String hrefUrl = element.attr("href");
					        if (hrefUrl.trim().startsWith("/")) {
					        	hrefUrl =tempContextUrl + hrefUrl;
					          element.attr("href", hrefUrl);
					        }
					      }
					      content = doc.body().toString();
						dto.setContent(content);//获取文章内容数据article.getArticleData().getContent()
						dto.setAuthor(article.getArticleData().getAuthor());
						dto.setCopyfrom(article.getArticleData().getCopyfrom());
						dto.setHits(article.getHits());
						dto.setAttach(article.getAttach());
						dto.setImage(article.getImage());
						dto.setCategroyId(categroyId);
						dto.setCategroyName(categroyName);
						List<ArticleImage> articleImages = article.getArticleImages();
						dto.setArticleImages(articleImages);
						result.setCode(0);
						result.setData(dto);
						return result;
				}
			}
		
		} catch (Exception e) {
			// TODO: handle exception
		}
		result.setCode(-1);
		result.setMsg("error");
		return result;
	}

	/**
	 * 获取链接列表
	 * 
	 * @param siteId
	 *            站点编号
	 * @param categoryId
	 *            分类编号
	 * @param number
	 *            获取数目
	 * @param param
	 *            预留参数，例： key1:'value1', key2:'value2' ...
	 * @return
	 */
	@RequestMapping(value = "linklist-{categoryId}-{number}", method = RequestMethod.GET)
	@ResponseBody
	public CustomResponse<List<Banner>> getLinkList(@PathVariable String categoryId,@PathVariable int number, String param) {
		Site site = CmsUtils.getSite(Site.defaultSiteId());
		CustomResponse<List<Banner>> data = new CustomResponse<List<Banner>>();
		try {
			Page<Link> page = new Page<Link>(1, -1, -1);
			Link link = new Link(new Category(categoryId, new Site(site.getId())));
			if (StringUtils.isNotBlank(param)) {
				@SuppressWarnings({ "unused", "rawtypes" })
				Map map = JsonMapper.getInstance().fromJson("{" + param + "}", Map.class);
			}
			link.setDelFlag(Link.DEL_FLAG_NORMAL);
			page = linkService.find(page, link, false);
			if (null!=page.getList()){
				List<Banner> banners = new ArrayList<Banner>();
				for(Link link2:page.getList()){
					Banner banner =new Banner();
					banner.setThref(link2.getHref());
					banner.setImage(link2.getImage());
					banner.setTitle(link2.getTitle());
					banners.add(banner);
				}
				data.setData(banners);	
			}		
		} catch (Exception e) {
			data.setData(new ArrayList<Banner>());
		}
		return data;
	}

	/**
	 * 站点地图
	 */
	@RequestMapping(value = "map-{siteId}")
	@ResponseBody
	public Map<String, Object> map(@PathVariable String siteId, Model model) {
		Map<String, Object> data = Maps.newHashMap();
		Site site = CmsUtils.getSite(siteId != null ? siteId : Site.defaultSiteId());
		data.put("site", site);
		return data;
	}

	private void setTplModelAttribute(Model model, String param) {
		if (StringUtils.isNotBlank(param)) {
			@SuppressWarnings("rawtypes")
			Map map = JsonMapper.getInstance().fromJson(param, Map.class);
			if (map != null) {
				for (Object o : map.keySet()) {
					model.addAttribute("viewConfig_" + o.toString(), map.get(o));
				}
			}
		}
	}

	private void setTplModelAttribute(Model model, Category category) {
		List<Category> categoryList = Lists.newArrayList();
		Category c = category;
		boolean goon = true;
		do {
			if (c.getParent() == null || c.getParent().isRoot()) {
				goon = false;
			}
			categoryList.add(c);
			c = c.getParent();
		} while (goon);
		Collections.reverse(categoryList);
		for (Category ca : categoryList) {
			setTplModelAttribute(model, ca.getViewConfig());
		}
	}
	
	/**
	 * app首页菜单
	 */
	@RequestMapping(value = "channel-{siteId}")
	@ResponseBody
	public List<Category> indexCategory(@PathVariable String siteId, Model model) {
		List<Category>  channels = null;
		try {
			channels = CmsUtils.getMainNavList(siteId);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return channels;
	}
	
	/**
	 * app栏目菜单
	 */
	@RequestMapping(value = "childchannel")
	@ResponseBody
	public List<Category> category(String cid, String param,Model model) {
		if (cid != null) {
			List<Category> categorys = null;
			try {
				Site site = CmsUtils.getSite(Site.defaultSiteId());
				Category childcategory = categoryService.get(cid);
				String parentId = childcategory.getParent().getId();
				categorys = CmsUtils.getCategoryList(site.getId(), parentId, -1, param);
			} catch (Exception e) {
				// TODO: handle exception
			}
			return categorys;
		}
		return null;
		
	}
	
	/**
	 * app栏目
	 */
	@RequestMapping(value = "category-{categoryId}")
	@ResponseBody
	public Object channel(@PathVariable String categoryId) {
		Map<String, Object> data = Maps.newHashMap();
		try {
			Category category = categoryService.get(categoryId);
			if (category == null) {
				String message = "错误";
				log.error("{}:栏目id不存在！", new Date().getTime());
				data.put("message", message);
				return data;
			}
			if ("2".equals(category.getShowModes()) && "article".equals(category.getModule())) {
				// 如果没有子栏目，并父节点为跟节点的，栏目列表为当前栏目。
				List<Category> categoryList = Lists.newArrayList();
				if (category.getParent().getId().equals("1")) {
					categoryList.add(category);
				} else {
					categoryList = categoryService.findByParentId(category.getParent().getId(), category.getSite().getId(),category.getAppinMenu());
				}
				data.put("category", category);
				data.put("categoryList", categoryList);
			} else {
				List<Category> categoryList = categoryService.findByParentId(category.getId(), category.getSite().getId(),category.getAppinMenu());

				// 展现方式为1 、无子栏目或公共模型，显示栏目内容列表
				if ("1".equals(category.getShowModes()) || categoryList.size() == 0) {
					// 有子栏目并展现方式为1，则获取第一个子栏目；无子栏目，则获取同级分类列表。
					if (categoryList.size() > 0) {
						category = categoryList.get(0);
						if (category.getId().equals(DictUtils.getDictValue("front.interactive", "comm_category_id", ""))) {
							category = categoryList.get(1);
							if (category.getId()
									.equals(DictUtils.getDictValue("front.onlineHomework", "comm_category_id", ""))) {
								try {
									String role = getCurrentRole();
									if (role.equals(TeacherRole)) {
										return data;
									} else if (role.equals(StudentRole)) {
										return data;
									} else {
										return data;
									}
								} catch (Exception e) {
									// TODO: handle exception
								}
							}
						}
					} else {
						// 如果没有子栏目，并父节点为根节点的，栏目列表为当前栏目。
						if (category.getParent().getId().equals("1")) {
							categoryList.add(category);
						} else {
							categoryList = categoryService.findByParentId(category.getParent().getId(),
									category.getSite().getId(),category.getAppinMenu());
						}
					}
					data.put("category", category);
					data.put("categoryList", categoryList);
				}
				// 有子栏目：显示子栏目列表
				else {
					data.put("category", category);
					data.put("categoryList", categoryList);
					return data;
				}
			}
			String showflag = category.getShowflag();
			data.put("showflag", showflag);
		} catch (Exception e) {
			// TODO: handle exception
		}
			return data;
	}
	
	/**
	 * app所有栏目菜单
	 */
	@RequestMapping(value = "categorys")
	@ResponseBody
	public CustomResponse<List<Category>> categorys(String appinMenu) {
		CustomResponse<List<Category>> response = new CustomResponse<List<Category>>();
		
		
		List<Category> categorys = categoryService.findByCategorys(appinMenu);
		response.setCode(0);
		response.setData(categorys);
		return response;
	}
	
	/**
	 * 根据关键字和栏目搜索列表
	 */
	@RequestMapping(value = "search-{categoryId}")
	@ResponseBody
	public Object searchlist(@PathVariable String categoryId,
			@RequestParam(value="page",required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(required = false, defaultValue = "15") Integer pageSize, String jobAddress, String order,
			String keyword, String gradeName, String className, String majorName, HttpServletRequest request,
			Model model, @RequestParam(required = false) String issue) {
		
		Map<String, Object> data = Maps.newHashMap();
		Category category = categoryService.get(categoryId);
		if (category == null) {
			String message = "错误";
			log.error("{}:栏目id不存在！",new Date().getTime());
			data.put("message", message);
			return data;
		}
		// 2：简介类栏目，栏目第一条内容
		if ("2".equals(category.getShowModes()) && "article".equals(category.getModule())) {
			// 如果没有子栏目，并父节点为跟节点的，栏目列表为当前栏目。
			List<Category> categoryList = Lists.newArrayList();
			if (category.getParent().getId().equals("1")) {
				categoryList.add(category);
			} else {
				categoryList = categoryService.findByParentId(category.getParent().getId(), category.getSite().getId(),category.getAppinMenu());
			}
			data.put("category", category);
			//data.put("categoryList", categoryList);
			try {
				// 获取文章内容
				Page<Article> page = new Page<Article>(1, 1, 0);
				Article article = new Article(category);
				if (StringUtils.isNotBlank(keyword)) {
					article.setTitle(keyword);
					ArticleData prticleData = new ArticleData();
					prticleData.setContent(keyword);
					article.setArticleData(prticleData);
				}
				data.put("keyword", keyword);
				page = articleService.findFront(page, article, false);
				if (page.getList().size() > 0) {
					article = page.getList().get(0);
					articleService.updateHitsAddOne(article.getId());
				}
				data.put("article", article);
				setTplModelAttribute(model, category);
				setTplModelAttribute(model, article.getViewConfig());
			} catch (Exception e) {
				// TODO: handle exception
			}
			return data;
		} else {
			List<Category> categoryList = categoryService.findByParentId(category.getId(), category.getSite().getId(),category.getAppinMenu());

			// 展现方式为1 、无子栏目或公共模型，显示栏目内容列表
			if ("1".equals(category.getShowModes()) || categoryList.size() == 0) {
				// 有子栏目并展现方式为1，则获取第一个子栏目；无子栏目，则获取同级分类列表。
				if (categoryList.size() > 0) {
					category = categoryList.get(0);
					if (category.getId().equals(DictUtils.getDictValue("front.interactive", "comm_category_id", ""))) {
						category = categoryList.get(1);
						if (category.getId()
								.equals(DictUtils.getDictValue("front.onlineHomework", "comm_category_id", ""))) {
							try {
								String role = getCurrentRole();
								if (role.equals(TeacherRole)) {
									
									return data;

								} else if (role.equals(StudentRole)) {
									
									return data;
								} else {
									
									return data;
								}
							} catch (Exception e) {
								// TODO: handle exception
							}
						}
					}
				} else {
					// 如果没有子栏目，并父节点为根节点的，栏目列表为当前栏目。
					if (category.getParent().getId().equals("1")) {
						categoryList.add(category);
					} else {
						categoryList = categoryService.findByParentId(category.getParent().getId(),
								category.getSite().getId(),category.getAppinMenu());
					}
				}
				data.put("category", category);
				//data.put("categoryList", categoryList);
				try {
					// 获取内容列表
					if ("article".equals(category.getModule())) {
						Page<Article> page = new Page<Article>(pageNo, pageSize);
						if(pageNo<=0 || pageNo>pageSize){
							return data;
						}
						Article searchArticle = new Article(category);
						if (StringUtils.isNotBlank(keyword)) {
							searchArticle.setTitle(keyword);
							ArticleData prticleData = new ArticleData();
							prticleData.setContent(keyword);
							searchArticle.setArticleData(prticleData);
						}
						page = articleService.findFront(page, searchArticle, false);
						data.put("keyword", keyword);
						data.put("page", page);
						// 如果第一个子栏目为简介类栏目，则获取该栏目第一篇文章
						if ("2".equals(category.getShowModes())) {
							Article article = new Article(category);
							if (page.getList().size() > 0) {
								article = page.getList().get(0);
								articleService.updateHitsAddOne(article.getId());
							}
							data.put("article", article);
							setTplModelAttribute(model, category);
							setTplModelAttribute(model, article.getViewConfig());
							return data;
						}
					} else if ("link".equals(category.getModule())) {
						Page<Link> page = new Page<Link>(1, -1);
						page = linkService.find(page, new Link(category), false);
						data.put("page", page);
					}
					setTplModelAttribute(model, category);
				} catch (Exception e) {
					// TODO: handle exception
				}
				return data;
			}
			// 有子栏目：显示子栏目列表
			else {
				data.put("category", category);
				data.put("categoryList", categoryList);
				Page<Article> page = new Page<Article>(pageNo, pageSize);
				if(pageNo<=0 || pageNo>pageSize){
					return data;
				}
				page = articleService.findFront(page, new Article(category), false);
				data.put("page", page);
				setTplModelAttribute(model, category);
				return data;
			}
		}
	}

}

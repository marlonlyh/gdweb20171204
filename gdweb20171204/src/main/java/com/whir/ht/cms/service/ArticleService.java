/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/whir/ht">ezSite</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.whir.ht.cms.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.shiro.SecurityUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.whir.ht.cms.dao.ArticleDao;
import com.whir.ht.cms.dao.CategoryDao;
import com.whir.ht.cms.entity.Article;
import com.whir.ht.cms.entity.Category;
import com.whir.ht.cms.entity.Site;
import com.whir.ht.common.persistence.Page;
import com.whir.ht.common.service.BaseService;
import com.whir.ht.common.utils.CacheUtils;
import com.whir.ht.common.utils.StringUtils;
import com.whir.ht.sys.utils.DictUtils;
import com.whir.ht.sys.utils.UserUtils;


/**
 * 文章Service
 * @author Elvin
 * @version 2013-05-15
 */
@Service
@Transactional(readOnly = true)
public class ArticleService extends BaseService {

	@Autowired
	private ArticleDao articleDao;
	@Autowired
	private CategoryDao categoryDao;
	
	public Article get(String id) {
		return articleDao.get(id);
	}
	
	@Transactional(readOnly = false)
	public Page<Article> find(Page<Article> page, Article article, boolean isDataScopeFilter) {
		// 更新过期的权重，间隔为“6”个小时
		Date updateExpiredWeightDate =  (Date)CacheUtils.get("updateExpiredWeightDateByArticle");
		if (updateExpiredWeightDate == null || (updateExpiredWeightDate != null 
				&& updateExpiredWeightDate.getTime() < new Date().getTime())){
			articleDao.updateExpiredWeight();
			CacheUtils.put("updateExpiredWeightDateByArticle", DateUtils.addHours(new Date(), 6));
		}
		DetachedCriteria dc = articleDao.createDetachedCriteria();
		dc.createAlias("category", "category");
		dc.createAlias("category.site", "category.site");
		dc.createAlias("articleData", "articleData");
		if (article.getCategory()!=null && StringUtils.isNotBlank(article.getCategory().getId()) && !Category.isRoot(article.getCategory().getId())){
			Category category = categoryDao.get(article.getCategory().getId());
			if (category!=null){
				dc.add(Restrictions.or(
						Restrictions.eq("category.id", category.getId()),
						Restrictions.like("category.parentIds", "%,"+category.getId()+",%")));
				dc.add(Restrictions.eq("category.site.id", category.getSite().getId()));
				article.setCategory(category);
			}else{
				dc.add(Restrictions.eq("category.site.id", Site.getCurrentSiteId()));
			}
		}else{
			dc.add(Restrictions.eq("category.site.id", Site.getCurrentSiteId()));
		}
		if (StringUtils.isNotEmpty(article.getTitle())){
			dc.add(Restrictions.like("title", "%"+article.getTitle()+"%"));
		}
		if (null!=article.getArticleData()){
			dc.add(Restrictions.like("articleData.content", "%"+article.getArticleData().getContent()+"%"));
		}
		if(article.getIsRecommend()!=null){
			dc.add(Restrictions.eq("isRecommend", article.getIsRecommend()));
		}
		if (StringUtils.isNotEmpty(article.getPosid())){
			dc.add(Restrictions.like("posid", "%,"+article.getPosid()+",%"));
		}
		if (StringUtils.isNotEmpty(article.getImage())&&Article.YES.equals(article.getImage())){
			dc.add(Restrictions.and(Restrictions.isNotNull("image"),Restrictions.ne("image","")));
		}
		if (article.getCreateBy()!=null && StringUtils.isNotBlank(article.getCreateBy().getId())){
			dc.add(Restrictions.eq("createBy.id", article.getCreateBy().getId()));
		}
//		if (isDataScopeFilter){
//			dc.createAlias("category.office", "categoryOffice").createAlias("createBy", "createBy");
//			dc.add(dataScopeFilter(UserUtils.getUser(), "categoryOffice", "createBy"));
//		}
		dc.add(Restrictions.eq(Article.FIELD_DEL_FLAG, article.getDelFlag()));
		if (StringUtils.isBlank(page.getOrderBy())){
			dc.addOrder(Order.desc("weight"));
			dc.addOrder(Order.desc("publishDate"));
		}
		return articleDao.find(page, dc);
	}

	@Transactional(readOnly = false)
	public Page<Article> findFront(Page<Article> page, Article article, boolean isDataScopeFilter) {
		// 更新过期的权重，间隔为“6”个小时
		Date updateExpiredWeightDate =  (Date)CacheUtils.get("updateExpiredWeightDateByArticle");
		if (updateExpiredWeightDate == null || (updateExpiredWeightDate != null 
				&& updateExpiredWeightDate.getTime() < new Date().getTime())){
			articleDao.updateExpiredWeight();
			CacheUtils.put("updateExpiredWeightDateByArticle", DateUtils.addHours(new Date(), 6));
		}
		DetachedCriteria dc = articleDao.createDetachedCriteria();
		dc.createAlias("category", "category");
		dc.createAlias("category.site", "category.site");
		dc.createAlias("articleData", "articleData");
//		dc.createAlias("grade", "grade");
//		dc.createAlias("classes", "classes");
//		dc.createAlias("major", "major");
		if (article.getCategory()!=null && StringUtils.isNotBlank(article.getCategory().getId()) && !Category.isRoot(article.getCategory().getId())){
			Category category = categoryDao.get(article.getCategory().getId());
			if (category!=null){
				dc.add(Restrictions.or(
						Restrictions.eq("category.id", category.getId()),
						Restrictions.like("category.parentIds", "%,"+category.getId()+",%")));
				dc.add(Restrictions.eq("category.site.id", category.getSite().getId()));
				article.setCategory(category);
			}else{
				dc.add(Restrictions.eq("category.site.id", Site.getCurrentSiteId()));
			}
		}else{
			dc.add(Restrictions.eq("category.site.id", Site.getCurrentSiteId()));
		}
		if (StringUtils.isNotEmpty(article.getTitle()) || null!=article.getArticleData()){
			dc.add(Restrictions.or(
					Restrictions.like("title", "%"+article.getTitle()+"%"),
					Restrictions.like("articleData.content", "%"+article.getArticleData().getContent()+"%")));
		}
		if(article.getIsRecommend()!=null){
			dc.add(Restrictions.eq("isRecommend", article.getIsRecommend()));
		}
		if (StringUtils.isNotEmpty(article.getPosid())){
			dc.add(Restrictions.like("posid", "%,"+article.getPosid()+",%"));
		}
		if (StringUtils.isNotEmpty(article.getImage())&&Article.YES.equals(article.getImage())){
			dc.add(Restrictions.and(Restrictions.isNotNull("image"),Restrictions.ne("image","")));
		}
		if (article.getCreateBy()!=null && StringUtils.isNotBlank(article.getCreateBy().getId())){
			dc.add(Restrictions.eq("createBy.id", article.getCreateBy().getId()));
		}
//		if (article.getGrade()!=null && StringUtils.isNotBlank(article.getGrade().getId())){
//			dc.add(Restrictions.eq("grade.id", article.getGrade().getId()));
//		}
//		
//		if (article.getClasses()!=null && StringUtils.isNotBlank(article.getClasses().getId())){
//			dc.add(Restrictions.eq("classes.id", article.getClasses().getId()));
//		}
//		
//		if (article.getMajor()!=null && StringUtils.isNotBlank(article.getMajor().getId())){
//			dc.add(Restrictions.eq("major.id", article.getMajor().getId()));
//		}
		dc.add(Restrictions.eq(Article.FIELD_DEL_FLAG, article.getDelFlag()));
		if (StringUtils.isBlank(page.getOrderBy())){
			dc.addOrder(Order.desc("weight"));
			dc.addOrder(Order.desc("publishDate"));
		}
		return articleDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(Article article) {
		if (article.getArticleData().getContent()!=null){
			String temp=StringEscapeUtils.unescapeHtml4(article.getArticleData().getContent());
			temp=StringUtils.replace(temp, "<!--[if !supportLineBreakNewLine]-->", "");
			temp=StringUtils.replace(temp, "<!--[endif]-->", "");
			article.getArticleData().setContent(temp);
		}
		
		// 如果没有审核权限，则将当前内容改为待审核状态
		if (!SecurityUtils.getSubject().isPermitted("cms:article:audit")){
			article.setDelFlag(Article.DEL_FLAG_AUDIT);
		}
		// 如果栏目不需要审核，则将该内容设为发布状态
		if (article.getCategory()!=null&&StringUtils.isNotBlank(article.getCategory().getId())){
			Category category = categoryDao.get(article.getCategory().getId());
			if (!Article.YES.equals(category.getIsAudit())){
				article.setDelFlag(Article.DEL_FLAG_NORMAL);
			}
		}
		if(UserUtils.getUser().getId() != null){
			article.setUpdateBy(UserUtils.getUser());
		}
		
		
		article.setUpdateDate(new Date());
        if (StringUtils.isNotBlank(article.getViewConfig())){
            article.setViewConfig(StringEscapeUtils.unescapeHtml4(article.getViewConfig()));
        }
		articleDao.clear();
		articleDao.save(article);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id, Boolean isRe) {
//		articleDao.updateDelFlag(id, isRe!=null&&isRe?Article.DEL_FLAG_NORMAL:Article.DEL_FLAG_DELETE);
		// 使用下面方法，以便更新索引。
		Article article = articleDao.get(id);
		article.setDelFlag(isRe!=null&&isRe?Article.DEL_FLAG_NORMAL:Article.DEL_FLAG_DELETE);
		articleDao.save(article);
	}
	
	/**
	 * 通过编号获取内容标题
	 * @return new Object[]{栏目Id,文章Id,文章标题}
	 */
	public List<Object[]> findByIds(String ids) {
		if (ids==null){
			ids="";
		}
		List<Object[]> list = Lists.newArrayList();
		String[] idss = StringUtils.split(ids,",");
		if (idss.length>0){
			List<Article> l = articleDao.findByIdIn(idss);
			for (Article e : l){
				list.add(new Object[]{e.getCategory().getId(),e.getId(),StringUtils.abbr(e.getTitle(),50)});
			}
		}
		return list;
	}
	
	/**
	 * 点击数加一
	 */
	@Transactional(readOnly = false)
	public void updateHitsAddOne(String id) {
		articleDao.updateHitsAddOne(id);
	}
	
	/**
	 * 更新索引
	 */
	public void createIndex(){
		articleDao.createIndex();
	}
	
	/**
	 * 全文检索
	 */
	public Page<Article> search(Page<Article> page, String q, String categoryId, String beginDate, String endDate){
		
		// 设置查询条件
		BooleanQuery query = articleDao.getFullTextQuery(q, "title","keywords","description","articleData.content");
		
		// 设置过滤条件
		List<BooleanClause> bcList = Lists.newArrayList();

		bcList.add(new BooleanClause(new TermQuery(new Term(Article.FIELD_DEL_FLAG, Article.DEL_FLAG_NORMAL)), Occur.MUST));
		if (StringUtils.isNotBlank(categoryId)){
			bcList.add(new BooleanClause(new TermQuery(new Term("category.ids", categoryId)), Occur.MUST));
		}
		
		if (StringUtils.isNotBlank(beginDate) && StringUtils.isNotBlank(endDate)) {   
			bcList.add(new BooleanClause(new TermRangeQuery("updateDate", beginDate.replaceAll("-", ""),
					endDate.replaceAll("-", ""), true, true), Occur.MUST));
		}   
		
		BooleanQuery queryFilter = articleDao.getFullTextQuery((BooleanClause[])bcList.toArray(new BooleanClause[bcList.size()]));

//		System.out.println(queryFilter);
		
		// 设置排序（默认相识度排序）
		Sort sort = null;//new Sort(new SortField("updateDate", SortField.DOC, true));
		// 全文检索
		articleDao.search(page, query, queryFilter, sort);
		// 关键字高亮
		articleDao.keywordsHighlight(query, page.getList(), 30, "title");
		articleDao.keywordsHighlight(query, page.getList(), 130, "description","articleData.content");
		
		return page;
	}
	
	
	@Transactional(readOnly = false)
	public Page<Article> search(Page<Article> page, List<String>categoryIds,String keyword,Date holdDate,String holdAddress,String holdCity,String holdSite,String isRecommend) {
		// 更新过期的权重，间隔为“6”个小时
		Date updateExpiredWeightDate =  (Date)CacheUtils.get("updateExpiredWeightDateByArticle");
		if (updateExpiredWeightDate == null || (updateExpiredWeightDate != null 
				&& updateExpiredWeightDate.getTime() < new Date().getTime())){
			articleDao.updateExpiredWeight();
			CacheUtils.put("updateExpiredWeightDateByArticle", DateUtils.addHours(new Date(), 6));
		}
		DetachedCriteria dc = articleDao.createDetachedCriteria();
		dc.createAlias("category", "category");
		dc.createAlias("category.site", "category.site");	
		dc.createAlias("articleData", "articleData");
		dc.add(Restrictions.eq("category.site.id", Site.getCurrentSiteId()));
		
		if (StringUtils.isNotEmpty(keyword)){
			dc.add(Restrictions.or(
					Restrictions.like("keywords","%"+keyword+"%"),
					Restrictions.like("title", "%"+keyword+"%"),
					Restrictions.like("articleData.content", "%"+keyword+"%")));
		}
		if (holdDate!=null) {
			Date sideDate=	holdDate;
			Date endDate=holdDate;
			if (sideDate != null) {
				Calendar calendar = DateUtils.toCalendar(sideDate);
				calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
				calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
				calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
				sideDate = calendar.getTime();
			}
			if (endDate != null) {
				Calendar calendar = DateUtils.toCalendar(endDate);
				calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
				calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
				calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
				endDate = calendar.getTime();
			}
			
			dc.add(Restrictions.ge("holdDate",sideDate));
			dc.add(Restrictions.le("holdDate",endDate));
			
		}
		if (StringUtils.isNotEmpty(isRecommend)) {
			dc.add(Restrictions.eq("isRecommend",isRecommend));
		}
		if (StringUtils.isNotEmpty(holdAddress)) {
			dc.add(Restrictions.eq("holdAddress",holdAddress));
		}
		if (StringUtils.isNotEmpty(holdCity)) {
			dc.add(Restrictions.eq("holdCity",holdCity));
		}
		if (StringUtils.isNotEmpty(holdSite)) {
			dc.add(Restrictions.eq("holdSite",holdSite));
		}
		dc.add(Restrictions.in("category.id", categoryIds));
		dc.add(Restrictions.eq("category.site.id", Site.getCurrentSiteId()));
		
		dc.add(Restrictions.eq(Article.FIELD_DEL_FLAG,Article.DEL_FLAG_NORMAL));
		if (StringUtils.isBlank(page.getOrderBy())){
			dc.addOrder(Order.desc("weight"));
			dc.addOrder(Order.desc("publishDate"));
		}
		return articleDao.find(page, dc);
	}
	
	/**
	 * 根据uid获取文章 
	 * @param page
	 * @param uid
	 * @return
	 */
	public Boolean findByUId(String uid) {		
		DetachedCriteria dc = articleDao.createDetachedCriteria();
		dc.createAlias("category", "category");
		dc.createAlias("category.site", "category.site");		
		dc.add(Restrictions.eq("category.site.id", Site.getCurrentSiteId()));
		dc.add(Restrictions.eq("uid", uid));		
		dc.add(Restrictions.eq(Article.FIELD_DEL_FLAG,Article.DEL_FLAG_NORMAL));
		List<Article> list=articleDao.find(dc);
		if ((list!=null) && (list.size()>0)){
			return true;
		}else {
			return false;
		}
	}
	
	/**获取工作地点*/
	public List<String> getJobAddress(){
		
		return  articleDao.find("select distinct jobAddress from Article where delFlag=0 and category_id='2016'");
	}
	
	
	/**前台根据关键字、工作地点和时间排序分页查询招聘信息*/
	@Transactional(readOnly = false)
	public Page<Article> find(Page<Article> page,String keyword, String jobAddress,String order, boolean isDataScopeFilter){
		DetachedCriteria dc = articleDao.createDetachedCriteria();
		String categoryId= DictUtils.getDictValue("front.jobInformation", "comm_category_id","");
		dc.add(Restrictions.eq("category.id",categoryId));
		if (StringUtils.isNotEmpty(keyword)){
			dc.createAlias("articleData", "articleData");
			dc.add(Restrictions.or(Restrictions.like("company", "%"+keyword+"%"),
					//Restrictions.like("title", "%"+keyword+"%"),
					Restrictions.like("articleData.content", "%"+keyword+"%")));
		}
		if (StringUtils.isNotEmpty(jobAddress)){
			dc.add(Restrictions.eq("jobAddress", jobAddress));
		}
		if (StringUtils.isNotEmpty(order)){
			if (order.equals("asc")){
				dc.addOrder(Order.asc("publishDate"));
			}
			if (order.equals("desc")){
				dc.addOrder(Order.desc("publishDate"));
			}
		}
		dc.add(Restrictions.eq(Article.FIELD_DEL_FLAG,Article.DEL_FLAG_NORMAL));
		if (StringUtils.isBlank(page.getOrderBy())){
			dc.addOrder(Order.desc("weight"));
			dc.addOrder(Order.desc("publishDate"));
		}
		
		return articleDao.find(page,dc);
	}
	
	/**前台根据标题和内容查询专业负责人和师资状况*/
	@Transactional(readOnly = false)
	public Page<Article> findPersonnel(Page<Article> page, Article article, String keyword,boolean isDataScopeFilter){
		DetachedCriteria dc = articleDao.createDetachedCriteria();
		Category category = categoryDao.get(article.getCategory().getId());
		dc.add(Restrictions.eq("category.id",category.getId()));
		if (StringUtils.isNotEmpty(keyword)){
			dc.createAlias("articleData", "articleData");
			dc.add(Restrictions.or(
					Restrictions.like("title", "%"+keyword+"%"),
					Restrictions.like("articleData.content", "%"+keyword+"%")));
		}
		dc.add(Restrictions.eq(Article.FIELD_DEL_FLAG,Article.DEL_FLAG_NORMAL));
		if (StringUtils.isBlank(page.getOrderBy())){
			dc.addOrder(Order.desc("weight"));
			dc.addOrder(Order.desc("publishDate"));
		}
		return articleDao.find(page,dc);
	}
	
	/**前台根据标题和内容查询班级风采*/
	@Transactional(readOnly = false)
	public Page<Article> findClassStyle(Page<Article> page, Article article, String keyword,String gradeName,String className,String majorName,boolean isDataScopeFilter){
		DetachedCriteria dc = articleDao.createDetachedCriteria();
		Category category = categoryDao.get(article.getCategory().getId());
		dc.add(Restrictions.eq("category.id",category.getId()));
		if (StringUtils.isNotEmpty(keyword) && !keyword.equals("请输入关键字")){
			dc.createAlias("articleData", "articleData");
			dc.add(Restrictions.or(
					Restrictions.like("title", "%"+keyword+"%"),
					Restrictions.like("articleData.content", "%"+keyword+"%")));
		}
		if (StringUtils.isNotEmpty(gradeName) && !gradeName.equals("全部")){
			dc.createAlias("grade", "grade");
			dc.add(Restrictions.eq("grade.gradeName", gradeName));
		}
		if (StringUtils.isNotEmpty(className) && !className.equals("全部")){
			dc.createAlias("classes", "classes");
			dc.add(Restrictions.eq("classes.className", className));
		}
		if (StringUtils.isNotEmpty(majorName) && !majorName.equals("全部")){
			dc.createAlias("major", "major");
			dc.add(Restrictions.eq("major.majorName", majorName));
		}
		dc.add(Restrictions.eq(Article.FIELD_DEL_FLAG,Article.DEL_FLAG_NORMAL));
		if (StringUtils.isBlank(page.getOrderBy())){
			dc.addOrder(Order.desc("weight"));
			dc.addOrder(Order.desc("publishDate"));
		}
		return articleDao.find(page,dc);
	}
	
	/**手机站前台根据标题和内容查询班级风采*/
	@Transactional(readOnly = false)
	public Page<Article> findMobileClassStyle(Page<Article> page, Article article, String keyword,String gradeName,String majorName,boolean isDataScopeFilter){
		DetachedCriteria dc = articleDao.createDetachedCriteria();
		Category category = categoryDao.get(article.getCategory().getId());
		dc.add(Restrictions.eq("category.id",category.getId()));
		if (StringUtils.isNotEmpty(keyword) && !keyword.equals("请输入关键字")){
			dc.createAlias("articleData", "articleData");
			dc.add(Restrictions.or(
					Restrictions.like("title", "%"+keyword+"%"),
					Restrictions.like("articleData.content", "%"+keyword+"%")));
		}
		if (StringUtils.isNotEmpty(gradeName) && !gradeName.equals("请选择")){
			dc.createAlias("grade", "grade");
			dc.add(Restrictions.eq("grade.gradeName", gradeName));
		}
		if (StringUtils.isNotEmpty(majorName) && !majorName.equals("请选择")){
			dc.createAlias("major", "major");
			dc.add(Restrictions.eq("major.majorName", majorName));
		}
		dc.add(Restrictions.eq(Article.FIELD_DEL_FLAG,Article.DEL_FLAG_NORMAL));
		if (StringUtils.isBlank(page.getOrderBy())){
			dc.addOrder(Order.desc("weight"));
			dc.addOrder(Order.desc("publishDate"));
		}
		return articleDao.find(page,dc);
	}
	
	/***
	 * APP查询培养方案
	 * @param page
	 * @param article
	 * @param isDataScopeFilter
	 * @return
	 */
	@Transactional(readOnly = false)
	public Page<Article> findFrontForPenYang(Page<Article> page, Article article, boolean isDataScopeFilter) {
		// 更新过期的权重，间隔为“6”个小时
		Date updateExpiredWeightDate =  (Date)CacheUtils.get("updateExpiredWeightDateByArticle");
		if (updateExpiredWeightDate == null || (updateExpiredWeightDate != null 
				&& updateExpiredWeightDate.getTime() < new Date().getTime())){
			articleDao.updateExpiredWeight();
			CacheUtils.put("updateExpiredWeightDateByArticle", DateUtils.addHours(new Date(), 6));
		}
		DetachedCriteria dc = articleDao.createDetachedCriteria();
		dc.createAlias("category", "category");
		dc.createAlias("category.site", "category.site");
		dc.createAlias("articleData", "articleData");
		
		Category category = categoryDao.get(article.getCategory().getId());
		
		dc.add(Restrictions.eq("category.site.id", category.getSite().getId()));
		dc.add(Restrictions.like("category.parentIds", "%,"+category.getId()+",%"));
		dc.add(Restrictions.eq("category.appinMenu", category.getAppinMenu()));

		
		if(article.getIsRecommend()!=null){
			dc.add(Restrictions.eq("isRecommend", article.getIsRecommend()));
		}
		if (StringUtils.isNotEmpty(article.getPosid())){
			dc.add(Restrictions.like("posid", "%,"+article.getPosid()+",%"));
		}
		if (StringUtils.isNotEmpty(article.getImage())&&Article.YES.equals(article.getImage())){
			dc.add(Restrictions.and(Restrictions.isNotNull("image"),Restrictions.ne("image","")));
		}
		if (article.getCreateBy()!=null && StringUtils.isNotBlank(article.getCreateBy().getId())){
			dc.add(Restrictions.eq("createBy.id", article.getCreateBy().getId()));
		}
		dc.add(Restrictions.eq(Article.FIELD_DEL_FLAG, article.getDelFlag()));
		if (StringUtils.isBlank(page.getOrderBy())){
			dc.addOrder(Order.desc("weight"));
			dc.addOrder(Order.desc("publishDate"));
		}
		return articleDao.find(page, dc);
	}
	
}

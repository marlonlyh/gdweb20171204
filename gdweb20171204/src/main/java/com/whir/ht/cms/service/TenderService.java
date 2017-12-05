package com.whir.ht.cms.service;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.whir.ht.cms.dao.CategoryDao;
import com.whir.ht.cms.dao.TenderDao;
import com.whir.ht.cms.entity.Article;
import com.whir.ht.cms.entity.Category;
import com.whir.ht.cms.entity.Site;
import com.whir.ht.common.persistence.Page;
import com.whir.ht.common.service.BaseService;
import com.whir.ht.common.utils.StringUtils;
import com.whir.ht.sys.utils.UserUtils;

/**
 * Service 招标中心
 * @author chenshaofeng
 *
 */
@Service
@Transactional(readOnly = true)
public class TenderService extends BaseService {

	@Autowired
	private TenderDao tenderDao;
	@Autowired
	private CategoryDao categoryDao;
	
	public Article get(String id){
		return tenderDao.get(id);
	}
	
	public Page<Article> findPage(Page<Article> page, Article article){
		DetachedCriteria dc = tenderDao.createDetachedCriteria();
		dc.createAlias("category", "category");
		dc.createAlias("category.site", "category.site");
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
		
		dc.add(Restrictions.eq("isRecommend", article.getIsRecommend()));
		dc.add(Restrictions.eq(Article.FIELD_DEL_FLAG, article.getDelFlag()));
		dc.addOrder(Order.desc("updateDate"));
		return tenderDao.find(page, dc);
	}
	
	/**
	 * 分页查找招标/中标信息
	 *  
	 * @param page
	 * @param status(1:招标，2：中标)
	 * @return
	 */
	public Page<Article> findTender(Page<Article> page, Article article, boolean isDataScopeFilter) {
		DetachedCriteria dc = tenderDao.createDetachedCriteria();
		dc.createAlias("category", "category");
		dc.createAlias("category.site", "category.site");
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
		if (isDataScopeFilter){
			dc.createAlias("category.office", "categoryOffice").createAlias("createBy", "createBy");
			dc.add(dataScopeFilter(UserUtils.getUser(), "categoryOffice", "createBy"));
		}
		dc.add(Restrictions.eq(Article.FIELD_DEL_FLAG, article.getDelFlag()));
		if(article.getHits()>0){
			dc.addOrder(Order.desc("hits"));
		}else{
			if (StringUtils.isBlank(page.getOrderBy())){
				dc.addOrder(Order.desc("weight"));
				dc.addOrder(Order.desc("updateDate"));
			}
		}
		return tenderDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(Article resource){
		tenderDao.clear();
		tenderDao.save(resource);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		Article resource = tenderDao.get(id);
		if(resource!=null){
			tenderDao.deleteById(id);
		}
	}
}

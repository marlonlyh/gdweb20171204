package com.whir.ht.cms.service;

import java.util.List;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.whir.ht.cms.dao.ArticleImageDao;
import com.whir.ht.cms.entity.Article;
import com.whir.ht.cms.entity.ArticleImage;
import com.whir.ht.common.service.BaseService;

@Service
@Transactional(readOnly = true)
public class ArticleImageService extends BaseService {
	
	@Autowired
	private ArticleImageDao articleImageDao;
	
	
	public ArticleImage get(String id){
		return articleImageDao.get(id);
	}
	
	public List<ArticleImage> findList(Article article){
		DetachedCriteria dc = articleImageDao.createDetachedCriteria();
		dc.add(Restrictions.eq("article",article));
		dc.add(Restrictions.eq(ArticleImage.FIELD_DEL_FLAG, ArticleImage.DEL_FLAG_NORMAL));
		return articleImageDao.find(dc);
	}
	@Transactional(readOnly = false)
	public void delete(String id) {
		ArticleImage articleImage = articleImageDao.get(id);
		if(articleImage!=null){
			articleImageDao.deleteById(id);
		}
	}
	@Transactional(readOnly = false)
	public void deletep(String id) {
		ArticleImage articleImage = articleImageDao.get(id);
		if(articleImage!=null){
			articleImageDao.delete(id);
		}
	}
}

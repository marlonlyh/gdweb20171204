package com.whir.ht.cms.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.whir.ht.cms.dao.ProductDao;
import com.whir.ht.cms.dao.SpecificateDao;
import com.whir.ht.cms.entity.Product;
import com.whir.ht.cms.entity.ProductType;
import com.whir.ht.cms.entity.Specificate;
import com.whir.ht.common.persistence.Page;
import com.whir.ht.common.service.BaseService;


/**
 * Service 产品
 * @author wuxiaoyuan
 *
 */
@Service
@Transactional(readOnly = true)
public class ProductService extends BaseService {
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private SpecificateDao specificateDao;

	public Product get(String id){
		return productDao.get(id);
	}
	
	/**
	 * 分页查找所有产品
	 * 
	 * @param page
	 * @return
	 */
	public Page<Product> findProduct(Page<Product> page,Product product) {
		DetachedCriteria dc = productDao.createDetachedCriteria();
		dc.createAlias("type", "type");
		if (product.getType() != null && StringUtils.isNotEmpty(product.getType().getName())){
			dc.add(Restrictions.like("type.name", "%"+product.getType().getName()+"%"));
		}
		if (StringUtils.isNotEmpty(product.getName())){
			dc.add(Restrictions.like("name", "%"+product.getName()+"%"));
		}
		if (StringUtils.isNotEmpty(product.getModel())){
			dc.add(Restrictions.like("model", "%"+product.getModel()+"%"));
		}		
		dc.add(Restrictions.eq(Product.FIELD_DEL_FLAG, Product.DEL_FLAG_NORMAL));
		dc.addOrder(Order.asc("sort"));
		return productDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(Product product){
		productDao.clear();
		productDao.save(product);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		Product product = productDao.get(id);
		if(product!=null){
			productDao.deleteById(id);
		}
		for (Specificate specificate : product.getSpecificates()) {
			specificateDao.deleteById(specificate.getId());
		}
	}
	
	/**
	 * 查找所有产品
	 * 
	 * @param page
	 * @return
	 */
	public Page<Product> findAll(Page<Product> page) {
		DetachedCriteria dc = productDao.createDetachedCriteria();
		dc.add(Restrictions.eq(Product.FIELD_DEL_FLAG, Product.DEL_FLAG_NORMAL));
		return productDao.find(page,dc);
	}
	
	/**
	 * 查找产品分类下的所有产品
	 * 
	 * @param page
	 * @return
	 */
	public Page<Product> findPage(Page<Product> page,ProductType productType) {
		DetachedCriteria dc = productDao.createDetachedCriteria();
		dc.createAlias("type", "p");
		// dc.createAlias("product.type", "p");
		dc.add(Restrictions.or(
				Restrictions.eq("p.id", productType.getId()),
				Restrictions.like("p.parentIds", "%,"+productType.getId()+",%")));
		dc.add(Restrictions.eq(Specificate.FIELD_DEL_FLAG, Specificate.DEL_FLAG_NORMAL));
		return productDao.find(page, dc);
	}
	
	/**
	 * 分页查找所有产品
	 * 
	 * @param page
	 * @return
	 */
	public Page<Product> findPage(Page<Product> page,Product product) {
		DetachedCriteria dc = productDao.createDetachedCriteria();
		dc.createAlias("type", "type");
		
		if (StringUtils.isNotEmpty(product.getName())){
			dc.add(Restrictions.like("name", "%"+product.getName()+"%"));
		}
		if (StringUtils.isNotEmpty(product.getModel())){
			dc.add(Restrictions.like("model", "%"+product.getModel()+"%"));
		}
		
		if (product.getType() != null){
		dc.add(Restrictions.or(
				Restrictions.eq("type.id", product.getType().getId()),
				Restrictions.like("type.parentIds", "%,"+product.getType().getId()+",%")));
		}
		
		// 电压
		if (StringUtils.isNotEmpty(product.getVoltage())){
			dc.add(Restrictions.like("voltage", "%" + product.getVoltage() + "%"));
		}
		
		// 标准号
		if (StringUtils.isNotEmpty(product.getStandard())){
			dc.add(Restrictions.like("standard", "%" + product.getStandard() + "%"));
		}
		
		dc.add(Restrictions.eq(Product.FIELD_DEL_FLAG, Product.DEL_FLAG_NORMAL));
		dc.addOrder(Order.asc("sort"));
		return productDao.find(page, dc);
	}
	
	
}

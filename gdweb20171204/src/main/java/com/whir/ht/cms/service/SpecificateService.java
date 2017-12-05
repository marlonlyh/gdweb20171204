package com.whir.ht.cms.service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.whir.ht.cms.dao.ProductDao;
import com.whir.ht.cms.dao.SpecificateDao;
import com.whir.ht.cms.dao.SpecificatePriceDao;
import com.whir.ht.cms.entity.Product;
import com.whir.ht.cms.entity.ProductType;
import com.whir.ht.cms.entity.Specificate;
import com.whir.ht.cms.entity.SpecificatePrice;
import com.whir.ht.common.persistence.Page;
import com.whir.ht.common.service.BaseService;
import com.whir.ht.common.utils.IdGen;
import com.whir.ht.common.utils.StringUtils;
import com.whir.ht.sys.entity.User;
import com.whir.ht.sys.utils.UserUtils;

/**
 * Service 产品规格表
 * @author wuxiaoyuan
 *
 */
@Service
@Transactional(readOnly = true)
public class SpecificateService extends BaseService {
	
	@Autowired
	private SpecificateDao specificateDao;
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private SpecificatePriceDao specificatePriceDao;
	
	public Specificate get(String id){
		return specificateDao.get(id);
	}
	
	/**
	 * 分页查找所有产品规格
	 * 
	 * @param page
	 * @return
	 */
	public Page<Specificate> findProduct(Page<Specificate> page,Specificate specificate) {
		DetachedCriteria dc = specificateDao.createDetachedCriteria();
		dc.createAlias("product", "product");
		if (specificate.getProduct() != null && StringUtils.isNotEmpty(specificate.getProduct().getName())){
			dc.add(Restrictions.like("product.name", "%"+specificate.getProduct().getName()+"%"));
		}
		if (specificate.getProduct() != null && StringUtils.isNotEmpty(specificate.getProduct().getModel())){
			dc.add(Restrictions.like("product.model", "%"+specificate.getProduct().getModel()+"%"));
		}
		if (specificate.getProduct() != null && StringUtils.isNotEmpty(specificate.getProduct().getVoltage())){
			dc.add(Restrictions.like("product.voltage", "%"+specificate.getProduct().getVoltage()+"%"));
		}
		if (StringUtils.isNotEmpty(specificate.getName())){
			dc.add(Restrictions.like("name", "%"+specificate.getName()+"%"));
		}
		dc.add(Restrictions.eq(Specificate.FIELD_DEL_FLAG, Specificate.DEL_FLAG_NORMAL));
		dc.addOrder(Order.asc("sort"));
		dc.addOrder(Order.desc("createDate"));
		return specificateDao.find(page, dc);
	}
	
	/**
	 * 查找所有产品规格
	 * @return
	 */
	public List<Product> findAll(){
		DetachedCriteria dc = productDao.createDetachedCriteria();
		dc.add(Restrictions.eq(Product.FIELD_DEL_FLAG, Product.DEL_FLAG_NORMAL));
		dc.addOrder(Order.asc("name"));
		return productDao.find(dc);
	}
	@Transactional(readOnly = false)
	public void save(Specificate specificate){
		//删除原材料为空的记录
		for (Iterator<SpecificatePrice> iterator = specificate.getPrices().iterator(); iterator.hasNext();) {
			SpecificatePrice specificatePrice = iterator.next();
			
			if (specificatePrice == null || StringUtils.isBlank(specificatePrice.getResource().getId()) ||  specificatePrice.getFlag().equals("2")) {
				if (specificatePrice.getId()!=null && specificatePrice.getFlag().equals("2")){
					specificatePriceDao.clear();
					specificatePriceDao.deleteById(specificatePrice.getId());
				}
				iterator.remove();
				continue;
			}
		}
		
		User user = UserUtils.getUser();
		Date updateDate = new Date();
		
		//当修改时，判断是否存在删除记录
		if (StringUtils.isNotBlank(specificate.getId())){
			Specificate pSpecificate=specificateDao.get(specificate.getId());	
			for (SpecificatePrice specificatePrice : pSpecificate.getPrices()) {
					for (SpecificatePrice specificatePrice2 : specificate.getPrices()) {
						if (specificatePrice2.getId()==specificatePrice.getId()){
							//更改存在信息
							if (StringUtils.isNotBlank(user.getId())){
								specificatePrice.setUpdateBy(user);
							}			    			
							specificatePrice.setUpdateDate(updateDate);
							specificatePrice.setCreateDate(specificatePrice.getCreateDate());
							specificatePrice.setDelFlag(specificatePrice.getDelFlag());
							break;
						}
				}
			}					
		}
		
		
		for (SpecificatePrice specificatePrice : specificate.getPrices()) {	
			if (StringUtils.isBlank(specificatePrice.getId())) {
				//添加新增信息
				if (StringUtils.isNotBlank(user.getId())){
					specificatePrice.setUpdateBy(user);
					specificatePrice.setCreateBy(user);
				}		
				specificatePrice.setCreateDate(updateDate);
				specificatePrice.setUpdateDate(updateDate);
				specificatePrice.setId(IdGen.uuid());
				specificatePrice.setSpecificate(specificate);		
			}
		}
		
		specificateDao.clear();
		specificateDao.save(specificate);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		Specificate specificate = specificateDao.get(id);
		if(specificate!=null){
			specificateDao.deleteById(id);
		}
	}
	
	/**
	 * 查找所有产品
	 * 
	 * @param page
	 * @return
	 */
	public List<Specificate> findList(Product product) {
		DetachedCriteria dc = specificateDao.createDetachedCriteria();
		dc.createAlias("product", "product");
		dc.add(Restrictions.eq("product.id", product.getId()));
		dc.add(Restrictions.eq(Specificate.FIELD_DEL_FLAG, Specificate.DEL_FLAG_NORMAL));
		return specificateDao.find(dc);
	}
	
	/**
	 * 查找产品分类下的所有产品
	 * 
	 * @param page
	 * @return
	 */
	public Page<Specificate> findPage(Page<Specificate> page,ProductType productType) {
		DetachedCriteria dc = specificateDao.createDetachedCriteria();
		dc.createAlias("product", "product");
		dc.createAlias("product.type", "p");
		dc.add(Restrictions.or(
				Restrictions.eq("p.id", productType.getId()),
				Restrictions.like("p.parentIds", "%,"+productType.getId()+",%")));
		dc.add(Restrictions.eq(Specificate.FIELD_DEL_FLAG, Specificate.DEL_FLAG_NORMAL));
		dc.add(Restrictions.eq("isRecommend", Specificate.YES));
		return specificateDao.find(page, dc);
	}
	
	/**
	 *根据产品名称查找相近的产品规格
	 * 
	 * @param page
	 * @return
	 */
	public Page<Specificate> findNameList(Page<Specificate> page,String name,Specificate specificate) {
		DetachedCriteria dc = specificateDao.createDetachedCriteria();
		dc.createAlias("product", "product");		
		dc.add(Restrictions.eq("product.name",name));
		dc.add(Restrictions.ne("id", specificate.getId()));
		dc.add(Restrictions.eq(Specificate.FIELD_DEL_FLAG, Specificate.DEL_FLAG_NORMAL));
		dc.addOrder(Order.asc("sort"));
		return specificateDao.find(page, dc);
	}
	
	/**
	 * 根据产品规格查询
	 * @param id
	 * @param name
	 * @return
	 */
	public Specificate getSpecificate(String id,String name) {
		return specificateDao.findSpecificate(id,name);
	}

}

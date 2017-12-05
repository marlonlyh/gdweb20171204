package com.whir.ht.cms.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.whir.ht.cms.dao.ProductTypeDao;
import com.whir.ht.cms.entity.ProductType;
import com.whir.ht.common.persistence.Page;
import com.whir.ht.common.service.BaseService;

/**
 * Service 产品分类
 * @author wuxiaoyuan
 *
 */
@Service
@Transactional(readOnly = true)
public class ProductTypeService extends BaseService {
	
	@Autowired
	private ProductTypeDao productTypeTDao;
	
	public ProductType get(String id){
		return productTypeTDao.get(id);
	}

	public List<ProductType> findByUser(boolean isCurrentSite, String module){
		
		List<ProductType> list = new ArrayList<ProductType>() ;
		
			
			DetachedCriteria dc = productTypeTDao.createDetachedCriteria();	
			dc.add(Restrictions.eq("delFlag", ProductType.DEL_FLAG_NORMAL));
			dc.addOrder(Order.asc("sort"));
			list = productTypeTDao.find(dc);
			// 将没有父节点的节点，找到父节点
//			Set<String> parentIdSet = Sets.newHashSet();
//			for (ProductType e : list){
//			
//				if (e.getParent()!=null && StringUtils.isNotBlank(e.getParent().getId())){
//					boolean isExistParent = false;
//					for (ProductType e2 : list){
//					
//						if (e.getParent().getId().equals(e2.getId())){
//							isExistParent = true;
//							break;
//						}
//					}
//					if (!isExistParent){
//						parentIdSet.add(e.getParent().getId());
//					}
//				}
//			}
//			if (parentIdSet.size() > 0){
//				dc = productTypeTDao.createDetachedCriteria();
//				dc.add(Restrictions.in("id", parentIdSet));
//				dc.add(Restrictions.eq("delFlag", ProductType.DEL_FLAG_NORMAL));
//				dc.addOrder(Order.asc("sort"));
//				list.addAll(0, productTypeTDao.find(dc));
//			}
			
	
		
//		if (isCurrentSite){
//			List<ProductType> productTypeList = Lists.newArrayList(); 
//			for (ProductType e : list){
//			
//				if (ProductType.isRoot(e.getId()) ){
//					
//					productTypeList.add(e);
//					
//				}
//			}
//			return productTypeList;
//		}
		return list;
	}
	/**
	 * 查找所有产品分类
	 * 
	 * @param page
	 * @return
	 */
	public List<ProductType> findProductType(Page<ProductType> page) {
		DetachedCriteria dc = productTypeTDao.createDetachedCriteria();
		dc.add(Restrictions.eq(ProductType.FIELD_DEL_FLAG, ProductType.DEL_FLAG_NORMAL));
		return productTypeTDao.find(dc);
	}
	
	/**
	 * 查找所有产品分类
	 * 
	 * @param page
	 * @return
	 */
	public List<ProductType> findProductType() {
		DetachedCriteria dc = productTypeTDao.createDetachedCriteria();
		dc.add(Restrictions.eq(ProductType.FIELD_DEL_FLAG, ProductType.DEL_FLAG_NORMAL));
		return productTypeTDao.find(dc);
	}
	
	public List<ProductType> findparent(){
		DetachedCriteria dc = productTypeTDao.createDetachedCriteria();
		dc.add(Restrictions.isNull("parent"));
		dc.add(Restrictions.eq(ProductType.FIELD_DEL_FLAG, ProductType.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("sort"));
		return productTypeTDao.find(dc);
	}
	
	@Transactional(readOnly = false)
	public void save(ProductType productType) {
	
		productType.setParent(this.get(productType.getParent().getId()));
		String oldParentIds = productType.getParentIds(); // 获取修改前的parentIds，用于更新子节点的parentIds
		productType.setParentIds(productType.getParent().getParentIds()+productType.getParent().getId()+",");
		productType.setGrade(productType.getParentIds().split(",").length);//层级
		productTypeTDao.clear();
		productTypeTDao.save(productType);
		// 更新子节点 parentIds
		List<ProductType> list = productTypeTDao.findByParentIdsLike("%,"+productType.getId()+",%");
		for (ProductType e : list){
			e.setParentIds(e.getParentIds().replace(oldParentIds, productType.getParentIds()));
		}
		productTypeTDao.save(list);
		
		
	}
	
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		ProductType productType = productTypeTDao.get(id);
		if(productType!=null){
			productTypeTDao.deleteById(id, "%,"+id+",%");
		}
	}
	
	
	public List<ProductType> findRootList(){
		DetachedCriteria dc = productTypeTDao.createDetachedCriteria();
		dc.add(Restrictions.eq("grade", 1));
		dc.add(Restrictions.eq("parent", productTypeTDao.get("1")));
		dc.add(Restrictions.eq(ProductType.FIELD_DEL_FLAG, ProductType.DEL_FLAG_NORMAL));
		dc.addOrder(Order.asc("sort"));
		return productTypeTDao.find(dc);
	}
}

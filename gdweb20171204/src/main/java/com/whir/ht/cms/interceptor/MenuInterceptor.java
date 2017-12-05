/*
 * Copyright 2005-2013 ezmarket.com. All rights reserved.
 * Support: http://www.ezmarket.com
 * License: http://www.ezmarket.com/license
 */
package com.whir.ht.cms.interceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.whir.ht.cms.entity.Category;
import com.whir.ht.cms.service.CategoryService;
import com.whir.ht.cms.utils.IpAddrUtil;
import com.whir.ht.sys.utils.DictUtils;

/**
 * Interceptor -前台菜单权限
 * 
 * @author 万户网络
 * @version 1.0
 */
public class MenuInterceptor extends HandlerInterceptorAdapter {

	/** 默认菜单URL */
	private static final String DEFAULT_MENU_URL = "/";

	/** menuURL */
	private String menuUrl = DEFAULT_MENU_URL;

	@Value("${url_escaping_charset}")
	private String urlEscapingCharset;

	@Autowired
	private CategoryService categoryService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//String ip= "14.145.36.58";
		String ip= request.getRemoteAddr();
      String url= request.getServletPath();
      int length= url.length();
      String categateId="";
      if(url.startsWith("/list")){
    	  categateId=url.substring(6,length-5);
      }
      if(url.startsWith("/view")){
    	  String[] st=url.split("-");
    	  categateId=st[1];
      }
      Boolean bool=IpAddrUtil.isLAN(ip);
      Category category =categoryService.get(categateId); 
		if (bool == true) {
			return true;
		} else {
			 if(category.getIds().contains(DictUtils.getDictValue("front.teachingResources", "comm_category_id","")) || category.getIds().contains(DictUtils.getDictValue("front.teachingInteraction", "comm_category_id",""))){
				response.sendRedirect(request.getContextPath());
				return false;
			}else {
		    	return true;
			}
		}
	}

	/**
	 * 获取菜单URL
	 * 
	 * @return 菜单URL
	 */
	public String getMenuUrl() {
		return menuUrl;
	}

	/**
	 * 设置菜单URL
	 * @param menuUrl
	 */
	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

}
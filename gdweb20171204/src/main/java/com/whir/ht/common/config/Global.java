/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/whir/ht">ezSite</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.whir.ht.common.config;

import java.util.Map;

import org.springframework.util.Assert;

import com.google.common.collect.Maps;
import com.whir.ht.common.utils.PropertiesLoader;

/**
 * 全局配置类
 * @author Elvin
 * @version 2013-03-23
 */
public class Global {
	
	/**
	 * 保存全局属性值
	 */
	private static Map<String, String> map = Maps.newHashMap();
	
	/**
	 * 属性文件加载对象
	 */
	private static PropertiesLoader propertiesLoader = new PropertiesLoader("cms.properties");
	
	/**
	 * 获取配置
	 */
	public static String getConfig(String key) {
		String value = map.get(key);
		if (value == null){
			value = propertiesLoader.getProperty(key);
			map.put(key, value);
		}
		return value;
	}

	/////////////////////////////////////////////////////////
	
	/**
	 * 获取管理端根路径
	 */
	public static String getAdminPath() {
		return getConfig("adminPath");
	}
	
	/**
	 * 获取前端根路径
	 */
	public static String getFrontPath() {
		return getConfig("frontPath");
	}
	
	/**
	 * 获取URL后缀
	 */
	public static String getUrlSuffix() {
		return getConfig("urlSuffix");
	}
	
	/**
	 * 是否是演示模式，演示模式下不能修改用户、角色、密码、菜单、授权
	 */
	public static Boolean isDemoMode() {
		String dm = getConfig("demoMode");
		return "true".equals(dm) || "1".equals(dm);
	}

	/**
	 * 获取CKFinder上传文件的根目录
	 * @return
	 */
	public static String getCkBaseDir() {
		String dir = getConfig("userfiles.basedir");
		Assert.hasText(dir, "配置文件里没有配置userfiles.basedir属性");
		if(!dir.endsWith("/")) {
			dir += "/";
		}
		return dir;
	}
	
	/**
	 * 获取最新公告ID，用于首页展示
	 * @return
	 */
	public static String getIndexAdvice(){
		return getConfig("index.advice");
	}
	
	/**
	 * 获取动态新闻ID，用于首页展示
	 * @return
	 */
	public static String getIndexNews(){
		return getConfig("index.news");
	}
	
	/**
	 * 获取轮播图片ID，用于首页展示
	 * @return
	 */
	public static String getIndexBana(){
		return getConfig("index.bana");
	}
	
	/**
	 * 获取业务介绍ID，用于首页展示
	 * @return
	 */
	public static String getIndexBusisness(){
		return getConfig("index.busisness");
	}
	
	/**
	 * 获取网站首页
	 */
	public static String getWebIndex() {
		String temp=getConfig("web.index");
		if (temp==""){
			return "";
		}else {
			return temp+getConfig("urlSuffix");
		}
	}

}
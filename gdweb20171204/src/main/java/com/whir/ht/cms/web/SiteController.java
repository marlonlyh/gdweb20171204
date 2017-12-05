/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/whir/ht">ezSite</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.whir.ht.cms.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.whir.ht.cms.entity.Site;
import com.whir.ht.cms.service.SiteService;
import com.whir.ht.common.config.Global;
import com.whir.ht.common.persistence.Page;
import com.whir.ht.common.utils.CookieUtils;
import com.whir.ht.common.utils.StringUtils;
import com.whir.ht.common.web.BaseController;
import com.whir.ht.sys.utils.UserUtils;

/**
 * 站点Controller
 * @author Elvin
 * @version 2013-3-23
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/site")
public class SiteController extends BaseController {

	@Autowired
	private SiteService siteService;
	
	@ModelAttribute
	public Site get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return siteService.get(id);
		}else{
			return new Site();
		}
	}
	
	@RequiresPermissions("cms:site:view")
	@RequestMapping(value = {"list", ""})
	public String list(Site site, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Site> page = siteService.find(new Page<Site>(request, response), site); 
        model.addAttribute("page", page);
		return "cms/siteList";
	}

	@RequiresPermissions("cms:site:view")
	@RequestMapping(value = "form")
	public String form(Site site, Model model) {
		//默认上传附件文件类型
		String filestr = "rar,zip,doc,docx";
	   String[] arr = filestr.split(",");
	   Map<String, String> maps = new HashMap<String, String>();
	   maps.put("rar", "application/octet-stream");
	   maps.put("zip", "application/zip");
	   maps.put("doc", "application/msword");
	   maps.put("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
	   
	   String tmp = "";
	   for(String ss:arr){
		  if (maps.containsKey(ss)){
			  tmp = tmp+maps.get(ss)+",";
		  }
	   }
	   
	   //默认上传图片类型
	   String imageStr = "gif,jpg,png,bmp,jpeg";
	   String[] imageStrs = imageStr.split(",");
	   Map<String,String> imageMaps = new HashMap<String,String>();
	   imageMaps.put("gif", "image/gif");
	   imageMaps.put("jpg", "image/jpeg");
	   imageMaps.put("png", "image/png");
	   imageMaps.put("bmp", "image/bmp");
	   imageMaps.put("jpeg", "image/jpeg");
	   
	   String tmpImage = "";
	   for(String aa:imageStrs){
		   if(imageMaps.containsKey(aa)){
			   tmpImage = tmpImage+imageMaps.get(aa)+",";
		   }
	   }

		model.addAttribute("filestr", filestr);
		model.addAttribute("tmp", tmp);
		model.addAttribute("imageStr", imageStr);
		model.addAttribute("tmpImage", tmpImage);
		model.addAttribute("site", site);
		return "cms/siteForm";
	}

	@RequiresPermissions("cms:site:edit")
	@RequestMapping(value = "save")
	public String save(Site site,String uploadFileFormat,String uploadImageFormat,Model model, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:"+Global.getAdminPath()+"/cms/site/?repage";
		}
		if (!beanValidator(model, site)){
			return form(site, model);
		}
		   String[] arr = uploadFileFormat.split(",");
		   Map<String, String> maps = new HashMap<String, String>();
		   maps.put("gif", "image/gif");
		   maps.put("jpg", "image/jpeg");
		   maps.put("png", "image/png");
		   maps.put("bmp", "image/bmp");
		   maps.put("jpeg", "image/jpeg");
		   maps.put("ico", "image/x-icon");
		   maps.put("rar", "application/octet-stream");
		   maps.put("zip", "application/zip");
		   maps.put("7z", "application/octet-stream");
		   maps.put("doc", "application/msword");
		   maps.put("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
		   maps.put("txt", "text/plain");
		   maps.put("xls", "application/vnd.ms-excel");
		   maps.put("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		   maps.put("ppt", "application/vnd.ms-powerpoint");
		   maps.put("pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation");
		   maps.put("pdf", "application/pdf");
		   maps.put("mp3", "audio/mpeg");
		   maps.put("mp4", "video/mp4");
		   maps.put("asf", "allpication/vnd.ms-asf");
		   maps.put("au", "audio/basic");
		   maps.put("flv", "video/x-flv");
		   
		   String tmp="";
		   String tmpFile = "";
		   for(String ss:arr){
			   if(maps.containsKey(ss)){
				   tmp=tmp+ss+",";
				   tmpFile=tmpFile+maps.get(ss)+",";
			   }
		   }
		   
		  if (StringUtils.isNotBlank(tmp)){
			   tmp=tmp.substring(0,tmp.length()-1); 
		   }
		  
		  if (StringUtils.isNotBlank(tmpFile)){
			  tmpFile=tmpFile.substring(0,tmpFile.length()-1); 
		   }
		  site.setUploadFileFormat(tmp);
		  site.setConvertFileFormat(tmpFile);
		  	   
		   String[] imageStrs = uploadImageFormat.split(",");
		   Map<String,String> imageMaps = new HashMap<String,String>();
		   imageMaps.put("gif", "image/gif");
		   imageMaps.put("jpg", "image/jpeg");
		   imageMaps.put("png", "image/png");
		   imageMaps.put("bmp", "image/bmp");
		   imageMaps.put("jpeg", "image/jpeg");
		   imageMaps.put("tif", "image/tiff");
		   imageMaps.put("tiff", "image/tiff");
		   imageMaps.put("svg", "image/svg+xml");
		   imageMaps.put("rgb", "image/x-rgb");
		   imageMaps.put("ras", "image/x-cmu-raster");
		   imageMaps.put("ppm", "image/x-portable-pixmap");
		   imageMaps.put("pbm", "image/x-portable-bitmap");
		   imageMaps.put("ief", "image/ief");
		   
		   tmp="";
		   String tmpImage = "";	   
		   for(String ss:imageStrs){
			   if(imageMaps.containsKey(ss)){
				   tmp=tmp+ss+",";
				   tmpImage=tmpImage+imageMaps.get(ss)+",";
			   }
		   }
		   
		   if (StringUtils.isNotBlank(tmp)){
			   tmp=tmp.substring(0,tmp.length()-1); 
		   }
		  
		  if (StringUtils.isNotBlank(tmpImage)){
			  tmpImage=tmpImage.substring(0,tmpImage.length()-1); 
		   }
		  site.setUploadImageFormat(tmp);
		  site.setConvertImageFormat(tmpImage);
		  siteService.save(site);   
		return "redirect:"+Global.getAdminPath()+"/cms/site/?repage";
	}
	
	@RequiresPermissions("cms:site:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, @RequestParam(required=false) Boolean isRe, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:"+Global.getAdminPath()+"/cms/site/?repage";
		}
		if (Site.isDefault(id)){
			addMessage(redirectAttributes, "删除站点失败, 不允许删除默认站点");
		}else{
			siteService.delete(id, isRe);
			addMessage(redirectAttributes, (isRe!=null&&isRe?"恢复":"")+"删除站点成功");
		}
		return "redirect:"+Global.getAdminPath()+"/cms/site/?repage";
	}
	
	/**
	 * 选择站点
	 * @param id
	 * @return
	 */
	@RequiresPermissions("cms:site:select")
	@RequestMapping(value = "select")
	public String select(String id, boolean flag, HttpServletResponse response){
		if (id!=null){
			UserUtils.putCache("siteId", id);
			// 保存到Cookie中，下次登录后自动切换到该站点
			CookieUtils.setCookie(response, "siteId", id);
		}
		if (flag){
			return "redirect:"+Global.getAdminPath();
		}
		return "cms/siteSelect";
	}
}

/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/whir/ht">ezSite</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.whir.ht.webservice.client;

import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.whir.ht.cms.entity.Site;
import com.whir.ht.cms.service.CaptchaService;
import com.whir.ht.cms.utils.CmsUtils;
import com.whir.ht.common.mapper.JsonMapper;
import com.whir.ht.common.utils.OpUtils;
import com.whir.ht.common.utils.StringUtils;
import com.whir.ht.common.web.BaseController;
import com.whir.ht.sys.utils.DictUtils;

/**
 * 盛辉接口配送价格接口和长途时效价格接口 
 * @author Elvin
 * @version 2013-3-15
 */
@Controller
@RequestMapping(value = "${frontPath}/work")
public class FrontWorkInterfaceController extends BaseController{


	@Autowired
	private CaptchaService captchaService;
	
	@RequestMapping(value = "freecount", method=RequestMethod.GET)
	public String getFreecount(Model model) {
		Site site = CmsUtils.getSite(Site.defaultSiteId());
		model.addAttribute("site", site);
		return "cms/front/themes/"+site.getTheme()+"/frontCount";
	}
	
	/**
	 * 配送价格接口
	 * 
	 */
	@RequestMapping(value = "freecount", method=RequestMethod.POST)
	@ResponseBody
	public String postFreecount(@RequestParam(required=true) String vehicleno, Model model) {
		
		Site site = CmsUtils.getSite(Site.defaultSiteId());
		model.addAttribute("site", site);
	
		String serverUrl = DictUtils.getDictValue("work_url", "gjsf_interface", "http://59.56.179.50:7112/work");
		/**
		 * 业务数据参数未加码部分
		 */
		Map<String,String> undataMap = Maps.newLinkedHashMap();
		try {
			if(StringUtils.isNotEmpty(vehicleno)){
				undataMap.put("vehicleno", vehicleno);			
			}
		} catch (Exception e) {
			logger.error("urlencode error:{}",e);
		}
		/**
		 * 业务数据参数部分
		 */
		Map<String,String> dataMap = Maps.newLinkedHashMap();
		try {
			if(StringUtils.isNotEmpty(vehicleno)){
				dataMap.put("vehicleno", URLEncoder.encode(vehicleno,"utf-8"));			
			}
		} catch (Exception e) {
			logger.error("urlencode error:{}",e);
		}
		
		
		/**
		 *  系统参数部分
		 */
		Long timestamp	= System.currentTimeMillis();
		
	   String method="com.work.freecount";
	   String app_key=DictUtils.getDictValue("Appkey", "gjsf_interface", "");
	   String data=JsonMapper.toJsonString(dataMap);
	   String app_secret=DictUtils.getDictValue("SKEY", "gjsf_interface", "");
	   String result="";
		
		try {		
			data=JsonMapper.toJsonString(dataMap);			
			CloseableHttpClient hc = HttpClients.createDefault();
			HttpPost hr = new HttpPost(serverUrl);
			//hr.setConfig(config);
			//hr.setHeader(name, value);
			List<NameValuePair> nvps = Lists.newArrayList();
			nvps.add(new BasicNameValuePair("action",method));
			nvps.add(new BasicNameValuePair("appkey",app_key));
			nvps.add(new BasicNameValuePair("t",String.valueOf(timestamp)));
			nvps.add(new BasicNameValuePair("key",OpUtils.md5WorkSign(JsonMapper.toJsonString(undataMap),app_key,timestamp,app_secret)));
			nvps.add(new BasicNameValuePair("data",data));
			nvps.add(new BasicNameValuePair("v","1.0"));
			nvps.add(new BasicNameValuePair("p","C"));
			hr.setEntity(new UrlEncodedFormEntity(nvps));		
			CloseableHttpResponse chr = hc.execute(hr);
			HttpEntity respEntity = chr.getEntity();			
			result = EntityUtils.toString(respEntity);			
			EntityUtils.consume(respEntity);
		
		} catch (Exception e) {
			logger.error("getvehiclegpsinfos error:{}",e);
			result="{\"Data\":{\"RESULT\":\"False\",\"RESULT_INFO\":\""+e.getMessage()+"\"}}";
		}
		
		return result;	
	}
	@RequestMapping(value = "check_longfreecount", method=RequestMethod.POST)	
	public @ResponseBody String checkLongfreecount(String startCompany,String descCompany, String sendDate,String weight,String volume){
		Map<String,String> dataMap = Maps.newLinkedHashMap();
		if(startCompany != null && descCompany != null&&weight!=null&&volume!=null){
			dataMap.put("type", "success");
			dataMap.put("content", "验证通过");
		}else{
			dataMap.put("type", "error");
			dataMap.put("content", "参数错误");
		}
		return JsonMapper.toJsonString(dataMap);
	}
	
	/**
	 * 长途标准价时效接口
	 * 
	 */
	@RequestMapping(value = "longfreecount", method=RequestMethod.POST)
	@ResponseBody
	public String postLongFreecount(@RequestParam(required=true) String startCompany,@RequestParam(required=true) String descCompany,@RequestParam(required=false) String sendDate,@RequestParam(required=true) String weight,@RequestParam(required=true) String volume, Model model) {
		
		Site site = CmsUtils.getSite(Site.defaultSiteId());
		model.addAttribute("site", site);
	
		String serverUrl = DictUtils.getDictValue("work_url", "gjsf_interface", "http://59.56.179.50:7112/work");
		/**
		 * 业务数据参数未加码部分
		 */
		Map<String,String> undataMap = Maps.newLinkedHashMap();
		try {
			if(StringUtils.isNotEmpty(startCompany)){
				undataMap.put("StartCompany",startCompany);
			}
			if(StringUtils.isNotEmpty(descCompany)){
				undataMap.put("DescCompany",descCompany);
			}
//			if(sendDate!=null){
//				undataMap.put("SendDate",sendDate);
//			}
			if(StringUtils.isNotEmpty(weight)){
				undataMap.put("Weight",weight);
			}
			if(StringUtils.isNotEmpty(volume)){
				undataMap.put("Volume",volume);
			}
		} catch (Exception e) {
			logger.error("urlencode error:{}",e);
		}	
		
		/**
		 * 业务数据参数部分
		 */
		Map<String,String> dataMap = Maps.newLinkedHashMap();
		try {
			if(StringUtils.isNotEmpty(startCompany)){
				dataMap.put("StartCompany", URLEncoder.encode(startCompany,"utf-8"));
			}
			if(StringUtils.isNotEmpty(descCompany)){
				dataMap.put("DescCompany", URLEncoder.encode(descCompany,"utf-8"));
			}
//			if(sendDate!=null){
//				dataMap.put("SendDate", URLEncoder.encode(sendDate,"utf-8"));
//			}
			if(StringUtils.isNotEmpty(weight)){
				dataMap.put("Weight", URLEncoder.encode(weight,"utf-8"));
			}
			if(StringUtils.isNotEmpty(volume)){
				dataMap.put("Volume", URLEncoder.encode(volume,"utf-8"));
			}
		} catch (Exception e) {
			logger.error("urlencode error:{}",e);
		}		
		
		/**
		 *  系统参数部分
		 */
		Long timestamp	= System.currentTimeMillis();	
	   String method="com.work.longfreecount";
	   String app_key=DictUtils.getDictValue("Appkey", "gjsf_interface", "");
	   String data=JsonMapper.toJsonString(dataMap);
	   String app_secret=DictUtils.getDictValue("SKEY", "gjsf_interface", "");	   
	   String result="";
		try {			
			data=JsonMapper.toJsonString(dataMap);			
			CloseableHttpClient hc = HttpClients.createDefault();
			//http post方式
			HttpPost hr = new HttpPost(serverUrl);
			//hr.setConfig(config);
			//hr.setHeader(name, value);
			List<NameValuePair> nvps = Lists.newArrayList();
			nvps.add(new BasicNameValuePair("action",method));
			nvps.add(new BasicNameValuePair("appkey",app_key));
			nvps.add(new BasicNameValuePair("t",String.valueOf(timestamp)));
			nvps.add(new BasicNameValuePair("key",OpUtils.md5WorkSign(JsonMapper.toJsonString(undataMap),app_key,timestamp,app_secret)));
			nvps.add(new BasicNameValuePair("data",data));
			nvps.add(new BasicNameValuePair("v","1.0"));
			nvps.add(new BasicNameValuePair("p","C"));
			hr.setEntity(new UrlEncodedFormEntity(nvps));			
			CloseableHttpResponse chr = hc.execute(hr);
			HttpEntity respEntity = chr.getEntity();			
			result = EntityUtils.toString(respEntity);			
			EntityUtils.consume(respEntity);	
		
		} catch (Exception e) {
			logger.error("getvehiclegpsinfos error:{}",e);
			result="{\"Data\":{\"RESULT\":\"False\",\"RESULT_INFO\":\""+e.getMessage()+"\"}}";
		}
		//result="{\"PRICE\":\"100.5\",\"PICKUP\":\"2015-03-15\",\"TRANSPORT_ARRIVE_TIME\":\"2015-03-18\",\"DISPATCH_TIME\":\"2015-03-17\"}";
		return result;		
	}
	
}
/*
 * Copyright 2005-2013 ezworks.com. All rights reserved.
 * Support: http://www.ezworks.com
 * License: http://www.ezworks.com/license
 */
package com.whir.ht.cms;

import java.util.Map;

import com.google.common.collect.Maps;
import com.whir.ht.cms.utils.SpringUtils;
import com.whir.ht.common.mapper.JsonMapper;


/**
 * 消息
 * 
 * @author 万户网络
 * @version 1.0
 */
public class Message {

	/**
	 * 类型
	 */
	public enum Type {

		/** 成功 */
		success,

		/** 警告 */
		warn,

		/** 错误 */
		error
	}

	/** 类型 */
	private Type type;

	/** 内容 */
	private String content;

	/**
	 * 初始化一个新创建的 Message 对象，使其表示一个空消息。
	 */
	public Message() {

	}

	/**
	 * 初始化一个新创建的 Message 对象
	 * 
	 * @param type
	 *            类型
	 * @param content
	 *            内容
	 */
	public Message(Type type, String content) {
		this.type = type;
		this.content = content;
	}

	/**
	 * @param type
	 *            类型
	 * @param content
	 *            内容
	 * @param args
	 *            参数
	 */
	public Message(Type type, String content, Object... args) {
		this.type = type;
		this.content = SpringUtils.getMessage(content, args);
	}

	/**
	 * 返回成功消息
	 * 
	 * @param content
	 *            内容
	 * @param args
	 *            参数
	 * @return 成功消息
	 */
	public static String success(String content, Object... args) {
		Map<String,String> dataMap = Maps.newLinkedHashMap();
		dataMap.put("type", "success");
		dataMap.put("content", content);
		return JsonMapper.toJsonString(dataMap);
	}

	/**
	 * 返回警告消息
	 * 
	 * @param content
	 *            内容
	 * @param args
	 *            参数
	 * @return 警告消息
	 */
	public static String warn(String content, Object... args) {
		Map<String,String> dataMap = Maps.newLinkedHashMap();
		dataMap.put("type", "warn");
		dataMap.put("content", content);
		return JsonMapper.toJsonString(dataMap);
	}

	/**
	 * 返回错误消息
	 * 
	 * @param content
	 *            内容
	 * @param args
	 *            参数
	 * @return 错误消息
	 */
	public static String error(String content, Object... args) {
		Map<String,String> dataMap = Maps.newLinkedHashMap();
		dataMap.put("type", "error");
		dataMap.put("content", content);
		return JsonMapper.toJsonString(dataMap);
	}

	/**
	 * 获取类型
	 * 
	 * @return 类型
	 */
	public Type getType() {
		return type;
	}

	/**
	 * 设置类型
	 * 
	 * @param type
	 *            类型
	 */
	public void setType(Type type) {
		this.type = type;
	}

	/**
	 * 获取内容
	 * 
	 * @return 内容
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 设置内容
	 * 
	 * @param content
	 *            内容
	 */
	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return SpringUtils.getMessage(content);
	}

}
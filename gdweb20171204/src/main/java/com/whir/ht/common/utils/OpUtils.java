package com.whir.ht.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.*;
/**
 * 开放式平台工具
 * @author muiacekeen
 *
 */
public class OpUtils {

    private static final Logger logger = LoggerFactory.getLogger(OpUtils.class);

    /**
     * 使用<code>secret</code>对paramValues按以下算法进行签名： <br/>
     * uppercase(hex(sha1(secretkey1value1key2value2...secret))
     *
     * @param paramValues 参数列表
     * @param secret
     * @return
     */
    public static String sha1sign(Map<String, String> paramValues, String secret) {
        return sha1sign(paramValues,null,secret);
    }

    /**
     * 对paramValues进行签名，其中ignoreParamNames这些参数不参与签名
     * @param paramValues
     * @param ignoreParamNames
     * @param secret
     * @return
     */
    public static String sha1sign(Map<String, String> paramValues, List<String> ignoreParamNames,String secret) {
        try {
            StringBuilder sb = new StringBuilder();
            List<String> paramNames = new ArrayList<String>(paramValues.size());
            paramNames.addAll(paramValues.keySet());
            if(ignoreParamNames != null && ignoreParamNames.size() > 0){
                for (String ignoreParamName : ignoreParamNames) {
                    paramNames.remove(ignoreParamName);
                }
            }
            Collections.sort(paramNames);

            sb.append(secret);
            for (String paramName : paramNames) {
                sb.append(paramName).append(paramValues.get(paramName));
            }
            sb.append(secret);
            byte[] sha1Digest = getSHA1Digest(sb.toString());
            return byte2hex(sha1Digest);
        } catch (IOException e) {
            //throw new Exception(e);
        	logger.error("{}",e);
        	return null;
        }
    }
    
    /**
     * 使用<code>secret</code>对paramValues按以下算法进行签名： <br/>
     * uppercase(hex(sha1(secretkey1value1key2value2...secret))
     *
     * @param paramValues 参数列表
     * @param secret
     * @return
     */
    public static String md5sign(Map<String, String> paramValues, String secret) {
        return md5Sign(paramValues,null,secret);
    }
    
    /**
     * 对paramValues进行签名，其中ignoreParamNames这些参数不参与签名
     * @param paramValues
     * @param ignoreParamNames
     * @param secret
     * @return
     */
    public static String md5Sign(Map<String, String> paramValues, List<String> ignoreParamNames,String secret) {
        try {
            StringBuilder sb = new StringBuilder();
            List<String> paramNames = new ArrayList<String>(paramValues.size());
            paramNames.addAll(paramValues.keySet());
            if(ignoreParamNames != null && ignoreParamNames.size() > 0){
                for (String ignoreParamName : ignoreParamNames) {
                    paramNames.remove(ignoreParamName);
                }
            }
            Collections.sort(paramNames);

            sb.append(secret);
            for (String paramName : paramNames) {
                sb.append(paramName).append(paramValues.get(paramName));
            }
            sb.append(secret);
            byte[] md5Digest = getMD5Digest(sb.toString());
            
            return byte2hex(md5Digest);
        } catch (IOException e) {
            //throw new Exception(e);
        	logger.error("{}",e);
        	return null;
        }
    }
    
    /**
     * 对paramValues进行签名，其中ignoreParamNames这些参数不参与签名
     * @param paramValues
     * @param ignoreParamNames
     * @param secret
     * @return
     */
    public static String md5WorkSign(String data, String appkey,Long t,String safekey) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(data);
            sb.append(appkey);
            sb.append(t);
            sb.append(safekey);
            System.out.println(sb.toString());
            byte[] md5Digest = getMD5Digest(sb.toString());
            
            return byte2hexcase(md5Digest);
        } catch (IOException e) {
            //throw new Exception(e);
        	logger.error("{}",e);
        	return null;
        }
    }

    public static String utf8Encoding(String value, String sourceCharsetName) {
        try {
            return new String(value.getBytes(sourceCharsetName), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private static byte[] getSHA1Digest(String data) throws IOException {
        byte[] bytes = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            bytes = md.digest(data.getBytes("UTF-8"));
        } catch (GeneralSecurityException gse) {
            throw new IOException(gse.getMessage());
        }
        return bytes;
    }

    private static byte[] getMD5Digest(String data) throws IOException {
        byte[] bytes = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            bytes = md.digest(data.getBytes("UTF-8"));
        } catch (GeneralSecurityException gse) {
            throw new IOException(gse.getMessage());
        }
        return bytes;
    }

    /**
     * 二进制转十六进制字符串
     *
     * @param bytes
     * @return
     */
    private static String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex.toUpperCase());
        }
        return sign.toString();
    }
    
    private static String byte2hexcase(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex);
        }
        return sign.toString();
    }

    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().toUpperCase();
    }

    public final static void main(String[] args) {
//    	Map<String,String> params = Maps.newLinkedHashMap();
//    	params.put("method", "com.whir.ht56.getbranchinfos");
//    	params.put("timestamp", "2014-09-17 16:42:05");
//    	params.put("data", "{\"province\":\"闽AB3921\"}");
//    	params.put("app_key", "sh_key");
//    	System.out.println(md5Sign(params,null,"033E68A22E7C006AE05364646401006A"));
    	//String data = "%7B%22Weight%22%3A%221111%22%2C%22StartCompany%22%3A%2200034%22%2C%22DescCompany%22%3A%2200003%22%2C%22Volume%22%3A%2222%22%7D";
    	String data = "{\"Weight\":\"1111\",\"StartCompany\":\"00034\",\"DescCompany\":\"00003\",\"Volume\":\"22\"}";
    	Long t	= System.currentTimeMillis();
    	
    	String appkey="10FE30F0CC40007EE05364646401007E";
    	String skey = "10FE2BA559110082E053646464010082";
    	System.out.println(md5WorkSign(data,appkey,t,skey));//8abb...96ae
    }
}
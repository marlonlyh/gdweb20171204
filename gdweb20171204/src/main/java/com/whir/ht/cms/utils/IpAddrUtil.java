package com.whir.ht.cms.utils;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

public class IpAddrUtil {
	public static final String ALLOWABLE_IP_REGEX = "(127[.]0[.]0[.]1)|"
			+ "(localhost)|" + "(10[.]\\d{1,3}[.]\\d{1,3}[.]\\d{1,3})|"
			+ "(172[.]((1[6-9])|(2\\d)|(3[01]))[.]\\d{1,3}[.]\\d{1,3})|"
			+ "(192[.]168[.]\\d{1,3}[.]\\d{1,3})";
	public static boolean isLAN(final String str) {
        String regEx= ALLOWABLE_IP_REGEX ;   //表示a或f 
        Pattern p=Pattern.compile(regEx);
        java.util.regex.Matcher m=p.matcher(str);
        boolean result =m.find();
        //logger.debug(">>>>>>>>>>>>>>>>> 是否为局域网ip 结果:" + result);
		return result ;
	}
	
	public static String getIpAddr(final HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip) || isLAN(ip))
        	ip = request.getHeader("X-Forwarded-For");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)||isLAN(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip) ||isLAN(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip) ||isLAN(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip.equals("0:0:0:0:0:0:0:1")) {
			ip = "127.0.0.1";
		}
        return ip;
    }
}

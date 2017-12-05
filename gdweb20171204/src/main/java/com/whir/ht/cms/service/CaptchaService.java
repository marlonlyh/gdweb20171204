package com.whir.ht.cms.service;

import java.awt.image.BufferedImage;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.whir.ht.common.service.BaseService;

/**
 * Service - 验证码
 * 
 * @author 万户网络
 * @version 1.0
 */
@Service
@Transactional(readOnly = true)
public class CaptchaService extends BaseService {
	
	@Resource(name = "imageCaptchaService")
	private com.octo.captcha.service.CaptchaService imageCaptchaService;

	public BufferedImage buildImage(String captchaId) {
		return (BufferedImage) imageCaptchaService.getChallengeForID(captchaId);
	}

	public boolean isValid(String captchaId, String captcha) {
		if (StringUtils.isNotEmpty(captchaId) && StringUtils.isNotEmpty(captcha)) {
			try {
				return imageCaptchaService.validateResponseForID(captchaId, captcha.toUpperCase());
			} catch (Exception e) {
				return false;
			}
		} else {
			return false;
		}
	}
}

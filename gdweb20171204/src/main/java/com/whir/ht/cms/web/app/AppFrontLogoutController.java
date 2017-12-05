package com.whir.ht.cms.web.app;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whir.ht.cms.web.app.model.CustomResponse;
import com.whir.ht.common.web.BaseController;

@Controller
@RequestMapping(value="${frontPath}/app")
public class AppFrontLogoutController extends BaseController{

	/**
	 * 注销
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	@ResponseBody
	public CustomResponse execute(HttpServletRequest request, HttpServletResponse response, HttpSession session,String uid,String sid) {
		CustomResponse result = new CustomResponse();
		result.setCode(0);
		result.setMsg("success");
		return result;
	}
}

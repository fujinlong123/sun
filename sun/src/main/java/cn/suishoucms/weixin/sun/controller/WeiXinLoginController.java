package cn.suishoucms.weixin.sun.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.suishoucms.weixin.sun.service.ConfigService;
import cn.suishoucms.weixin.sun.service.WeiXinLoginService;
import cn.suishoucms.weixin.sun.utils.Result;
import cn.suishoucms.weixin.sun.utils.SessionIdUtils;

@Controller
@RequestMapping("weixin")
public class WeiXinLoginController {

	@Resource
	private ConfigService configService;
	
	@Resource
	private WeiXinLoginService weiXinLoginService;

	

	@RequestMapping("onLogin")
	@ResponseBody
	public Object onLogin(String code,HttpServletRequest request) {
		
		return weiXinLoginService.onLogin(configService.getAppId(), configService.getAppSecret(), code,"sun");
	
	}
	
	@RequestMapping("onLogin2")
	@ResponseBody
	public Object onLogin2(String code,HttpServletRequest request) {
		
		return weiXinLoginService.onLogin(configService.getAppId2(), configService.getAppSecret2(), code,"sun2");
	
	}

	
	@RequestMapping("noLogin")
	@ResponseBody
	public Object noLogin(HttpServletRequest request){
		Result result= Result.fail("未登录","noLogin");
		result.setSessionId(SessionIdUtils.getSessionId());
		return result;
	}
}

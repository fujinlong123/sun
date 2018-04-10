package cn.suishoucms.weixin.sun.controller;

import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.mysql.cj.api.Session;

import cn.suishoucms.weixin.sun.model.WeiXinLoginInfo;
import cn.suishoucms.weixin.sun.service.ConfigService;
import cn.suishoucms.weixin.sun.service.InitService;
import cn.suishoucms.weixin.sun.service.SessionService;
import cn.suishoucms.weixin.sun.service.WeiXinLoginService;
import cn.suishoucms.weixin.sun.sharesession.ShareSession;
import cn.suishoucms.weixin.sun.utils.HttpUtils;
import cn.suishoucms.weixin.sun.utils.Result;
import cn.suishoucms.weixin.sun.utils.SessionIdUtils;
import cn.suishoucms.weixin.sun.utils.StringResponse;

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
		
		return weiXinLoginService.onLogin(configService.getAppId(), configService.getAppSecret(), code);
	
	}
	
	@RequestMapping("onLogin2")
	@ResponseBody
	public Object onLogin2(String code,HttpServletRequest request) {
		
		return weiXinLoginService.onLogin(configService.getAppId2(), configService.getAppSecret2(), code);
	
	}

	
	@RequestMapping("noLogin")
	@ResponseBody
	public Object noLogin(HttpServletRequest request){
		Result result= Result.fail("未登录","noLogin");
		result.setSessionId(SessionIdUtils.getSessionId());
		return result;
	}
}

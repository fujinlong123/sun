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
	private SessionService sessionService;
	@Resource
	private InitService initService;
	

	@RequestMapping("onLogin")
	@ResponseBody
	public Object onLogin(String code,HttpServletRequest request) {
		String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + configService.getAppId() + "&secret="
				+ configService.getAppSecret() + "&js_code=" + code + "&grant_type=authorization_code";
		try {
			StringResponse sr = HttpUtils.get(url);
			JSONObject jsonObject=JSON.parseObject(sr.getResponseBody());
			if(!jsonObject.containsKey("errcode")){
				String sessionId=SessionIdUtils.getNewSessionId();
				SessionIdUtils.setSessionId(sessionId);
				ShareSession shareSession=sessionService.getShareSession();
				shareSession.setWeiXinLoginInfo(sr.getResponseBody());
				WeiXinLoginInfo weiXinLoginInfo=shareSession.getWeiXinLoginInfo();
				initService.init(weiXinLoginInfo.getOpenid());
				Result result=Result.success("登录成功");
				result.setSessionId(sessionId);
				
				return result;
			}else{
				Result result=Result.fail("登录失败");
				return result;
			}
		
		} catch (Exception e) {
			e.printStackTrace();
			Result result=Result.fail("服务器异常");
			return result;
		}
	}

	
	@RequestMapping("noLogin")
	@ResponseBody
	public Object noLogin(HttpServletRequest request){
		Result result= Result.fail("未登录","noLogin");
		result.setSessionId(SessionIdUtils.getSessionId());
		return result;
	}
}

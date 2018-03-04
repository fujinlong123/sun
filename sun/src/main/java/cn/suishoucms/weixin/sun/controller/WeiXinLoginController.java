package cn.suishoucms.weixin.sun.controller;

import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.mysql.cj.api.Session;

import cn.suishoucms.weixin.sun.service.ConfigService;
import cn.suishoucms.weixin.sun.service.SessionService;
import cn.suishoucms.weixin.sun.sharesession.ShareSession;
import cn.suishoucms.weixin.sun.utils.HttpUtils;
import cn.suishoucms.weixin.sun.utils.Result;
import cn.suishoucms.weixin.sun.utils.StringResponse;

@Controller
@RequestMapping("weixin")
public class WeiXinLoginController {

	@Resource
	private ConfigService configService;
	
	@Resource
	private SessionService sessionService;

	@RequestMapping("onLogin")
	@ResponseBody
	public Object onLogin(String code,HttpServletRequest request) {
		String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + configService.getAppId() + "&secret="
				+ configService.getAppSecret() + "&js_code=" + code + "&grant_type=authorization_code";
		try {
			StringResponse sr = HttpUtils.get(url);
			System.out.println(sr.getResponseBody());
			JSONObject jsonObject=JSON.parseObject(sr.getResponseBody());
			if(!jsonObject.containsKey("errcode")){
				String sessionId=UUID.randomUUID().toString().replace("-", "");
				request.setAttribute("sessionId", sessionId);
				ShareSession shareSession=sessionService.getShareSession();
				shareSession.setWeiXinLoginInfo(sr.getResponseBody());
				Result result=Result.success("登录成功");
				result.data("sessionId", sessionId);
				return result;
			}else{
				Result result=Result.fail("登录失败");
				return result;
			}
		
		} catch (Exception e) {
			Result result=Result.fail("服务器异常");
			return result;
		}
	}

}

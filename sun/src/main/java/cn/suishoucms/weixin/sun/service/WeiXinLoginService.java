package cn.suishoucms.weixin.sun.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.suishoucms.weixin.sun.model.WeiXinLoginInfo;
import cn.suishoucms.weixin.sun.sharesession.ShareSession;
import cn.suishoucms.weixin.sun.utils.HttpUtils;
import cn.suishoucms.weixin.sun.utils.Result;
import cn.suishoucms.weixin.sun.utils.SessionIdUtils;
import cn.suishoucms.weixin.sun.utils.StringResponse;

@Service
public class WeiXinLoginService {
	@Resource
	private SessionService sessionService;
	@Resource
	private InitService initService;
	public Result onLogin(String appid,String appSecret,String code ){
		String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appid + "&secret="
				+ appSecret + "&js_code=" + code + "&grant_type=authorization_code";
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
}

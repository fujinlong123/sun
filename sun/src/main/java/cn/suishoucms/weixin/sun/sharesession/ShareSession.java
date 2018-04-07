package cn.suishoucms.weixin.sun.sharesession;

import com.alibaba.fastjson.JSON;

import cn.suishoucms.weixin.sun.model.WeiXinLoginInfo;
import cn.suishoucms.weixin.sun.service.SessionService;

public class ShareSession {
	private SessionService sessionService;

	private String sessionId;

	public ShareSession(String sessionId, SessionService sessionService) {
		super();
		this.sessionId = sessionId;
		this.sessionService = sessionService;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public <T> T getAttribute(String key,Class<T> clazz,boolean fromDb) {
		String value=sessionService.get(sessionId, key);
		if(clazz==String.class){
			return (T)value;
		}else{
			return JSON.parseObject(value, clazz);
		}
	}

	public void setAttribute(String key, Object value) {
		if (value == null) {
			sessionService.del(sessionId, key);
			return;
		}
		if (value instanceof String) {
			sessionService.put(sessionId, key, (String)value);
		}else{
			sessionService.put(sessionId, key, JSON.toJSONString(value));
		}

	}
	
	
	
	public void setWeiXinLoginInfo(String text){
		setAttribute("weiXinLoginInfo", text);
	}
	public WeiXinLoginInfo getWeiXinLoginInfo(){
		return getWeiXinLoginInfo(false);
	}
	
	public WeiXinLoginInfo getWeiXinLoginInfo(boolean fromDb){
		return  getAttribute("weiXinLoginInfo", WeiXinLoginInfo.class, fromDb);
	}

}

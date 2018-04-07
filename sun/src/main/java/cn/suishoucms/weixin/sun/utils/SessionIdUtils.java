package cn.suishoucms.weixin.sun.utils;

import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import cn.suishoucms.weixin.sun.sharesession.ShareSession;

public class SessionIdUtils {
	
	private static final ThreadLocal<String> sessionIdThreadLocal=new ThreadLocal<>();
	
	public static String getSessionId(){
		String sessionId=sessionIdThreadLocal.get();
		if(StringUtils.isBlank(sessionId)){
			sessionId=getNewSessionId();
		}
		return sessionId;
	}
	
	public static String getNewSessionId(){
		return UUID.randomUUID().toString();
	}
	
	public static void setSessionId(String sessionId){
		sessionIdThreadLocal.set(sessionId);
	}

}

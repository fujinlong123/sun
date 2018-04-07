package cn.suishoucms.weixin.sun.service;

import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import cn.suishoucms.weixin.sun.entity.Session;
import cn.suishoucms.weixin.sun.mapper.SessionMapper;
import cn.suishoucms.weixin.sun.sharesession.ShareSession;
import cn.suishoucms.weixin.sun.utils.SessionIdUtils;

@Service
public class SessionService {

	@Resource
	private SessionMapper sessionMapper;
	
	private  LoadingCache<String, ShareSession> cache = CacheBuilder.newBuilder().maximumSize(100000).refreshAfterWrite(1, TimeUnit.DAYS)
			.build(new CacheLoader<String, ShareSession>(){
				@Override
				public ShareSession load(String key) throws Exception {	
					return new ShareSession(key, SessionService.this);
				}

	});
	
	



	/**
	 * 获取共享sessionId,必须登录之后才能使用
	 * 
	 * @return
	 * @throws ExecutionException 
	 */
	public ShareSession getShareSession() throws ExecutionException {
		String sessionId=SessionIdUtils.getSessionId();
		return cache.get(sessionId);
	}

	public int put(String sessionId, String key, String value) {
		Session session = sessionMapper.selectBy(sessionId, key);
		if (session == null) {
			session = new Session();
			session.setInsertTime(new Date());
			session.setUpdateTime(new Date());
			session.setKey(key);
			session.setSessionId(sessionId);
			session.setValue(value);
			return sessionMapper.insert(session);
		} else {
			session.setValue(value);
			session.setUpdateTime(new Date());
			return sessionMapper.updateByPrimaryKey(session);
		}

	}
	
	public int put(String sessionId, String key, Object value) {
	return	put( sessionId,  key,JSON.toJSONString(value));

	}

	public String get(String sessionId, String key) {
		Session session = sessionMapper.selectBy(sessionId, key);
		if (session != null) {
			return session.getValue();
		}
		return null;

	}
	public int del(String sessionId, String key) {
	return sessionMapper.del(sessionId, key);

	}

}

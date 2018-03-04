package cn.suishoucms.weixin.sun.service;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.suishoucms.weixin.sun.entity.Session;
import cn.suishoucms.weixin.sun.mapper.SessionMapper;
import cn.suishoucms.weixin.sun.sharesession.ShareSession;

@Service
public class SessionService {

	@Resource
	private SessionMapper sessionMapper;

	private ThreadLocal<ShareSession> ShareSessionThreadLocal = new ThreadLocal<ShareSession>() {
		@Override
		protected ShareSession initialValue() {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
					.getRequest();
			String sessionId = request.getParameter("sessionId");
			if (StringUtils.isEmpty(sessionId)) {
				sessionId = (String) request.getAttribute("sessionId");
			}
			if (StringUtils.isEmpty(sessionId)) {
				throw new RuntimeException("sessionId为空，请登录之后在使用ShareSession");
			}
			return new ShareSession(sessionId, SessionService.this);
		};
	};

	/**
	 * 获取共享sessionId,必须登录之后才能使用
	 * 
	 * @return
	 */
	public ShareSession getShareSession() {
		return ShareSessionThreadLocal.get();
	}

	public int put(String sessionId, String key, String value) {
		Session session = sessionMapper.selectBy(sessionId, key);
		if (session == null) {
			session = new Session();
			session.setInsertTime(new Date());
			session.setKey(key);
			session.setSessionId(sessionId);
			session.setValue(value);
			return sessionMapper.insert(session);
		} else {
			session.setValue(value);
			return sessionMapper.updateByPrimaryKey(session);
		}

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

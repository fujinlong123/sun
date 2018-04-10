package cn.suishoucms.weixin.sun.utils;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import cn.suishoucms.weixin.sun.service.SessionService;
import cn.suishoucms.weixin.sun.sharesession.ShareSession;

public class LoginFilter implements Filter {

	private SessionService sessionService = SpringUtil.getBean(SessionService.class);
	private Set<String> noLoginPath = new HashSet<String>();

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String contextPath = ((HttpServletRequest) request).getContextPath();
		String path = ((HttpServletRequest) request).getRequestURI().replace(contextPath, "");

		SessionIdUtils.setSessionId(request.getParameter("sessionId"));

		if (!noLoginPath.contains(path)) {
			try {
				ShareSession shareSession = sessionService.getShareSession();
				if (shareSession.getWeiXinLoginInfo(true) == null) {
					request.getRequestDispatcher("weixin/noLogin").forward(request, response);
				} else {
					chain.doFilter(request, response);
				}
			} catch (ExecutionException e) {
				throw new ServletException(e);
			}

		} else {
			chain.doFilter(request, response);
		}

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		noLoginPath.add("/weixin/onLogin");
		noLoginPath.add("/weixin/onLogin2");

	}

}

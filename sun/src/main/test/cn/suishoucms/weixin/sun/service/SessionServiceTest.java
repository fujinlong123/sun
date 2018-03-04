package cn.suishoucms.weixin.sun.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;

import cn.suishoucms.weixin.sun.model.WeiXinLoginInfo;
import cn.suishoucms.weixin.sun.sharesession.ShareSession;
import common.BaseTest;

public class SessionServiceTest extends BaseTest {

	@Resource
	SessionService sessionService;
	
	@Test
	public void test() throws InterruptedException{
		long s=System.currentTimeMillis();
		((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
		.getRequest().setAttribute("sessionId", "390ac68599de4c71bf9f6f7048cc6c59");
		ShareSession shareSession=sessionService.getShareSession();
		shareSession.setAttribute("fujinlong", "kokoko");
		shareSession.setAttribute("fujinlong", "kokoko2");
		shareSession.setAttribute("fujinlong1", "kokoko");
		shareSession.setAttribute("fujinlong1", null);
		Map<String, Object> map=new HashMap<>();
		map.put("xx", "dd");
		shareSession.setAttribute("fujinlong2", map);
		map =shareSession.getAttribute("fujinlong2", Map.class);
		System.out.println(map.get("xx"));
		WeiXinLoginInfo weiXinLoginInfo=shareSession.getWeiXinLoginInfo();
		System.out.println(JSON.toJSONString(weiXinLoginInfo));
	
	}
	
}

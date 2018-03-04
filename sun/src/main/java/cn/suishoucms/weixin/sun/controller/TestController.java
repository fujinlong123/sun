package cn.suishoucms.weixin.sun.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {
	
	
	
	@RequestMapping("ok")
	@ResponseBody
	public Object ok(){
		Map<String, Object> map=new HashMap<>();
		map.put("ret", "ok");
		map.put("date", new Date());
		map.put("foo", null);
		return map;
	}

}

package cn.suishoucms.weixin.sun.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.http.client.MockClientHttpRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import common.BaseTest;
import junit.framework.Assert;

public class WeiXinLoginControllerTest extends BaseTest {

	
	private MockMvc mockMvc;
	 
	  @Before
	  public void setUp() {
	    WeiXinLoginController weiXinLoginController = new WeiXinLoginController();
	    mockMvc = MockMvcBuilders.standaloneSetup(weiXinLoginController).build();
	  }
	@Test
	public void noLogin(){
		try {
		      MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/weixin/noLogin"))
		                .andExpect(MockMvcResultMatchers.status().is(200))
		                .andDo(MockMvcResultHandlers.print())
		                .andReturn();
		      int status = mvcResult.getResponse().getStatus();
		      System.out.println("请求状态码：" + status);
		      String result = mvcResult.getResponse().getContentAsString();
		      System.out.println("接口返回结果：" + result);
		      JSONObject resultObj = JSON.parseObject(result);
		     
		    } catch (Exception e) {
		      e.printStackTrace();
		    }
	}
	
}

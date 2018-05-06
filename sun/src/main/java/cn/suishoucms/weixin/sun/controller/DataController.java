package cn.suishoucms.weixin.sun.controller;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.suishoucms.weixin.sun.entity.Store;
import cn.suishoucms.weixin.sun.entity.StoreUser;
import cn.suishoucms.weixin.sun.entity.User;
import cn.suishoucms.weixin.sun.mapper.StoreMapper;
import cn.suishoucms.weixin.sun.mapper.StoreUserMapper;
import cn.suishoucms.weixin.sun.mapper.UserMapper;
import cn.suishoucms.weixin.sun.model.WeiXinLoginInfo;
import cn.suishoucms.weixin.sun.service.SessionService;
import cn.suishoucms.weixin.sun.sharesession.ShareSession;
import cn.suishoucms.weixin.sun.utils.Result;

@Controller
public class DataController {

	@Resource
	private SessionService sessionService;
	@Resource
	private UserMapper userMapper;
	@Resource
	private StoreUserMapper storeUserMapper;

	@Resource
	private StoreMapper storeMapper;
	
	
	@RequestMapping("loadSessionData")
	@ResponseBody
	public Result loadSessionData() throws ExecutionException {

		ShareSession shareSession = sessionService.getShareSession();
		WeiXinLoginInfo weiXinLoginInfo = shareSession.getWeiXinLoginInfo();
		User user = userMapper.selectByOpenid(weiXinLoginInfo.getOpenid());
		StoreUser storeUser = storeUserMapper.selectCurrentStoreUser(user.getId());
		Store store=storeMapper.selectByPrimaryKey(storeUser.getStoreId());
		Result result=Result.success("加载成功");
		result.data("storeId", storeUser.getStoreId());
		result.data("storeName", store.getName());
		return result;
		
		
		

	}

}

package cn.suishoucms.weixin.sun.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.suishoucms.weixin.sun.entity.Store;
import cn.suishoucms.weixin.sun.entity.StoreUser;
import cn.suishoucms.weixin.sun.entity.User;
import cn.suishoucms.weixin.sun.mapper.StoreMapper;
import cn.suishoucms.weixin.sun.mapper.StoreUserMapper;
import cn.suishoucms.weixin.sun.mapper.UserMapper;

@Service
public class InitService {
	
	@Resource
	private UserMapper userMapper;
	@Resource
	private StoreUserMapper storeUserMapper;
	@Resource
	private StoreMapper storeMapper;
	
	public void init(String openid){
		User user=userMapper.selectByOpenid(openid);
		if(user==null){
			user=new User();
			user.setOpenid(openid);
			user.setInsertTime(new Date());
			user.setUpdateTime(new Date());
			userMapper.insertSelective(user);
		}
		
	List<StoreUser> storeUserList=	storeUserMapper.selectOwnerStoreUser(user.getId());
	if(storeUserList.isEmpty()){
		Store store=new Store();
		store.setName("你的店铺名称");
		storeMapper.insertSelective(store);
		StoreUser storeUser=new StoreUser();
		storeUser.setFire(0);
		storeUser.setRole("owner");
		storeUser.setSelected(1);
		storeUser.setStoreId(store.getId());
		storeUser.setUserId(user.getId());
		storeUserMapper.insertSelective(storeUser);
	}
	
	
		
	}
	
	
}

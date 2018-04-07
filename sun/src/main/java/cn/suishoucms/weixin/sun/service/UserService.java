package cn.suishoucms.weixin.sun.service;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.suishoucms.weixin.sun.entity.User;
import cn.suishoucms.weixin.sun.mapper.UserMapper;

@Service
public class UserService {

	@Resource
	private UserMapper userMapper;
	
	public int insertOrUpdate(User user){
		User user2= userMapper.selectByOpenid(user.getOpenid());
		user.setUpdateTime(new Date());
		if(user2!=null){
			user.setId(user2.getId());
			return userMapper.updateByPrimaryKeySelective(user);
		}else{
			user.setInsertTime(new Date());
			return userMapper.insertSelective(user);
		}
	}
	
	
	
	
	
	
	
	
}

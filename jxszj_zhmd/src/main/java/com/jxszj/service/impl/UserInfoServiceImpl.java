package com.jxszj.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jxszj.mapper.UserinfoMapper;
import com.jxszj.pojo.Userinfo;
import com.jxszj.pojo.UserinfoExample;
import com.jxszj.service.IUserInfoService;

@Service
public class UserInfoServiceImpl implements IUserInfoService {

	@Autowired
	public UserinfoMapper userinfoMapper;
	
	@Override
	public int updateUser(Userinfo record) {
		int i=0;
		try {
			i=userinfoMapper.updateByPrimaryKey(record);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}

	@Override
	public List<Userinfo> findUser(UserinfoExample example) {
		List<Userinfo> user=null;
		try {
			user=userinfoMapper.selectByExample(example);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

}

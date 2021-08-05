package com.jxszj.service;

import java.util.List;

import com.jxszj.pojo.Userinfo;
import com.jxszj.pojo.UserinfoExample;


public interface IUserInfoService {

	
	public int updateUser(Userinfo record);
	
	public List<Userinfo> findUser(UserinfoExample example);
}

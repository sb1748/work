package com.jxszj.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.jxszj.pojo.Userinfo;
import com.jxszj.pojo.UserinfoExample;
import com.jxszj.pojo.UserinfoExample.Criteria;
import com.jxszj.service.IUserInfoService;

@Controller
public class LoginController {
	
	@Autowired
	private IUserInfoService userInfoService;
	
	@RequestMapping("/sign")
	public ModelAndView login(Userinfo userinfo){
		try {
			if(userinfo.getName()==null || "".equals(userinfo.getName())){
				Map<String, String> map = new HashMap<String,String>();
		        map.put("msg", "用户名或密码不能为空");
		        return new ModelAndView("login", map);
			}
			UserinfoExample example=new UserinfoExample();
			Criteria criteria=example.createCriteria();
			criteria.andNameEqualTo(userinfo.getName());
			criteria.andPwdEqualTo(userinfo.getPwd());
			List<Userinfo> user=userInfoService.findUser(example);
			if(user.size()>0){
				Map<String, String> map = new HashMap<String,String>();
		        map.put("name", userinfo.getName());
		        return new ModelAndView("main",map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, String> map = new HashMap<String,String>();
        map.put("msg", "密码错误");
        return new ModelAndView("login", map);
	}
	
}

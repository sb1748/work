package com.jxszj.controller.sap;


import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jxszj.pojo.EUDataGridResult;
import com.jxszj.pojo.sap.DailyuserTb;
import com.jxszj.service.sap.IDailyUserService;
import com.jxszj.utils.MessageResult;


@Controller
public class DailyUserController {

	@Autowired
	private IDailyUserService dailyUserService;
	
	@RequestMapping("/getDailyUserList")
	@ResponseBody
	public EUDataGridResult getDailyUserList(Integer page, Integer rows){
		EUDataGridResult result =dailyUserService.getDailyUser(page, rows);
		return result;
	}
	
	
	@RequestMapping("/addDailyuserTb")
	@ResponseBody
	public MessageResult addDailyuserTb(DailyuserTb dailyuserTb){
		try {
			int i=dailyUserService.addDailyUser(dailyuserTb);
			if(i>0){
				return MessageResult.build(200,"添加成功！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MessageResult.build(null,"添加失败！");
	}
	
	@RequestMapping("/deleteDailyuserTb")
	@ResponseBody
	public MessageResult deleteDailyuserTb(String[] ids){
		try {
			int i=dailyUserService.deleteDailyUser(Arrays.asList(ids));
			if(i>0){
				return MessageResult.build(200,"删除成功！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MessageResult.build(null,"删除失败！");
	}
	
}

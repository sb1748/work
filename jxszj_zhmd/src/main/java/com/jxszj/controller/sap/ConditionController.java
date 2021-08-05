package com.jxszj.controller.sap;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jxszj.pojo.EUDataGridResult;
import com.jxszj.pojo.sap.SapConditionCqTb;
import com.jxszj.service.sap.IConditionService;
import com.jxszj.utils.MessageResult;


@Controller
@RequestMapping("/sap")
public class ConditionController {

	@Autowired
	private IConditionService conditionService;
	
	@RequestMapping("/getConditionList")
	@ResponseBody
	public EUDataGridResult getConditionList(Integer page, Integer rows){
		EUDataGridResult result =conditionService.getConditionList(page, rows);
		return result;
	}
	
	
	@RequestMapping("/addCondition")
	@ResponseBody
	public MessageResult addCondition(SapConditionCqTb sapConditionCqTb){
		try {
			int i=conditionService.insertCondition(sapConditionCqTb);
			if(i>0){
				return MessageResult.build(200,"添加成功！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MessageResult.build(null,"添加失败！");
	}
	
	@RequestMapping("/editCondition")
	@ResponseBody
	public MessageResult editCondition(SapConditionCqTb sapConditionCqTb){
		try {
			int i=conditionService.updateCondition(sapConditionCqTb);
			if(i>0){
				return MessageResult.build(200,"修改成功！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MessageResult.build(null,"修改失败！");
	}
	
	@RequestMapping("/deleteCondition")
	@ResponseBody
	public MessageResult deleteCondition(Integer[] ids){
		try {
			int i=conditionService.deleteCondition(Arrays.asList(ids));
			if(i>0){
				return MessageResult.build(200,"删除成功！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MessageResult.build(null,"删除失败！");
	}
	
}

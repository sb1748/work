package com.jxszj.controller.sap;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jxszj.pojo.EUDataGridResult;
import com.jxszj.pojo.sap.SapDataTb;
import com.jxszj.service.sap.ISapDataService;
import com.jxszj.utils.MessageResult;


@Controller
@RequestMapping("/sap/data")
public class SapDataController {

	@Autowired
	private ISapDataService sapDataService;
	
	@RequestMapping("/getSapDataList")
	@ResponseBody
	public EUDataGridResult getSapDataList(Integer page, Integer rows,String bmz){
		EUDataGridResult result =sapDataService.getSapDataList(page, rows,bmz);
		return result;
	}
	
	
	@RequestMapping("/addSapData")
	@ResponseBody
	public MessageResult addSapData(SapDataTb sapData){
		try {
			int i=sapDataService.insertSapData(sapData);
			if(i>0){
				return MessageResult.build(200,"添加成功！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MessageResult.build(null,"添加失败！");
	}
	
	@RequestMapping("/editSapData")
	@ResponseBody
	public MessageResult editSapData(SapDataTb sapData){
		try {
			int i=sapDataService.updateSapData(sapData);
			if(i>0){
				return MessageResult.build(200,"修改成功！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MessageResult.build(null,"修改失败！");
	}
	
	@RequestMapping("/deleteSapData")
	@ResponseBody
	public MessageResult deleteSapData(Integer[] ids){
		try {
			int i=sapDataService.deleteSapData(Arrays.asList(ids));
			if(i>0){
				return MessageResult.build(200,"删除成功！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MessageResult.build(null,"删除失败！");
	}
	
}

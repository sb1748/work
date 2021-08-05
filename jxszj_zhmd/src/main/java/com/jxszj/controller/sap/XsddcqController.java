package com.jxszj.controller.sap;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jxszj.pojo.EUDataGridResult;
import com.jxszj.pojo.sap.SapConditionCqTb;
import com.jxszj.pojo.sap.SapXsddCqTb;
import com.jxszj.service.sap.IConditionService;
import com.jxszj.service.sap.IXsddcqService;
import com.jxszj.utils.MessageResult;


@Controller
@RequestMapping("/sap")
public class XsddcqController {

	@Autowired
	private IXsddcqService xsddcqService;
	
	@RequestMapping("/getSapXsddCqTbList")
	@ResponseBody
	public EUDataGridResult getSapXsddCqTbList(Integer page, Integer rows,String xspz){
		EUDataGridResult result =xsddcqService.getSapXsddCqTbList(page, rows,xspz);
		return result;
	}
	
	
	@RequestMapping("/editSapXsddCqTb")
	@ResponseBody
	public MessageResult editSapXsddCqTb(SapXsddCqTb sapXsddCqTb){
		try {
			int i=xsddcqService.updateSapXsddCqTb(sapXsddCqTb);
			if(i>0){
				return MessageResult.build(200,"修改成功！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MessageResult.build(null,"修改失败！");
	}
	
	@RequestMapping("/deleteSapXsddCqTb")
	@ResponseBody
	public MessageResult deleteSapXsddCqTb(String[] ids){
		try {
			int i=xsddcqService.deleteSapXsddCqTb(Arrays.asList(ids));
			if(i>0){
				return MessageResult.build(200,"删除成功！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MessageResult.build(null,"删除失败！");
	}
	
}

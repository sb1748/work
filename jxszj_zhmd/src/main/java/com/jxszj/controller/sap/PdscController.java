package com.jxszj.controller.sap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jxszj.pojo.EUDataGridResult;
import com.jxszj.service.sap.IPdscService;

//排单生产
@Controller
public class PdscController {

	@Autowired
	private IPdscService pdscService;
	
	@RequestMapping("/getPdscView")
	@ResponseBody
	public EUDataGridResult getPdscView(Integer page, Integer rows,String pdrq){
		EUDataGridResult result =pdscService.getPdscView(page, rows,pdrq);
		return result;
	}
}

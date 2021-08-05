package com.jxszj.controller.sap;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jxszj.pojo.EUDataGridResult;
import com.jxszj.service.sap.IGysqzService;
import com.jxszj.utils.MessageResult;

//供应商清账
@Controller
public class GysqzController {

	@Autowired
	private IGysqzService gysqzService;
	
	@RequestMapping("/gysqz/uploadPostExcel")
	@ResponseBody
	public MessageResult uploadPostExcel(@RequestParam("gysqzExcelFile") MultipartFile gysqzExcelFile,HttpServletRequest request, HttpServletResponse response){
		try {
			if (gysqzExcelFile==null) {
				return MessageResult.build(null,"上传Excel失败,请重试！");
			}
			int num=gysqzService.uploadGysqzExcel(request, response, gysqzExcelFile);
			if(num>0){
				return MessageResult.build(200,"同步完成");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MessageResult.build(500,"同步失败");
	}
	
	@RequestMapping("/gysqz/getSAPPostList")
	@ResponseBody
	public EUDataGridResult getSAPPostList(Integer page, Integer rows,String khqzStatus){
		EUDataGridResult result =gysqzService.getSAPPost(page, rows,khqzStatus);
		return result;
	}
	
	@RequestMapping("/gysqz/sendGysqz")
	@ResponseBody
	public MessageResult sendPost(Integer[] ids){
		try {
			gysqzService.sendPost(Arrays.asList(ids));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MessageResult.build(200,"同步完成！");
	}
	
	
	@RequestMapping("/gysqz/delGysqz")
	@ResponseBody
	public MessageResult delGysqz(Integer[] ids){
		int num=0;
		try {
			num=gysqzService.delete(Arrays.asList(ids));
			if(num==ids.length){
				return MessageResult.build(200,"删除成功！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MessageResult.build(500,"删除失败！");
	}
}

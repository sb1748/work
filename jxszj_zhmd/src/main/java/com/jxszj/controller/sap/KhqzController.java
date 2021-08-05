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
import com.jxszj.service.sap.IKhqzService;
import com.jxszj.utils.MessageResult;

//客户清账
@Controller
public class KhqzController {

	@Autowired
	private IKhqzService khqzService;
	
	@RequestMapping("/khqz/uploadPostExcel")
	@ResponseBody
	public MessageResult uploadPostExcel(@RequestParam("khqzExcelFile") MultipartFile khqzExcelFile,HttpServletRequest request, HttpServletResponse response){
		try {
			if (khqzExcelFile==null) {
				return MessageResult.build(null,"上传Excel失败,请重试！");
			}
			int num=khqzService.uploadKhqzExcel(request, response, khqzExcelFile);
			if(num>0){
				return MessageResult.build(200,"同步完成");
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return MessageResult.build(500,"同步失败");
	}
	
	@RequestMapping("/khqz/getSAPPostList")
	@ResponseBody
	public EUDataGridResult getSAPPostList(Integer page, Integer rows,String khqzStatus){
		EUDataGridResult result =khqzService.getSAPPost(page, rows,khqzStatus);
		return result;
	}
	
	@RequestMapping("/khqz/sendKhqz")
	@ResponseBody
	public MessageResult sendPost(Integer[] ids){
		try {
			khqzService.sendPost(Arrays.asList(ids));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MessageResult.build(200,"同步完成！");
	}
	
	
	@RequestMapping("/khqz/delKhqz")
	@ResponseBody
	public MessageResult delKhqz(Integer[] ids){
		int num=0;
		try {
			num=khqzService.delete(Arrays.asList(ids));
			if(num==ids.length){
				return MessageResult.build(200,"删除成功！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MessageResult.build(500,"删除失败！");
	}
}

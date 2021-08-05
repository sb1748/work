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
import com.jxszj.service.sap.IZzfyjtService;
import com.jxszj.utils.MessageResult;

//费用计提
@Controller
public class ZzfyjtController {

	@Autowired
	private IZzfyjtService zzfyjtService;
	
	@RequestMapping("/zzfyjt/uploadPostExcel")
	@ResponseBody
	public MessageResult uploadPostExcel(@RequestParam("zzfyjtExcelFile") MultipartFile zzfyjtExcelFile,HttpServletRequest request, HttpServletResponse response){
		try {
			if (zzfyjtExcelFile==null) {
				return MessageResult.build(null,"上传Excel失败,请重试！");
			}
			int num=zzfyjtService.uploadFyjtExcel(request, response, zzfyjtExcelFile);
			if(num>0){
				return MessageResult.build(200,"同步完成");
			}else if(num==-1){
				return MessageResult.build(500,"序号不完整");
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return MessageResult.build(500,"同步失败");
	}
	
	@RequestMapping("/zzfyjt/getSAPPostList")
	@ResponseBody
	public EUDataGridResult getSAPPostList(Integer page, Integer rows,String zzfyjtStatus){
		EUDataGridResult result =zzfyjtService.getSAPPost(page, rows, zzfyjtStatus);
		return result;
	}
	
	
	@RequestMapping("/zzfyjt/delPost")
	@ResponseBody
	public MessageResult delPost(Integer[] ids){
		int num=0;
		try {
			num=zzfyjtService.delete(Arrays.asList(ids));
			if(num==ids.length){
				return MessageResult.build(200,"删除成功！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MessageResult.build(500,"删除失败！");
	}
}

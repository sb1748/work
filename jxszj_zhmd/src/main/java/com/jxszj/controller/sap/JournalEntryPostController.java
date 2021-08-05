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
import com.jxszj.service.sap.IJournalEntryPostService;
import com.jxszj.utils.MessageResult;

//费用计提
@Controller
public class JournalEntryPostController {

	@Autowired
	private IJournalEntryPostService journalEntryPostService;
	
	@RequestMapping("/uploadPostExcel")
	@ResponseBody
	public MessageResult uploadPostExcel(@RequestParam("postExcelFile") MultipartFile postExcelFile,HttpServletRequest request, HttpServletResponse response){
		try {
			if (postExcelFile==null) {
				return MessageResult.build(null,"上传Excel失败,请重试！");
			}
			int num=journalEntryPostService.uploadFyjtExcel(request, response, postExcelFile);
			if(num>0){
				return MessageResult.build(200,"同步完成");
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return MessageResult.build(500,"同步失败");
	}
	
	@RequestMapping("/getSAPPostList")
	@ResponseBody
	public EUDataGridResult getSAPPostList(Integer page, Integer rows,String journalEntryPostStatus){
		EUDataGridResult result =journalEntryPostService.getSAPPost(page, rows, journalEntryPostStatus);
		return result;
	}
	
	@RequestMapping("/sendPost")
	@ResponseBody
	public MessageResult sendPost(Integer[] ids){
		try {
			journalEntryPostService.sendPost(Arrays.asList(ids));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MessageResult.build(200,"同步完成！");
	}
	
	
	@RequestMapping("/delPost")
	@ResponseBody
	public MessageResult delPost(Integer[] ids){
		int num=0;
		try {
			num=journalEntryPostService.delete(Arrays.asList(ids));
			if(num==ids.length){
				return MessageResult.build(200,"删除成功！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MessageResult.build(500,"删除失败！");
	}
}

package com.jxszj.controller.sap;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jxszj.pojo.EUDataGridResult;
import com.jxszj.service.sap.IFwfService;
import com.jxszj.utils.MessageResult;

//转经销商货款
@Controller
public class FwfController {

	@Autowired
	private IFwfService fwfService;
	
	@RequestMapping("/fwf/uploadPostExcel")
	@ResponseBody
	public MessageResult uploadPostExcel(@RequestParam("fwfExcelFile") MultipartFile fwfExcelFile,HttpServletRequest request, HttpServletResponse response){
		try {
			if (fwfExcelFile==null) {
				return MessageResult.build(null,"上传Excel失败,请重试！");
			}
			int num=fwfService.uploadFwfExcel(request, response, fwfExcelFile);
			if(num>0){
				return MessageResult.build(200,"同步完成");
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return MessageResult.build(500,"同步失败");
	}
	@RequestMapping("/fwf/getSAPPostList")
	@ResponseBody
	public EUDataGridResult getSAPPostList(Integer page, Integer rows,String fwfStatus,String zkrq){
		EUDataGridResult result =fwfService.getSAPPost(page, rows,fwfStatus,zkrq);
		return result;
	}
	
//	@RequestMapping("/dskqz/sendDskqz")
//	@ResponseBody
//	public MessageResult sendPost(Integer[] ids){
//		try {
//			fwfService.sendPost(Arrays.asList(ids));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return MessageResult.build(200,"同步完成！");
//	}
	
	@RequestMapping("/fwf/delfwf")
	@ResponseBody
	public MessageResult delfwf(Integer[] ids){
		int num=0;
		try {
			num=fwfService.delete(Arrays.asList(ids));
			if(num==ids.length){
				return MessageResult.build(200,"删除成功！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MessageResult.build(500,"删除失败！");
	}
	
	@RequestMapping("/fwf/export")
	public void export(HttpServletRequest request, HttpServletResponse response) {
		// excel模板路径
		//本地：E:/work/jxszj_zhmd/src/main/webapp/WEB-INF/excel/补经销商服务费.xlsx
		//生产：/root/opt/jxszj/apache-tomcat-7.0.105/webapps/jxszj_zhmd/WEB-INF/excel/补经销商服务费.xlsx
		String excelUrl = "/root/opt/jxszj/apache-tomcat-7.0.105/webapps/jxszj_zhmd/WEB-INF/excel/补经销商服务费.xlsx";
		String fwfStatus=request.getParameter("fwfStatus");
		String zkrq=request.getParameter("zkrq");
		XSSFWorkbook wb = fwfService.getXSSFWorkbook(excelUrl,fwfStatus, zkrq);

		try {
			String fileName = "补经销商服务费.xlsx";
			fileName = fileName.replace("-", "");
			response.setHeader("Content-disposition","attachment;filename=" + new String(fileName.getBytes("UTF-8"), "ISO8859-1"));// 设置文件头编码格式
			response.setContentType("APPLICATION/OCTET-STREAM;charset=UTF-8");// 设置类型
			response.setHeader("Cache-Control", "no-cache");// 设置头
			response.setDateHeader("Expires", 0);// 设置日期头
			wb.write(response.getOutputStream());

			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

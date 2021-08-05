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
import com.jxszj.service.sap.IHdfService;
import com.jxszj.utils.MessageResult;

//转经销商货款
@Controller
public class HdfController {

	@Autowired
	private IHdfService hdfService;
	
	@RequestMapping("/hdf/uploadPostExcel")
	@ResponseBody
	public MessageResult uploadPostExcel(@RequestParam("hdfExcelFile") MultipartFile hdfExcelFile,HttpServletRequest request, HttpServletResponse response){
		try {
			if (hdfExcelFile==null) {
				return MessageResult.build(null,"上传Excel失败,请重试！");
			}
			int num=hdfService.uploadHdfExcel(request, response, hdfExcelFile);
			if(num>0){
				return MessageResult.build(200,"同步完成");
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return MessageResult.build(500,"同步失败");
	}
	@RequestMapping("/hdf/getSAPPostList")
	@ResponseBody
	public EUDataGridResult getSAPPostList(Integer page, Integer rows,String hdfStatus,String zkrq){
		EUDataGridResult result =hdfService.getSAPPost(page, rows,hdfStatus,zkrq);
		return result;
	}
	
	@RequestMapping("/hdf/delhdf")
	@ResponseBody
	public MessageResult delhdf(Integer[] ids){
		int num=0;
		try {
			num=hdfService.delete(Arrays.asList(ids));
			if(num==ids.length){
				return MessageResult.build(200,"删除成功！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MessageResult.build(500,"删除失败！");
	}
	
	@RequestMapping("/hdf/export")
	public void export(HttpServletRequest request, HttpServletResponse response) {
		// excel模板路径
		//本地：E:/work/jxszj_zhmd/src/main/webapp/WEB-INF/excel/活动费扣款.xlsx
		//生产：/root/opt/jxszj/apache-tomcat-7.0.105/webapps/jxszj_zhmd/WEB-INF/excel/活动费扣款.xlsx
		String excelUrl = "/root/opt/jxszj/apache-tomcat-7.0.105/webapps/jxszj_zhmd/WEB-INF/excel/活动费扣款.xlsx";
		String hdfStatus=request.getParameter("hdfStatus");
		String zkrq=request.getParameter("zkrq");
		XSSFWorkbook wb = hdfService.getXSSFWorkbook(excelUrl,hdfStatus, zkrq);

		try {
			String fileName = "活动费扣款.xlsx";
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

package com.jxszj.controller.sap;



import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiReportCommentListRequest;
import com.dingtalk.api.request.OapiReportListRequest;
import com.dingtalk.api.request.OapiUserGetRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiReportCommentListResponse;
import com.dingtalk.api.response.OapiReportListResponse;
import com.dingtalk.api.response.OapiUserGetResponse;
import com.jxszj.utils.DateUtils;


@Controller
public class DailycontentController {

	@RequestMapping("/dingTalkLog/export")
	public void export(HttpServletRequest request, HttpServletResponse response) {
		// excel模板路径
		//本地：E:/work/jxszj_zhmd/src/main/webapp/WEB-INF/excel/日志模板.xlsx
		//生产：/root/opt/jxszj/apache-tomcat-7.0.105/webapps/jxszj_zhmd/WEB-INF/excel/日志模板.xlsx
		try {
			String excelUrl = "/root/opt/jxszj/apache-tomcat-7.0.105/webapps/jxszj_zhmd/WEB-INF/excel/日志模板.xlsx";
			String startDate=request.getParameter("startDate");
			String endDate=request.getParameter("endDate");
			List<String> items=Arrays.asList(request.getParameter("items").split(","));
			List<String> ids=new ArrayList<>();
			StringBuilder sb=new StringBuilder();
			for (int i = 0; i < items.size(); i++) {
				ids.add(items.get(i).substring(0, items.get(i).indexOf(":")));
				sb.append(items.get(i).substring(items.get(i).indexOf(":")+1, items.get(i).length())+"、");
			}
			String name=sb.toString().substring(0, sb.toString().length()-1);
			XSSFWorkbook wb = getXSSFWorkbook(startDate,endDate,excelUrl,ids);
			String fileName = name.toString()+startDate+"至"+endDate+"的日志记录.xlsx";
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
	
	public XSSFWorkbook getXSSFWorkbook(String startDate,String endDate,String excelUrl,List<String> ids){
		String token=getToken();
		XSSFWorkbook workbook = null;
		try {
			//
			List<String> days=DateUtils.getDays(startDate,endDate);
			workbook=new XSSFWorkbook(new FileInputStream(new File(excelUrl)));
			XSSFCellStyle cellStyle1=workbook.createCellStyle();
			cellStyle1.setBorderBottom(BorderStyle.THIN);
			cellStyle1.setBorderLeft(BorderStyle.THIN);
			cellStyle1.setBorderRight(BorderStyle.THIN);
			cellStyle1.setBorderTop(BorderStyle.THIN);
			cellStyle1.setWrapText(true);
			cellStyle1.setAlignment(HorizontalAlignment.CENTER); // 居中
			cellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
			
			XSSFCellStyle cellStyle2=workbook.createCellStyle();
			cellStyle2.setBorderBottom(BorderStyle.THIN);
			cellStyle2.setBorderLeft(BorderStyle.THIN);
			cellStyle2.setBorderRight(BorderStyle.THIN);
			cellStyle2.setBorderTop(BorderStyle.THIN);
			cellStyle2.setVerticalAlignment(VerticalAlignment.CENTER);
			cellStyle2.setWrapText(true);
			
			XSSFFont font1 = workbook.createFont(); 
			font1.setFontHeightInPoints((short) 8.5);//设置字体大小
			font1.setFontName("宋体");
			cellStyle1.setFont(font1);
			
			XSSFFont font2 = workbook.createFont(); 
			font2.setFontHeightInPoints((short) 7.5);//设置字体大小
			font2.setFontName("宋体");
			cellStyle2.setFont(font2);
			for (int i = 0; i < ids.size(); i++) {
				for (int j = 0; j < days.size(); j++) {
		            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/report/list");
					OapiReportListRequest req = new OapiReportListRequest();
					req.setStartTime(DateUtils.DateToTimeStamp(days.get(j), DateUtils.FORMAT_1_STRING));
					req.setEndTime(DateUtils.DateToTimeStamp(DateUtils.getAfterDay(days.get(j)), DateUtils.FORMAT_1_STRING));
					req.setUserid(ids.get(i));
					req.setCursor(0L);
					req.setSize(5L);
					OapiReportListResponse rsp = client.execute(req, token);
					JSONObject jsonObject=JSON.parseObject(rsp.getBody());
					Map<String,String> map=new HashMap<>();
					if(jsonObject.getString("errcode").equals("0")){
						jsonObject=jsonObject.getJSONObject("result");
						JSONArray jSONArray=jsonObject.getJSONArray("data_list");
						if(jSONArray.size()!=0){
							//日志编写人
							String name=jSONArray.getJSONObject(0).getString("creator_name");
							//提报日期
							String modified_time=DateUtils.getLongToString(jSONArray.getJSONObject(0).getString("modified_time"), DateUtils.FORMAT_3_STRING);
							
//							XSSFSheet sheet=workbook.getSheetAt(0);//读取第一个sheet
							XSSFSheet sheet=workbook.cloneSheet(0,name+DateUtils.getLongToString(jSONArray.getJSONObject(0).getString("modified_time"), DateUtils.FORMAT_1_STRING));
							
							XSSFRow row = sheet.getRow(0);
							if (null == row) {
								row = sheet.createRow(0);
							}
							XSSFCell cell = row.getCell(0);
							if (null == cell) {
								cell = row.createCell(0);
							}
							cell.setCellValue("姓名："+name+"       提报日期："+modified_time+"       部门：总裁办");
							
							
							//日志id
							String id=jSONArray.getJSONObject(0).getString("report_id");
							List<String> comments=getCommentList(id,token);
							jSONArray=jSONArray.getJSONObject(0).getJSONArray("contents");
							int rowIndex=1;
							for (int x = 0; x < jSONArray.size(); x++) {
								if(!"[]".equals(jSONArray.getJSONObject(x).getString("value"))){
									map.put(jSONArray.getJSONObject(x).getString("key"), jSONArray.getJSONObject(x).getString("value"));
									XSSFRow rows = sheet.getRow(rowIndex);
									if (null == rows) {
										rows = sheet.createRow(rowIndex);
									}
									XSSFCell cell0 = rows.getCell(0);
									if (null == cell0) {
										cell0 = rows.createCell(0);
									}
									cell0.setCellStyle(cellStyle1);
									cell0.setCellValue("\r\n"+jSONArray.getJSONObject(x).getString("key")+"\r\n");
									
									XSSFCell cell1 = rows.getCell(1);
									if (null == cell1) {
										cell1 = rows.createCell(1);
									}
									cell1.setCellStyle(cellStyle2);
									cell1.setCellValue("\r\n"+jSONArray.getJSONObject(x).getString("value")+"\r\n");
									rowIndex++;
								}
							}
							XSSFRow rows = sheet.getRow(rowIndex);
							if (null == rows) {
								rows = sheet.createRow(rowIndex);
							}
							XSSFCell cell0 = rows.getCell(0);
							if (null == cell0) {
								cell0 = rows.createCell(0);
							}
							cell0.setCellStyle(cellStyle1);
							cell0.setCellValue("评论");
							
							XSSFCell cell1 = rows.getCell(1);
							if (null == cell1) {
								cell1 = rows.createCell(1);
							}
							cell1.setCellStyle(cellStyle2);
							StringBuilder comment=new StringBuilder();
							if(comments.size()!=0){
								for (int k = 0; k < comments.size(); k++) {
									comment.append(comments.get(k)+"\r\n");
								}
							}
							cell1.setCellValue(comments.size()==0?"":comment.toString());
						}
					}
				}
			}
			workbook.removeSheetAt(0);
//			FileOutputStream fos = new FileOutputStream("C:\\Users\\Administrator\\Desktop\\excel\\工作簿1(1).xlsx");  
//			workbook.write(fos);//写文件
//			fos.close(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return workbook;
	}
	
	
	
	public static String getToken(){
		String token="";
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
			OapiGettokenRequest req = new OapiGettokenRequest();
			req.setAppkey("dingdan9zym9hxhwl0qw");
			req.setAppsecret("SHb-MuMaC2ILdWj1JTY6Pps9TKpOz2aa5EAU8rdh7S1HIVIG78IjaLj1kbbpIHMG");
			req.setHttpMethod("GET");
			OapiGettokenResponse rsp = client.execute(req);
			JSONObject jsonObject=JSON.parseObject(rsp.getBody());
			if(jsonObject.getString("errcode").equals("0")){
				token=jsonObject.getString("access_token");
			}
        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }
	
	//获取对应的日志评论
	public  List<String> getCommentList(String id,String token){
		List<String> comments=new ArrayList<>();
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/report/comment/list");
			OapiReportCommentListRequest req = new OapiReportCommentListRequest();
			req.setReportId(id);
			OapiReportCommentListResponse rsp = client.execute(req, token);
			JSONObject jsonObject=JSON.parseObject(rsp.getBody());
			if(jsonObject.getString("errcode").equals("0")){
				jsonObject=jsonObject.getJSONObject("result");
				JSONArray jSONArray=jsonObject.getJSONArray("comments");
				for (int i = 0; i < jSONArray.size(); i++) {
					comments.add(getUserName(jSONArray.getJSONObject(i).getString("userid"),token)+":"+jSONArray.getJSONObject(i).getString("content"));
				}
			}
        } catch (Exception e) {
            e.printStackTrace();
        }
        return comments;
	}
	
	//获取钉钉用户名
	public String  getUserName(String userId, String token){
		String name="";
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/get");
			OapiUserGetRequest req = new OapiUserGetRequest();
			req.setUserid(userId);
			req.setHttpMethod("GET");
			OapiUserGetResponse rsp = client.execute(req, token);
			JSONObject jsonObject=JSON.parseObject(rsp.getBody());
			if(jsonObject.getString("errcode").equals("0")){
				name=jsonObject.getString("name");
			}
        } catch (Exception e) {
            e.printStackTrace();
        }
       return name;
	}
	
}

package com.jxszj.controller.sap;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.jxszj.pojo.sap.PpxxTb;
import com.jxszj.pojo.sap.YdmbTb;
import com.jxszj.service.sap.IPpxxService;
import com.jxszj.service.sap.IYdmbService;
import com.jxszj.utils.DateUtils;
import com.jxszj.utils.JDYAPIUtils;
import com.jxszj.utils.MessageResult;

import net.sf.json.JSONArray;

@Controller
@RequestMapping("/ydmb")
public class YDMBController {
	
	@Autowired
	private IPpxxService ppxxService;
	
	@Autowired
	private IYdmbService ydmbService;
	
	@RequestMapping("/getPp")
	@ResponseBody
	public String getPp(){
		List<PpxxTb> list=ppxxService.getPpxxLists();
		JSONArray json = JSONArray.fromObject(list);
		return json.toString();
		
	}
	
	@RequestMapping("/ydmbExport")
	public void ydmbExport(HttpServletRequest request, HttpServletResponse response) {
		// excel模板路径
		//本地：E:/work/jxszj_zhmd/src/main/webapp/WEB-INF/excel/本月目标回款.xlsx
		//生产：/root/opt/jxszj/apache-tomcat-7.0.105/webapps/jxszj_zhmd/WEB-INF/excel/本月目标回款.xlsx
		String excelUrl = "/root/opt/jxszj/apache-tomcat-7.0.105/webapps/jxszj_zhmd/WEB-INF/excel/本月目标回款.xlsx";
		String ppbm=request.getParameter("ppbm");
		String dd=request.getParameter("dd");
		XSSFWorkbook wb = ydmbService.getXSSFWorkbook(excelUrl,ppbm,dd);

		try {
			String fileName = "本月目标回款.xlsx";
			fileName = fileName.replace("-", "");
			response.setHeader("Content-disposition",
					"attachment;filename=" + new String(fileName.getBytes("UTF-8"), "ISO8859-1"));// 设置文件头编码格式
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
	
	@RequestMapping("/uploadYdmbExcel")
	@ResponseBody
	public MessageResult uploadYdmbExcel(@RequestParam("ydmbExcelFile") MultipartFile ydmbExcelFile,HttpServletRequest request, HttpServletResponse response){
		try {
			if (ydmbExcelFile==null) {
				return MessageResult.build(null,"上传Excel失败,请重试！");
			}
			int num=ydmbService.uploadYdmbExcel(request, response, ydmbExcelFile);
			if(num>0){
				return MessageResult.build(200,"已成功上传"+num+"条数据！");
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return MessageResult.build(500,"上传失败！");
	}
	
	
	@RequestMapping("/getYdmb")
	@ResponseBody
	public EUDataGridResult getYdmb(Integer page, Integer rows){
		EUDataGridResult result =ydmbService.getYdmb(page, rows);
		return result;
	}
	
	@RequestMapping("/addYdmbData")
	public void addYdmbData(){
		// 简道云      月度目标维护
		String ENTRYID = "609a4176637de90009c21f63";
	 	String APPID = "5cc110c3b3c41744aaa12b2e";
		String APIKEY = "uWEAujuxvzv5fMVifOvMRzlIJcPLgYkv";
		
		//获取当月每个经销商的月目标值
		final List<Map<String, Object>> condList = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("field", "_widget_1620722143402");
		map.put("type", "text");
		map.put("method", "eq");
		map.put("value", DateUtils.getNowDateToString(DateUtils.FORMAT_STRING1_MINUTE));
		condList.add(map);
		Map<String, Object> filter = new HashMap<String, Object>() {
			{
				put("rel", "and");
				put("cond", condList);
			}
		};
		JDYAPIUtils api = new JDYAPIUtils(APPID, ENTRYID, APIKEY);
		List<Map<String, Object>> list=api.getAllFormData(null, filter);
		
		List<YdmbTb > ydmbTbs =new ArrayList<YdmbTb>();
		for (int i = 0; i < list.size(); i++) {
			YdmbTb ydmbTb=new YdmbTb();
			ydmbTb.setJxsmc(list.get(i).get("_widget_1620722144567").toString());
			ydmbTb.setJxsbm(list.get(i).get("_widget_1620722144565").toString());
			ydmbTb.setPpbm(list.get(i).get("_widget_1620722144561").toString());
			ydmbTb.setDd(list.get(i).get("_widget_1620722144571").toString());
			ydmbTb.setYyzt(list.get(i).get("_widget_1620722144569").toString());
			ydmbTb.setBymbhk(Double.valueOf(list.get(i).get("_widget_1620722145500").toString()));
			ydmbTb.setDrsj(DateUtils.getNowDateToString());
			ydmbTbs.add(ydmbTb);
		}
		ydmbService.addData(ydmbTbs);
	}

}

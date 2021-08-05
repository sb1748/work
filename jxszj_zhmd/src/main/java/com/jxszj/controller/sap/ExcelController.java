package com.jxszj.controller.sap;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jxszj.service.sap.IDskqzService;
import com.jxszj.service.sap.ISapBmService;
import com.jxszj.service.sap.ISapWlExportService;
import com.jxszj.utils.MessageResult;

@Controller
public class ExcelController {
	
	@Autowired
	private ISapBmService sapBmService;
	
	
	//物料主数据
	@Autowired
	private ISapWlExportService sapWlExportService;
	
	@Autowired
	private IDskqzService dskqzService;

	/**
	 * 
	 * <pre>
	 *<b>.</b>
	 *<b>Description:</b> 
	 *    直接通过浏览器下载
	 *<b>Author:</b> yanwei
	 *<b>Date:</b> 2019年5月8日 上午11:50:35
	 *&#64;param request
	 *&#64;param response
	 * </pre>
	 */
	@RequestMapping("/export")
	public void export(HttpServletRequest request, HttpServletResponse response) {
		// excel模板路径
		//本地：E:/work/jxszj_zhmd/src/main/webapp/WEB-INF/excel/wlbm.xlsx
		//生产：/root/opt/jxszj/apache-tomcat-7.0.90/webapps/jxszj_zhmd/WEB-INF/excel/wlbm.xlsx
		String excelUrl = "/root/opt/jxszj/apache-tomcat-7.0.105/webapps/jxszj_zhmd/WEB-INF/excel/wlbm.xlsx";
		XSSFWorkbook wb = sapBmService.getXSSFWorkbook(excelUrl);

		try {
			String fileName = "wlbm.xlsx";
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

	/**
	 * 导入Excel
	 * 
	 * @param uploadExcel
	 *            上传的excel文件
	 * 
	 * @param request
	 *            请求
	 * 
	 * @param resposne
	 *            响应
	 * 
	 * @throws UnsupportedEncodingException
	 *             编码异常
	 * 
	 */
	@RequestMapping("/sap/uploadExcel")
	@ResponseBody
	public MessageResult uploadExcel(@RequestParam("excelFile") MultipartFile excelFile,HttpServletRequest request, HttpServletResponse response){
		MessageResult result=null;
		try {
			if (excelFile==null) {
				return MessageResult.build(null,"上传Excel失败,请重试！");
			}
			result=sapBmService.uploadExcel(request, response, excelFile);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return result;

	}
	
	
	/**
	 * 
	 * <pre>
	 *<b>.</b>
	 *<b>Description:</b> 
	 *    直接通过浏览器下载
	 *<b>Author:</b> yanwei
	 *<b>Date:</b> 2019年5月8日 上午11:50:35
	 *&#64;param request
	 *&#64;param response
	 * </pre>
	 */
	@RequestMapping("/wl/export")
	public void wlExport(HttpServletRequest request, HttpServletResponse response) {
		// excel模板路径
		//本地：E:/Workspaces/jxszj_zhmd/src/main/webapp/WEB-INF/excel/wl.xlsx
		//生产：/root/opt/jxszj/apache-tomcat-7.0.90/webapps/jxszj_zhmd/WEB-INF/excel/wl.xlsx
		String excelUrl = "/root/opt/jxszj/apache-tomcat-7.0.90/webapps/jxszj_zhmd/WEB-INF/excel/wl.xlsx";
		XSSFWorkbook wb = sapWlExportService.getXSSFWorkbook(excelUrl);

		try {
			String fileName = "物料主数据.xlsx";
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
	
	
	@RequestMapping("/dskqz/export")
	public void dskExport(HttpServletRequest request, HttpServletResponse response) {
		// excel模板路径
		//本地：E:/work/jxszj_zhmd/src/main/webapp/WEB-INF/excel/代收款清账模板.xlsx
		//生产：/root/opt/jxszj/apache-tomcat-7.0.105/webapps/jxszj_zhmd/WEB-INF/excel/代收款清账模板.xlsx
		String excelUrl = "/root/opt/jxszj/apache-tomcat-7.0.105/webapps/jxszj_zhmd/WEB-INF/excel/代收款清账模板.xlsx";
		XSSFWorkbook wb = dskqzService.getXSSFWorkbook(excelUrl);

		try {
			String fileName = "代收款清账.xlsx";
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
	
	
	
	/**
	 * 
	 *<pre>
	 *<b>.</b>
	 *<b>Description:</b> 
	 *    在钉钉消息推送中下载排名报表
	 *<b>Author:</b> yanwei
	 *<b>Date:</b> 2019年5月8日 下午1:25:27
	 *@param request
	 *@param response
	 *@param tbrq
	 *</pre>
	 */
	@RequestMapping(value = "/downloadExcel", method = RequestMethod.GET)
	public void downloadExcel(HttpServletRequest request, HttpServletResponse response, String strDate) {
		BufferedInputStream in = null;
		BufferedOutputStream out = null;
		try {
			String filename =strDate+"逾期报表.xlsx";
			String path = "/root/excel/";
			File file = new File(path + filename);
			in = new BufferedInputStream(new FileInputStream(file));
			out = new BufferedOutputStream(response.getOutputStream());
			response.setContentType(new MimetypesFileTypeMap().getContentType(file));// 设置response内容的类型
			response.setHeader("Content-disposition",
					"attachment; filename=" + new String(filename.getBytes("utf-8"), "ISO8859-1"));
			byte[] buffer = new byte[1024];
			int length = 0;
			while ((length = in.read(buffer)) > 0) {
				out.write(buffer, 0, length);
			}
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

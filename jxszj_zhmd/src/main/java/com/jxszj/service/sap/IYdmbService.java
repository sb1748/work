package com.jxszj.service.sap;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.jxszj.pojo.EUDataGridResult;
import com.jxszj.pojo.sap.YdmbTb;


public interface IYdmbService {
	
	public EUDataGridResult getYdmb(int page, int rows);
	
	public XSSFWorkbook getXSSFWorkbook(String excelUrl,String ppbm,String dd);
	
	public int uploadYdmbExcel(HttpServletRequest request,HttpServletResponse response,MultipartFile file) throws Exception;
	
	public List<YdmbTb> getYdmbs();
	
	public int addData(List<YdmbTb > ydmbTbs);
	
}

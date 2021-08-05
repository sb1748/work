package com.jxszj.service.sap;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.jxszj.pojo.EUDataGridResult;

public interface IDskqzService {
	
	public EUDataGridResult getSAPPost(int page, int rows,String dskqzStatus);
	
	public void sendPost(List<Integer> list);
	
	public int uploadDskExcel(HttpServletRequest request,HttpServletResponse response,MultipartFile file) throws Exception;
	
	public int delete(List<Integer> list);
	
	public XSSFWorkbook getXSSFWorkbook(String excelUrl);

}

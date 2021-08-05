package com.jxszj.service.sap;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.jxszj.pojo.EUDataGridResult;
import com.jxszj.pojo.sap.SapBmTb;
import com.jxszj.utils.MessageResult;


public interface ISapBmService {

	public EUDataGridResult getSapBmList(int page, int rows,String newWlbm,String newName,String oldWlbm,String oldName,String bom);
	
	public EUDataGridResult getSapBmRemList(int page, int rows,String newWlbm,String newName,String oldWlbm,String oldName,String bom);
	
	public List<SapBmTb> getSapBmListsByNewWlbm(String newWlbm);
	
	public List<SapBmTb> getSapBmListsByNewName(String newName);
	
	public List<SapBmTb> getSapBmListsByOldWlbm(String oldWlbm);
	
	public List<SapBmTb> getSapBmListsByOldName(String oldName);
	
	public int insertSapBm(SapBmTb sapBm);
	
	public int updateSapBm(SapBmTb sapBm);
	
	public int deleteSapBm(List<Integer> id);
	
	public int deleteAllSapBm();
	
	public int removeSapBm(List<Integer> id);
	
	public int restoreSapBm(List<Integer> id);
	
	MessageResult uploadExcel(HttpServletRequest request,HttpServletResponse response,MultipartFile file) throws Exception;
	
	public XSSFWorkbook getXSSFWorkbook(String excelUrl);
}

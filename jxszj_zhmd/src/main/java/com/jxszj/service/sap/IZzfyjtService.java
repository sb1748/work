package com.jxszj.service.sap;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.jxszj.pojo.EUDataGridResult;

public interface IZzfyjtService {
	
	public EUDataGridResult getSAPPost(int page, int rows,String zzfyjtStatus);
	
	public int uploadFyjtExcel(HttpServletRequest request,HttpServletResponse response,MultipartFile file) throws Exception;
	
	public int delete(List<Integer> list);
}

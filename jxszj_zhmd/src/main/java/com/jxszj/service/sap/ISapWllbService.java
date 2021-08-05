package com.jxszj.service.sap;

import java.util.List;

import com.jxszj.pojo.EUDataGridResult;
import com.jxszj.pojo.sap.SapWlTb;


public interface ISapWllbService {

	public EUDataGridResult getWlbmList(int page, int rows);
	
	public List<SapWlTb> getWlbmLists();
	
	public int insertWlbm(SapWlTb wl);
	
	public int updateWlbm(SapWlTb wl);
	
	public int deleteWlbm(List<Long> ids);
	
}

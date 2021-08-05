package com.jxszj.service.sap;

import java.util.List;

import com.jxszj.pojo.EUDataGridResult;
import com.jxszj.pojo.sap.SapDataTb;


public interface ISapDataService {

	public EUDataGridResult getSapDataList(int page, int rows,String bmz);
	
	public List<SapDataTb> getSapDataLists(String bmz,String gzs);
	
	public int insertSapData(SapDataTb sapData);
	
	public int updateSapData(SapDataTb sapData);
	
	public int deleteSapData(List<Integer> id);
}

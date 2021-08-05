package com.jxszj.service.sap;

import java.util.List;

import com.jxszj.pojo.EUDataGridResult;
import com.jxszj.pojo.sap.SapXsddCqTb;

public interface IXsddcqService {

	public EUDataGridResult getSapXsddCqTbList(int page, int rows,String xspz);
	
	public int updateSapXsddCqTb(SapXsddCqTb sapXsddCqTb);
	
	public int deleteSapXsddCqTb(List<String> id);
}

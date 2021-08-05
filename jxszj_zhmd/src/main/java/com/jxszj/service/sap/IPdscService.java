package com.jxszj.service.sap;


import com.jxszj.pojo.EUDataGridResult;

public interface IPdscService {
	
	public EUDataGridResult getPdscView(int page, int rows,String pdrq);
	
}

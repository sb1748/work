package com.jxszj.service.sap;



import java.util.List;


import com.jxszj.pojo.EUDataGridResult;
import com.jxszj.pojo.sap.SapJdywlTb;
import com.jxszj.pojo.sap.SapJdywlTbExample;

public interface ISapJdywlService {
	
	  int deleteByExample(SapJdywlTbExample example);
	  
	  int insertByBatch(List<SapJdywlTb> sapJdywlTb);

	  public EUDataGridResult getJdywl(int page, int rows,String cpmc);
	  
}

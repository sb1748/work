package com.jxszj.service.sap;

import java.util.List;

import com.jxszj.pojo.sap.SapScpgTb;


public interface ISapScpgService {

	 int saveOrUpdate(List<SapScpgTb> record);
	 
	 List<SapScpgTb> getSapScpgTb(String id);
}

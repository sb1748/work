package com.jxszj.service.sap;

import java.util.List;

import com.jxszj.pojo.EUDataGridResult;
import com.jxszj.pojo.sap.SapConditionCqTb;

public interface IConditionService {

	public EUDataGridResult getConditionList(int page, int rows);
	
	public int insertCondition(SapConditionCqTb sapConditionCqTb);
	
	public int updateCondition(SapConditionCqTb sapConditionCqTb);
	
	public int deleteCondition(List<Integer> id);
}

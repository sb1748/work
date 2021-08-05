package com.jxszj.service.sap;

import java.util.List;

import com.jxszj.pojo.EUDataGridResult;
import com.jxszj.pojo.sap.DailyuserTb;

public interface IDailyUserService {

	public EUDataGridResult getDailyUser(int page, int rows);
	
	public int addDailyUser(DailyuserTb dailyuserTb);
	
	public int deleteDailyUser(List<String> id);
	
	public List<DailyuserTb> getDailyUsers();
}

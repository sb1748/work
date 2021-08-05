package com.jxszj.service.sap.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxszj.mapper.sap.DailyuserTbMapper;
import com.jxszj.pojo.EUDataGridResult;
import com.jxszj.pojo.sap.DailyuserTb;
import com.jxszj.pojo.sap.DailyuserTbExample;
import com.jxszj.pojo.sap.DailyuserTbExample.Criteria;
import com.jxszj.service.sap.IDailyUserService;

@Service
public class DailyUserServiceImpl implements IDailyUserService {
	
	@Autowired
	private DailyuserTbMapper dailyuserTbMapper;

	@Override
	public EUDataGridResult getDailyUser(int page, int rows) {
		DailyuserTbExample example=new DailyuserTbExample();
		// 分页处理
		PageHelper.startPage(page, rows);
		List<DailyuserTb> item=dailyuserTbMapper.selectByExample(example);
		// 创建一个返回值对象
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(item);
		// 取记录总条数
		PageInfo<DailyuserTb> pageInfo = new PageInfo<>(item);
		result.setTotal(pageInfo.getTotal());
		return result;
	}

	@Override
	public int addDailyUser(DailyuserTb dailyuserTb) {
		int i=dailyuserTbMapper.insert(dailyuserTb);
		return i;
	}

	@Override
	public int deleteDailyUser(List<String> ids) {
		DailyuserTbExample example=new DailyuserTbExample();
		Criteria criteria=example.createCriteria();
		criteria.andUserIdIn(ids);
		int i=dailyuserTbMapper.deleteByExample(example);
		return i;
	}

	@Override
	public List<DailyuserTb> getDailyUsers() {
		DailyuserTbExample example=new DailyuserTbExample();
		List<DailyuserTb> dailyuserTbs=dailyuserTbMapper.selectByExample(example);
		return dailyuserTbs;
	}


}

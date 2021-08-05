package com.jxszj.service.sap.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxszj.mapper.sap.SapConditionCqTbMapper;
import com.jxszj.pojo.EUDataGridResult;
import com.jxszj.pojo.sap.SapConditionCqTb;
import com.jxszj.pojo.sap.SapConditionCqTbExample;
import com.jxszj.pojo.sap.SapConditionCqTbExample.Criteria;
import com.jxszj.service.sap.IConditionService;
@Service
public class ConditionServiceImpl implements IConditionService {
	
	@Autowired
	private SapConditionCqTbMapper sapConditionCqTbMapper;

	@Override
	public EUDataGridResult getConditionList(int page, int rows) {
		SapConditionCqTbExample example=new SapConditionCqTbExample();
		// 分页处理
		PageHelper.startPage(page, rows);
		List<SapConditionCqTb> item=sapConditionCqTbMapper.selectByExample(example);
		// 创建一个返回值对象
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(item);
		// 取记录总条数
		PageInfo<SapConditionCqTb> pageInfo = new PageInfo<>(item);
		result.setTotal(pageInfo.getTotal());
		return result;
	}
	
	@Override
	public int insertCondition(SapConditionCqTb sapConditionCqTb) {
		int i=sapConditionCqTbMapper.insert(sapConditionCqTb);
		return i;
	}

	@Override
	public int updateCondition(SapConditionCqTb sapConditionCqTb) {
		int i=sapConditionCqTbMapper.updateByPrimaryKey(sapConditionCqTb);
		return i;
	}

	@Override
	public int deleteCondition(List<Integer> id) {
		SapConditionCqTbExample example=new SapConditionCqTbExample();
		Criteria criteria=example.createCriteria();
		criteria.andIdIn(id);
		int i=sapConditionCqTbMapper.deleteByExample(example);
		return i;
	}

}

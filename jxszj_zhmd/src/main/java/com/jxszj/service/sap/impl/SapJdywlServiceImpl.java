package com.jxszj.service.sap.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxszj.mapper.sap.SapJdywlTbMapper;
import com.jxszj.pojo.EUDataGridResult;
import com.jxszj.pojo.sap.SapJdywlTb;
import com.jxszj.pojo.sap.SapJdywlTbExample;
import com.jxszj.pojo.sap.SapJdywlTbExample.Criteria;
import com.jxszj.service.sap.ISapJdywlService;

@Service
public class SapJdywlServiceImpl implements ISapJdywlService {
	
	@Autowired
	private SapJdywlTbMapper sapJdywlTbMapper;
	
	@Override
	public int insertByBatch(List<SapJdywlTb> sapJdywlTb) {
		int num=0;
		try {
			sapJdywlTbMapper.truncateTable();
			num=sapJdywlTbMapper.insertByBatch(sapJdywlTb);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return num;
	}


	@Override
	public EUDataGridResult getJdywl(int page, int rows,String cpmc) {
		SapJdywlTbExample example = new SapJdywlTbExample();
		Criteria criteria=example.createCriteria();
		if(cpmc!=null&&!("".equals(cpmc))){
			criteria.andCpmcLike("%"+cpmc+"%");
		}
		PageHelper.startPage(page, rows);
		List<SapJdywlTb> sapJdywlTb=sapJdywlTbMapper.selectByExample(example);
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(sapJdywlTb);
		PageInfo<SapJdywlTb> pageInfo = new PageInfo<>(sapJdywlTb);
		result.setTotal(pageInfo.getTotal());
		return result;
	}


	@Override
	public int deleteByExample(SapJdywlTbExample example) {
		int i=sapJdywlTbMapper.deleteByExample(example);
		return i;
	}

}

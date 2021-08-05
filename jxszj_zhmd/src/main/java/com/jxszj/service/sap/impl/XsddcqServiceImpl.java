package com.jxszj.service.sap.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxszj.mapper.sap.SapXsddCqTbMapper;
import com.jxszj.pojo.EUDataGridResult;
import com.jxszj.pojo.sap.SapXsddCqTb;
import com.jxszj.pojo.sap.SapXsddCqTbExample;
import com.jxszj.pojo.sap.SapXsddCqTbExample.Criteria;
import com.jxszj.service.sap.IXsddcqService;
@Service
public class XsddcqServiceImpl implements IXsddcqService {
	
	@Autowired
	private SapXsddCqTbMapper sapXsddCqTbMapper;

	@Override
	public EUDataGridResult getSapXsddCqTbList(int page, int rows,String xspz) {
		SapXsddCqTbExample example=new SapXsddCqTbExample();
		if(xspz!=null && !"".equals(xspz)){
			Criteria criteria =example.createCriteria();
			criteria.andXspzEqualTo(xspz);
		}
		// 分页处理
		PageHelper.startPage(page, rows);
		List<SapXsddCqTb> item=sapXsddCqTbMapper.selectByExample(example);
		// 创建一个返回值对象
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(item);
		// 取记录总条数
		PageInfo<SapXsddCqTb> pageInfo = new PageInfo<>(item);
		result.setTotal(pageInfo.getTotal());
		return result;
	}

	@Override
	public int updateSapXsddCqTb(SapXsddCqTb sapXsddCqTb) {
		int i=sapXsddCqTbMapper.updateByPrimaryKey(sapXsddCqTb);
		return i;
	}

	@Override
	public int deleteSapXsddCqTb(List<String> id) {
		SapXsddCqTbExample example=new SapXsddCqTbExample();
		Criteria criteria=example.createCriteria();
		criteria.andIdIn(id);
		int i=sapXsddCqTbMapper.deleteByExample(example);
		return i;
	}

}

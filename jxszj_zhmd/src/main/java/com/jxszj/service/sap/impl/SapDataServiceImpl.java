package com.jxszj.service.sap.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxszj.mapper.sap.SapDataTbMapper;
import com.jxszj.pojo.EUDataGridResult;
import com.jxszj.pojo.sap.SapDataTb;
import com.jxszj.pojo.sap.SapDataTbExample;
import com.jxszj.pojo.sap.SapDataTbExample.Criteria;
import com.jxszj.service.sap.ISapDataService;
@Service
public class SapDataServiceImpl implements ISapDataService {
	
	@Autowired
	private SapDataTbMapper sapDataTbMapper;

	@Override
	public EUDataGridResult getSapDataList(int page, int rows,String bmz) {
		SapDataTbExample example=new SapDataTbExample();
		Criteria criteria=example.createCriteria();
		if(bmz!=null&&!("".equals(bmz))){
			criteria.andBmzLike("%"+bmz+"%");
		}
		// 分页处理
		PageHelper.startPage(page, rows);
		List<SapDataTb> item=sapDataTbMapper.selectByExample(example);
		// 创建一个返回值对象
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(item);
		// 取记录总条数
		PageInfo<SapDataTb> pageInfo = new PageInfo<>(item);
		result.setTotal(pageInfo.getTotal());
		return result;
	}
	
	@Override
	public List<SapDataTb> getSapDataLists(String bmz,String gzs) {
		SapDataTbExample example=new SapDataTbExample();
		Criteria criteria=example.createCriteria();
		criteria.andBmzEqualTo(bmz);
		if(gzs==null || "".equals(gzs)){
			criteria.andGzsEqualTo(bmz);
		}else{
			criteria.andGzsLike("%"+gzs+"%");
		}
		List<SapDataTb> item=sapDataTbMapper.selectByExample(example);
		return item;
	}

	@Override
	public int insertSapData(SapDataTb sapData) {
		int i=sapDataTbMapper.insert(sapData);
		return i;
	}

	@Override
	public int updateSapData(SapDataTb sapData) {
		int i=sapDataTbMapper.updateByPrimaryKey(sapData);
		return i;
	}

	@Override
	public int deleteSapData(List<Integer> id) {
		SapDataTbExample example=new SapDataTbExample();
		Criteria criteria=example.createCriteria();
		criteria.andIdIn(id);
		int i=sapDataTbMapper.deleteByExample(example);
		return i;
	}

}

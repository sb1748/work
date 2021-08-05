package com.jxszj.service.sap.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxszj.mapper.sap.SapXsddpdscTbMapper;
import com.jxszj.pojo.EUDataGridResult;
import com.jxszj.pojo.sap.SapXsddpdscTb;
import com.jxszj.pojo.sap.SapXsddpdscTbExample;
import com.jxszj.pojo.sap.SapXsddpdscTbExample.Criteria;
import com.jxszj.service.sap.IPdscService;

@Service
public class PdscServiceImpl implements IPdscService {
	
	@Autowired
	private SapXsddpdscTbMapper sapXsddpdscTbMapper;
	
	
	@Override
	public EUDataGridResult getPdscView(int page, int rows,String pdrq) {
		SapXsddpdscTbExample example = new SapXsddpdscTbExample();
		Criteria criteria=example.createCriteria();
		if(pdrq!=null && !"".equals(pdrq)){
			criteria.andPdrqLike(pdrq+"%");
		}
		example.setOrderByClause("pdrq DESC");
		// 分页处理
		PageHelper.startPage(page, rows);
		List<SapXsddpdscTb> item = sapXsddpdscTbMapper.selectByExample(example);
		// 创建一个返回值对象
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(item);
		// 取记录总条数
		PageInfo<SapXsddpdscTb> pageInfo = new PageInfo<>(item);
		result.setTotal(pageInfo.getTotal());
		return result;
	}

}

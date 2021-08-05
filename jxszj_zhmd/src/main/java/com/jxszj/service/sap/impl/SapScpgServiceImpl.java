package com.jxszj.service.sap.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jxszj.mapper.sap.SapScpgTbMapper;
import com.jxszj.pojo.sap.SapScpgTb;
import com.jxszj.pojo.sap.SapScpgTbExample;
import com.jxszj.pojo.sap.SapScpgTbExample.Criteria;
import com.jxszj.service.sap.ISapScpgService;

@Service
public class SapScpgServiceImpl implements ISapScpgService {

	@Autowired
	private SapScpgTbMapper sapScpgTbMapper;
	
	@Override
	public int saveOrUpdate(List<SapScpgTb> record) {
		int num=sapScpgTbMapper.saveOrUpdate(record);
		return num;
	}

	@Override
	public List<SapScpgTb> getSapScpgTb(String id) {
		SapScpgTbExample example=new SapScpgTbExample();
		Criteria criteria=example.createCriteria();
		criteria.andIdEqualTo(id);
		List<SapScpgTb> sapScpgTbs=sapScpgTbMapper.selectByExample(example);
		return sapScpgTbs;
	}

}

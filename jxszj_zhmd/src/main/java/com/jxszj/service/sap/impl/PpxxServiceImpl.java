package com.jxszj.service.sap.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jxszj.mapper.sap.PpxxTbMapper;
import com.jxszj.pojo.sap.PpxxTb;
import com.jxszj.pojo.sap.PpxxTbExample;
import com.jxszj.service.sap.IPpxxService;

@Service
public class PpxxServiceImpl implements IPpxxService {

	@Autowired
	private PpxxTbMapper ppxxTbMapper;
	

	@Override
	public List<PpxxTb> getPpxxLists() {
		PpxxTbExample example=new PpxxTbExample();
		List<PpxxTb> list=ppxxTbMapper.selectByExample(example);
		return list;
	}

	

}

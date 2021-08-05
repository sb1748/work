package com.jxszj.service.sap.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxszj.mapper.sap.SapPriceTbMapper;
import com.jxszj.pojo.EUDataGridResult;
import com.jxszj.pojo.sap.SapPriceTb;
import com.jxszj.pojo.sap.SapPriceTbExample;
import com.jxszj.pojo.sap.SapPriceTbExample.Criteria;
import com.jxszj.service.sap.IPriceService;

@Service
public class PriceServiceImpl implements IPriceService {
	
	@Autowired
	private SapPriceTbMapper sapPriceTbMapper;

	@Override
	public EUDataGridResult getPricePage(int page, int rows, String priceMaterial,String priceCjr,String priceTbzt) {
		SapPriceTbExample example = new SapPriceTbExample();
		Criteria criteria=example.createCriteria();
		if(priceMaterial!=null && !"".equals(priceMaterial)){
			criteria.andWlbmEqualTo(priceMaterial);
		}
		if(priceCjr!=null && !"".equals(priceCjr)){
			criteria.andCjrEqualTo(priceCjr);
		}
		if(priceTbzt!=null && !"".equals(priceTbzt)){
			criteria.andTbztEqualTo(priceTbzt);
		}
		// 分页处理
		PageHelper.startPage(page, rows);
		List<SapPriceTb> item = sapPriceTbMapper.selectByExample(example);
		// 创建一个返回值对象
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(item);
		// 取记录总条数
		PageInfo<SapPriceTb> pageInfo = new PageInfo<>(item);
		result.setTotal(pageInfo.getTotal());
		return result;
	}

	@Override
	public int updaePrice(String id) {
		int i =sapPriceTbMapper.updateTbzt(id);
		return i;
	}

	@Override
	public int addSAPPrices(List<SapPriceTb> sapPriceTbs) {
		int i=sapPriceTbMapper.insertByBatch(sapPriceTbs);
		return i;
	}

	@Override
	public SapPriceTb getById(String id) {
		SapPriceTb sapPriceTb=sapPriceTbMapper.selectByPrimaryKey(id);
		return sapPriceTb;
	}

	@Override
	public List<SapPriceTb> getPrices(SapPriceTbExample example) {
		List<SapPriceTb> sapPriceTb = sapPriceTbMapper.selectByExample(example);
		return sapPriceTb;
	}

	@Override
	public int updaePriceById(SapPriceTb sapPriceTb) {
		int i=sapPriceTbMapper.updateByPrimaryKey(sapPriceTb);
		return i;
	}

	@Override
	public SapPriceTb findById(String id) {
		SapPriceTb  sapPriceTb =sapPriceTbMapper.selectByPrimaryKey(id);
		return sapPriceTb;
	}

	@Override
	public int addSAPPrice(SapPriceTb sapPriceTb) {
		int i=sapPriceTbMapper.insert(sapPriceTb);
		return i;
	}

}

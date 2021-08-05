package com.jxszj.service.sap;

import java.util.List;


import com.jxszj.pojo.EUDataGridResult;
import com.jxszj.pojo.sap.SapPriceTb;
import com.jxszj.pojo.sap.SapPriceTbExample;

public interface IPriceService {
	
	public EUDataGridResult getPricePage(int page, int rows, String priceMaterial,String priceCjr,String priceTbzt);
	
	public int updaePrice(String id);
	
	public SapPriceTb getById(String id);
	
	public int addSAPPrices(List<SapPriceTb> sapPriceTbs);
	
	public List<SapPriceTb> getPrices(SapPriceTbExample example);
	
	public int updaePriceById(SapPriceTb sapPriceTb);
	
	public SapPriceTb findById(String id);
	
	public  int addSAPPrice(SapPriceTb sapPriceTb);
	
}

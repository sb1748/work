package com.jxszj.quartz;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jxszj.pojo.sap.SapPriceTb;
import com.jxszj.pojo.sap.SapPriceTbExample;
import com.jxszj.pojo.sap.SapPriceTbExample.Criteria;
import com.jxszj.service.sap.IPriceService;
import com.jxszj.utils.DateUtils;
import com.jxszj.utils.JDYAPIUtils;


/**
 * 
 * <pre>
 * <b>.</b>
 * <b>Description:</b> 
 *    将SAP中的价格主数据同步至简道云价格表信息中
 * <b>Author:</b> yanwei
 * <b>Date:</b> 2020年3月30日下午1:49:59
 * </pre>
 */
public class SapPriceSendJdyPriceQuarte {
	
	@Autowired
	private IPriceService priceService;
	
	// POS经销商之家 -- 经销商供货价格接口同步
	private static final String JXSZJ_APPID = "5cc110c3b3c41744aaa12b2e";
	private static final  String JXSZJ_ENTRYID = "5e848d96f4cca90006acb5ff";
	private static final  String JXSZJ_APIKEY = "uWEAujuxvzv5fMVifOvMRzlIJcPLgYkv";
	
	
	public void execute() {
		try {
			
			List<SapPriceTb> list=new ArrayList<>();
			//目前订单人员稳定，人数不多，就直接写写死，当人数多不稳定的时候再移到数据库进行配置
			Map<String,String> map=new HashMap<>();
			map.put("CB9980000033", "易瑾");
			map.put("CB9980000035", "吴灿");
			map.put("CB9980000012", "黄凤娇");
			map.put("CB9980000081", "沈梦婷");
			map.put("CB9980000230", "黄苑怡");
			map.put("CB9980000000", "无");
			map.put("CB9980000112", "刘勇");
			//存放物料描述
			Map<String,String> mapWl=new HashMap<>();
			
			//获取失效日期为当前日期前一天的价格数据
			JSONArray  jSONArray=getJSONArray("https://my300201-api.saps4hanacloud.cn/sap/opu/odata/sap/API_SLSPRICINGCONDITIONRECORD_SRV/A_SlsPrcgCndnRecdValidity?$filter=ConditionValidityEndDate%20eq%20datetime'"+DateUtils.getBeforeTwo()+"T00:00:00'%20and%20DistributionChannel%20eq%20'01'%20and%20ConditionType%20ne%20'DM01'%20and%20SalesOrganization%20eq%20'C001'");
			for (int i = 0; i < jSONArray.size(); i++) {
				JSONArray  jSONArray1=getJSONArray("https://my300201-api.saps4hanacloud.cn/sap/opu/odata/sap/API_SLSPRICINGCONDITIONRECORD_SRV/A_SlsPrcgConditionRecord?$filter=ConditionRecord%20eq%20'"+jSONArray.getJSONObject(i).get("ConditionRecord")+"'");	
				if(jSONArray1.size()==0){
					continue;
				}
				String wlms="";
				if(mapWl.get(jSONArray.getJSONObject(i).getString("Material"))!=null){
					wlms=mapWl.get(jSONArray.getJSONObject(i).getString("Material"));
				}else{
					JSONObject json=getJSONObject("https://my300201-api.saps4hanacloud.cn/sap/opu/odata/sap/API_PRODUCT_SRV/A_ProductDescription(Product='"+jSONArray.getJSONObject(i).getString("Material")+"',Language='ZH')");
					wlms=json.getString("ProductDescription");
					mapWl.put(jSONArray.getJSONObject(i).getString("Material"), wlms);
				}
				
				SapPriceTb sapPriceTb=getPrice(jSONArray.getJSONObject(i),jSONArray1.getJSONObject(0),map,wlms);
				list.add(sapPriceTb);
			}
			//获取当天新建的价格数据
			jSONArray=getJSONArray("https://my300201-api.saps4hanacloud.cn/sap/opu/odata/sap/API_SLSPRICINGCONDITIONRECORD_SRV/A_SlsPrcgConditionRecord?$filter=CreationDate%20eq%20datetime'"+DateUtils.getNowDateToString()+"T00:00:00'%20and%20ConditionType%20ne%20'DM01'");
			for (int i = 0; i < jSONArray.size(); i++) {
				JSONArray jSONArray1=getJSONArray("https://my300201-api.saps4hanacloud.cn/sap/opu/odata/sap/API_SLSPRICINGCONDITIONRECORD_SRV/A_SlsPrcgCndnRecdValidity?$filter=ConditionRecord%20eq%20'"+jSONArray.getJSONObject(i).get("ConditionRecord")+"'%20and%20DistributionChannel%20eq%20'01'%20and%20SalesOrganization%20eq%20'C001'");
				if(jSONArray1.size()==0){
					continue;
				}
				String wlms="";
				if(mapWl.get(jSONArray1.getJSONObject(0).getString("Material"))!=null){
					wlms=mapWl.get(jSONArray1.getJSONObject(0).getString("Material"));
				}else{
					JSONObject json=getJSONObject("https://my300201-api.saps4hanacloud.cn/sap/opu/odata/sap/API_PRODUCT_SRV/A_ProductDescription(Product='"+jSONArray1.getJSONObject(0).getString("Material")+"',Language='ZH')");
					wlms=json.getString("ProductDescription");
					mapWl.put(jSONArray1.getJSONObject(0).getString("Material"), wlms);
				}
				SapPriceTb sapPriceTb=getPrice(jSONArray1.getJSONObject(0),jSONArray.getJSONObject(i),map,wlms);
				list.add(sapPriceTb);
			}
			//先保存到数据库
//			priceService.addSAPPrices(list);
			for (int j = 0; j < list.size(); j++) {
				SapPriceTb sapPriceTb =priceService.findById(list.get(j).getId());
				if(sapPriceTb==null){
					priceService.addSAPPrice(list.get(j));
				}else{
					if(!sapPriceTb.equals(list.get(j))){
						priceService.updaePriceById(list.get(j));
					}
				}
			}
			
			JDYAPIUtils api = new JDYAPIUtils(JXSZJ_APPID, JXSZJ_ENTRYID, JXSZJ_APIKEY);
			//先查询失效未同步的数据同步到简道云
			SapPriceTbExample example =new SapPriceTbExample();
			Criteria criteria=example.createCriteria();
			criteria.andSxztEqualTo("失效");
			criteria.andTbztEqualTo("未同步");
			List<SapPriceTb> sapPriceTbs=priceService.getPrices(example);
			sendJDY(sapPriceTbs,api);
			for (int i = 0; i < sapPriceTbs.size(); i++) {
				SapPriceTb sapPriceTb=sapPriceTbs.get(i);
				sapPriceTb.setTbzt("已同步");
				priceService.updaePriceById(sapPriceTb);
			}
			
			//再查询生效未同步的数据
			example =new SapPriceTbExample();
			criteria=example.createCriteria();
			criteria.andSxztEqualTo("生效");
			criteria.andTbztEqualTo("未同步");
			sapPriceTbs=priceService.getPrices(example);
			sendJDY(sapPriceTbs,api);
			for (int i = 0; i < sapPriceTbs.size(); i++) {
				SapPriceTb sapPriceTb=sapPriceTbs.get(i);
				sapPriceTb.setTbzt("已同步");
				priceService.updaePriceById(sapPriceTb);
			}
			
			//查询未生效未同步的数据
			example =new SapPriceTbExample();
			criteria=example.createCriteria();
			criteria.andSxztEqualTo("未生效");
			criteria.andTbztEqualTo("未同步");
			criteria.andYxksrqEqualTo(DateUtils.getAfterDay());
			sapPriceTbs=priceService.getPrices(example);
			sendJDY(sapPriceTbs,api);
			for (int i = 0; i < sapPriceTbs.size(); i++) {
				SapPriceTb sapPriceTb=sapPriceTbs.get(i);
				sapPriceTb.setSxzt("生效");
				sapPriceTb.setTbzt("已同步");
				priceService.updaePriceById(sapPriceTb);
			}
			
			//查询有效期至是当天，但数据库里面显示的是生效的数据
			example =new SapPriceTbExample();
			criteria=example.createCriteria();
			criteria.andSxztEqualTo("生效");
			criteria.andYxjsrqEqualTo(DateUtils.getNowDateToString());
			sapPriceTbs=priceService.getPrices(example);
			for (int i = 0; i < sapPriceTbs.size(); i++) {
				SapPriceTb sapPriceTb=sapPriceTbs.get(i);
				sapPriceTb.setSxzt("失效");
				sapPriceTb.setTbzt("已同步");
				priceService.updaePriceById(sapPriceTb);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sendJDY(List<SapPriceTb> sapPriceTbs,JDYAPIUtils api){
		for (int i = 0; i < sapPriceTbs.size(); i++) {
			//根据条件记录编号查询
			final List<Map<String, Object>> condList = new ArrayList<Map<String, Object>>();
			Map<String,Object> map=new HashMap<>();
			map.put("field", "_widget_1598944894560");//条件记录编号
			map.put("type", "text");
			map.put("method", "eq");
			map.put("value", sapPriceTbs.get(i).getId());
			condList.add(map);
			Map<String, Object> filter = new HashMap<String, Object>() {
				{
					put("rel", "and");
					put("cond", condList);
				}
			};
			List<Map<String, Object>> formData = api.getAllFormData(null, filter);
			for (int j = 0; j < formData.size(); j++) {
				api.deleteData(formData.get(j).get("_id").toString());
			}
			if("生效".equals(sapPriceTbs.get(i).getSxzt())){
				api.createData(getData(sapPriceTbs.get(i)));
				priceService.updaePrice(sapPriceTbs.get(i).getId());
			}else if("未生效".equals(sapPriceTbs.get(i).getSxzt())){
				api.createData(getData(sapPriceTbs.get(i)));
			}
		}
	}
	
	public Map<String, Object> getData(SapPriceTb  sapPriceTb){
		Map<String, Object> rawData = new HashMap<String, Object>();
		Map<String, Object> m1 = new HashMap<String, Object>();
		m1.put("value", sapPriceTb.getKhjgz());
		rawData.put("_widget_1585745302686", m1);// 客户价格组
		Map<String, Object> m2 = new HashMap<String, Object>();
		m2.put("value", sapPriceTb.getKhbm());
		rawData.put("_widget_1585745302701", m2);// 客户编码
		Map<String, Object> m3 = new HashMap<String, Object>();
		m3.put("value", sapPriceTb.getWlbm());
		rawData.put("_widget_1585745302716", m3);// 物料编码
		Map<String, Object> m4 = new HashMap<String, Object>();
		m4.put("value", sapPriceTb.getDjzk());
		rawData.put("_widget_1585745302731", m4);// 单价
		Map<String, Object> m5 = new HashMap<String, Object>();
		m5.put("value", sapPriceTb.getDw());
		rawData.put("_widget_1585901362201", m5);// 单位
		Map<String, Object> m6 = new HashMap<String, Object>();
		m6.put("value", sapPriceTb.getBz());
		rawData.put("_widget_1585901362080", m6);// 币种
		Map<String, Object> m7 = new HashMap<String, Object>();
		m7.put("value",sapPriceTb.getTjlx());
		rawData.put("_widget_1586482329032", m7);//条件类型
		if("PPR0".equals(sapPriceTb.getTjlx())){ //PPR0标识
			Map<String, Object> m8 = new HashMap<String, Object>();
			m8.put("value",sapPriceTb.getDjbs());
			rawData.put("_widget_1586499946999", m8);//
		}else if("YK07".equals(sapPriceTb.getTjlx())){ //YK07标识
			Map<String, Object> m8 = new HashMap<String, Object>();
			m8.put("value",sapPriceTb.getZkbs());
			rawData.put("_widget_1586499946984", m8);//
		}
		Map<String, Object> m9 = new HashMap<String, Object>();
		m9.put("value",sapPriceTb.getXszz());
		rawData.put("_widget_1586499946729", m9);//销售组织
		Map<String, Object> m10 = new HashMap<String, Object>();
		m10.put("value",sapPriceTb.getFxqd());
		rawData.put("_widget_1586499946714", m10);//分销渠道
		
		Map<String, Object> m11 = new HashMap<String, Object>();
		m11.put("value",sapPriceTb.getWyj());
		rawData.put("_widget_1591845499086", m11);//唯一码
		
		Map<String, Object> m12 = new HashMap<String, Object>();
		m12.put("value",sapPriceTb.getYxksrq());
		rawData.put("_widget_1598165436554", m12);//有效期开始时间
		
		Map<String, Object> m13 = new HashMap<String, Object>();
		m13.put("value",sapPriceTb.getYxjsrq());
		rawData.put("_widget_1598165436576", m13);//有效期结束时间
		
		Map<String, Object> m14 = new HashMap<String, Object>();
		m14.put("value",sapPriceTb.getId());
		rawData.put("_widget_1598944894560", m14);//条件记录编号
		return rawData;
	}
	
	public SapPriceTb getPrice(JSONObject  j1,JSONObject  j2,Map<String,String> map,String wlms) throws ParseException{
		SapPriceTb sapPriceTb=new SapPriceTb();
		sapPriceTb.setKhjgz(j1.getString("CustomerPriceGroup"));
		sapPriceTb.setKhbm(j1.getString("Customer"));
		sapPriceTb.setWlbm(j1.getString("Material"));
		sapPriceTb.setDjzk(j2.getString("ConditionRateValue"));
		sapPriceTb.setDw(j2.getString("ConditionQuantityUnit"));
		sapPriceTb.setBz(j2.getString("ConditionRateValueUnit"));
		sapPriceTb.setTjlx(j1.getString("ConditionType"));
		if("PPR0".equals(j1.getString("ConditionType"))){ //PPR0标识
			if(!"".equals(j1.getString("Customer")) && j1.getString("Customer")!=null){
				sapPriceTb.setDjbs(j1.getString("SalesOrganization")+j1.getString("DistributionChannel")+j1.getString("Customer")+j1.getString("Material"));
			}else if(!"".equals(j1.getString("CustomerPriceGroup")) && j1.getString("CustomerPriceGroup")!=null){
				sapPriceTb.setDjbs(j1.getString("SalesOrganization")+j1.getString("DistributionChannel")+j1.getString("CustomerPriceGroup")+j1.getString("Material"));
			}else{
				sapPriceTb.setDjbs(j1.getString("SalesOrganization")+j1.getString("DistributionChannel")+j1.getString("Material"));
			}
			sapPriceTb.setZkbs("");
		}else if("YK07".equals(j1.getString("ConditionType"))){ //YK07标识
			if(!"".equals(j1.getString("Customer")) && !"".equals(j1.getString("Material"))){
				sapPriceTb.setZkbs(j1.getString("SalesOrganization")+j1.getString("DistributionChannel")+j1.getString("Customer")+j1.getString("Material"));
			}else if(!"".equals(j1.getString("CustomerPriceGroup")) && !"".equals(j1.getString("Material"))){
				sapPriceTb.setZkbs(j1.getString("SalesOrganization")+j1.getString("DistributionChannel")+j1.getString("CustomerPriceGroup")+j1.getString("Material"));
			}
			sapPriceTb.setDjbs("");
		}
		sapPriceTb.setXszz(j1.getString("SalesOrganization"));
		sapPriceTb.setFxqd(j1.getString("DistributionChannel"));
		
		String wyj=j1.getString("ConditionType")+j1.getString("SalesOrganization")+j1.getString("DistributionChannel")+(j1.getString("Customer")==null?"":j1.getString("Customer"))+(j1.getString("CustomerPriceGroup")==null?"":j1.getString("CustomerPriceGroup"))+j1.getString("Material");
		sapPriceTb.setWyj(wyj);
		sapPriceTb.setYxksrq(DateUtils.getLongToString(j1.getString("ConditionValidityStartDate"),DateUtils.FORMAT_1_STRING));
		
		sapPriceTb.setYxjsrq(DateUtils.getLongToString(j1.getString("ConditionValidityEndDate"),DateUtils.FORMAT_1_STRING));
		sapPriceTb.setId(j1.getString("ConditionRecord"));
		sapPriceTb.setCjrq(DateUtils.getLongToString(j2.getString("CreationDate"), DateUtils.FORMAT_1_STRING));
		
		if(DateUtils.compareDateString1(DateUtils.getNowDateToString(), DateUtils.getLongToString(j1.getString("ConditionValidityEndDate"),DateUtils.FORMAT_1_STRING))){
			sapPriceTb.setSxzt("失效");
		}else if(DateUtils.compareDateString(DateUtils.getNowDateToString(), DateUtils.getLongToString(j1.getString("ConditionValidityStartDate"),DateUtils.FORMAT_1_STRING)) && 
				DateUtils.compareDateString(DateUtils.getLongToString(j1.getString("ConditionValidityEndDate"),DateUtils.FORMAT_1_STRING),DateUtils.getNowDateToString())){
			sapPriceTb.setSxzt("生效");
		}else if(DateUtils.compareDateString1(DateUtils.getLongToString(j1.getString("ConditionValidityStartDate"),DateUtils.FORMAT_1_STRING),DateUtils.getNowDateToString())){
			sapPriceTb.setSxzt("未生效");
		}
		sapPriceTb.setTbzt("未同步");
		sapPriceTb.setCjr(map.get(j2.getString("CreatedByUser")));
		sapPriceTb.setWlms(wlms);
		return sapPriceTb;
	}
	
	
	public JSONArray getJSONArray(String url){
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();   
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse response = null;
		JSONArray  array=null;
		try {
			httpGet.setHeader("Content-Type","application/json");
			httpGet.setHeader("Accept","application/json");
			httpGet.setHeader("Authorization", "Basic SkRZVVNFUjpIZmpUUD5UZ2ZxUU10SEVnaFpiVXhtZ2VOdnJmZlh3OUNvQm5BVGps");
			// 由客户端执行(发送)Get请求
			response = httpClient.execute(httpGet);
			// 从响应模型中获取响应实体
			HttpEntity responseEntity = response.getEntity();
			if(response.getStatusLine().getStatusCode()==200){
				JSONObject jsonObject=JSON.parseObject(EntityUtils.toString(responseEntity));
				jsonObject = jsonObject.getJSONObject("d");
				array = jsonObject.getJSONArray("results");
			}else if(response.getStatusLine().getStatusCode()!=200){
				array=new JSONArray();
				array.add(response.getStatusLine().getStatusCode());
				return array;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return array;
	}
	
	public JSONObject getJSONObject(String url){
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();   
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse response = null;
		JSONObject jsonObject=null;
		try {
			httpGet.setHeader("Content-Type","application/json");
			httpGet.setHeader("Accept","application/json");
			httpGet.setHeader("Authorization", "Basic SkRZVVNFUjpIZmpUUD5UZ2ZxUU10SEVnaFpiVXhtZ2VOdnJmZlh3OUNvQm5BVGps");
			// 由客户端执行(发送)Get请求
			response = httpClient.execute(httpGet);
			// 从响应模型中获取响应实体
			HttpEntity responseEntity = response.getEntity();
			if(response.getStatusLine().getStatusCode()==200){
				jsonObject=JSON.parseObject(EntityUtils.toString(responseEntity));
				jsonObject = jsonObject.getJSONObject("d");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
}

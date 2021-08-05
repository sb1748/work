package com.jxszj.controller.sap;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jxszj.pojo.EUDataGridResult;
import com.jxszj.pojo.sap.SapPriceTb;
import com.jxszj.service.sap.IPriceService;
import com.jxszj.utils.DateUtils;
import com.jxszj.utils.JDYAPIUtils;
import com.jxszj.utils.MessageResult;


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
@Controller
@RequestMapping("/price")
public class SapPriceSendJdyPriceController {
	
	@Autowired
	private IPriceService priceService;
	
	static String status="true";
	
	static String tb="true";
	
	// POS经销商之家 -- 经销商供货价格接口同步
	private static final String JXSZJ_APPID = "5cc110c3b3c41744aaa12b2e";
	private static final  String JXSZJ_ENTRYID = "5e848d96f4cca90006acb5ff";
	private static final  String JXSZJ_APIKEY = "uWEAujuxvzv5fMVifOvMRzlIJcPLgYkv";
	
//	@RequestMapping("/getPrice")
//	@ResponseBody
//	public MessageResult getPrice(HttpServletRequest request) {
//		
//		if(status.equals("false")){
//			return MessageResult.build(500, "系统检测到正在同步价格，请等待...");
//		}
//		status="false";
//		
//		// POS经销商之家 -- 经销商供货价格接口同步
//		String jxszj_appId = "5cc110c3b3c41744aaa12b2e";
//		String jxszj_entryId = "5e848d96f4cca90006acb5ff";
//		String jxszj_apiKey = "uWEAujuxvzv5fMVifOvMRzlIJcPLgYkv";
//		
//		// POS经销商之家 -- 经销商供货价格_副表
//		String a_entryId = "5f4dfa0a6c18270006e4c29f";//副表
//		
//		JDYAPIUtils jxszj_api = new JDYAPIUtils(jxszj_appId, jxszj_entryId, jxszj_apiKey);
//		
//		JDYAPIUtils a_api = new JDYAPIUtils(jxszj_appId, a_entryId, jxszj_apiKey);
//		try{
//			List<String> recordNumbers=new ArrayList<String>();
//			String recordNumber=request.getParameter("recordNumber");
//			if(recordNumber.contains("，") || recordNumber.contains(",")){
//				recordNumber=recordNumber.replace("，", ",").replace(" ", "");
//				recordNumbers=Arrays.asList(recordNumber.split(","));
//			}else{
//				recordNumbers.add(recordNumber);
//			}
//			List<String> list=new ArrayList<String>();
//			
//			for (int i = 0; i < recordNumbers.size(); i++) {
//				JSONArray  jSONArray=getJSONArray("https://my300201-api.saps4hanacloud.cn/sap/opu/odata/sap/API_SLSPRICINGCONDITIONRECORD_SRV/A_SlsPrcgCndnRecdValidity?$filter=Material%20eq%20'"+recordNumbers.get(i)+"'%20and%20DistributionChannel%20eq%20'01'");
//				if(jSONArray==null){
//					status="true";
//					return MessageResult.build(500, "更新价格出错！");
//				}else if(jSONArray.size()==0){
//					list.add(recordNumbers.get(i));
//					continue;
//				}
//				//先删除简道云当前物料的价格
//				final List<Map<String, Object>> condList = new ArrayList<Map<String, Object>>();
//				Map<String,Object> map=new HashMap<>();
//				map.put("field", "_widget_1585745302716");
//				map.put("type", "text");
//				map.put("method", "eq");
//				map.put("value", recordNumbers.get(i));
//				condList.add(map);
//				Map<String, Object> filter = new HashMap<String, Object>() {
//					{
//						put("rel", "and");
//						put("cond", condList);
//					}
//				};
//				List<Map<String, Object>> formData = jxszj_api.getAllFormData(null, filter);
//				for (int j = 0; j < formData.size(); j++) {
//					jxszj_api.deleteData(formData.get(j).get("_id").toString());
//				}
//				//再重新添加价格
//				for (int j = 0; j < jSONArray.size(); j++) {
//					if(Long.parseLong(DateUtils.getLongToString(jSONArray.getJSONObject(j).getString("ConditionValidityStartDate"),DateUtils.FORMAT_INTEGER))<=Long.parseLong(DateUtils.getNowDateToString(DateUtils.FORMAT_INTEGER)) && 
//							Long.parseLong(DateUtils.getLongToString(jSONArray.getJSONObject(j).getString("ConditionValidityEndDate"),DateUtils.FORMAT_INTEGER))>=Long.parseLong(DateUtils.getNowDateToString(DateUtils.FORMAT_INTEGER))){
//						JSONArray  jSONArray1=getJSONArray("https://my300201-api.saps4hanacloud.cn/sap/opu/odata/sap/API_SLSPRICINGCONDITIONRECORD_SRV/A_SlsPrcgConditionRecord?$filter=ConditionRecord%20eq%20'"+jSONArray.getJSONObject(j).get("ConditionRecord")+"'%20and%20ConditionIsDeleted%20eq%20false");	
//						if(jSONArray1==null || jSONArray1.size()==0){
//							continue;
//						}
//						if(jSONArray.getJSONObject(j).getString("ConditionType").equals("PPR0") || jSONArray.getJSONObject(j).getString("ConditionType").equals("YK07")){
//							Map<String, Object> rawData=getData(jSONArray.getJSONObject(j),jSONArray1.getJSONObject(0));
//							jxszj_api.createForData(rawData);
//						}
//					}else if(Long.parseLong(DateUtils.getLongToString(jSONArray.getJSONObject(j).getString("ConditionValidityStartDate"),DateUtils.FORMAT_INTEGER))>Long.parseLong(DateUtils.getNowDateToString(DateUtils.FORMAT_INTEGER))){
//						JSONArray  jSONArray1=getJSONArray("https://my300201-api.saps4hanacloud.cn/sap/opu/odata/sap/API_SLSPRICINGCONDITIONRECORD_SRV/A_SlsPrcgConditionRecord?$filter=ConditionRecord%20eq%20'"+jSONArray.getJSONObject(j).get("ConditionRecord")+"'%20and%20ConditionIsDeleted%20eq%20false");	
//						if(jSONArray1==null || jSONArray1.size()==0){
//							continue;
//						}
//						if(jSONArray.getJSONObject(j).getString("ConditionType").equals("PPR0") || jSONArray.getJSONObject(j).getString("ConditionType").equals("YK07")){
//							Map<String, Object> rawData=getData(jSONArray.getJSONObject(j),jSONArray1.getJSONObject(0));
//							String id=jSONArray.getJSONObject(j).getString("ConditionType")+jSONArray.getJSONObject(j).getString("SalesOrganization")+jSONArray.getJSONObject(j).getString("DistributionChannel")+(jSONArray.getJSONObject(j).getString("Customer")==null?"":jSONArray.getJSONObject(j).getString("Customer"))+(jSONArray.getJSONObject(j).getString("CustomerPriceGroup")==null?"":jSONArray.getJSONObject(j).getString("CustomerPriceGroup"))+jSONArray.getJSONObject(j).getString("Material");
//							//先查询
//							final List<Map<String, Object>> condList1 = new ArrayList<Map<String, Object>>();
//							condList1.add(new HashMap<String, Object>() {
//								{
//									put("field", "_widget_1591845499086");
//									put("type", "text");
//									put("method", "eq");
//									put("value", id);
//								}
//							});
//							Map<String, Object> filter1 = new HashMap<String, Object>() {
//								{
//									put("rel", "and");
//									put("cond", condList1);
//								}
//							};
//							List<Map<String, Object>> formData1 = a_api.getAllFormData(null, filter1);
//							if(formData1.size()==0){
//								a_api.createForData(rawData);
//							}else if(formData1.size()==1){
//								a_api.updateForData(formData1.get(0).get("_id").toString(), rawData);
//							}
//							
//						}
//					}
//					
//				}
//			}
//			if(list.size()>0){
//				status="true";
//				return MessageResult.build(500, "同步更新完成,SAP中不存在物料"+list);
//			}
//		} catch (Exception e) {
//			status="true";
//			e.printStackTrace();
//			return MessageResult.build(400, e.getMessage());
//		}
//		status="true";
//		return MessageResult.build(200, "同步更新完成");
//	}
	
	
	@RequestMapping("/delPrice")
	@ResponseBody
	public MessageResult delPrice(HttpServletRequest request) {
		// POS经销商之家 -- 经销商供货价格接口同步
		String jxszj_appId = "5cc110c3b3c41744aaa12b2e";
		String jxszj_entryId = "5e848d96f4cca90006acb5ff";
		String jxszj_apiKey = "uWEAujuxvzv5fMVifOvMRzlIJcPLgYkv";
		
		// 简道云智慧门店系统
//		String zhmd_appId = "5c6fa6da7eacb23f3daf642e";
//		String zhmd_entryId = "5ee334d36c17d20006aaa0ba";
//		String zhmd_apiKey = "O3nyFjunjn5neMk0c4cfEogVLFFXqHfU";
		
		JDYAPIUtils jxszj_api = new JDYAPIUtils(jxszj_appId, jxszj_entryId, jxszj_apiKey);
		
//		JDYAPIUtils zhmd_api = new JDYAPIUtils(zhmd_appId, zhmd_entryId, zhmd_apiKey);
		try{
			List<String> recordNumbers=new ArrayList<String>();
			String recordNumber=request.getParameter("recordNumber");
			if(recordNumber.contains("，") || recordNumber.contains(",")){
				recordNumber=recordNumber.replace("，", ",");
				recordNumbers=Arrays.asList(recordNumber.split(","));
			}else{
				recordNumbers.add(recordNumber);
			}
			List<String> list=new ArrayList<String>();
			for (int i = 0; i < recordNumbers.size(); i++) {
				JSONArray  jSONArray=getJSONArray("https://my300201-api.saps4hanacloud.cn/sap/opu/odata/sap/API_SLSPRICINGCONDITIONRECORD_SRV/A_SlsPrcgConditionRecord('"+recordNumbers.get(i)+"')/to_SlsPrcgCndnRecdValidity");
				if(jSONArray.size()==0){
					continue;
				}else if(jSONArray.size()>1){
					list.add(recordNumbers.get(i));
					continue;
				}
				
				
				String id=jSONArray.getJSONObject(0).getString("SalesOrganization")+jSONArray.getJSONObject(0).getString("DistributionChannel")+(jSONArray.getJSONObject(0).getString("Customer")==null?"":jSONArray.getJSONObject(0).getString("Customer"))+(jSONArray.getJSONObject(0).getString("CustomerPriceGroup")==null?"":jSONArray.getJSONObject(0).getString("CustomerPriceGroup"))+jSONArray.getJSONObject(0).getString("Material");
				//先查询
				final List<Map<String, Object>> condList = new ArrayList<Map<String, Object>>();
				condList.add(new HashMap<String, Object>() {
					{
						put("field", "_widget_1591845499086");
						put("type", "text");
						put("method", "eq");
						put("value", id);
					}
				});
				Map<String, Object> filter = new HashMap<String, Object>() {
					{
						put("rel", "and");
						put("cond", condList);
					}
				};
				
				List<Map<String, Object>> formData1 = jxszj_api.getAllFormData(null, filter);
				for (int j = 0; j < formData1.size(); j++) {
					jxszj_api.deleteData(formData1.get(j).get("_id").toString());
				}
				
//				List<Map<String, Object>> formData3 = zhmd_api.getAllFormData(null, filter);
//				for (int j = 0; j < formData3.size(); j++) {
//					zhmd_api.deleteData(formData3.get(j).get("_id").toString());
//				}
				
			}
			if(list.size()>0){
				return MessageResult.build(500, "同步删除完成,条件记录编号"+list+"同步删除失败，同一个编号存在多条记录！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return MessageResult.build(500, e.getMessage());
		}
		return MessageResult.build(200, "同步删除完成！");
	}
	
	
	@RequestMapping("/delFailPrice")
	@ResponseBody
	public MessageResult delFailPrice() {
		// POS经销商之家 -- 经销商供货价格接口同步
		String jxszj_appId = "5cc110c3b3c41744aaa12b2e";
		String jxszj_entryId = "5e848d96f4cca90006acb5ff";
		String jxszj_apiKey = "uWEAujuxvzv5fMVifOvMRzlIJcPLgYkv";
		
		JDYAPIUtils jxszj_api = new JDYAPIUtils(jxszj_appId, jxszj_entryId, jxszj_apiKey);
		
		//先查询
		final List<Map<String, Object>> condList = new ArrayList<Map<String, Object>>();
		condList.add(new HashMap<String, Object>() {
			{
				put("field", "_widget_1598165436576");//有效期结束时间
				put("type", "text");
				put("method", "eq");
				put("value", DateUtils.getYesterday());
			}
		});
		Map<String, Object> filter = new HashMap<String, Object>() {
			{
				put("rel", "and");
				put("cond", condList);
			}
		};
		int num=0;
		List<Map<String, Object>> formData1 = jxszj_api.getAllFormData(null, filter);
		for (int j = 0; j < formData1.size(); j++) {
			jxszj_api.deleteData(formData1.get(j).get("_id").toString());
			num++;
		}
		return MessageResult.build(200, "清理完成，共发现"+formData1.size()+"条失效价格，清理了"+num+"条！");
	}
	
	
	
//	public JSONArray getJSONObject(String url){
//		CloseableHttpClient httpClient = HttpClientBuilder.create().build();   
//		HttpGet httpGet = new HttpGet(url);
//		CloseableHttpResponse response = null;
//		JSONObject jsonObject=null;
//		try {
//			httpGet.setHeader("Content-Type","application/json");
//			httpGet.setHeader("Accept","application/json");
//			httpGet.setHeader("Authorization", "Basic SkRZVVNFUjpIZmpUUD5UZ2ZxUU10SEVnaFpiVXhtZ2VOdnJmZlh3OUNvQm5BVGps");
//			// 由客户端执行(发送)Get请求
//			response = httpClient.execute(httpGet);
//			// 从响应模型中获取响应实体
//			HttpEntity responseEntity = response.getEntity();
//			if(response.getStatusLine().getStatusCode()==200){
//				jsonObject=JSON.parseObject(EntityUtils.toString(responseEntity));
//				jsonObject = jsonObject.getJSONObject("d");
//				array = jsonObject.getJSONArray("results");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return jsonObject;
//	}
	
	
	@RequestMapping("/getSAPSalesPrice")
	@ResponseBody
	public MessageResult getSAPSalesPrice(String priceWlbm,String sapPriceCjr){
		try {
			if(status.equals("false")){
				return MessageResult.build(500, "系统正在获取价格，请稍后再试...");
			}
			status="false";
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
			
			//按物料编码进行获取数据
			if(!"".equals(priceWlbm)){
				JSONArray  jSONArray=getJSONArray("https://my300201-api.saps4hanacloud.cn/sap/opu/odata/sap/API_SLSPRICINGCONDITIONRECORD_SRV/A_SlsPrcgCndnRecdValidity?$filter=Material%20eq%20'"+priceWlbm+"'%20and%20DistributionChannel%20eq%20'01'%20and%20ConditionType%20ne%20'DM01'%20and%20SalesOrganization%20eq%20'C001'");
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
			}else if(!"".equals(sapPriceCjr)){
				//获取失效日期为当前日期前一天的价格数据
				JSONArray  jSONArray=getJSONArray("https://my300201-api.saps4hanacloud.cn/sap/opu/odata/sap/API_SLSPRICINGCONDITIONRECORD_SRV/A_SlsPrcgCndnRecdValidity?$filter=ConditionValidityEndDate%20eq%20datetime'"+DateUtils.getBeforeTwo()+"T00:00:00'%20and%20DistributionChannel%20eq%20'01'%20and%20ConditionType%20ne%20'DM01'%20and%20SalesOrganization%20eq%20'C001'");
				for (int i = 0; i < jSONArray.size(); i++) {
					JSONArray  jSONArray1=getJSONArray("https://my300201-api.saps4hanacloud.cn/sap/opu/odata/sap/API_SLSPRICINGCONDITIONRECORD_SRV/A_SlsPrcgConditionRecord?$filter=ConditionRecord%20eq%20'"+jSONArray.getJSONObject(i).get("ConditionRecord")+"'%20and%20CreatedByUser%20eq%20'"+sapPriceCjr+"'");	
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
				jSONArray=getJSONArray("https://my300201-api.saps4hanacloud.cn/sap/opu/odata/sap/API_SLSPRICINGCONDITIONRECORD_SRV/A_SlsPrcgConditionRecord?$filter=CreationDate%20eq%20datetime'"+DateUtils.getNowDateToString()+"T00:00:00'%20and%20ConditionType%20ne%20'DM01'%20and%20CreatedByUser%20eq%20'"+sapPriceCjr+"'");
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
			}else{
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
			}

//			if(list.size()!=0){
//				i=priceService.addSAPPrices(list);
//			}
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
			status="true";
			return MessageResult.build(200, "价格获取完成！");
		} catch (Exception e) {
			e.printStackTrace();
		}
		status="true";
		return MessageResult.build(500, "价格获取失败，请重新获取价格！");
		
	}
	
	@RequestMapping("/getPriceList")
	@ResponseBody
	public EUDataGridResult getPriceList(Integer page, Integer rows,String priceMaterial,String priceCjr,String priceTbzt){
		EUDataGridResult result =priceService.getPricePage(page, rows,priceMaterial,priceCjr,priceTbzt);
		return result;
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
	
	
	@RequestMapping("/sendPriceToJDY")
	@ResponseBody
	public MessageResult sendPriceToJDY(@RequestBody JSONArray jsons){
		int x=0;
		try {
			if(tb.equals("false")){
				return MessageResult.build(500, "系统正在同步价格，请稍后再试...");
			}
			tb="false";
			JDYAPIUtils jxszj_api = new JDYAPIUtils(JXSZJ_APPID, JXSZJ_ENTRYID, JXSZJ_APIKEY);
			for (int i = 0; i < jsons.size(); i++) {
				//根据条件记录编号查询
				final List<Map<String, Object>> condList = new ArrayList<Map<String, Object>>();
				Map<String,Object> map=new HashMap<>();
				map.put("field", "_widget_1598944894560");//条件记录编号
				map.put("type", "text");
				map.put("method", "eq");
				map.put("value", jsons.getJSONObject(i).getString("id"));
				condList.add(map);
				Map<String, Object> filter = new HashMap<String, Object>() {
					{
						put("rel", "and");
						put("cond", condList);
					}
				};
				List<Map<String, Object>> formData = jxszj_api.getAllFormData(null, filter);
				for (int j = 0; j < formData.size(); j++) {
					jxszj_api.deleteData(formData.get(j).get("_id").toString());
				}
				if("生效".equals(jsons.getJSONObject(i).getString("sxzt"))){
					jxszj_api.createData(getData(jsons.getJSONObject(i)));
				}
				x+=priceService.updaePrice(jsons.getJSONObject(i).getString("id"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		tb="true";
		return MessageResult.build(200, "价格同步完成，总共同步条"+jsons.size()+",成功同步条"+x+"条！");
	}
	
	
	public Map<String, Object> getData(JSONObject  json){
		Map<String, Object> rawData = new HashMap<String, Object>();
		Map<String, Object> m1 = new HashMap<String, Object>();
		m1.put("value", json.getString("khjgz"));
		rawData.put("_widget_1585745302686", m1);// 客户价格组
		Map<String, Object> m2 = new HashMap<String, Object>();
		m2.put("value", json.getString("khbm"));
		rawData.put("_widget_1585745302701", m2);// 客户编码
		Map<String, Object> m3 = new HashMap<String, Object>();
		m3.put("value", json.getString("wlbm"));
		rawData.put("_widget_1585745302716", m3);// 物料编码
		Map<String, Object> m4 = new HashMap<String, Object>();
		m4.put("value", json.getString("djzk"));
		rawData.put("_widget_1585745302731", m4);// 单价
		Map<String, Object> m5 = new HashMap<String, Object>();
		m5.put("value", json.getString("dw"));
		rawData.put("_widget_1585901362201", m5);// 单位
		Map<String, Object> m6 = new HashMap<String, Object>();
		m6.put("value", json.getString("bz"));
		rawData.put("_widget_1585901362080", m6);// 币种
		Map<String, Object> m7 = new HashMap<String, Object>();
		m7.put("value",json.getString("tjlx"));
		rawData.put("_widget_1586482329032", m7);//条件类型
		if("PPR0".equals(json.getString("tjlx"))){ //PPR0标识
			Map<String, Object> m8 = new HashMap<String, Object>();
			m8.put("value",json.getString("djbs"));
			rawData.put("_widget_1586499946999", m8);//
		}else if("YK07".equals(json.getString("tjlx"))){ //YK07标识
			Map<String, Object> m8 = new HashMap<String, Object>();
			m8.put("value",json.getString("zkbs"));
			rawData.put("_widget_1586499946984", m8);//
		}
		Map<String, Object> m9 = new HashMap<String, Object>();
		m9.put("value",json.getString("xszz"));
		rawData.put("_widget_1586499946729", m9);//销售组织
		Map<String, Object> m10 = new HashMap<String, Object>();
		m10.put("value",json.getString("fxqd"));
		rawData.put("_widget_1586499946714", m10);//分销渠道
		
		Map<String, Object> m11 = new HashMap<String, Object>();
		m11.put("value",json.getString("wyj"));
		rawData.put("_widget_1591845499086", m11);//唯一码
		
		Map<String, Object> m12 = new HashMap<String, Object>();
		m12.put("value",json.getString("yxksrq"));
		rawData.put("_widget_1598165436554", m12);//有效期开始时间
		
		Map<String, Object> m13 = new HashMap<String, Object>();
		m13.put("value",json.getString("yxjsrq"));
		rawData.put("_widget_1598165436576", m13);//有效期结束时间
		
		Map<String, Object> m14 = new HashMap<String, Object>();
		m14.put("value",json.getString("id"));
		rawData.put("_widget_1598944894560", m14);//条件记录编号
		return rawData;
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

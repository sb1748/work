package com.jxszj.controller.sap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jxszj.utils.DateUtils;
import com.jxszj.utils.JDYAPIUtils;
import com.jxszj.utils.MessageResult;

/**
 * 
 * <pre>
 * <b>.</b>
 * <b>Description:</b> 
 *    将SAP中的物料主数据同步至简道云华生集团物料信息中
 * <b>Author:</b> yanwei
 * <b>Date:</b> 2020年3月30日下午1:49:59
 * </pre>
 */
@Controller
@RequestMapping("/hs")
public class SapWlSendJdyHsWlController {

	
	// OA华生集团 -- SAP物料信息接口同步
	String hs_appId = "5c048a8379332d0978a68b8e";
	String hs_entryId = "5ee32a6adf89950006f4646a";
	String hs_apiKey = "SGmAqjtntz3q5M0rZqm2b4nGlRpSlah6";

	
	@RequestMapping("/sendBatchWl")
	@ResponseBody
	public MessageResult sendBatchWl(){
		
		JDYAPIUtils hs_api = new JDYAPIUtils(hs_appId, hs_entryId, hs_apiKey);
		
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();   
		HttpGet httpGet = new HttpGet("https://my300201-api.saps4hanacloud.cn/sap/opu/odata/sap/YY1_MATERIELDATAPRICEADAPI_CDS/YY1_MaterielDataPriceAdAPI");
		CloseableHttpResponse response = null;
		try {
			
			httpGet.setHeader("Content-Type","application/json");
			httpGet.setHeader("Accept","application/json");
			httpGet.setHeader("Authorization", "Basic SkRZVVNFUjpIZmpUUD5UZ2ZxUU10SEVnaFpiVXhtZ2VOdnJmZlh3OUNvQm5BVGps");
			// 由客户端执行(发送)Get请求
			response = httpClient.execute(httpGet);
			// 从响应模型中获取响应实体
			HttpEntity responseEntity = response.getEntity();
			if(response.getStatusLine().getStatusCode()==200){
				JSONObject jsonObject1=JSON.parseObject(EntityUtils.toString(responseEntity));
				JSONObject  obj1 = jsonObject1.getJSONObject("d");
				JSONArray  array = obj1.getJSONArray("results");
				for (int i = 0; i < array.size(); i++) {
					final List<Map<String, Object>> condList = new ArrayList<Map<String, Object>>();
					Map<String, Object> map1=new HashMap<String, Object>();
					map1.put("field", "_widget_1591091866205");
					map1.put("type", "text");
					map1.put("method", "eq");
					map1.put("value",array.getJSONObject(i).getString("ValuationArea")+array.getJSONObject(i).getString("Product"));
			        condList.add(map1);
			        Map<String, Object> filter = new HashMap<String, Object>(){
			            {
			                put("rel", "and");
			                put("cond", condList);
			            }
			        };
			        
			      //华生集团
			        List<Map<String, Object>> hsList= hs_api.getAllFormData(null, filter);
			        
			        Map<String, Object> rawData3=getData(array.getJSONObject(i));
			        if(hsList.size()==0 && rawData3!=null){
						hs_api.createForData(rawData3);
			        }else if(hsList.size()==1 && rawData3!=null){
						hs_api.updateForData(hsList.get(0).get("_id").toString(), rawData3);
			        }
				}
			}else{
				return MessageResult.build(null, "获取SAP数据失败，同步更新失败，请重新同步");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MessageResult.build(200, "同步更新完成");
	}
	
	@RequestMapping("/sendWl")
	@ResponseBody
	public MessageResult sendWl(HttpServletRequest request) {
		
		JDYAPIUtils hs_api = new JDYAPIUtils(hs_appId, hs_entryId, hs_apiKey);
		
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();   
		HttpGet httpGet = new HttpGet("https://my300201-api.saps4hanacloud.cn/sap/opu/odata/sap/YY1_MATERIELDATAPRICEADAPI_CDS/YY1_MaterielDataPriceAdAPI");
		CloseableHttpResponse response = null;
		Set<String> sets=new HashSet<>();
		try {
			
			httpGet.setHeader("Content-Type","application/json");
			httpGet.setHeader("Accept","application/json");
			httpGet.setHeader("Authorization", "Basic SkRZVVNFUjpIZmpUUD5UZ2ZxUU10SEVnaFpiVXhtZ2VOdnJmZlh3OUNvQm5BVGps");
			// 由客户端执行(发送)Get请求
			response = httpClient.execute(httpGet);
			// 从响应模型中获取响应实体
			HttpEntity responseEntity = response.getEntity();
			if(response.getStatusLine().getStatusCode()==200){
				List<String> products=new ArrayList<String>();
				String product=request.getParameter("hswl_Product");
				if(product.contains("，") || product.contains(",")){
					product=product.replace("，", ",").replace(" ", "");
					products=Arrays.asList(product.split(","));
				}else{
					products.add(product);
				}
				
				List<String> valuationAreas=new ArrayList<String>();
				String valuationArea=request.getParameter("valuationArea");
				if(valuationArea.contains("，") || valuationArea.contains(",")){
					valuationArea=valuationArea.replace("，", ",").replace(" ", "");
					valuationAreas=Arrays.asList(valuationArea.split(","));
				}else{
					valuationAreas.add(valuationArea);
				}
				JSONObject jsonObject1=JSON.parseObject(EntityUtils.toString(responseEntity));
				JSONObject  obj1 = jsonObject1.getJSONObject("d");
				JSONArray  array = obj1.getJSONArray("results");
				if(array.size()==0){
					return MessageResult.build(null, "当天没有被创建或修改的物料!");
				}
				
				for (int j = 0; j < valuationAreas.size(); j++) {
					for (int k = 0; k < products.size(); k++) {
						boolean mark=false;
						for (int i = 0; i < array.size(); i++) {
							if(products.get(k).equals(array.getJSONObject(i).getString("Product")) && valuationAreas.get(j).equals(array.getJSONObject(i).getString("ValuationArea"))){
								final List<Map<String, Object>> condList = new ArrayList<Map<String, Object>>();
								Map<String, Object> map1=new HashMap<String, Object>();
								map1.put("field", "_widget_1591091866205");
								map1.put("type", "text");
								map1.put("method", "eq");
								map1.put("value",valuationAreas.get(j)+products.get(k));
						        condList.add(map1);
						        Map<String, Object> filter = new HashMap<String, Object>(){
						            {
						                put("rel", "and");
						                put("cond", condList);
						            }
						        };
						        
						      //华生集团
						        List<Map<String, Object>> hsList= hs_api.getAllFormData(null, filter);
						        
						        Map<String, Object> rawData3=getData(array.getJSONObject(i));
						        if(hsList.size()==0 && rawData3!=null){
									hs_api.createForData(rawData3);
						        }else if(hsList.size()==1 && rawData3!=null){
									hs_api.updateForData(hsList.get(0).get("_id").toString(), rawData3);
						        }
						        mark=true;
							}
						}
						if(mark==false){
							sets.add(products.get(k));
						}
					}
				}
				
			}else{
				return MessageResult.build(null, "获取SAP数据失败，同步更新失败，请重新同步");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MessageResult.build(200, sets.size()!=0?"同步完成，"+sets+"不属于B001或C001":"同步成功");
	}
	
	public Map<String, Object> getData(JSONObject json){
		Map<String, Object> rawData=null;
		try {
			rawData = new HashMap<String, Object>();
			Map<String, Object> m1 = new HashMap<String, Object>();
			m1.put("value", json.getString("ValuationArea")+json.getString("Product"));
			rawData.put("_widget_1591091866205", m1);// 唯一键
			Map<String, Object> m3 = new HashMap<String, Object>();
			m3.put("value", json.get("ProductType"));
			rawData.put("_widget_1591081777035", m3);// 产品类型
			Map<String, Object> m6 = new HashMap<String, Object>();
			m6.put("value", json.get("ProductGroup"));
			rawData.put("_widget_1586158226585", m6);// 物料组编码
			Map<String, Object> m7 = new HashMap<String, Object>();
			m7.put("value", json.get("MaterialGroupName"));
			rawData.put("_widget_1590977351648", m7);// 物料组名称
			Map<String, Object> m8 = new HashMap<String, Object>();
			m8.put("value", json.get("Product"));
			rawData.put("_widget_1585385207624", m8);// 产品编码
			Map<String, Object> m9 = new HashMap<String, Object>();
			m9.put("value", json.get("ProductDescription"));
			rawData.put("_widget_1585385207639", m9);// 产品名称
			Map<String, Object> m10 = new HashMap<String, Object>();
			m10.put("value", json.get("SizeOrDimensionText"));
			rawData.put("_widget_1585385207744", m10);// 规格尺寸
			Map<String, Object> m11 = new HashMap<String, Object>();
			m11.put("value", json.get("BaseUnit_01"));
			rawData.put("_widget_1585385207684", m11);// 基本单位
			Map<String, Object> m12 = new HashMap<String, Object>();
			m12.put("value", json.get("UnitOfMeasureLongName"));
			rawData.put("_widget_1585385207699", m12);// 基本单位文本
			Map<String, Object> m15 = new HashMap<String, Object>();
			m15.put("value",  DateUtils.getLongToString(json.getString("CreationDate"),DateUtils.FORMAT_1_STRING));
			rawData.put("_widget_1585385207759", m15);// 创建日期
			Map<String, Object> m16 = new HashMap<String, Object>();
			m16.put("value", DateUtils.getLongToString(json.getString("LastChangeDate"),DateUtils.FORMAT_1_STRING));
			rawData.put("_widget_1585385207771", m16);// 最近修改日期
			Map<String, Object> m17 = new HashMap<String, Object>();
			m17.put("value", json.get("CrossPlantStatus"));
			rawData.put("_widget_1591081777301", m17);// 使用状态
			Map<String, Object> m18 = new HashMap<String, Object>();
			m18.put("value", json.get("ValuationArea"));
			rawData.put("_widget_1592557293254", m18);// 评估范围
			Map<String, Object> m19 = new HashMap<String, Object>();
			m19.put("value", json.get("ValuationType"));
			rawData.put("_widget_1592557293305", m19);// 评估类型
			Map<String, Object> m20 = new HashMap<String, Object>();
			m20.put("value", json.get("StandardPrice"));
			rawData.put("_widget_1592557293522", m20);// 标准价格
			Map<String, Object> m21 = new HashMap<String, Object>();
			m21.put("value", json.get("MovingAveragePrice"));
			rawData.put("_widget_1592557293537", m21);// 移动价格
			Map<String, Object> m22 = new HashMap<String, Object>();
			m22.put("value", json.get("PurchasingGroup"));
			rawData.put("_widget_1592872702355", m22);// 采购组
			Map<String, Object> m23 = new HashMap<String, Object>();
			m23.put("value", json.get("MaterialVolume"));
			rawData.put("_widget_1611727206118", m23);// 体积
			Map<String, Object> m24 = new HashMap<String, Object>();
			m24.put("value", json.get("VolumeUnit"));
			rawData.put("_widget_1610504013713", m24);// 体积单位
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rawData;
	}
	
}

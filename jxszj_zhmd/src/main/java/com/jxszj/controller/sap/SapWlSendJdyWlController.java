package com.jxszj.controller.sap;

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
 *    将SAP中的物料主数据同步至简道云物料信息中
 * <b>Author:</b> yanwei
 * <b>Date:</b> 2020年3月30日下午1:49:59
 * </pre>
 */

@Controller
public class SapWlSendJdyWlController {

	
	// POS经销商之家 -- J2物料信息接口同步
	String jxszj_appId = "5cc110c3b3c41744aaa12b2e";
	String jxszj_entryId = "5e7f03d274924c000681ad02";
	String jxszj_apiKey = "uWEAujuxvzv5fMVifOvMRzlIJcPLgYkv";
	// 简道云智慧门店系统
//	String zhmd_appId = "5c6fa6da7eacb23f3daf642e";
//	String zhmd_entryId = "5ee334cb3752de0007c7c491";
//	String zhmd_apiKey = "O3nyFjunjn5neMk0c4cfEogVLFFXqHfU";

	
	@RequestMapping("/sendBatchWl")
	@ResponseBody
	public MessageResult sendBatchWl(){
		
		JDYAPIUtils jxszj_api = new JDYAPIUtils(jxszj_appId, jxszj_entryId, jxszj_apiKey);
		
//		JDYAPIUtils cszhmd_api = new JDYAPIUtils(cszhmd_appId, cszhmd_entryId, cszhmd_apiKey);
		
//		JDYAPIUtils zhmd_api = new JDYAPIUtils(zhmd_appId, zhmd_entryId, zhmd_apiKey);
		
		// POS经销商之家 -- R1物料组返利参数
		String entryId = "5ed9b22de59be600062abc84";
		JDYAPIUtils api = new JDYAPIUtils(jxszj_appId, entryId, jxszj_apiKey);
		List<Map<String, Object>> lists= api.getAllFormData(null, null);
		Map<String,Object> map=new HashMap<String,Object>();
		for (int i = 0; i < lists.size(); i++) {
			map.put(lists.get(i).get("_widget_1591240492654").toString(), lists.get(i).get("_widget_1591240492821"));
		}
		
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();   
		HttpGet httpGet = new HttpGet("https://my300201-api.saps4hanacloud.cn/sap/opu/odata/sap/YY1_MATERIELDATAENTFILAPI_CDS/YY1_MaterielDataEntFilAPI");
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
					map1.put("value",array.getJSONObject(i).getString("Product")+array.getJSONObject(i).getString("ProductSalesOrg")+array.getJSONObject(i).getString("ProductDistributionChnl"));
			        condList.add(map1);
			        Map<String, Object> filter = new HashMap<String, Object>(){
			            {
			                put("rel", "and");
			                put("cond", condList);
			            }
			        };
			        //经销商之家
			        List<Map<String, Object>> jxszjList= jxszj_api.getAllFormData(null, filter);
			        
			        Map<String, Object> rawData1=getData(array.getJSONObject(i));
			        if(jxszjList.size()==0 && rawData1!=null){
			        	Map<String, Object> m19 = new HashMap<String, Object>();
						m19.put("value", map.get(array.getJSONObject(i).get("ProductGroup")));
						rawData1.put("_widget_1591705259670", m19);// 返利标识
						jxszj_api.createForData(rawData1);
			        }else if(jxszjList.size()==1 && rawData1!=null){
			        	Map<String, Object> m19 = new HashMap<String, Object>();
						m19.put("value", jxszjList.get(0).get("_widget_1591705259670"));
						rawData1.put("_widget_1591705259670", m19);// 返利标识
						jxszj_api.updateForData(jxszjList.get(0).get("_id").toString(), rawData1);
			        }
			        
			      //智慧门店系统
//			        List<Map<String, Object>> zhmdList= zhmd_api.getAllFormData(null, filter);
//			        
//			        Map<String, Object> rawData4=getData(array.getJSONObject(i));
//			        if(zhmdList.size()==0 && rawData4!=null){
//			        	Map<String, Object> m19 = new HashMap<String, Object>();
//						m19.put("value", map.get(array.getJSONObject(i).get("ProductGroup")));
//						rawData4.put("_widget_1591705259670", m19);// 返利标识
//						zhmd_api.createForData(rawData4);
//			        }else if(zhmdList.size()==1 && rawData4!=null){
//			        	Map<String, Object> m19 = new HashMap<String, Object>();
//						m19.put("value", zhmdList.get(0).get("_widget_1591705259670"));
//						rawData4.put("_widget_1591705259670", m19);// 返利标识
//						zhmd_api.updateForData(zhmdList.get(0).get("_id").toString(), rawData4);
//			        }
				}
			}else{
				return MessageResult.build(null, "获取SAP数据失败，同步更新失败，请重新同步");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MessageResult.build(200, "同步更新完成");
	}
	
	/**
	 * 
	 *<pre>
	 *<b>.</b>
	 *<b>Description:</b> 
	 *    
	 *<b>Author:</b> yanwei
	 *<b>Date:</b> 2021年2月25日 下午4:08:44
	 *@param request
	 *@return
	 *</pre>
	 */
	@RequestMapping("/sendWl")
	@ResponseBody
	public MessageResult sendWl(HttpServletRequest request) {
		
		JDYAPIUtils jxszj_api = new JDYAPIUtils(jxszj_appId, jxszj_entryId, jxszj_apiKey);
		
//		JDYAPIUtils cszhmd_api = new JDYAPIUtils(cszhmd_appId, cszhmd_entryId, cszhmd_apiKey);
		
//		JDYAPIUtils zhmd_api = new JDYAPIUtils(zhmd_appId, zhmd_entryId, zhmd_apiKey);
		
		// POS经销商之家 -- R1物料组返利参数
		String entryId = "5ed9b22de59be600062abc84";
		JDYAPIUtils api = new JDYAPIUtils(jxszj_appId, entryId, jxszj_apiKey);
		List<Map<String, Object>> lists= api.getAllFormData(null, null);
		Map<String,Object> map=new HashMap<String,Object>();
		for (int i = 0; i < lists.size(); i++) {
			map.put(lists.get(i).get("_widget_1591240492654").toString(), lists.get(i).get("_widget_1591240492821"));
		}
		
		List<String> products=new ArrayList<String>();
		String product=request.getParameter("wl_Product");
		if(product.contains("，") || product.contains(",")){
			product=product.replace("，", ",").replace(" ", "");
			products=Arrays.asList(product.split(","));
		}else{
			products.add(product);
		}
		
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();   
		HttpGet httpGet = new HttpGet("https://my300201-api.saps4hanacloud.cn/sap/opu/odata/sap/YY1_MATERIELDATAENTFILAPI_CDS/YY1_MaterielDataEntFilAPI");
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
				JSONObject jsonObject=JSON.parseObject(EntityUtils.toString(responseEntity));
				jsonObject = jsonObject.getJSONObject("d");
				JSONArray  array = jsonObject.getJSONArray("results");
//				JSONArray  array = new JSONArray();
//				for (int i = 0; i < products.size(); i++) {
//					for (int j = 0; j < array1.size(); j++) {
//						if(products.get(i).equals(array1.getJSONObject(j).getString("Product"))){
//							array.add(array1.getJSONObject(j));
//						}
//					}
//				}
				for (int i = 0; i < products.size(); i++) {
					for (int j = 0; j < array.size(); j++) {
						if(products.get(i).equals(array.getJSONObject(j).getString("Product")) && request.getParameter("wl_ProductSalesOrg").equals(array.getJSONObject(j).getString("ProductSalesOrg")) && request.getParameter("wl_ProductDistributionChnl").equals(array.getJSONObject(j).getString("ProductDistributionChnl"))){
							final List<Map<String, Object>> condList = new ArrayList<Map<String, Object>>();
							Map<String, Object> map1=new HashMap<String, Object>();
							map1.put("field", "_widget_1591091866205");
							map1.put("type", "text");
							map1.put("method", "eq");
							map1.put("value",products.get(i)+request.getParameter("wl_ProductSalesOrg")+request.getParameter("wl_ProductDistributionChnl"));
					        condList.add(map1);
					        Map<String, Object> filter = new HashMap<String, Object>(){
					            {
					                put("rel", "and");
					                put("cond", condList);
					            }
					        };
					        List<Map<String, Object>> jxszjList= jxszj_api.getAllFormData(null, filter);
					        
					    	Map<String, Object> rawData1=getData(array.getJSONObject(j));
					        if(jxszjList.size()==0 && rawData1 !=null){
					        	Map<String, Object> m19 = new HashMap<String, Object>();
								m19.put("value", map.get(array.getJSONObject(j).get("ProductGroup")));
								rawData1.put("_widget_1591705259670", m19);// 返利标识
								jxszj_api.createForData(rawData1);
					        }else if(jxszjList.size()==1 && rawData1 !=null){
					        	Map<String, Object> m19 = new HashMap<String, Object>();
								m19.put("value", jxszjList.get(0).get("_widget_1591705259670"));
								rawData1.put("_widget_1591705259670", m19);// 返利标识
								jxszj_api.updateForData(jxszjList.get(0).get("_id").toString(), rawData1);
					        }
					        
					      //智慧门店系统
//					        List<Map<String, Object>> zhmdList= zhmd_api.getAllFormData(null, filter);
//					        
//					        Map<String, Object> rawData4=getData(array.getJSONObject(j));
//					        if(zhmdList.size()==0 && rawData4!=null){
//					        	Map<String, Object> m19 = new HashMap<String, Object>();
//								m19.put("value", map.get(array.getJSONObject(j).get("ProductGroup")));
//								rawData4.put("_widget_1591705259670", m19);// 返利标识
//								zhmd_api.createForData(rawData4);
//					        }else if(zhmdList.size()==1 && rawData4!=null){
//					        	Map<String, Object> m19 = new HashMap<String, Object>();
//								m19.put("value", zhmdList.get(0).get("_widget_1591705259670"));
//								rawData4.put("_widget_1591705259670", m19);// 返利标识
//								zhmd_api.updateForData(zhmdList.get(0).get("_id").toString(), rawData4);
//					        }
					        
						}
						
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
	
	public Map<String, Object> getData(JSONObject json){
		Map<String, Object> rawData=null;
		try {
			rawData = new HashMap<String, Object>();
			Map<String, Object> m1 = new HashMap<String, Object>();
			m1.put("value", json.get("ProductSalesOrg"));
			rawData.put("_widget_1585385207654", m1);// 销售组织
			Map<String, Object> m2 = new HashMap<String, Object>();
			m2.put("value", json.get("ProductDistributionChnl"));
			rawData.put("_widget_1585385207669", m2);// 分销渠道
			Map<String, Object> m3 = new HashMap<String, Object>();
			m3.put("value", json.get("ProductType"));
			rawData.put("_widget_1591081777035", m3);// 产品类型
			Map<String, Object> m4 = new HashMap<String, Object>();
			m4.put("value", json.get("FirstSalesSpecProductGroup"));
			rawData.put("_widget_1586152118862", m4);// 品牌SAP码
			Map<String, Object> m5 = new HashMap<String, Object>();
			m5.put("value", json.get("AdditionalMaterialGroup1Name"));
			rawData.put("_widget_1585908861148", m5);// 品牌名称
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
			Map<String, Object> m13 = new HashMap<String, Object>();
			m13.put("value", json.get("SalesMeasureUnit"));
			rawData.put("_widget_1585385207714", m13);// 销售单位
			Map<String, Object> m14 = new HashMap<String, Object>();
			m14.put("value", json.get("UnitOfMeasureLongName_01"));
			rawData.put("_widget_1585385207729", m14);// 销售单位文本
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
			m18.put("value", json.getString("Product")+json.getString("ProductSalesOrg")+json.getString("ProductDistributionChnl"));
			rawData.put("_widget_1591091866205", m18);// 唯一键
			Map<String, Object> m19 = new HashMap<String, Object>();
			m19.put("value", json.get("MaterialVolume"));
			rawData.put("_widget_1611727443486", m19);// 体积
			Map<String, Object> m20 = new HashMap<String, Object>();
			m20.put("value", json.get("VolumeUnit"));
			rawData.put("_widget_1610503598302", m20);// 体积单位
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rawData;
	}
	
}

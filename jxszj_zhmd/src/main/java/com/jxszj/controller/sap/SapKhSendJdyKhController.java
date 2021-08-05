package com.jxszj.controller.sap;

import java.util.ArrayList;
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
import com.alibaba.fastjson.JSONObject;
import com.jxszj.utils.JDYAPIUtils;
import com.jxszj.utils.MessageResult;

/**
 * 
 * <pre>
 * <b>.</b>
 * <b>Description:</b> 
 *    将SAP中的客户主数据同步至简道云客户信息中
 * <b>Author:</b> yanwei
 * <b>Date:</b> 2020年3月30日下午1:49:59
 * </pre>
 */
@Controller
public class SapKhSendJdyKhController {

	
	// POS经销商之家 -- J1客户信息接口同步
	String jxszj_appId = "5cc110c3b3c41744aaa12b2e";
	String jxszj_entryId = "5e7ec57478cb560006d32863";
	String jxszj_apiKey = "uWEAujuxvzv5fMVifOvMRzlIJcPLgYkv";
	
//	// 简道云智慧门店--长沙
//	String cszhmd_appId = "5cce5b4465ab7c30ed24b546";
//	String cszhmd_entryId = "5ee3358aed6dc7000699f522";
//	String cszhmd_apiKey = "uWEAujuxvzv5fMVifOvMRzlIJcPLgYkv";
	
	// 简道云智慧门店系统
	String zhmd_appId = "5c6fa6da7eacb23f3daf642e";
	String zhmd_entryId = "5ee334ba6e663a0006250f6b";
	String zhmd_apiKey = "O3nyFjunjn5neMk0c4cfEogVLFFXqHfU";
	
		
	JDYAPIUtils jxszj_api=  new JDYAPIUtils(jxszj_appId, jxszj_entryId, jxszj_apiKey);
	
//	JDYAPIUtils cszhmd_api = new JDYAPIUtils(cszhmd_appId, cszhmd_entryId, cszhmd_apiKey);
	
	JDYAPIUtils zhmd_api = new JDYAPIUtils(zhmd_appId, zhmd_entryId, zhmd_apiKey);
	
	Map<String,Object> map=null;
	
	@RequestMapping("/sendKh")
	@ResponseBody
	public MessageResult sendKh(HttpServletRequest request) {
		// POS经销商之家 -- J6-品牌信息
		String entryId = "5c21cf94bbc94335f06f9899";
		JDYAPIUtils api = new JDYAPIUtils(jxszj_appId, entryId, jxszj_apiKey);
        List<Map<String, Object>> pps= api.getAllFormData(null,null);
        map=new HashMap<String,Object>();
        for (int i = 0; i < pps.size(); i++) {
			map.put(pps.get(i).get("_widget_1545185349787").toString(), pps.get(i).get("_widget_1545185349832"));
		}
        
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();   
		HttpGet httpGet = new HttpGet("https://my300201-api.saps4hanacloud.cn/sap/opu/odata/sap/YY1_CUSTOMERDATAENTITY_CDS/YY1_CustomerDataEntity(Customer='"+request.getParameter("customer")+"',SalesOrganization='"+request.getParameter("kh_SalesOrganization")+"',DistributionChannel='"+request.getParameter("kh_DistributionChannel")+"',Division='"+request.getParameter("kh_Division")+"')");
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
				JSONObject  obj = jsonObject1.getJSONObject("d");
				
				final List<Map<String, Object>> condList = new ArrayList<Map<String, Object>>();
				Map<String, Object> map1=new HashMap<String, Object>();
				map1.put("field", "_widget_1585380393487");
				map1.put("type", "text");
				map1.put("method", "eq");
				map1.put("value",obj.get("Customer"));
		        condList.add(map1);
		        Map<String, Object> map2=new HashMap<String, Object>();
				map2.put("field", "_widget_1585380393658");
				map2.put("type", "text");
				map2.put("method", "eq");
				map2.put("value",obj.get("SalesOrganization"));
		        condList.add(map2);
		        Map<String, Object> map3=new HashMap<String, Object>();
				map3.put("field", "_widget_1585380393673");
				map3.put("type", "text");
				map3.put("method", "eq");
				map3.put("value",obj.get("DistributionChannel"));
		        condList.add(map3);
		        Map<String, Object> map4=new HashMap<String, Object>();
				map4.put("field", "_widget_1585380393688");
				map4.put("type", "text");
				map4.put("method", "eq");
				map4.put("value",obj.get("Division"));
		        condList.add(map4);
		        
		        Map<String, Object> filter = new HashMap<String, Object>(){
		            {
		                put("rel", "and");
		                put("cond", condList);
		            }
		        };
		        List<Map<String, Object>> jxszjList= jxszj_api.getAllFormData(null, filter);
		        Map<String, Object> rawData1=getRawData(obj);
		        if(jxszjList.size()==0 && rawData1!=null){
		        	jxszj_api.createForData(rawData1);
		        }else if(jxszjList.size()==1 && rawData1!=null){
		        	jxszj_api.updateForData(jxszjList.get(0).get("_id").toString(), rawData1);
		        }
		        
//		        List<Map<String, Object>> cszhmdList= cszhmd_api.getAllFormData(null, filter);
//		        Map<String, Object> rawData2=getRawData(obj);
//		        if(cszhmdList.size()==0 && rawData2!=null){
//		        	cszhmd_api.createForData(rawData2);
//		        }else if(cszhmdList.size()==1 && rawData2!=null){
//		        	cszhmd_api.updateForData(cszhmdList.get(0).get("_id").toString(), rawData2);
//		        }
		        
		        List<Map<String, Object>> zhmdList= zhmd_api.getAllFormData(null, filter);
		        Map<String, Object> rawData3=getRawData(obj);
		        if(zhmdList.size()==0 && rawData3!=null){
		        	zhmd_api.createForData(rawData3);
		        }else if(zhmdList.size()==1 && rawData3!=null){
		        	zhmd_api.updateForData(zhmdList.get(0).get("_id").toString(), rawData3);
		        }
		        
			}else{
				return MessageResult.build(null, "获取SAP数据失败，同步更新失败，请重新同步");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MessageResult.build(200, "同步更新完成");
	}
	
	public Map<String, Object> getRawData(JSONObject json){
		Map<String, Object> rawData=null;
		try {
			if(json.getInteger("DistributionChannel")==01){
				rawData = new HashMap<String, Object>();
				Map<String, Object> m1 = new HashMap<String, Object>();
				m1.put("value", json.get("Customer"));
				rawData.put("_widget_1585380393487", m1);// 客户编码
				Map<String, Object> m2 = new HashMap<String, Object>();
				m2.put("value", json.get("CustomerName"));
				rawData.put("_widget_1585380393515", m2);// 客户名称
//				Map<String, Object> m3 = new HashMap<String, Object>();
//				m3.put("value", json.get("CustomerGroup"));
//				rawData.put("_widget_1585380393921", m3);// 客户组
				Map<String, Object> m4 = new HashMap<String, Object>();
				m4.put("value", json.get("CustomerPriceGroup"));
				rawData.put("_widget_1585380393962", m4);// 客户价格组
				Map<String, Object> m5 = new HashMap<String, Object>();
				m5.put("value", json.get("SalesOrganization"));
				rawData.put("_widget_1585380393658", m5);// 销售组织
				Map<String, Object> m6 = new HashMap<String, Object>();
				m6.put("value", json.get("DistributionChannel"));
				rawData.put("_widget_1585380393673", m6);// 分销渠道
				Map<String, Object> m7 = new HashMap<String, Object>();
				m7.put("value", json.get("Division"));
				rawData.put("_widget_1585380393688", m7);// 产品组
				Map<String, Object> m8 = new HashMap<String, Object>();
				m8.put("value", json.get("SalesDistrict"));
				rawData.put("_widget_1585380393755", m8);// 销售区域
				Map<String, Object> m9 = new HashMap<String, Object>();
				m9.put("value", json.get("CountryName"));
				rawData.put("_widget_1585380393770", m9);// 国家
				Map<String, Object> m10 = new HashMap<String, Object>();
				m10.put("value", json.get("RegionName"));
				rawData.put("_widget_1585380393798", m10);// 省份
				Map<String, Object> m11 = new HashMap<String, Object>();
				m11.put("value", json.get("CityName"));
				rawData.put("_widget_1585380393826", m11);// 城市
				Map<String, Object> m12 = new HashMap<String, Object>();
				m12.put("value", json.get("TelephoneNumber1"));
				rawData.put("_widget_1585380393990", m12);// 电话
				Map<String, Object> m13 = new HashMap<String, Object>();
				m13.put("value", json.get("StreetName"));
				rawData.put("_widget_1585380394018", m13);// 地址
				Map<String, Object> m14 = new HashMap<String, Object>();
				m14.put("value", json.get("CustomerClassification"));
				rawData.put("_widget_1585742792425", m14);// 客户分类
				Map<String, Object> m15 = new HashMap<String, Object>();
				m15.put("value", map.get(json.getString("CustomerName").substring(0, json.getString("CustomerName").indexOf("-"))));
				rawData.put("_widget_1586152181373", m15);// 品牌
				Map<String, Object> m16 = new HashMap<String, Object>();
				m16.put("value", json.getString("CustomerName").substring(0, json.getString("CustomerName").indexOf("-")));
				rawData.put("_widget_1586152181388", m16);// 品牌编码
				Map<String, Object> m17 = new HashMap<String, Object>();
				m17.put("value", json.getString("CustomerPriceGroupName"));
				rawData.put("_widget_1591088113407", m17);// 客户价格组名称
				Map<String, Object> m18 = new HashMap<String, Object>();
				m18.put("value", json.getString("SalesDistrictName"));
				rawData.put("_widget_1591088193718", m18);// 销售区域名称
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rawData;
	}
	
}

package com.jxszj.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jxszj.utils.ExceptionRobot;
import com.jxszj.utils.JDYAPIUtils;

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
public class SapKhSendJdyKhTest {

	
	// 简道云
	String apiKey = "uWEAujuxvzv5fMVifOvMRzlIJcPLgYkv";
	String appId = "5cc110c3b3c41744aaa12b2e";
	JDYAPIUtils api=null;
	Map<String,Object> map=null;
	public void getKh() {
		//品牌
		String entryId = "5c21cf94bbc94335f06f9899";
		api = new JDYAPIUtils(appId, entryId, apiKey);
        List<Map<String, Object>> pps= api.getAllFormData(null,null);
        map=new HashMap<String,Object>();
        for (int i = 0; i < pps.size(); i++) {
			map.put(pps.get(i).get("_widget_1545185349787").toString(), pps.get(i).get("_widget_1545185349832"));
		}
        
        //客户
  		entryId = "5e7ec57478cb560006d32863";
  		api = new JDYAPIUtils(appId, entryId, apiKey);
		
  		//https://my300407-api.saps4hanacloud.cn/sap/opu/odata/sap/YY1_CUSTOMERDATAENTITYFIL_CDS/YY1_CustomerDataEntityFil
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();   
		HttpGet httpGet = new HttpGet("https://my300201-api.saps4hanacloud.cn/sap/opu/odata/sap/YY1_CUSTOMERDATAENTITY_CDS/YY1_CustomerDataEntity");
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
					createKh(array.getJSONObject(i));
				}
			}else{
				ExceptionRobot.robotSapMessage("SAP同步客户主数据","接口状态码"+response.getStatusLine().getStatusCode());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void createKh(JSONObject json){
		try {
			if(json.getInteger("DistributionChannel")==01){
				Map<String, Object> rawData = new HashMap<String, Object>();
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
				System.out.println(json.getString("CustomerName"));
				if(json.getString("CustomerName").contains("-")){
					Map<String, Object> m15 = new HashMap<String, Object>();
					m15.put("value", map.get(json.getString("CustomerName").substring(0, json.getString("CustomerName").indexOf("-"))));
					rawData.put("_widget_1586152181373", m15);// 品牌
					Map<String, Object> m16 = new HashMap<String, Object>();
					m16.put("value", json.getString("CustomerName").substring(0, json.getString("CustomerName").indexOf("-")));
					rawData.put("_widget_1586152181388", m16);// 品牌编码
				}
				Map<String, Object> m17 = new HashMap<String, Object>();
				m17.put("value", json.getString("CustomerPriceGroupName"));
				rawData.put("_widget_1591088113407", m17);// 客户价格组名称
				Map<String, Object> m18 = new HashMap<String, Object>();
				m18.put("value", json.getString("SalesDistrictName"));
				rawData.put("_widget_1591088193718", m18);// 销售区域名称
				api.createForData(rawData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis(); 
		new SapKhSendJdyKhTest().getKh();
		long endTime = System.currentTimeMillis();
		System.out.println("程序运行时间：" + (endTime - startTime) + "ms");
	}
	
}

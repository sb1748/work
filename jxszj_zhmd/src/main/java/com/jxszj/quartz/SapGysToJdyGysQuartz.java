package com.jxszj.quartz;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
 *    将SAP中的供应商同步到简道云华生集团SAP供应商信息接口同步
 * <b>Author:</b> yanwei
 * <b>Date:</b> 2020年6月19日上午9:39:46
 * </pre>
 */
public class SapGysToJdyGysQuartz {

	private static Logger logger = LoggerFactory.getLogger(SapGysToJdyGysQuartz.class.getName());
	
	// 简道云华生集团
	String appId = "5c048a8379332d0978a68b8e";
	String entryId = "5ea97dfaac9b4e0006abe3d5";
	String apiKey = "SGmAqjtntz3q5M0rZqm2b4nGlRpSlah6";
	JDYAPIUtils api = new JDYAPIUtils(appId, entryId, apiKey);
	
	
	public void execute(){
		logger.info("--------------------供应商--------------------");
		CloseableHttpClient httpClient = HttpClientBuilder.create().build(); 
		HttpGet httpGet = new HttpGet("https://my300201-api.saps4hanacloud.cn/sap/opu/odata/sap/YY1_SUPPLIERLISTAPI_CDS/YY1_SupplierListAPI");
		CloseableHttpResponse response = null;
		List<Map<String, Object>> list=api.getAllFormData(null, null);
		for (int i = 0; i < list.size(); i++) {
			api.deleteData(list.get(i).get("_id").toString());
		}
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
					createData(array.getJSONObject(i));
				}
			}else{
				ExceptionRobot.robotSapMessage("SAP同步交货进度","调用SAP接口失败，接口状态码"+response.getStatusLine().getStatusCode());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void createData(JSONObject json){
		try {
			Map<String, Object> rawData = new HashMap<String, Object>();
			Map<String, Object> m3 = new HashMap<String, Object>();
			m3.put("value", json.get("BusinessPartnerGrouping"));
			rawData.put("_widget_1588472994092", m3);// 分组
			Map<String, Object> m4 = new HashMap<String, Object>();
			m4.put("value", json.get("Supplier"));
			rawData.put("_widget_1588471232115", m4);// 供应商编码
			Map<String, Object> m5 = new HashMap<String, Object>();
			m5.put("value", json.get("SupplierName"));
			rawData.put("_widget_1588472993777", m5);// 供应商名称
			Map<String, Object> m6 = new HashMap<String, Object>();
			m6.put("value", json.get("BankIdentification"));
			rawData.put("_widget_1592221710693", m6);// 银行明细标识
			Map<String, Object> m7 = new HashMap<String, Object>();
			m7.put("value", json.get("BankAccountName"));
			rawData.put("_widget_1588486169386", m7);// 账户名称
			Map<String, Object> m8 = new HashMap<String, Object>();
			m8.put("value", json.get("BankName"));
			rawData.put("_widget_1588486169414", m8);// 开户银行
			Map<String, Object> m9 = new HashMap<String, Object>();
			m9.put("value", json.getString("BankAccount")+json.getString("BankAccountReferenceText"));
			rawData.put("_widget_1588486169442", m9);// 银行账号
			Map<String, Object> m10 = new HashMap<String, Object>();
			m10.put("value", json.getString("CompanyCode")+json.getString("Supplier"));
			rawData.put("_widget_1592528300218", m10);// 唯一码
			Map<String, Object> m11 = new HashMap<String, Object>();
			m11.put("value", json.get("CompanyCode"));
			rawData.put("_widget_1592551336844", m11);// 公司代码
			api.createForData(rawData);
		} catch (Exception e) {
			e.printStackTrace();
			ExceptionRobot.robotSapMessage("SAP同步供应商","同步供应商"+json.getString("Supplier")+"失败");
		}
	}
}

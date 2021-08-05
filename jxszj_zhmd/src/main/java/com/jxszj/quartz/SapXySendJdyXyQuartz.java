package com.jxszj.quartz;

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
 *    将SAP中的信用额度数据同步至简道云信用信息表中
 * <b>Author:</b> yanwei
 * <b>Date:</b> 2020年3月30日下午1:49:59
 * </pre>
 */
public class SapXySendJdyXyQuartz {
	
	private static Logger logger = LoggerFactory.getLogger(SapXySendJdyXyQuartz.class.getName());
	
	// 简道云
	String appId = "5cc110c3b3c41744aaa12b2e";
	String entryId = "5e848edfba3ea300069574b8";
	String apiKey = "uWEAujuxvzv5fMVifOvMRzlIJcPLgYkv";
	JDYAPIUtils api = new JDYAPIUtils(appId, entryId, apiKey);
	
	
	public void execute(){
		logger.info("--------------------信用额度--------------------");
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();   
		HttpGet httpGet = new HttpGet("https://my300201-api.saps4hanacloud.cn/sap/opu/odata/sap/YY1_CREDIT_CDS/YY1_credit");
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
					map1.put("field", "_widget_1585745632485");
					map1.put("type", "text");
					map1.put("method", "eq");
					map1.put("value",array.getJSONObject(i).get("BusinessPartner"));
			        condList.add(map1);
			        Map<String, Object> filter = new HashMap<String, Object>(){
			            {
			                put("rel", "and");
			                put("cond", condList);
			            }
			        };
			        List<Map<String, Object>> list= api.getAllFormData(null, filter);
			        for (int j = 0; j < list.size(); j++) {
						api.deleteData(list.get(j).get("_id").toString());
					}
				}
				for (int i = 0; i < array.size(); i++) {
					createXy(array.getJSONObject(i));
				}
			}else{
				ExceptionRobot.robotSapMessage("SAP同步客户信用额度","调用SAP接口失败，接口状态码"+response.getStatusLine().getStatusCode());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void createXy(JSONObject json){
		try {
			Map<String, Object> rawData = new HashMap<String, Object>();
			Map<String, Object> m1 = new HashMap<String, Object>();
			m1.put("value", json.get("BusinessPartner"));
			rawData.put("_widget_1585745632485", m1);// 业务伙伴
			Map<String, Object> m2 = new HashMap<String, Object>();
			m2.put("value", json.get("CreditSegment"));
			rawData.put("_widget_1585745632500", m2);// 信用段
			Map<String, Object> m3 = new HashMap<String, Object>();
			m3.put("value", json.get("DisplayCurrency"));
			rawData.put("_widget_1585745632560", m3);// 显示货币
			Map<String, Object> m4 = new HashMap<String, Object>();
			m4.put("value", json.get("CustomerCreditExposureAmoun"));
			rawData.put("_widget_1585745632515", m4);// 敞口金额
			Map<String, Object> m5 = new HashMap<String, Object>();
			m5.put("value", json.get("CustomerCreditLimitAmount"));
			rawData.put("_widget_1585745632530", m5);// 信用额度
			Map<String, Object> m6 = new HashMap<String, Object>();
			m6.put("value", json.get("RemainingCreditAmtInDspCrcy"));
			rawData.put("_widget_1585745632545", m6);// 剩余贷方金额
			Map<String, Object> m7 = new HashMap<String, Object>();
			m7.put("value", json.get("BusinessPartnerName"));
			rawData.put("_widget_1589608236556", m7);// 业务伙伴名称
			api.createForData(rawData);
		} catch (Exception e) {
			e.printStackTrace();
			ExceptionRobot.robotSapMessage("SAP同步客户信用额度","同步"+json.getString("BusinessPartner")+"客户信用额度失败,"+e.getMessage());
		}
	}
}

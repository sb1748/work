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
 *    将SAP中的自定义客户主数据(华生集团)同步至简道云华生集团SAP客户信息中
 * <b>Author:</b> yanwei
 * <b>Date:</b> 2020年3月30日下午1:49:59
 * </pre>
 */
@Controller
@RequestMapping("/hs")
public class SapHSKhSendJdyHSKhController {

	// OA华生集团 -- SAP客户信息
	String appId = "5c048a8379332d0978a68b8e";
	String entryId = "5eb4f6dfe3e2aa0006df27df";
	String apiKey = "SGmAqjtntz3q5M0rZqm2b4nGlRpSlah6";
		
	JDYAPIUtils api = new JDYAPIUtils(appId, entryId, apiKey);
	Map<String,Object> map=null;
	
	@RequestMapping("/sendKh")
	@ResponseBody
	public MessageResult sendKh(HttpServletRequest request) {
        
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();   
		HttpGet httpGet = new HttpGet("https://my300201-api.saps4hanacloud.cn/sap/opu/odata/sap/YY1_CUSTOMERENTITYHSAPI_CDS/YY1_CustomerEntityHSAPI(Customer='"+request.getParameter("hs_customer")+"',SalesOrganization='"+request.getParameter("hs_SalesOrganization")+"',DistributionChannel='"+request.getParameter("hs_DistributionChannel")+"',Division='"+request.getParameter("hs_Division")+"')");
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
				JSONObject obj=JSON.parseObject(EntityUtils.toString(responseEntity));
				obj = obj.getJSONObject("d");
//				JSONArray array=obj.getJSONArray("results");
				String id=obj.getString("Customer")+obj.getString("SalesOrganization")+obj.getString("DistributionChannel")+obj.getString("Division");
				final List<Map<String, Object>> condList = new ArrayList<Map<String, Object>>();
				Map<String, Object> map=new HashMap<String, Object>();
				map.put("field", "_widget_1594191711957");
				map.put("type", "text");
				map.put("method", "eq");
				map.put("value",id);
		        condList.add(map);
		        
		        Map<String, Object> filter = new HashMap<String, Object>(){
		            {
		                put("rel", "and");
		                put("cond", condList);
		            }
		        };
		        List<Map<String, Object>> hsList= api.getAllFormData(null, filter);
		        Map<String, Object> rawData=getRawData(obj,id);
		        if(hsList.size()==0 && rawData!=null){
		        	api.createForData(rawData);
		        }else if(hsList.size()==1 && rawData!=null){
		        	api.updateForData(hsList.get(0).get("_id").toString(), rawData);
		        }
			}else{
				return MessageResult.build(null, "获取SAP数据失败，同步更新失败，请重新同步");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MessageResult.build(200, "同步更新完成");
	}
	
	public Map<String, Object> getRawData(JSONObject json,String id){
		Map<String, Object> rawData=null;
		try {
				rawData = new HashMap<String, Object>();
				Map<String, Object> m1 = new HashMap<String, Object>();
				m1.put("value", id);
				rawData.put("_widget_1594191711957", m1);// 唯一键
				Map<String, Object> m2 = new HashMap<String, Object>();
				m2.put("value", json.get("SalesOrganization"));
				rawData.put("_widget_1588917983400", m2);// 公司代码
				Map<String, Object> m3 = new HashMap<String, Object>();
				m3.put("value", json.get("Customer"));
				rawData.put("_widget_1588917983415", m3);// 客户编码
				Map<String, Object> m4 = new HashMap<String, Object>();
				m4.put("value", json.get("CustomerName"));
				rawData.put("_widget_1588917983430", m4);// 客户名称
				Map<String, Object> m5 = new HashMap<String, Object>();
				m5.put("value", json.get("BusinessPartnerGrouping"));
				rawData.put("_widget_1588917983588", m5);// 分组
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rawData;
	}
	
}

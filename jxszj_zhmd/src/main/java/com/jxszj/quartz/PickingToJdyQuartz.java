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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jxszj.utils.DateUtils;
import com.jxszj.utils.JDYAPIUtils;

/**
 * 
 * <pre>
 * <b>Description:</b> 
 *    定时任务：将SAP管理出库交货中的数据每10分钟同步到简道云华生集团MES出库交货单
 * <b>Author:</b> yanwei
 * <b>Date:</b> 2021年3月16日上午10:19:32
 * </pre>
 */
public class PickingToJdyQuartz {
	
	// 简道云
	private static final  String APP_ID = "5c048a8379332d0978a68b8e";
	private static final  String ENTRY_ID = "604ae10c7194660007b9e45c";
	private static final  String API_KEY = "SGmAqjtntz3q5M0rZqm2b4nGlRpSlah6";
	

    public void execute(){
    	JDYAPIUtils api = new JDYAPIUtils(APP_ID, ENTRY_ID, API_KEY);
		List<String> urls=new ArrayList<>();
		urls.add("https://my300201-api.saps4hanacloud.cn/sap/opu/odata/sap/API_OUTBOUND_DELIVERY_SRV/A_OutbDeliveryHeader?$filter=OverallGoodsMovementStatus%20eq%20'A'");
		urls.add("https://my300201-api.saps4hanacloud.cn/sap/opu/odata/sap/API_CUSTOMER_RETURNS_DELIVERY_SRV;v=2/A_ReturnsDeliveryHeader?$filter=OverallGoodsMovementStatus%20eq%20'A'");
		for (String url : urls) {
			try {
				JSONArray array=getJSONArray(url);
				for (int i = 0; i < array.size(); i++) {
					final List<Map<String, Object>> condList = new ArrayList<Map<String, Object>>();
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("field", "_widget_1615520154903");
					map.put("type", "text");
					map.put("method", "eq");
					map.put("value", array.getJSONObject(i).get("DeliveryDocument"));
					condList.add(map);
					Map<String, Object> filter = new HashMap<String, Object>() {
						{
							put("rel", "and");
							put("cond", condList);
						}
					};
					List<Map<String, Object>> list=api.getAllFormData(null, filter);
					if(list.size()==0){
						createData(array.getJSONObject(i),api);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
    }
    
    public void createData(JSONObject json,JDYAPIUtils api){
    	//主表
    	Map<String,Object> header=new HashMap<>();
    	Map<String, Object> m1 = new HashMap<String, Object>();
		m1.put("value", json.get("DeliveryDocument"));
		header.put("_widget_1615520154903", m1);// 出库交货单号
		Map<String, Object> m2 = new HashMap<String, Object>();
		m2.put("value", json.get("ShipToParty"));
		header.put("_widget_1615520157414", m2);// 收货方编码
		Map<String, Object> m3 = new HashMap<String, Object>();
		m3.put("value", json.get("ShippingPoint"));
		header.put("_widget_1615520154994", m3);// 装运点
		Map<String, Object> m4 = new HashMap<String, Object>();
		m4.put("value", json.get("DeliveryDocumentType"));
		header.put("_widget_1615520155056", m4);// 交货类型
		Map<String, Object> m5 = new HashMap<String, Object>();
		m5.put("value", DateUtils.getLongToString(json.getString("DeliveryDate"),DateUtils.FORMAT_1_STRING));
		header.put("_widget_1615520155030", m5);// 交货日期
		//子表
		List<Map<String,Object>> listItem=new ArrayList<>();
		String uri=json.getJSONObject("to_DeliveryDocumentItem").getJSONObject("__deferred").getString("uri");
		JSONArray array=getJSONArray(uri);
		for (int i = 0; i < array.size(); i++) {
			Map<String,Object> item=new HashMap<>();
			Map<String, Object> m6 = new HashMap<String, Object>();
			m6.put("value", array.getJSONObject(i).get("DeliveryDocumentItem"));
			item.put("_widget_1615520155782", m6);// 项目
			Map<String, Object> m7 = new HashMap<String, Object>();
			m7.put("value", array.getJSONObject(i).get("Material"));
			item.put("_widget_1615520155867", m7);// 物料编码
			Map<String, Object> m8 = new HashMap<String, Object>();
			m8.put("value", array.getJSONObject(i).get("DeliveryDocumentItemText"));
			item.put("_widget_1615520155885", m8);// 物料名称
			Map<String, Object> m9 = new HashMap<String, Object>();
			m9.put("value", array.getJSONObject(i).get("Plant"));
			item.put("_widget_1615520156024", m9);// 工厂
			Map<String, Object> m10 = new HashMap<String, Object>();
			m10.put("value", array.getJSONObject(i).get("StorageLocation"));
			item.put("_widget_1615520156048", m10);// 存储地点
			Map<String, Object> m11 = new HashMap<String, Object>();
			m11.put("value", array.getJSONObject(i).get("ActualDeliveryQuantity"));
			item.put("_widget_1615520156111", m11);// 交货数量
			Map<String, Object> m12 = new HashMap<String, Object>();
			m12.put("value", array.getJSONObject(i).get("BaseUnit"));
			item.put("_widget_1615520156271", m12);// 数量单位
			Map<String, Object> m13 = new HashMap<String, Object>();
			m13.put("value", array.getJSONObject(i).get("ItemVolume"));
			item.put("_widget_1615520156338", m13);// 体积
			Map<String, Object> m14 = new HashMap<String, Object>();
			m14.put("value", array.getJSONObject(i).get("ItemVolumeUnit"));
			item.put("_widget_1615520156616", m14);// 单位(体积)
			listItem.add(item);
		}
		Map<String, Object> m15 = new HashMap<String, Object>();
		m15.put("value", listItem);
		header.put("_widget_1615520155677", m15);// 出库交货明细
		api.createCpData(header);
    }
    
    public JSONArray getJSONArray(String url){
    	JSONArray  array=null;
    	try {
    		CloseableHttpClient httpClient = HttpClientBuilder.create().build();   
			HttpGet httpGet = new HttpGet(url);
			CloseableHttpResponse response = null;
    		httpGet.setHeader("Content-Type","application/json");
			httpGet.setHeader("Accept","application/json");
			httpGet.setHeader("Authorization", "Basic SkRZVVNFUjpIZmpUUD5UZ2ZxUU10SEVnaFpiVXhtZ2VOdnJmZlh3OUNvQm5BVGps");
			// 由客户端执行(发送)Get请求
			response = httpClient.execute(httpGet);
			// 从响应模型中获取响应实体
			HttpEntity responseEntity = response.getEntity();
			if(response.getStatusLine().getStatusCode()==200){
				JSONObject jsonObject=JSON.parseObject(EntityUtils.toString(responseEntity));
				jsonObject= jsonObject.getJSONObject("d");
				array = jsonObject.getJSONArray("results");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return array;
    }
    
}

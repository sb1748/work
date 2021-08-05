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
import com.jxszj.utils.DateUtils;
import com.jxszj.utils.ExceptionRobot;
import com.jxszj.utils.JDYAPIUtils;

/**
 * 
 * <pre>
 * <b>.</b>
 * <b>Description:</b> 
 *    将SAP订单明细金额(出货分析)同步到简道云进销商之家O1出货明细（2小时同步一次）
 * <b>Author:</b> yanwei
 * <b>Date:</b> 2020年6月4日下午3:46:44
 * </pre>
 */
public class SapOrderListSendJdyChmxQuartz {
	
	private static Logger logger = LoggerFactory.getLogger(SapOrderListSendJdyChmxQuartz.class.getName());

	// 简道云
	String appId = "5cc110c3b3c41744aaa12b2e";
	String entryId = "5ed861a3d7a3c600067b75a0";
	String apiKey = "uWEAujuxvzv5fMVifOvMRzlIJcPLgYkv";
	JDYAPIUtils api = new JDYAPIUtils(appId, entryId, apiKey);
	
	
	public void execute(){
		logger.info("--------------------明细金额(出货分析)--------------------");
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();   
		HttpGet httpGet = new HttpGet("https://my300201-api.saps4hanacloud.cn/sap/opu/odata/sap/YY1_ORDERLISTVALUEAPI_CDS/YY1_OrderListValueAPI");
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
					map1.put("field", "_widget_1591256704534");
					map1.put("type", "text");
					map1.put("method", "eq");
					map1.put("value",array.getJSONObject(i).getString("SalesDocument")+array.getJSONObject(i).getString("SalesDocumentItem")+array.getJSONObject(i).getString("ConditionType")+array.getJSONObject(i).getString("DeliveryDocument")+array.getJSONObject(i).getString("DeliveryDocumentItem"));
			        condList.add(map1);
			        Map<String, Object> filter = new HashMap<String, Object>(){
			            {
			                put("rel", "and");
			                put("cond", condList);
			            }
			        };
			        List<Map<String, Object>> list= api.getAllFormData(null, filter);
			        Map<String, Object> rawData=getData(array.getJSONObject(i));
			        if(list.size()==0 && rawData!=null){
			        	api.createForData(rawData);
			        }else if(list.size()==1 && rawData!=null){
			        	api.updateForData(list.get(0).get("_id").toString(), rawData);
			        }else if(list.size()>1 || rawData==null){
			        	ExceptionRobot.robotSapMessage("订单明细金额(出货分析)","销售凭证"+array.getJSONObject(i).getString("SalesDocument")+"销售凭证项目"+array.getJSONObject(i).getString("SalesDocumentItem")+"条件类型"+array.getJSONObject(i).getString("ConditionType")+"同步失败");
			        }
				}
			}else{
				ExceptionRobot.robotSapMessage("订单明细金额(出货分析)","调用SAP接口失败，接口状态码"+response.getStatusLine().getStatusCode());
			}
		} catch (Exception e) {
			e.printStackTrace();
			ExceptionRobot.robotSapMessage("订单明细金额(出货分析)","程序运行出错"+e.getMessage());
		}
	}
	
	public Map<String, Object> getData(JSONObject json){
		Map<String, Object> rawData=null;
		try {
			rawData = new HashMap<String, Object>();
			Map<String, Object> m1 = new HashMap<String, Object>();
			m1.put("value", json.getString("SalesDocument")+json.getString("SalesDocumentItem")+json.getString("ConditionType")+json.getString("DeliveryDocument")+json.getString("DeliveryDocumentItem"));
			rawData.put("_widget_1591256704534", m1);// 唯一键
			Map<String, Object> m2 = new HashMap<String, Object>();
			m2.put("value", json.get("SalesOrganization"));
			rawData.put("_widget_1591239158858", m2);// 销售组织
			Map<String, Object> m3 = new HashMap<String, Object>();
			m3.put("value", json.get("DistributionChannel"));
			rawData.put("_widget_1591239158890", m3);// 分销渠道
			Map<String, Object> m4 = new HashMap<String, Object>();
			m4.put("value", json.get("OrganizationDivision"));
			rawData.put("_widget_1591239158922", m4);// 产品组
			Map<String, Object> m5 = new HashMap<String, Object>();
			m5.put("value", json.get("SalesDocumentType"));
			rawData.put("_widget_1591239158954", m5);// 销售凭证类型
			Map<String, Object> m6 = new HashMap<String, Object>();
			m6.put("value", json.get("SalesDocumentTypeName"));
			rawData.put("_widget_1591239159458", m6);// 销售凭证类型描述
			Map<String, Object> m7 = new HashMap<String, Object>();
			m7.put("value", json.get("FirstSalesSpecProductGroup"));
			rawData.put("_widget_1591239159617", m7);// 品牌SAP码
			Map<String, Object> m8 = new HashMap<String, Object>();
			m8.put("value", json.get("SalesDistrict"));
			rawData.put("_widget_1591239160301", m8);// 销售地区编码
			Map<String, Object> m9 = new HashMap<String, Object>();
			m9.put("value", json.get("SalesDistrictName"));
			rawData.put("_widget_1591239160254", m9);// 销售地区名称
			Map<String, Object> m10 = new HashMap<String, Object>();
			m10.put("value", json.get("AdditionalMaterialGroup1Name"));
			rawData.put("_widget_1591239159685", m10);// 品牌名称
			Map<String, Object> m11 = new HashMap<String, Object>();
			m11.put("value", json.get("SalesDocument"));
			rawData.put("_widget_1591239158826", m11);// 销售凭证
			Map<String, Object> m12 = new HashMap<String, Object>();
			m12.put("value", json.get("SalesDocumentItem"));
			rawData.put("_widget_1591239159082", m12);// 销售凭证项目
			Map<String, Object> m13 = new HashMap<String, Object>();
			m13.put("value", json.get("SoldToParty"));
			rawData.put("_widget_1591239158986", m13);// 客户编码
			Map<String, Object> m14 = new HashMap<String, Object>();
			m14.put("value", json.get("CustomerName"));
			rawData.put("_widget_1591239159018", m14);// 客户名称
			Map<String, Object> m15 = new HashMap<String, Object>();
			m15.put("value", json.get("DeliveryDocument"));
			rawData.put("_widget_1591239160190", m15);// 交货凭证
			Map<String, Object> m16 = new HashMap<String, Object>();
			m16.put("value", json.get("DeliveryDocumentItem"));
			rawData.put("_widget_1591239160222", m16);// 交货凭证项目
			Map<String, Object> m17 = new HashMap<String, Object>();
			m17.put("value", json.get("PurchaseOrderByCustomer"));
			rawData.put("_widget_1591239159050", m17);// 订货单号
			Map<String, Object> m18 = new HashMap<String, Object>();
			m18.put("value",json.get("CreationDate")!=null?DateUtils.getLongToString(json.getString("CreationDate"),DateUtils.FORMAT_1_STRING):"");
			rawData.put("_widget_1591239159649", m18);// 订货日期
			Map<String, Object> m19 = new HashMap<String, Object>();
			m19.put("value", json.get("CreationDate")!=null?DateUtils.getLongToString(json.getString("CreationDate").toString(),DateUtils.FORMAT_STRING_YEAR):"");
			rawData.put("_widget_1591239160631", m19);// 订货日期-年
			Map<String, Object> m20 = new HashMap<String, Object>();
			m20.put("value", json.get("CreationDate")!=null?DateUtils.getLongToString(json.getString("CreationDate").toString(),DateUtils.FORMAT_STRING_MINUTE):"");
			rawData.put("_widget_1591239160648", m20);// 订货日期-年月
			Map<String, Object> m21 = new HashMap<String, Object>();
			m21.put("value", json.get("CreationDate")!=null?DateUtils.getLongToString(json.getString("CreationDate").toString(),DateUtils.FORMAT_2_STRING):"");
			rawData.put("_widget_1591239160665", m21);// 订货日期-年月日
			Map<String, Object> m22 = new HashMap<String, Object>();
			m22.put("value", json.get("ProductType"));
			rawData.put("_widget_1591239159717", m22);// 产品类型
			Map<String, Object> m23 = new HashMap<String, Object>();
			m23.put("value", json.get("ProductGroup"));
			rawData.put("_widget_1591239159540", m23);// 物料组编码
			Map<String, Object> m24 = new HashMap<String, Object>();
			m24.put("value", json.get("MaterialGroupName"));
			rawData.put("_widget_1591239159749", m24);// 物料组名称
			Map<String, Object> m25 = new HashMap<String, Object>();
			m25.put("value", json.get("Material"));
			rawData.put("_widget_1591239159114", m25);// 产品编码
			Map<String, Object> m26 = new HashMap<String, Object>();
			m26.put("value", json.get("SalesDocumentItemText"));
			rawData.put("_widget_1591239159146", m26);// 产品名称
			Map<String, Object> m27 = new HashMap<String, Object>();
			m27.put("value", json.get("OrderQuantity"));
			rawData.put("_widget_1591239159258", m27);// 订货数量
			Map<String, Object> m28 = new HashMap<String, Object>();
			m28.put("value", json.get("BaseUnit"));
			rawData.put("_widget_1591239159781", m28);// 基本计量单位
			Map<String, Object> m29 = new HashMap<String, Object>();
			m29.put("value", json.get("OrderQuantityUnit"));
			rawData.put("_widget_1591239159286", m29);// 销售单位
			Map<String, Object> m30 = new HashMap<String, Object>();
			m30.put("value", json.get("DeliveryStatus"));
			rawData.put("_widget_1591239159318", m30);// 交货状态
			Map<String, Object> m31 = new HashMap<String, Object>();
			m31.put("value", json.get("TransactionCurrency"));
			rawData.put("_widget_1591239159426", m31);// 凭证货币
			Map<String, Object> m32 = new HashMap<String, Object>();
			m32.put("value", json.get("SDDocumentRejectionStatus"));
			rawData.put("_widget_1591239159379", m32);//拒绝状态
			Map<String, Object> m33 = new HashMap<String, Object>();
			
			m33.put("value", json.get("ActualGoodsMovementDate")!=null?DateUtils.getLongToString(json.getString("ActualGoodsMovementDate").toString(),DateUtils.FORMAT_1_STRING):"");
			rawData.put("_widget_1591239159813", m33);//出货日期
			Map<String, Object> m34 = new HashMap<String, Object>();
			m34.put("value", json.get("ActualGoodsMovementDate")!=null?DateUtils.getLongToString(json.get("ActualGoodsMovementDate").toString(),DateUtils.FORMAT_STRING_YEAR):"");
			rawData.put("_widget_1591239160855", m34);//出货日期-年
			Map<String, Object> m35 = new HashMap<String, Object>();
			m35.put("value", json.get("ActualGoodsMovementDate")!=null?DateUtils.getLongToString(json.get("ActualGoodsMovementDate").toString(),DateUtils.FORMAT_STRING_MINUTE):"");
			rawData.put("_widget_1591239160872", m35);//出货日期-年月
			Map<String, Object> m36 = new HashMap<String, Object>();
			m36.put("value", json.get("ActualGoodsMovementDate")!=null?DateUtils.getLongToString(json.get("ActualGoodsMovementDate").toString(),DateUtils.FORMAT_2_STRING):"");
			rawData.put("_widget_1591239160889", m36);//出货日期-年月日
			Map<String, Object> m37 = new HashMap<String, Object>();
			m37.put("value", json.get("ActualDeliveredQtyInBaseUnit"));
			rawData.put("_widget_1591239159798", m37);//出货数量
			Map<String, Object> m38 = new HashMap<String, Object>();
			m38.put("value", json.get("ConditionType"));
			rawData.put("_widget_1591239159909", m38);//条件类型
			Map<String, Object> m39 = new HashMap<String, Object>();
			m39.put("value", json.get("ConditionBaseValue"));
			rawData.put("_widget_1591239159941", m39);//基准值
			Map<String, Object> m40 = new HashMap<String, Object>();
			m40.put("value", json.get("ConditionApplication"));
			rawData.put("_widget_1591239159996", m40);//应用程序
			Map<String, Object> m41 = new HashMap<String, Object>();
			m41.put("value", json.get("ConditionRateValue"));
			rawData.put("_widget_1591239160083", m41);//金额
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rawData;
	}
}

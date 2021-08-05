package com.jxszj.quartz;

import java.io.FileNotFoundException;
import java.io.IOException;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jxszj.mapper.sap.SapNewdeliveryTbMapper;
import com.jxszj.mapper.sap.SapOlddeliveryTbMapper;
import com.jxszj.pojo.sap.SapDeliveryTb;
import com.jxszj.utils.DateUtils;
import com.jxszj.utils.ExceptionRobot;
import com.jxszj.utils.JDYAPIUtils;

/**  
 * 
 * <pre>
 * <b>.</b>
 * <b>Description:</b> 
 *    获取SAP自定义交货进度YY1_OrderDelAPI
 * <b>Author:</b> yanwei
 * <b>Date:</b> 2020年5月12日下午1:45:28
 * </pre>
 */
@Service
public class SapDeliverySendJdyDeliveryQuartz {
	
	@Autowired
	private SapNewdeliveryTbMapper sapNewdeliveryTbMapper;
	
	@Autowired
	private SapOlddeliveryTbMapper sapOlddeliveryTbMapper;

	// 简道云
	String appId = "5cc110c3b3c41744aaa12b2e";
	String entryId = "5eb6886e8fbd0c0006117aad";
	String apiKey = "uWEAujuxvzv5fMVifOvMRzlIJcPLgYkv";
	JDYAPIUtils api = null;
	
	public void execute() throws FileNotFoundException, IOException{
		api = new JDYAPIUtils(appId, entryId, apiKey);
		List<SapDeliveryTb> sapDeliveryTbs=getDelivery();
		sapNewdeliveryTbMapper.truncateTable();
		sapNewdeliveryTbMapper.insertByBatch(sapDeliveryTbs);//先添加
		sapDeliveryTbs=sapNewdeliveryTbMapper.selectByGroupById();//再分组查询
		sapNewdeliveryTbMapper.truncateTable();
		sapNewdeliveryTbMapper.insertByBatch(sapDeliveryTbs);//再添加
		
		//获取有更新的数据
		List<SapDeliveryTb> updateSapDeliveryTbs=sapNewdeliveryTbMapper.selectCompare1();
		for (int i = 0; i < updateSapDeliveryTbs.size(); i++) {
			updateData(updateSapDeliveryTbs.get(i));
		}
		
		//获取有新增的数据
		List<SapDeliveryTb> createSapDeliveryTbs=sapNewdeliveryTbMapper.selectCompare2();
		for (int i = 0; i < createSapDeliveryTbs.size(); i++) {
			createData(createSapDeliveryTbs.get(i));
		}
		
		//查询是否有被取消的产品
		List<SapDeliveryTb> deleteSapDeliveryTbs=sapOlddeliveryTbMapper.selectCompare();
		for (int i = 0; i < deleteSapDeliveryTbs.size(); i++) {
			deleteData(deleteSapDeliveryTbs.get(i));
		}
		
		sapOlddeliveryTbMapper.truncateTable();
		sapOlddeliveryTbMapper.insertByBatch(sapDeliveryTbs);
	}
	
	public List<SapDeliveryTb> getDelivery(){
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();   
		HttpGet httpGet = new HttpGet("https://my300201-api.saps4hanacloud.cn/sap/opu/odata/sap/YY1_DELIVERYQUERY1API_CDS/YY1_DeliveryQuery1API");
		CloseableHttpResponse response = null;
		List<SapDeliveryTb> sapNewdeliveryTbs =new ArrayList<SapDeliveryTb>();
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
				jsonObject= jsonObject.getJSONObject("d");
				JSONArray  array = jsonObject.getJSONArray("results");
				for (int i = 0; i < array.size(); i++) {
					SapDeliveryTb sapNewdeliveryTb=new SapDeliveryTb();
					sapNewdeliveryTb.setId(array.getJSONObject(i).getString("SalesDocument")+array.getJSONObject(i).getString("SalesDocumentItem"));
					sapNewdeliveryTb.setXsdd(array.getJSONObject(i).getString("SalesDocument"));
					sapNewdeliveryTb.setXszz(array.getJSONObject(i).getString("SalesOrganization"));
					sapNewdeliveryTb.setFxqd(array.getJSONObject(i).getString("DistributionChannel"));
					sapNewdeliveryTb.setCpz(array.getJSONObject(i).getString("OrganizationDivision"));
					sapNewdeliveryTb.setDdsl(array.getJSONObject(i).getString("OrderQuantity"));
					sapNewdeliveryTb.setXsdw(array.getJSONObject(i).getString("OrderQuantityUnit"));
					sapNewdeliveryTb.setXspzxm(array.getJSONObject(i).getString("SalesDocumentItem"));
					sapNewdeliveryTb.setCpbm(array.getJSONObject(i).getString("Material"));
					sapNewdeliveryTb.setCpmc(array.getJSONObject(i).getString("SalesDocumentItemText"));
					sapNewdeliveryTb.setJhzt(array.getJSONObject(i).getString("DeliveryStatus"));
					sapNewdeliveryTb.setJxsbm(array.getJSONObject(i).getString("SoldToParty"));
					sapNewdeliveryTb.setJxsmc(array.getJSONObject(i).getString("CustomerName"));
					sapNewdeliveryTb.setJjzt(array.getJSONObject(i).getString("SDDocumentRejectionStatus"));
					sapNewdeliveryTb.setCjrq(DateUtils.getLongToString(array.getJSONObject(i).getString("CreationDate"),DateUtils.FORMAT_1_STRING));
					sapNewdeliveryTb.setDhlx(array.getJSONObject(i).getString("YY1_DHLX_SDI"));
					sapNewdeliveryTb.setCjptcdgd(array.getJSONObject(i).getString("YY1_CJPTCDGD_SDI"));
					sapNewdeliveryTb.setCjptcj(array.getJSONObject(i).getString("YY1_CJPTCJ_SDI"));
					sapNewdeliveryTb.setQt(array.getJSONObject(i).getString("YY1_DHQTBZ_SDI"));
					sapNewdeliveryTb.setGcgd(array.getJSONObject(i).getString("YY1_SalesPerson_SDI"));
					sapNewdeliveryTb.setJhsl(array.getJSONObject(i).getString("ActualDeliveryQuantity"));
					sapNewdeliveryTbs.add(sapNewdeliveryTb);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sapNewdeliveryTbs;
	}
	
	public void createData(SapDeliveryTb sapDeliveryTb){
		try {
			Map<String, Object> rawData = new HashMap<String, Object>();
			Map<String, Object> m1 = new HashMap<String, Object>();
			m1.put("value", sapDeliveryTb.getId());
			rawData.put("_widget_1605086326675", m1);// 唯一值
			Map<String, Object> m2 = new HashMap<String, Object>();
			m2.put("value", sapDeliveryTb.getXszz());
			rawData.put("_widget_1589018127597", m2);// 销售组织
			Map<String, Object> m3 = new HashMap<String, Object>();
			m3.put("value", sapDeliveryTb.getFxqd());
			rawData.put("_widget_1589018127612", m3);// 分销渠道
			Map<String, Object> m4 = new HashMap<String, Object>();
			m4.put("value", sapDeliveryTb.getCpz());
			rawData.put("_widget_1589018127877", m4);// 产品组
			Map<String, Object> m6 = new HashMap<String, Object>();
			m6.put("value", sapDeliveryTb.getXsdd());
			rawData.put("_widget_1589018127582", m6);// 销售订单
			Map<String, Object> m7 = new HashMap<String, Object>();
			m7.put("value", sapDeliveryTb.getDdsl());
			rawData.put("_widget_1606111697411", m7);// 订单数量
			Map<String, Object> m8 = new HashMap<String, Object>();
			m8.put("value", sapDeliveryTb.getXsdw());
			rawData.put("_widget_1605086326780", m8);//销售单位
			Map<String, Object> m9 = new HashMap<String, Object>();
			m9.put("value", sapDeliveryTb.getXspzxm());
			rawData.put("_widget_1605086326797", m9);// 销售凭证项目
			Map<String, Object> m13 = new HashMap<String, Object>();
			m13.put("value", sapDeliveryTb.getCpbm());
			rawData.put("_widget_1589018127627", m13);// 产品编码
			Map<String, Object> m14 = new HashMap<String, Object>();
			m14.put("value", sapDeliveryTb.getCpmc());
			rawData.put("_widget_1589018127642", m14);// 产品名称
			Map<String, Object> m16 = new HashMap<String, Object>();
			m16.put("value", sapDeliveryTb.getJhzt());
			rawData.put("_widget_1589018127657", m16);// 交货状态
			Map<String, Object> m17 = new HashMap<String, Object>();
			m17.put("value", sapDeliveryTb.getJxsbm());
			rawData.put("_widget_1589018128091", m17);// 经销商编码
			Map<String, Object> m19 = new HashMap<String, Object>();
			m19.put("value", sapDeliveryTb.getJxsmc());
			rawData.put("_widget_1589018128242", m19);// 经销商名称
			Map<String, Object> m15 = new HashMap<String, Object>();
			m15.put("value", sapDeliveryTb.getJjzt());
			rawData.put("_widget_1591952504445", m15);// 拒绝状态
			Map<String, Object> m18 = new HashMap<String, Object>();
			m18.put("value", sapDeliveryTb.getCjrq());
			rawData.put("_widget_1589018128339", m18);//创建日期
			Map<String, Object> m10 = new HashMap<String, Object>();
			m10.put("value", sapDeliveryTb.getDhlx());
			rawData.put("_widget_1591952287401", m10);// 订货类型
			Map<String, Object> m20 = new HashMap<String, Object>();
			m20.put("value", sapDeliveryTb.getCjptcdgd());
			rawData.put("_widget_1591952287384", m20);// 床架配套床垫高度
			Map<String, Object> m22 = new HashMap<String, Object>();
			m22.put("value", sapDeliveryTb.getCjptcj());
			rawData.put("_widget_1589018128119", m22);// 床架配套床脚
			Map<String, Object> m23 = new HashMap<String, Object>();
			m23.put("value", sapDeliveryTb.getQt());
			rawData.put("_widget_1589018128367", m23);// 其他
			Map<String, Object> m25 = new HashMap<String, Object>();
			m25.put("value", sapDeliveryTb.getGcgd());
			rawData.put("_widget_1606111697541", m25);// 工厂跟单
			Map<String, Object> m26 = new HashMap<String, Object>();
			m26.put("value", sapDeliveryTb.getJhsl());
			rawData.put("_widget_1606111697266", m26);// 交货数量
			api.createForData(rawData);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void updateData(SapDeliveryTb sapDeliveryTb){
		try {
			final List<Map<String, Object>> condList = new ArrayList<Map<String, Object>>();
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("field", "_widget_1605086326675");
			map.put("type", "text");
			map.put("method", "eq");
			map.put("value",sapDeliveryTb.getId());
	        condList.add(map);
	        Map<String, Object> filter = new HashMap<String, Object>(){
	            {
	                put("rel", "and");
	                put("cond", condList);
	            }
	        };
	        List<Map<String, Object>> list= api.getAllFormData(null, filter);
			if(list.size()!=1){
				ExceptionRobot.robotSapMessage("同步SAP经销商交货进度","不存在或存在多个id为"+sapDeliveryTb.getId());
			}else{
				Map<String, Object> rawData = new HashMap<String, Object>();
				Map<String, Object> m1 = new HashMap<String, Object>();
				m1.put("value", sapDeliveryTb.getJhsl());
				rawData.put("_widget_1606111697266", m1);// 交货数量
				api.updateData(list.get(0).get("_id").toString(), rawData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteData(SapDeliveryTb sapDeliveryTb){
		try {
			final List<Map<String, Object>> condList = new ArrayList<Map<String, Object>>();
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("field", "_widget_1605086326675");
			map.put("type", "text");
			map.put("method", "eq");
			map.put("value",sapDeliveryTb.getId());
	        condList.add(map);
	        Map<String, Object> filter = new HashMap<String, Object>(){
	            {
	                put("rel", "and");
	                put("cond", condList);
	            }
	        };
	        List<Map<String, Object>> list= api.getAllFormData(null, filter);
			if(list.size()==1){
				api.deleteData(list.get(0).get("_id").toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

}

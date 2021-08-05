package com.jxszj.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jxszj.mapper.sap.SapDskqzTbMapper;
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
public class SapDeliverySendJdyDeliveryTest {
	
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
//		List<SapDeliveryTb> updateSapDeliveryTbs=sapNewdeliveryTbMapper.selectCompare1();
//		for (int i = 0; i < updateSapDeliveryTbs.size(); i++) {
//			updateData(updateSapDeliveryTbs.get(i));
//		}
//		
//		//获取有新增的数据
//		List<SapDeliveryTb> createSapDeliveryTbs=sapNewdeliveryTbMapper.selectCompare2();
//		for (int i = 0; i < createSapDeliveryTbs.size(); i++) {
//			createData(createSapDeliveryTbs.get(i));
//		}
		
		sapOlddeliveryTbMapper.truncateTable();
		sapOlddeliveryTbMapper.insertByBatch(sapDeliveryTbs);
		
		
		
		String excel = "C:/U/work/jxszj_zhmd/src/main/webapp/WEB-INF/excel/经销商交货.xlsx";
		File fi = new File(excel);
		XSSFWorkbook wb =new XSSFWorkbook(new FileInputStream(fi));
		XSSFSheet sheet = wb.getSheetAt(0);
		int rowIndex = 1;
		int index = 1;
		System.out.println("生成Excel");
		for (int i = 0; i < sapDeliveryTbs.size(); i++) {
			XSSFRow row = sheet.getRow(rowIndex);
			if (null == row) {
				row = sheet.createRow(rowIndex);
			}
			//唯一键
			XSSFCell cell1 = row.getCell(0);
			if (null == cell1) {
				cell1 = row.createCell(0);
			}
			cell1.setCellValue(sapDeliveryTbs.get(i).getId());
			//产品类型
			XSSFCell cell2 = row.getCell(1);
			if (null == cell2) {
				cell2 = row.createCell(1);
			}
			cell2.setCellValue(sapDeliveryTbs.get(i).getXszz());
			//物料组编码
			XSSFCell cell3 = row.getCell(2);
			if (null == cell3) {
				cell3 = row.createCell(2);
			}
			cell3.setCellValue(sapDeliveryTbs.get(i).getFxqd());
			//物料组名称
			XSSFCell cell4 = row.getCell(3);
			if (null == cell4) {
				cell4 = row.createCell(3);
			}
			cell4.setCellValue(sapDeliveryTbs.get(i).getCpz());
			
			//采购组
			XSSFCell cell5 = row.getCell(4);
			if (null == cell5) {
				cell5 = row.createCell(4);
			}
			cell5.setCellValue(sapDeliveryTbs.get(i).getXsdd());
			
			//产品编码
			XSSFCell cell6 = row.getCell(5);
			if (null == cell6) {
				cell6 = row.createCell(5);
			}
			cell6.setCellValue(sapDeliveryTbs.get(i).getDdsl());
			//产品名称
			XSSFCell cell7 = row.getCell(6);
			if (null == cell7) {
				cell7 = row.createCell(6);
			}
			cell7.setCellValue(sapDeliveryTbs.get(i).getXsdw());
			//规格尺寸
			XSSFCell cell8 = row.getCell(7);
			if (null == cell8) {
				cell8 = row.createCell(7);
			}
			cell8.setCellValue(sapDeliveryTbs.get(i).getXspzxm());
			//基本单位
			XSSFCell cell9 = row.getCell(8);
			if (null == cell9) {
				cell9 = row.createCell(8);
			}
			cell9.setCellValue(sapDeliveryTbs.get(i).getCpbm());
			//基本单位文本
			XSSFCell cell10 = row.getCell(9);
			if (null == cell10) {
				cell10 = row.createCell(9);
			}
			cell10.setCellValue(sapDeliveryTbs.get(i).getCpmc());
			//创建日期
			XSSFCell cell11 = row.getCell(10);
			if (null == cell11) {
				cell11 = row.createCell(10);
			}
			cell11.setCellValue(sapDeliveryTbs.get(i).getJhzt());
			//最近修改日期
			XSSFCell cell12 = row.getCell(11);
			if (null == cell12) {
				cell12 = row.createCell(11);
			}
			cell12.setCellValue(sapDeliveryTbs.get(i).getJxsbm());
			//使用状态
			XSSFCell cell13 = row.getCell(12);
			if (null == cell13) {
				cell13 = row.createCell(12);
			}
			cell13.setCellValue(sapDeliveryTbs.get(i).getJxsmc());
			//评估范围
			XSSFCell cell14 = row.getCell(13);
			if (null == cell14) {
				cell14 = row.createCell(13);
			}
			cell14.setCellValue(sapDeliveryTbs.get(i).getJjzt());
			//评估类型
			XSSFCell cell15 = row.getCell(14);
			if (null == cell15) {
				cell15 = row.createCell(14);
			}
			cell15.setCellValue(sapDeliveryTbs.get(i).getCjrq());
			//标准价格
			XSSFCell cell16 = row.getCell(15);
			if (null == cell16) {
				cell16 = row.createCell(15);
			}
			cell16.setCellValue(sapDeliveryTbs.get(i).getDhlx());
			//移动价格
			XSSFCell cell17 = row.getCell(16);
			if (null == cell17) {
				cell17 = row.createCell(16);
			}
			cell17.setCellValue(sapDeliveryTbs.get(i).getCjptcdgd());
			
			XSSFCell cell18 = row.getCell(17);
			if (null == cell18) {
				cell18 = row.createCell(17);
			}
			cell18.setCellValue(sapDeliveryTbs.get(i).getCjptcj());
			
			XSSFCell cell19 = row.getCell(18);
			if (null == cell19) {
				cell19 = row.createCell(18);
			}
			cell19.setCellValue(sapDeliveryTbs.get(i).getQt());
			
			XSSFCell cell20 = row.getCell(19);
			if (null == cell20) {
				cell20 = row.createCell(19);
			}
			cell20.setCellValue(sapDeliveryTbs.get(i).getGcgd());
			
			XSSFCell cell21 = row.getCell(20);
			if (null == cell21) {
				cell21 = row.createCell(20);
			}
			cell21.setCellValue(sapDeliveryTbs.get(i).getJhsl());
			rowIndex++;
			index++;
		}
		String fileName = "华生SAP物料信息接口同步.xlsx";

		FileOutputStream fileOutputStream = new FileOutputStream("C:/U/excel/" + fileName);// 指定路径与名字和格式
		wb.write(fileOutputStream);// 将数据写出去
		fileOutputStream.close();// 关闭输出流
		
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
			rawData.put("_widget_1589018127946", m7);// 订单数量
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
			rawData.put("_widget_1589018128450", m25);// 工厂跟单
			Map<String, Object> m26 = new HashMap<String, Object>();
			m26.put("value", sapDeliveryTb.getJhsl());
			rawData.put("_widget_1589018128693", m26);// 交货数量
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
				rawData.put("_widget_1589018128693", m1);// 交货数量
				api.updateData(list.get(0).get("_id").toString(), rawData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

}

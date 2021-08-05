package com.jxszj.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * <pre>
 * <b>.</b>
 * <b>Description:</b> 
 *    将SAP中的价格主数据同步至简道云价格信息中
 * <b>Author:</b> yanwei
 * <b>Date:</b> 2020年3月30日下午1:49:59
 * </pre>
 */
public class SapPriceSendJdyTest {

	
	
	public static final String URL = "jdbc:mysql://rm-wz9a0y703d5n2m4du3o.mysql.rds.aliyuncs.com:3306/jsszj?characterEncoding=utf-8&rewriteBatchedStatements=true";
	public static final String USER = "root";
	public static final String PASSWORD = "Root@123";
	
	
	public void getPrice() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			conn.setAutoCommit(false);
			
			//truncate table sap_price_cust_tb
			String sql="truncate table sap_price_cust_tb";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
			conn.commit();
			
			sql="truncate table sap_price_tb";
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
			conn.commit();
			
			//先查询客户物料表
			JSONArray  array1=getJSONArray("https://my300201-api.saps4hanacloud.cn/sap/opu/odata/sap/API_SLSPRICINGCONDITIONRECORD_SRV/A_SlsPrcgCndnRecdValidity");
			sql = "insert into sap_price_cust_tb(ConditionRecord,ConditionValidityStartDate,ConditionValidityEndDate,ConditionType,Customer,Material,SalesOrganization,DistributionChannel,CustomerPriceGroup) values(?,?,?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			System.out.println("array1.size()="+array1.size());
			int num=0;
			for (int i = 0; i < array1.size(); i++) {
				if("YK07".equals(array1.getJSONObject(i).getString("ConditionType"))||"PPR0".equals(array1.getJSONObject(i).getString("ConditionType"))){
					if("B001".equals(array1.getJSONObject(i).getString("SalesOrganization")) || "C001".equals(array1.getJSONObject(i).getString("SalesOrganization"))){
						if("01".equals(array1.getJSONObject(i).getString("DistributionChannel"))){
							pstmt.setString(1, array1.getJSONObject(i).getString("ConditionRecord"));//在客户物料表唯一编号，如果修改了价格，编号会改变
							pstmt.setDate(2, new Date(Long.parseLong(array1.getJSONObject(i).getString("ConditionValidityStartDate").substring(6, array1.getJSONObject(i).getString("ConditionValidityStartDate").length()-2))));//有效期开始时间
							pstmt.setDate(3, new Date(Long.parseLong(array1.getJSONObject(i).getString("ConditionValidityEndDate").substring(6, array1.getJSONObject(i).getString("ConditionValidityEndDate").length()-2))));//有效期结束时间
							pstmt.setString(4, array1.getJSONObject(i).getString("ConditionType"));//条件类型
							pstmt.setString(5, array1.getJSONObject(i).getString("Customer"));//客户编码
							pstmt.setString(6, array1.getJSONObject(i).getString("Material"));//物料编码
							pstmt.setString(7, array1.getJSONObject(i).getString("SalesOrganization"));//销售组织
							pstmt.setString(8, array1.getJSONObject(i).getString("DistributionChannel"));//分销渠道
							pstmt.setString(9, array1.getJSONObject(i).getString("CustomerPriceGroup"));//客户价格组
							pstmt.addBatch();
							num++;
						}
					}
				}
			}
			pstmt.executeBatch();
			conn.commit();
			System.out.println("num="+num);
			//查询价格表 
			JSONArray  array2=getJSONArray("https://my300201-api.saps4hanacloud.cn/sap/opu/odata/sap/API_SLSPRICINGCONDITIONRECORD_SRV/A_SlsPrcgConditionRecord");
			sql = "insert into sap_price_tb(ConditionRecord,ConditionValidityStartDate,ConditionValidityEndDate,ConditionRateValue,ConditionRateValueUnit,ConditionQuantity,ConditionQuantityUnit) values(?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			System.out.println("array2.size()="+array2.size());
			int number=0;
			System.out.println("查询价格表开始");
			for (int i = 0; i < array2.size(); i++) {
				if(("PPR0".equals(array2.getJSONObject(i).getString("ConditionType")) || "YK07".equals(array2.getJSONObject(i).getString("ConditionType"))) && "false".equals(array2.getJSONObject(i).getString("ConditionIsDeleted")) ){
					pstmt.setString(1, array2.getJSONObject(i).getString("ConditionRecord"));//在客户物料表唯一编号，如果修改了价格，编号会改变
					pstmt.setDate(2, new Date(Long.parseLong(array2.getJSONObject(i).getString("ConditionValidityStartDate").substring(6, array2.getJSONObject(i).getString("ConditionValidityStartDate").length()-2))));//有效期开始时间
					pstmt.setDate(3, new Date(Long.parseLong(array2.getJSONObject(i).getString("ConditionValidityEndDate").substring(6, array2.getJSONObject(i).getString("ConditionValidityEndDate").length()-2))));//有效期结束时间
					pstmt.setString(4, array2.getJSONObject(i).getString("ConditionRateValue"));//价格
					pstmt.setString(5, array2.getJSONObject(i).getString("ConditionRateValueUnit"));//币种
					pstmt.setString(6, array2.getJSONObject(i).getString("ConditionQuantity"));//数量
					pstmt.setString(7, array2.getJSONObject(i).getString("ConditionQuantityUnit"));//单位
					pstmt.addBatch();
					number++;
				}
			}
			System.out.println("number="+number);
			pstmt.executeBatch();
			conn.commit();
			
			//SELECT * FROM sap_price_cust_tb,sap_price_tb WHERE sap_price_cust_tb.ConditionRecord=sap_price_tb.ConditionRecord AND sap_price_cust_tb.ConditionValidityStartDate>CURDATE()
			ArrayList<Map<String,String>> listMap=new ArrayList<Map<String,String>>();
			sql = "SELECT * FROM sap_price_cust_tb,sap_price_tb WHERE sap_price_cust_tb.ConditionRecord=sap_price_tb.ConditionRecord AND sap_price_cust_tb.ConditionValidityEndDate>=CURDATE() AND sap_price_cust_tb.ConditionValidityStartDate<=CURDATE()";
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Map<String,String> map=new HashMap<String,String>();
				map.put("CustomerPriceGroup", rs.getString("CustomerPriceGroup"));
				map.put("Customer", rs.getString("Customer"));
				map.put("Material", rs.getString("Material"));
				if("YK07".equals(rs.getString("ConditionType"))){
					map.put("price", rs.getString("ConditionRateValue"));
				}else if("PPR0".equals(rs.getString("ConditionType"))){
					map.put("price", String.valueOf(Double.parseDouble(rs.getString("ConditionRateValue"))/Double.parseDouble(rs.getString("ConditionQuantity"))));
				}
				map.put("ConditionQuantityUnit", rs.getString("ConditionQuantityUnit"));
				map.put("ConditionRateValueUnit", rs.getString("ConditionRateValueUnit"));
				map.put("ConditionType", rs.getString("ConditionType"));
				map.put("SalesOrganization", rs.getString("SalesOrganization"));
				map.put("DistributionChannel", rs.getString("DistributionChannel"));
				map.put("ConditionValidityStartDate", rs.getString("ConditionValidityStartDate"));
				map.put("ConditionValidityEndDate", rs.getString("ConditionValidityEndDate"));
				listMap.add(map);
			}
			close(rs, pstmt, conn);
			
			String excel = "E:/work/jxszj_zhmd/src/main/webapp/WEB-INF/excel/SAP经销商供货价格接口同步.xlsx";
			File fi = new File(excel);
			XSSFWorkbook wb =new XSSFWorkbook(new FileInputStream(fi));
			XSSFSheet sheet = wb.getSheetAt(0);
			int rowIndex = 1;
			int index = 1;
			System.out.println("生成Excel");
			for (int i = 0; i < listMap.size(); i++) {
				XSSFRow row = sheet.getRow(rowIndex);
				if (null == row) {
					row = sheet.createRow(rowIndex);
				}
				//唯一码 
				XSSFCell cell1 = row.getCell(0);
				if (null == cell1) {
					cell1 = row.createCell(0);
				}
				cell1.setCellValue(listMap.get(i).get("ConditionType")+listMap.get(i).get("SalesOrganization")+listMap.get(i).get("DistributionChannel")+listMap.get(i).get("Customer")+listMap.get(i).get("CustomerPriceGroup")+listMap.get(i).get("Material"));
				//销售组织
				XSSFCell cell2 = row.getCell(1);
				if (null == cell2) {
					cell2 = row.createCell(1);
				}
				cell2.setCellValue(listMap.get(i).get("SalesOrganization"));
				//分销渠道
				XSSFCell cell3 = row.getCell(2);
				if (null == cell3) {
					cell3 = row.createCell(2);
				}
				cell3.setCellValue(listMap.get(i).get("DistributionChannel"));
				//条件类型
				XSSFCell cell4 = row.getCell(3);
				if (null == cell4) {
					cell4 = row.createCell(3);
				}
				cell4.setCellValue(listMap.get(i).get("ConditionType"));
				//客户编码
				XSSFCell cell5 = row.getCell(4);
				if (null == cell5) {
					cell5 = row.createCell(4);
				}
				cell5.setCellValue(listMap.get(i).get("Customer"));
				//客户价格组
				XSSFCell cell6 = row.getCell(5);
				if (null == cell6) {
					cell6 = row.createCell(5);
				}
				cell6.setCellValue(listMap.get(i).get("CustomerPriceGroup"));
				//物料编码
				XSSFCell cell7 = row.getCell(6);
				if (null == cell7) {
					cell7 = row.createCell(6);
				}
				cell7.setCellValue(listMap.get(i).get("Material"));
				//单位
				XSSFCell cell8 = row.getCell(7);
				if (null == cell8) {
					cell8 = row.createCell(7);
				}
				cell8.setCellValue(listMap.get(i).get("ConditionQuantityUnit"));
				//币种
				XSSFCell cell9 = row.getCell(8);
				if (null == cell9) {
					cell9 = row.createCell(8);
				}
				cell9.setCellValue(listMap.get(i).get("ConditionRateValueUnit"));
				//含税单价/折扣率
				XSSFCell cell10 = row.getCell(9);
				if (null == cell10) {
					cell10 = row.createCell(9);
				}
				cell10.setCellValue(listMap.get(i).get("price"));
				
				if("PPR0".equals(listMap.get(i).get("ConditionType"))){ //PPR0标识
					if(!"".equals(listMap.get(i).get("Customer"))){
						XSSFCell cell11 = row.getCell(10);
						if (null == cell11) {
							cell11 = row.createCell(10);
						}
						cell11.setCellValue(listMap.get(i).get("SalesOrganization")+listMap.get(i).get("DistributionChannel")+listMap.get(i).get("Customer")+listMap.get(i).get("Material"));
					}else if(!"".equals(listMap.get(i).get("CustomerPriceGroup"))){
						XSSFCell cell11 = row.getCell(10);
						if (null == cell11) {
							cell11 = row.createCell(10);
						}
						cell11.setCellValue(listMap.get(i).get("SalesOrganization")+listMap.get(i).get("DistributionChannel")+listMap.get(i).get("CustomerPriceGroup")+listMap.get(i).get("Material"));
					}else if("".equals(listMap.get(i).get("Customer")) && "".equals(listMap.get(i).get("CustomerPriceGroup"))){
						XSSFCell cell11 = row.getCell(10);
						if (null == cell11) {
							cell11 = row.createCell(10);
						}
						cell11.setCellValue(listMap.get(i).get("SalesOrganization")+listMap.get(i).get("DistributionChannel")+listMap.get(i).get("Material"));
					}
				}else if("YK07".equals(listMap.get(i).get("ConditionType"))){ //YK07标识
					if(!"".equals(listMap.get(i).get("Customer")) && !"".equals(listMap.get(i).get("Material"))){
						XSSFCell cell12 = row.getCell(11);
						if (null == cell12) {
							cell12 = row.createCell(11);
						}
						cell12.setCellValue(listMap.get(i).get("SalesOrganization")+listMap.get(i).get("DistributionChannel")+listMap.get(i).get("Customer")+listMap.get(i).get("Material"));
					}else if(!"".equals(listMap.get(i).get("CustomerPriceGroup")) && !"".equals(listMap.get(i).get("Material"))){
						XSSFCell cell12 = row.getCell(11);
						if (null == cell12) {
							cell12 = row.createCell(11);
						}
						cell12.setCellValue(listMap.get(i).get("SalesOrganization")+listMap.get(i).get("DistributionChannel")+listMap.get(i).get("CustomerPriceGroup")+listMap.get(i).get("Material"));
					}
				}
				
				//有效期开始时间
				XSSFCell cell13 = row.getCell(12);
				if (null == cell13) {
					cell13 = row.createCell(12);
				}
				cell13.setCellValue(listMap.get(i).get("ConditionValidityStartDate"));
				
				//有效期结束时间
				XSSFCell cell14 = row.getCell(13);
				if (null == cell14) {
					cell14 = row.createCell(13);
				}
				cell14.setCellValue(listMap.get(i).get("ConditionValidityEndDate"));
				
				rowIndex++;
				index++;
			}
			String fileName = "SAP经销商供货价格接口同步.xlsx";

			FileOutputStream fileOutputStream = new FileOutputStream("E:/U/excel/" + fileName);// 指定路径与名字和格式
			wb.write(fileOutputStream);// 将数据写出去
			fileOutputStream.close();// 关闭输出流
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
//	
//	public void createPrice(ArrayList<Map<String,String>> listMap){
//		try {
//			for (int i = 0; i < listMap.size(); i++) {
//				Map<String, Object> rawData = new HashMap<String, Object>();
//				Map<String, Object> m1 = new HashMap<String, Object>();
//				m1.put("value", listMap.get(i).get("CustomerPriceGroup"));
//				rawData.put("_widget_1585745302686", m1);// 客户价格组
//				Map<String, Object> m2 = new HashMap<String, Object>();
//				m2.put("value", listMap.get(i).get("Customer"));
//				rawData.put("_widget_1585745302701", m2);// 客户编码
//				Map<String, Object> m3 = new HashMap<String, Object>();
//				m3.put("value", listMap.get(i).get("Material"));
//				rawData.put("_widget_1585745302716", m3);// 物料编码
//				Map<String, Object> m4 = new HashMap<String, Object>();
//				m4.put("value", listMap.get(i).get("price"));
//				rawData.put("_widget_1585745302731", m4);// 单价
//				Map<String, Object> m5 = new HashMap<String, Object>();
//				m5.put("value", listMap.get(i).get("ConditionQuantityUnit"));
//				rawData.put("_widget_1585901362201", m5);// 单位
//				Map<String, Object> m6 = new HashMap<String, Object>();
//				m6.put("value", listMap.get(i).get("ConditionRateValueUnit"));
//				rawData.put("_widget_1585901362080", m6);// 币种
//				Map<String, Object> m7 = new HashMap<String, Object>();
//				m7.put("value",listMap.get(i).get("ConditionType"));
//				rawData.put("_widget_1586482329032", m7);//条件类型
//				if("PPR0".equals(listMap.get(i).get("ConditionType"))){ //PPR0标识
//					if(!"".equals(listMap.get(i).get("Customer"))){
//						Map<String, Object> m8 = new HashMap<String, Object>();
//						m8.put("value",listMap.get(i).get("SalesOrganization")+listMap.get(i).get("DistributionChannel")+listMap.get(i).get("Customer")+listMap.get(i).get("Material"));
//						rawData.put("_widget_1586499946999", m8);//
//					}else if(!"".equals(listMap.get(i).get("CustomerPriceGroup"))){
//						Map<String, Object> m8 = new HashMap<String, Object>();
//						m8.put("value",listMap.get(i).get("SalesOrganization")+listMap.get(i).get("DistributionChannel")+listMap.get(i).get("CustomerPriceGroup")+listMap.get(i).get("Material"));
//						rawData.put("_widget_1586499946999", m8);//
//					}else if("".equals(listMap.get(i).get("Customer")) && "".equals(listMap.get(i).get("CustomerPriceGroup"))){
//						Map<String, Object> m8 = new HashMap<String, Object>();
//						m8.put("value",listMap.get(i).get("SalesOrganization")+listMap.get(i).get("DistributionChannel")+listMap.get(i).get("Material"));
//						rawData.put("_widget_1586499946999", m8);//
//					}
//				}else if("YK07".equals(listMap.get(i).get("ConditionType"))){ //YK07标识
//					if(!"".equals(listMap.get(i).get("Customer")) && !"".equals(listMap.get(i).get("Material"))){
//						Map<String, Object> m8 = new HashMap<String, Object>();
//						m8.put("value",listMap.get(i).get("SalesOrganization")+listMap.get(i).get("DistributionChannel")+listMap.get(i).get("Customer")+listMap.get(i).get("Material"));
//						rawData.put("_widget_1586499946984", m8);//
//					}else if(!"".equals(listMap.get(i).get("CustomerPriceGroup")) && !"".equals(listMap.get(i).get("Material"))){
//						Map<String, Object> m8 = new HashMap<String, Object>();
//						m8.put("value",listMap.get(i).get("SalesOrganization")+listMap.get(i).get("DistributionChannel")+listMap.get(i).get("CustomerPriceGroup")+listMap.get(i).get("Material"));
//						rawData.put("_widget_1586499946984", m8);//
//					}
//				}
//				Map<String, Object> m9 = new HashMap<String, Object>();
//				m9.put("value",listMap.get(i).get("SalesOrganization"));
//				rawData.put("_widget_1586499946729", m9);//销售组织
//				Map<String, Object> m10 = new HashMap<String, Object>();
//				m10.put("value",listMap.get(i).get("DistributionChannel"));
//				rawData.put("_widget_1586499946714", m10);//分销渠道
//				
//				Map<String, Object> m11 = new HashMap<String, Object>();
//				m11.put("value",listMap.get(i).get("DistributionChannel"));
//				rawData.put("_widget_1591845499086", m11);//唯一码
//				
//				Map<String, Object> map=api.createForData(rawData);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	
	public JSONArray getJSONArray(String url){
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();   
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse response = null;
		JSONArray  array=null;
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
				jsonObject1 = jsonObject1.getJSONObject("d");
				array = jsonObject1.getJSONArray("results");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return array;
	}
	
	public void close(ResultSet rs,PreparedStatement pstmt,Connection conn){
		if(rs!=null){
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(pstmt!=null){
			try {
				pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(conn!=null){
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis(); 
		new SapPriceSendJdyTest().getPrice();
		long endTime = System.currentTimeMillis();
		System.out.println("程序运行时间：" + (endTime - startTime) + "ms");
	}
}

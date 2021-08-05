package com.jxszj.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
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
import com.jxszj.utils.DateUtils;
import com.jxszj.utils.ExceptionRobot;
import com.jxszj.utils.JDYAPIUtils;

/**
 * 
 * <pre>
 * <b>.</b>
 * <b>Description:</b> 
 *    将SAP中的物料主数据同步至简道云物料信息中
 * <b>Author:</b> yanwei
 * <b>Date:</b> 2020年3月30日下午1:49:59
 * </pre>
 */
public class SapHsWlSendJdyKh {

	
	// 简道云
//	String appId = "5cc110c3b3c41744aaa12b2e";
//	String apiKey = "uWEAujuxvzv5fMVifOvMRzlIJcPLgYkv";
//	JDYAPIUtils productApi=null;
//	Map<String,Object> map1=null;
//	Map<String,Object> map2=null;
	
	public void getWl() {
		
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();   
		HttpGet httpGet = new HttpGet("https://my300201-api.saps4hanacloud.cn/sap/opu/odata/sap/YY1_MATERIELDATAPRICEAPI_CDS/YY1_MaterielDataPriceAPI");
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
				
				String excel = "E:/work/jxszj_zhmd/src/main/webapp/WEB-INF/excel/华生SAP物料信息接口同步.xlsx";
				File fi = new File(excel);
				XSSFWorkbook wb =new XSSFWorkbook(new FileInputStream(fi));
				XSSFSheet sheet = wb.getSheetAt(0);
				int rowIndex = 1;
				int index = 1;
				System.out.println("生成Excel");
				for (int i = 0; i < array.size(); i++) {
					XSSFRow row = sheet.getRow(rowIndex);
					if (null == row) {
						row = sheet.createRow(rowIndex);
					}
					//唯一键
					XSSFCell cell1 = row.getCell(0);
					if (null == cell1) {
						cell1 = row.createCell(0);
					}
					cell1.setCellValue(array.getJSONObject(i).getString("ValuationArea")+array.getJSONObject(i).getString("Product"));
					//产品类型
					XSSFCell cell2 = row.getCell(1);
					if (null == cell2) {
						cell2 = row.createCell(1);
					}
					cell2.setCellValue(array.getJSONObject(i).getString("ProductType"));
					//物料组编码
					XSSFCell cell3 = row.getCell(2);
					if (null == cell3) {
						cell3 = row.createCell(2);
					}
					cell3.setCellValue(array.getJSONObject(i).getString("ProductGroup"));
					//物料组名称
					XSSFCell cell4 = row.getCell(3);
					if (null == cell4) {
						cell4 = row.createCell(3);
					}
					cell4.setCellValue(array.getJSONObject(i).getString("MaterialGroupName"));
					
					//采购组
					XSSFCell cell5 = row.getCell(4);
					if (null == cell5) {
						cell5 = row.createCell(4);
					}
					cell5.setCellValue(array.getJSONObject(i).getString("PurchasingGroup"));
					
					//产品编码
					XSSFCell cell6 = row.getCell(5);
					if (null == cell6) {
						cell6 = row.createCell(5);
					}
					cell6.setCellValue(array.getJSONObject(i).getString("Product"));
					//产品名称
					XSSFCell cell7 = row.getCell(6);
					if (null == cell7) {
						cell7 = row.createCell(6);
					}
					cell7.setCellValue(array.getJSONObject(i).getString("ProductDescription"));
					//规格尺寸
					XSSFCell cell8 = row.getCell(7);
					if (null == cell8) {
						cell8 = row.createCell(7);
					}
					cell8.setCellValue(array.getJSONObject(i).getString("SizeOrDimensionText"));
					//体积
					XSSFCell cell9 = row.getCell(8);
					if (null == cell9) {
						cell9 = row.createCell(8);
					}
					cell9.setCellValue(array.getJSONObject(i).getString("MaterialVolume"));
					//体积单位
					XSSFCell cell10 = row.getCell(9);
					if (null == cell10) {
						cell10 = row.createCell(9);
					}
					cell10.setCellValue(array.getJSONObject(i).getString("VolumeUnit"));
					//基本单位
					XSSFCell cell11 = row.getCell(10);
					if (null == cell11) {
						cell11 = row.createCell(10);
					}
					cell11.setCellValue(array.getJSONObject(i).getString("BaseUnit"));
					//基本单位文本
					XSSFCell cell12 = row.getCell(11);
					if (null == cell12) {
						cell12 = row.createCell(11);
					}
					cell12.setCellValue(array.getJSONObject(i).getString("UnitOfMeasureLongName"));
					//创建日期
					XSSFCell cell13 = row.getCell(12);
					if (null == cell13) {
						cell13 = row.createCell(12);
					}
					cell13.setCellValue(DateUtils.getLongToString(array.getJSONObject(i).getString("CreationDate"),DateUtils.FORMAT_1_STRING));
					//最近修改日期
					XSSFCell cell14 = row.getCell(13);
					if (null == cell14) {
						cell14 = row.createCell(13);
					}
					cell14.setCellValue(DateUtils.getLongToString(array.getJSONObject(i).getString("LastChangeDate"),DateUtils.FORMAT_1_STRING));
					//使用状态
					XSSFCell cell15 = row.getCell(14);
					if (null == cell15) {
						cell15 = row.createCell(14);
					}
					cell15.setCellValue(array.getJSONObject(i).getString("CrossPlantStatus"));
					//评估范围
					XSSFCell cell16 = row.getCell(15);
					if (null == cell16) {
						cell16 = row.createCell(15);
					}
					cell16.setCellValue(array.getJSONObject(i).getString("ValuationArea"));
					//评估类型
					XSSFCell cell17 = row.getCell(16);
					if (null == cell17) {
						cell17 = row.createCell(16);
					}
					cell17.setCellValue(array.getJSONObject(i).getString("ValuationType"));
					//标准价格
					XSSFCell cell18 = row.getCell(17);
					if (null == cell18) {
						cell18 = row.createCell(17);
					}
					cell18.setCellValue(array.getJSONObject(i).getString("StandardPrice"));
					//移动价格
					XSSFCell cell19 = row.getCell(18);
					if (null == cell19) {
						cell19 = row.createCell(18);
					}
					cell19.setCellValue(array.getJSONObject(i).getString("MovingAveragePrice"));
					rowIndex++;
					index++;
				}
				String fileName = "华生SAP物料信息接口同步.xlsx";

				FileOutputStream fileOutputStream = new FileOutputStream("E:/excel/" + fileName);// 指定路径与名字和格式
				wb.write(fileOutputStream);// 将数据写出去
				fileOutputStream.close();// 关闭输出流
			}else{
				System.out.println("SAP同步物料主数据,接口状态码"+response.getStatusLine().getStatusCode());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new SapHsWlSendJdyKh().getWl();
	}
	
}

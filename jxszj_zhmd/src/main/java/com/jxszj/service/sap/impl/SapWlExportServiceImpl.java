package com.jxszj.service.sap.impl;

import java.io.File;
import java.io.FileInputStream;
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
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jxszj.service.sap.ISapWlExportService;
import com.jxszj.utils.DateUtils;
import com.jxszj.utils.JDYAPIUtils;

@Service
public class SapWlExportServiceImpl implements ISapWlExportService {

	// 简道云
	String appId = "5cc110c3b3c41744aaa12b2e";
	String apiKey = "uWEAujuxvzv5fMVifOvMRzlIJcPLgYkv";
	JDYAPIUtils productApi=null;
	
	@Override
	public XSSFWorkbook getXSSFWorkbook(String excelUrl) {
		//物料
		String productEntryId = "5e7f03d274924c000681ad02";
        productApi = new JDYAPIUtils(appId, productEntryId, apiKey);
        
		//SAP物料组返利参数
		String entryId = "5ed9b22de59be600062abc84";
		JDYAPIUtils api = new JDYAPIUtils(appId, entryId, apiKey);
		List<Map<String, Object>> lists= api.getAllFormData(null, null);
		Map<String,Object> map=new HashMap<String,Object>();
		for (int i = 0; i < lists.size(); i++) {
			map.put(lists.get(i).get("_widget_1591240492654").toString(), lists.get(i).get("_widget_1591240492821"));
		}
        
        
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();   
		HttpGet httpGet = new HttpGet("https://my300201-api.saps4hanacloud.cn/sap/opu/odata/sap/YY1_MATERIELDATAENTITYAPI_CDS/YY1_MaterielDataEntityAPI");
		CloseableHttpResponse response = null;
		XSSFWorkbook wb = null;
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
				
				File fi = new File(excelUrl);
				wb =new XSSFWorkbook(new FileInputStream(fi));
				XSSFSheet sheet = wb.getSheetAt(0);
				int rowIndex = 1;
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
					cell1.setCellValue(array.getJSONObject(i).getString("Product")+array.getJSONObject(i).getString("ProductSalesOrg")+array.getJSONObject(i).getString("ProductDistributionChnl"));
					//销售组织
					XSSFCell cell2 = row.getCell(1);
					if (null == cell2) {
						cell2 = row.createCell(1);
					}
					cell2.setCellValue(array.getJSONObject(i).getString("ProductSalesOrg"));
					//分销渠道
					XSSFCell cell3 = row.getCell(2);
					if (null == cell3) {
						cell3 = row.createCell(2);
					}
					cell3.setCellValue(array.getJSONObject(i).getString("ProductDistributionChnl"));
					//产品类型
					XSSFCell cell4 = row.getCell(3);
					if (null == cell4) {
						cell4 = row.createCell(3);
					}
					cell4.setCellValue(array.getJSONObject(i).getString("ProductType"));
					//品牌SAP码
					XSSFCell cell5 = row.getCell(4);
					if (null == cell5) {
						cell5 = row.createCell(4);
					}
					cell5.setCellValue(array.getJSONObject(i).getString("FirstSalesSpecProductGroup"));
					//品牌名称
					XSSFCell cell6 = row.getCell(5);
					if (null == cell6) {
						cell6 = row.createCell(5);
					}
					cell6.setCellValue(array.getJSONObject(i).getString("AdditionalMaterialGroup1Name"));
					//物料组编码
					XSSFCell cell7 = row.getCell(6);
					if (null == cell7) {
						cell7 = row.createCell(6);
					}
					cell7.setCellValue(array.getJSONObject(i).getString("ProductGroup"));
					//物料组名称
					XSSFCell cell8 = row.getCell(7);
					if (null == cell8) {
						cell8 = row.createCell(7);
					}
					cell8.setCellValue(array.getJSONObject(i).getString("MaterialGroupName"));
					//返利标识
					XSSFCell cell9 = row.getCell(8);
					if (null == cell9) {
						cell9 = row.createCell(8);
					}
					cell9.setCellValue(getString(map.get(array.getJSONObject(i).getString("ProductGroup"))));
					//产品编码
					XSSFCell cell10 = row.getCell(9);
					if (null == cell10) {
						cell10 = row.createCell(9);
					}
					cell10.setCellValue(array.getJSONObject(i).getString("Product"));
					//产品名称
					XSSFCell cell11 = row.getCell(10);
					if (null == cell11) {
						cell11 = row.createCell(10);
					}
					cell11.setCellValue(array.getJSONObject(i).getString("ProductDescription"));
					//规格尺寸
					XSSFCell cell12 = row.getCell(11);
					if (null == cell12) {
						cell12 = row.createCell(11);
					}
					cell12.setCellValue(array.getJSONObject(i).getString("SizeOrDimensionText"));
					//基本单位
					XSSFCell cell13 = row.getCell(12);
					if (null == cell13) {
						cell13 = row.createCell(12);
					}
					cell13.setCellValue(array.getJSONObject(i).getString("BaseUnit_01"));
					//基本单位文本
					XSSFCell cell14 = row.getCell(13);
					if (null == cell14) {
						cell14 = row.createCell(13);
					}
					cell14.setCellValue(array.getJSONObject(i).getString("UnitOfMeasureLongName"));
					//销售单位
					XSSFCell cell15 = row.getCell(14);
					if (null == cell15) {
						cell15 = row.createCell(14);
					}
					cell15.setCellValue(array.getJSONObject(i).getString("SalesMeasureUnit"));
					//销售单位文本
					XSSFCell cell16 = row.getCell(15);
					if (null == cell16) {
						cell16 = row.createCell(15);
					}
					cell16.setCellValue(array.getJSONObject(i).getString("UnitOfMeasureLongName_01"));
					//创建日期
					XSSFCell cell17 = row.getCell(16);
					if (null == cell17) {
						cell17 = row.createCell(16);
					}
					cell17.setCellValue(DateUtils.getLongToString(array.getJSONObject(i).getString("CreationDate"),DateUtils.FORMAT_1_STRING));
					//最近修改日期
					XSSFCell cell18 = row.getCell(17);
					if (null == cell18) {
						cell18 = row.createCell(17);
					}
					cell18.setCellValue(DateUtils.getLongToString(array.getJSONObject(i).getString("LastChangeDate"),DateUtils.FORMAT_1_STRING));
					//使用状态
					XSSFCell cell19 = row.getCell(18);
					if (null == cell19) {
						cell19 = row.createCell(18);
					}
					cell19.setCellValue(array.getJSONObject(i).getString("CrossPlantStatus"));
					
					rowIndex++;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wb;
	}

	public String getString(Object obj){
		String str="";
		if(obj==null){
			return str;
		}
		return obj.toString();
	}
}

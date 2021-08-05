package com.jxszj.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

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

/**
 * 
 * <pre>
 * <b>.</b>
 * <b>Description:</b> 
 *    将SAP订单明细金额(出货分析)同步到简道云进销商之家O1出货明细
 * <b>Author:</b> yanwei
 * <b>Date:</b> 2020年3月30日下午1:49:59
 * </pre>
 */
public class SapOrderList {

	
	public void getWl() {
        
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
				
				String excel = "E:/Workspaces/jxszj_zhmd/src/main/webapp/WEB-INF/excel/O1出货明细_20200714174630.xlsx";
				File fi = new File(excel);
				XSSFWorkbook wb =new XSSFWorkbook(new FileInputStream(fi));
				XSSFSheet sheet = wb.getSheetAt(0);
				int rowIndex = 1;
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
					cell1.setCellValue(array.getJSONObject(i).getString("SalesDocument")+array.getJSONObject(i).getString("SalesDocumentItem")+array.getJSONObject(i).getString("ConditionType")+array.getJSONObject(i).getString("DeliveryDocument")+array.getJSONObject(i).getString("DeliveryDocumentItem"));
					//销售组织
					XSSFCell cell2 = row.getCell(1);
					if (null == cell2) {
						cell2 = row.createCell(1);
					}
					cell2.setCellValue(array.getJSONObject(i).getString("SalesOrganization"));
					//分销渠道
					XSSFCell cell3 = row.getCell(2);
					if (null == cell3) {
						cell3 = row.createCell(2);
					}
					cell3.setCellValue(array.getJSONObject(i).getString("DistributionChannel"));
					//产品组
					XSSFCell cell4 = row.getCell(3);
					if (null == cell4) {
						cell4 = row.createCell(3);
					}
					cell4.setCellValue(array.getJSONObject(i).getString("OrganizationDivision"));
					//销售凭证类型
					XSSFCell cell5 = row.getCell(4);
					if (null == cell5) {
						cell5 = row.createCell(4);
					}
					cell5.setCellValue(array.getJSONObject(i).getString("SalesDocumentType"));
					//销售凭证类型描述
					XSSFCell cell6 = row.getCell(5);
					if (null == cell6) {
						cell6 = row.createCell(5);
					}
					cell6.setCellValue(array.getJSONObject(i).getString("SalesDocumentTypeName"));
					//品牌SAP码
					XSSFCell cell7 = row.getCell(6);
					if (null == cell7) {
						cell7 = row.createCell(6);
					}
					cell7.setCellValue(array.getJSONObject(i).getString("FirstSalesSpecProductGroup"));
					//销售地区编码
					XSSFCell cell8 = row.getCell(7);
					if (null == cell8) {
						cell8 = row.createCell(7);
					}
					cell8.setCellValue(array.getJSONObject(i).getString("SalesDistrict"));
					//销售地区名称
					XSSFCell cell9 = row.getCell(8);
					if (null == cell9) {
						cell9 = row.createCell(8);
					}
					cell9.setCellValue(array.getJSONObject(i).getString("SalesDistrictName"));
					//品牌名称
					XSSFCell cell10 = row.getCell(9);
					if (null == cell10) {
						cell10 = row.createCell(9);
					}
					cell10.setCellValue(array.getJSONObject(i).getString("AdditionalMaterialGroup1Name"));
					//销售凭证
					XSSFCell cell11 = row.getCell(10);
					if (null == cell11) {
						cell11 = row.createCell(10);
					}
					cell11.setCellValue(array.getJSONObject(i).getString("SalesDocument"));
					//销售凭证项目
					XSSFCell cell12 = row.getCell(11);
					if (null == cell12) {
						cell12 = row.createCell(11);
					}
					cell12.setCellValue(array.getJSONObject(i).getString("SalesDocumentItem"));
					//客户编码
					XSSFCell cell13 = row.getCell(12);
					if (null == cell13) {
						cell13 = row.createCell(12);
					}
					cell13.setCellValue(array.getJSONObject(i).getString("SoldToParty"));
					//客户名称
					XSSFCell cell14 = row.getCell(13);
					if (null == cell14) {
						cell14 = row.createCell(13);
					}
					cell14.setCellValue(array.getJSONObject(i).getString("CustomerName"));
					//交货凭证
					XSSFCell cell15 = row.getCell(14);
					if (null == cell15) {
						cell15 = row.createCell(14);
					}
					cell15.setCellValue(array.getJSONObject(i).getString("DeliveryDocument"));
					//交货凭证项目
					XSSFCell cell16 = row.getCell(15);
					if (null == cell16) {
						cell16 = row.createCell(15);
					}
					cell16.setCellValue(array.getJSONObject(i).getString("DeliveryDocumentItem"));
					//订货单号
					XSSFCell cell17 = row.getCell(16);
					if (null == cell17) {
						cell17 = row.createCell(16);
					}
					cell17.setCellValue(array.getJSONObject(i).getString("PurchaseOrderByCustomer"));
					//订货日期
					XSSFCell cell18 = row.getCell(17);
					if (null == cell18) {
						cell18 = row.createCell(17);
					}
					cell18.setCellValue(array.getJSONObject(i).get("CreationDate")!=null?DateUtils.getLongToString(array.getJSONObject(i).getString("CreationDate"),DateUtils.FORMAT_1_STRING):"");
					//订货日期-年
					XSSFCell cell19 = row.getCell(18);
					if (null == cell19) {
						cell19 = row.createCell(18);
					}
					cell19.setCellValue(array.getJSONObject(i).get("CreationDate")!=null?DateUtils.getLongToString(array.getJSONObject(i).getString("CreationDate").toString(),DateUtils.FORMAT_STRING_YEAR):"");
					
					//订货日期-年月
					XSSFCell cell20 = row.getCell(19);
					if (null == cell20) {
						cell20 = row.createCell(19);
					}
					cell20.setCellValue(array.getJSONObject(i).get("CreationDate")!=null?DateUtils.getLongToString(array.getJSONObject(i).getString("CreationDate").toString(),DateUtils.FORMAT_STRING_MINUTE):"");
					
					//订货日期-年月日
					XSSFCell cell21 = row.getCell(20);
					if (null == cell21) {
						cell21 = row.createCell(20);
					}
					cell21.setCellValue(array.getJSONObject(i).get("CreationDate")!=null?DateUtils.getLongToString(array.getJSONObject(i).getString("CreationDate").toString(),DateUtils.FORMAT_2_STRING):"");
					
					//产品类型
					XSSFCell cell22 = row.getCell(21);
					if (null == cell22) {
						cell22 = row.createCell(21);
					}
					cell22.setCellValue(array.getJSONObject(i).getString("ProductType"));
					
					//物料组编码
					XSSFCell cell23 = row.getCell(22);
					if (null == cell23) {
						cell23 = row.createCell(22);
					}
					cell23.setCellValue(array.getJSONObject(i).getString("ProductGroup"));
					
					//物料组名称
					XSSFCell cell24 = row.getCell(23);
					if (null == cell24) {
						cell24 = row.createCell(23);
					}
					cell24.setCellValue(array.getJSONObject(i).getString("MaterialGroupName"));
					
					//产品编码
					XSSFCell cell25 = row.getCell(24);
					if (null == cell25) {
						cell25 = row.createCell(24);
					}
					cell25.setCellValue(array.getJSONObject(i).getString("Material"));
					
					//产品名称
					XSSFCell cell26 = row.getCell(25);
					if (null == cell26) {
						cell26 = row.createCell(25);
					}
					cell26.setCellValue(array.getJSONObject(i).getString("SalesDocumentItemText"));
					
					//订货数量
					XSSFCell cell27 = row.getCell(26);
					if (null == cell27) {
						cell27 = row.createCell(26);
					}
					cell27.setCellValue(array.getJSONObject(i).getString("OrderQuantity"));
					
					//基本计量单位
					XSSFCell cell28 = row.getCell(27);
					if (null == cell28) {
						cell28 = row.createCell(27);
					}
					cell28.setCellValue(array.getJSONObject(i).getString("BaseUnit"));
					
					//销售单位
					XSSFCell cell29 = row.getCell(28);
					if (null == cell29) {
						cell29 = row.createCell(28);
					}
					cell29.setCellValue(array.getJSONObject(i).getString("OrderQuantityUnit"));
					
					//交货状态
					XSSFCell cell30 = row.getCell(29);
					if (null == cell30) {
						cell30 = row.createCell(29);
					}
					cell30.setCellValue(array.getJSONObject(i).getString("DeliveryStatus"));
					
					//凭证货币
					XSSFCell cell31 = row.getCell(30);
					if (null == cell31) {
						cell31 = row.createCell(30);
					}
					cell31.setCellValue(array.getJSONObject(i).getString("TransactionCurrency"));
					
					//拒绝状态
					XSSFCell cell32 = row.getCell(31);
					if (null == cell32) {
						cell32 = row.createCell(31);
					}
					cell32.setCellValue(array.getJSONObject(i).getString("SDDocumentRejectionStatus"));
					
					//出货日期
					XSSFCell cell33 = row.getCell(32);
					if (null == cell33) {
						cell33 = row.createCell(32);
					}
					cell33.setCellValue(array.getJSONObject(i).get("ActualGoodsMovementDate")!=null?DateUtils.getLongToString(array.getJSONObject(i).getString("ActualGoodsMovementDate").toString(),DateUtils.FORMAT_1_STRING):"");
					
					//出货日期-年
					XSSFCell cell34 = row.getCell(33);
					if (null == cell34) {
						cell34 = row.createCell(33);
					}
					cell34.setCellValue(array.getJSONObject(i).get("ActualGoodsMovementDate")!=null?DateUtils.getLongToString(array.getJSONObject(i).get("ActualGoodsMovementDate").toString(),DateUtils.FORMAT_STRING_YEAR):"");
					
					//出货日期-年月
					XSSFCell cell35 = row.getCell(34);
					if (null == cell35) {
						cell35 = row.createCell(34);
					}
					cell35.setCellValue(array.getJSONObject(i).get("ActualGoodsMovementDate")!=null?DateUtils.getLongToString(array.getJSONObject(i).get("ActualGoodsMovementDate").toString(),DateUtils.FORMAT_STRING_MINUTE):"");
					
					//出货日期-年月日
					XSSFCell cell36 = row.getCell(35);
					if (null == cell36) {
						cell36 = row.createCell(35);
					}
					cell36.setCellValue(array.getJSONObject(i).get("ActualGoodsMovementDate")!=null?DateUtils.getLongToString(array.getJSONObject(i).get("ActualGoodsMovementDate").toString(),DateUtils.FORMAT_2_STRING):"");
					
					//出货数量
					XSSFCell cell37 = row.getCell(36);
					if (null == cell37) {
						cell37 = row.createCell(36);
					}
					cell37.setCellValue(array.getJSONObject(i).getString("ActualDeliveredQtyInBaseUnit"));
					
					//条件类型
					XSSFCell cell38 = row.getCell(37);
					if (null == cell38) {
						cell38 = row.createCell(37);
					}
					cell38.setCellValue(array.getJSONObject(i).getString("ConditionType"));
					
					//基准值
					XSSFCell cell39 = row.getCell(38);
					if (null == cell39) {
						cell39 = row.createCell(38);
					}
					cell39.setCellValue(array.getJSONObject(i).getString("ConditionBaseValue"));
					
					//应用程序
					XSSFCell cell40 = row.getCell(39);
					if (null == cell40) {
						cell40 = row.createCell(39);
					}
					cell40.setCellValue(array.getJSONObject(i).getString("ConditionApplication"));
					
					//金额
					XSSFCell cell41 = row.getCell(40);
					if (null == cell41) {
						cell41 = row.createCell(40);
					}
					cell41.setCellValue(array.getJSONObject(i).getString("ConditionRateValue"));
					
					rowIndex++;
				}
				String fileName = "O1出货明细.xlsx";

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
		new SapOrderList().getWl();
	}
	
}

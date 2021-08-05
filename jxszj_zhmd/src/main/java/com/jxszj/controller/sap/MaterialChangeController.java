package com.jxszj.controller.sap;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.jxszj.utils.ExcelUtil;
import com.jxszj.utils.MessageResult;


@Controller
@RequestMapping("/materialChange")
public class MaterialChangeController {
	
	
	@RequestMapping("/uploadWlmsExcel")
	@ResponseBody
	public MessageResult uploadWlmsExcel(@RequestParam("wlExcelFile") MultipartFile wlExcelFile,HttpServletRequest request, HttpServletResponse response){
		try {
			if (wlExcelFile==null) {
				return MessageResult.build(null,"上传Excel失败,请重试！");
			}
			InputStream in = null;
			List<List<Object>> listob = null;
			List<Integer> wlbms=new ArrayList<>();
			
			in = wlExcelFile.getInputStream();
			listob = new ExcelUtil().getBankListByExcel(in, wlExcelFile.getOriginalFilename());
			for (int i = 1; i < listob.size(); i++) {
				//将Excel中的数据提出来
				String wlbm=updateMaterial(listob.get(i).get(0).toString(),listob.get(i).get(1).toString(),listob.get(i).get(2).toString());
				if(!wlbm.equals("")){
					wlbms.add(i+1);
				}
			}
			if(wlbms.size()>0){
				return MessageResult.build(500,"批量修改完成，第"+wlbms+"行修改失败！");
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return MessageResult.build(200,"批量修改完成！");
	}
	
	
	@RequestMapping("/uploadWltjExcel")
	@ResponseBody
	public MessageResult uploadWltjExcel(@RequestParam("wlExcelFile") MultipartFile wlExcelFile,HttpServletRequest request, HttpServletResponse response){
		try {
			if (wlExcelFile==null) {
				return MessageResult.build(null,"上传Excel失败,请重试！");
			}
			InputStream in = null;
			List<List<Object>> listob = null;
			List<Integer> wlbms=new ArrayList<>();
			
			in = wlExcelFile.getInputStream();
			listob = new ExcelUtil().getBankListByExcel(in, wlExcelFile.getOriginalFilename());
			for (int i = 0; i < listob.size(); i++) {
				//将Excel中的数据提出来
				String wlbm=updateMaterialVolume(listob.get(i).get(0).toString(),listob.get(i).get(1).toString(),listob.get(i).get(2).toString());
				if(!wlbm.equals("")){
					wlbms.add(i+1);
				}
			}
			if(wlbms.size()>0){
				return MessageResult.build(500,"批量修改完成，第"+wlbms+"行修改失败！");
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return MessageResult.build(200,"批量修改完成！");
	}
	
	
	public String updateMaterial(String product,String language,String material){
		String wlbm="";
		String url="https://my300201-api.saps4hanacloud.cn/sap/opu/odata/sap/API_PRODUCT_SRV/A_ProductDescription(Product='"+product+"',Language='"+language+"')";
		try {
			Map<String,String> map=getToken(url);
			if(map.size()==0){
				return wlbm;
			}
			CloseableHttpClient httpClient = HttpClientBuilder.create().build();
			HttpPatch httpPatch=new HttpPatch(url);
			Map<String,Object> mapObj=new HashMap<String,Object>();
			
			mapObj.put("ProductDescription", material);//物料名称
			String strJson=JSON.toJSONString(mapObj);
			CloseableHttpResponse response = null;
			StringEntity entity=new StringEntity(strJson, Charset.forName("UTF-8"));
			httpPatch.setEntity(entity);
			httpPatch.setHeader("Content-Type","application/json; charset=utf-8");
			httpPatch.setHeader("Accept","application/json");
			httpPatch.setHeader("Authorization", "Basic SkRZVVNFUjpIZmpUUD5UZ2ZxUU10SEVnaFpiVXhtZ2VOdnJmZlh3OUNvQm5BVGps");
			httpPatch.setHeader("x-csrf-token",map.get("token").split(";;")[1]);
			httpPatch.setHeader("cookie",map.get("token").split(";;")[0]);
			response = httpClient.execute(httpPatch);
			
//			HttpEntity responseEntity = response.getEntity();
//			String strEntity=EntityUtils.toString(responseEntity);
//			System.out.println(strEntity);
			if(response.getStatusLine().getStatusCode()!=204){
				wlbm=product;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wlbm;
	}
	
	
	public String updateMaterialVolume(String product,String materialVolume,String volumeUnit){
		String wlbm="";
		String url="https://my300201-api.saps4hanacloud.cn/sap/opu/odata/sap/API_PRODUCT_SRV/A_Product(Product='"+product+"')";
		try {
			Map<String,String> map=getToken(url);
			if(map.size()==0){
				return wlbm;
			}
			CloseableHttpClient httpClient = HttpClientBuilder.create().build();
			HttpPatch httpPatch=new HttpPatch(url);
			Map<String,Object> mapObj=new HashMap<String,Object>();
			mapObj.put("MaterialVolume", materialVolume);//物料体积
			mapObj.put("VolumeUnit", volumeUnit);//物料体积单位
			String strJson=JSON.toJSONString(mapObj);
			CloseableHttpResponse response = null;
			StringEntity entity=new StringEntity(strJson, Charset.forName("UTF-8"));
			httpPatch.setEntity(entity);
			httpPatch.setHeader("Content-Type","application/json; charset=utf-8");
			httpPatch.setHeader("Accept","application/json");
			httpPatch.setHeader("Authorization", "Basic SkRZVVNFUjpIZmpUUD5UZ2ZxUU10SEVnaFpiVXhtZ2VOdnJmZlh3OUNvQm5BVGps");
			httpPatch.setHeader("x-csrf-token",map.get("token").split(";;")[1]);
			httpPatch.setHeader("cookie",map.get("token").split(";;")[0]);
			response = httpClient.execute(httpPatch);
			
			if(response.getStatusLine().getStatusCode()!=204){
				wlbm=product;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wlbm;
	}
	
	public Map<String,String> getToken(String url){
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse response = null;
		String token="";
		Map<String,String> map=new HashMap<String,String>();
		try {
			httpGet.setHeader("Content-Type","application/json");
			httpGet.setHeader("Accept","application/json");
			httpGet.setHeader("Authorization", "Basic SkRZVVNFUjpIZmpUUD5UZ2ZxUU10SEVnaFpiVXhtZ2VOdnJmZlh3OUNvQm5BVGps");
			httpGet.setHeader("x-csrf-token", "fetch");
			// 由客户端执行(发送)Get请求
			response = httpClient.execute(httpGet);
			if(response.getStatusLine().getStatusCode()==200){
				Header[] headers=response.getAllHeaders();
				for (Header h : headers) {
					if (h.getName().equals("set-cookie")) {
					   token = token+h.getValue()+";";
					}
					if (h.getName().equals("x-csrf-token")) {
					   token = token+";"+h.getValue();
					}
				}
				map.put("token", token);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
}

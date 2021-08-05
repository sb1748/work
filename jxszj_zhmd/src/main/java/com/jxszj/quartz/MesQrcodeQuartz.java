package com.jxszj.quartz;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jxszj.pojo.sap.SapScpgTb;
import com.jxszj.service.sap.ISapScpgService;
import com.jxszj.utils.DateUtils;
import com.jxszj.utils.JDYAPIUtils;

public class MesQrcodeQuartz {
	
	@Autowired
	private ISapScpgService sapScpgService;
	
	
	// 简道云华生集团
	String appId = "5c048a8379332d0978a68b8e";
	String entryId = "5f3669a5b97b0a00069aa49d";
	String apiKey = "SGmAqjtntz3q5M0rZqm2b4nGlRpSlah6";
	
	String wlz_entryId = "5f5b4063dfea1f0006dad122";
	String ctg_entryId = "5fa245999fbdc30006c675ec";//床头柜产品配置名单
	// OA华生集团 -- 产品名称打印对照表
	static String dydz_entryId = "60a6286dccaf180007b9ad43";
	
	public void execute(){
		Map<String,Long> jhzqs=new HashMap<String,Long>();
		JDYAPIUtils api = new JDYAPIUtils(appId, wlz_entryId, apiKey);
		JDYAPIUtils ctg_api = new JDYAPIUtils(appId, ctg_entryId, apiKey);
		JDYAPIUtils dydz_api = new JDYAPIUtils(appId, dydz_entryId, apiKey);
		List<Map<String, Object>> ctg_list=ctg_api.getAllFormData(null, null);
		Map<String,Long> wlzmap=new HashMap<String,Long>();
		
		//查询产品名称打印对照表中的数据
		List<Map<String, Object>> dydz_list=dydz_api.getAllFormData(null, null);
		Map<String,String> dydzmap=new HashMap<>();
		for (int i = 0; i < dydz_list.size(); i++) {
			dydzmap.put(dydz_list.get(i).get("_widget_1621502063280").toString(), dydz_list.get(i).get("_widget_1621502063262").toString());
		}
		try {
			//找出物料组中所有的交货周期
	        List<Map<String, Object>> lists= api.getAllFormData(null,null);
	        for (int i = 0; i < lists.size(); i++) {
				wlzmap.put(lists.get(i).get("_widget_1599815780035").toString(), Long.valueOf(lists.get(i).get("_widget_1599815780069").toString()));
			}
	        
	        //查询出交货周期表所有的生产订单，并去重
	        JSONArray array=getJSONArray("https://my300201-api.saps4hanacloud.cn/sap/opu/odata/sap/YY1_MANUFACORDERAPI_CDS/YY1_ManufacOrderAPI");
	        Set<String> sets=new HashSet<String>();//存放销售订单
	        for (int i = 0; i < array.size(); i++) {
	        	sets.add(array.getJSONObject(i).getString("SalesOrder"));
			}
	       
	        for (String SalesOrder:sets) {
	        	//循环查询每一个销售订单
	        	array=getJSONArray("https://my300201-api.saps4hanacloud.cn/sap/opu/odata/sap/YY1_SALESDOCUMENTTRACKAPI_CDS/YY1_SalesDocumentTrackAPI?$filter=SalesDocument%20eq%20'"+SalesOrder+"'");
	        	Set<String> sets1=new HashSet<String>();//获取销售订单中每个物料的物料组编码
	        	for (int i = 0; i < array.size(); i++) {
	        		sets1.add(array.getJSONObject(i).getString("MaterialGroup"));
				}
	        	//用物料组编码找出对应的交货周期
	        	List<Long> lists2=new ArrayList<Long>();
	        	for (String materialGroup : sets1) {
	        		if(wlzmap.get(materialGroup)!=null){
	        			lists2.add(wlzmap.get(materialGroup));
	        		}
				}
	        	if(lists2==null || lists2.size()==0){
	        		continue;
	        	}
	        	Collections.sort(lists2);
	        	jhzqs.put(SalesOrder, lists2.get(lists2.size()-1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		List<SapScpgTb> listSapScpgTb=new ArrayList<SapScpgTb>();
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();   
		HttpGet httpGet = new HttpGet("https://my300201-api.saps4hanacloud.cn/sap/opu/odata/sap/YY1_MANUFACORDERAPI_CDS/YY1_ManufacOrderAPI");
		CloseableHttpResponse response = null;
		api = new JDYAPIUtils(appId, entryId, apiKey);
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
				jsonObject = jsonObject.getJSONObject("d");
				JSONArray  array = jsonObject.getJSONArray("results");
				for (int i = 0; i < array.size(); i++) {
					int num=Double.valueOf(array.getJSONObject(i).getString("MfgOrderItemPlannedTotalQty")).intValue();
					int[] nums=new int[num];
					for (int j = 1; j <= nums.length; j++) {
						String str="";
						if(String.valueOf(j).length()==1){
							str="00"+j;
						}else if(String.valueOf(j).length()==2){
							str="0"+j;
						}else if(String.valueOf(j).length()==3){
							str=""+j;
						}
						
						String qrCode=array.getJSONObject(i).getString("SalesOrder")+array.getJSONObject(i).getString("SalesOrderItem")+array.getJSONObject(i).getString("ManufacturingOrder")+str;
						final List<Map<String, Object>> condList = new ArrayList<Map<String, Object>>();
						Map<String, Object> map=new HashMap<String, Object>();
						map.put("field", "_widget_1597219749750");//以二维码数据作为唯一值
						map.put("type", "text");
						map.put("method", "eq");
						map.put("value",qrCode);
				        condList.add(map);
				        Map<String, Object> filter = new HashMap<String, Object>(){
				            {
				                put("rel", "and");
				                put("cond", condList);
				            }
				        };
				        String barcode="";
				        Integer dyzs=1;
				        String tempname="";
				        String status="0";
				        List<SapScpgTb> sapScpgTbs=sapScpgService.getSapScpgTb(qrCode);
				        if(sapScpgTbs!=null && sapScpgTbs.size()!=0){
				        	barcode=sapScpgTbs.get(0).getBarcode();
				        	dyzs=sapScpgTbs.get(0).getDyzs();
				        	tempname=sapScpgTbs.get(0).getTempname();
				        	status=sapScpgTbs.get(0).getStatus();
				        }else{
				        	barcode=getStr();
				        }
				        List<Map<String, Object>> list= api.getAllFormData(null, filter);
				        listSapScpgTb.add(getSapScpgTb(array.getJSONObject(i),qrCode,str,dyzs,tempname,status,barcode));
				        if(list.size()==0){
				        	Map<String, Object> rawData=getData(array.getJSONObject(i),qrCode,str,wlzmap,barcode,jhzqs,ctg_list,dydzmap);
							api.createForData(rawData);
				        }else if(list.size()==1){
				        	Map<String, Object> rawData=getUpdateData(array.getJSONObject(i),qrCode,str,wlzmap,jhzqs,dydzmap);
							api.updateForData(list.get(0).get("_id").toString(), rawData);
				        }
					}
					
				}
				sapScpgService.saveOrUpdate(listSapScpgTb);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public Map<String, Object> getData(JSONObject json,String qrCode,String str,Map<String,Long> map,String barcode,Map<String,Long> jhzqs,List<Map<String, Object>> ctg_list,Map<String,String> dydzmap){
		Map<String, Object> rawData=null;
		try {
			List<String> list=Arrays.asList(json.getString("ProductDescription").split("/"));
			rawData = new HashMap<String, Object>();
			Map<String, Object> m1 = new HashMap<String, Object>();
			m1.put("value", qrCode);
			rawData.put("_widget_1597219749750", m1);// 二维码数据
			Map<String, Object> m3 = new HashMap<String, Object>();
			m3.put("value", json.get("ManufacturingOrder"));
			rawData.put("_widget_1597218715227", m3);// 订单
			Map<String, Object> m6 = new HashMap<String, Object>();
			m6.put("value", str);
			rawData.put("_widget_1597218715274", m6);// 流水号
			Map<String, Object> m7 = new HashMap<String, Object>();
			m7.put("value", list.get(0));
			rawData.put("_widget_1597218715486", m7);// 品牌
			Map<String, Object> m8 = new HashMap<String, Object>();
			m8.put("value", DateUtils.getLongToString(json.getString("MfgOrderScheduledReleaseDate"),DateUtils.FORMAT_1_STRING));
			rawData.put("_widget_1597219257856", m8);// 计划下达日期
			Map<String, Object> m9 = new HashMap<String, Object>();
			m9.put("value", DateUtils.getLongToString(json.getString("MfgOrderActualReleaseDate"),DateUtils.FORMAT_1_STRING));
			rawData.put("_widget_1597219257966", m9);// 实际下达日期
			Map<String, Object> m10 = new HashMap<String, Object>();
			m10.put("value", DateUtils.getLongToString(json.getString("MfgOrderItemPlndDeliveryDate"),DateUtils.FORMAT_1_STRING));
			rawData.put("_widget_1597395915492", m10);// 计划交货日期
			Map<String, Object> m11 = new HashMap<String, Object>();
			m11.put("value", json.get("SalesOrder"));
			rawData.put("_widget_1597219257988", m11);// 销售订单
			Map<String, Object> m12 = new HashMap<String, Object>();
			m12.put("value", json.get("SalesOrderItem"));
			rawData.put("_widget_1597219577354", m12);// 销售订单项目
			Map<String, Object> m15 = new HashMap<String, Object>();
			m15.put("value",  json.get("SoldToParty"));
			rawData.put("_widget_1597219746134", m15);// 经销商编码
			Map<String, Object> m16 = new HashMap<String, Object>();
			m16.put("value", json.get("CustomerName"));
			rawData.put("_widget_1597219746117", m16);// 经销商名称
			Map<String, Object> m17 = new HashMap<String, Object>();
			m17.put("value", json.get("ProductGroup"));
			rawData.put("_widget_1597219746483", m17);// 物料组编码
			Map<String, Object> m18 = new HashMap<String, Object>();
			m18.put("value", json.get("MaterialGroupName"));
			rawData.put("_widget_1597219746515", m18);// 物料组名称
			Map<String, Object> m19 = new HashMap<String, Object>();
			m19.put("value", json.get("Material"));
			rawData.put("_widget_1597219746726", m19);// 物料编码
			Map<String, Object> m20 = new HashMap<String, Object>();
			m20.put("value", json.get("ProductDescription"));
			rawData.put("_widget_1597219746773", m20);// 物料名称
			if(list.size()==1){
				Map<String, Object> m21 = new HashMap<String, Object>();
				m21.put("value", "");
				rawData.put("_widget_1597219747013", m21);// 物料名称-打印产品名称（洗水唛）
				Map<String, Object> m22 = new HashMap<String, Object>();
				m22.put("value", "");
				rawData.put("_widget_1597219747196", m22);// 物料名称-皮号/色号
				Map<String, Object> m23 = new HashMap<String, Object>();
				m23.put("value", "");
				rawData.put("_widget_1597219747075", m23);// 物料名称-产品规格
				Map<String, Object> m39 = new HashMap<String, Object>();
				m39.put("value", "");
				rawData.put("_widget_1621825010924", m39);// 物料名称-打印产品名称（不干胶及派工单）
				Map<String, Object> m41 = new HashMap<String, Object>();
				m41.put("value", "");
				rawData.put("_widget_1621825011671", m41);// 床架全真皮/半真皮/无真皮标识
			}else if(list.size()==2){
				Map<String, Object> m21 = new HashMap<String, Object>();
				m21.put("value", getCpmc(json.getString("ProductGroup"),list.get(1),list.get(0),dydzmap));
				rawData.put("_widget_1597219747013", m21);// 物料名称-打印产品名称（洗水唛）
				Map<String, Object> m22 = new HashMap<String, Object>();
				m22.put("value", "");
				rawData.put("_widget_1597219747196", m22);// 物料名称-皮号/色号
				Map<String, Object> m23 = new HashMap<String, Object>();
				m23.put("value", "");
				rawData.put("_widget_1597219747075", m23);// 物料名称-产品规格
				Map<String, Object> m39 = new HashMap<String, Object>();
				m39.put("value", getBgjDyCpmc(json.getString("ProductGroup"),list.get(1),list.get(0),dydzmap));
				rawData.put("_widget_1621825010924", m39);// 物料名称-打印产品名称（不干胶及派工单）
				Map<String, Object> m41 = new HashMap<String, Object>();
				m41.put("value", "");
				rawData.put("_widget_1621825011671", m41);// 床架全真皮/半真皮/无真皮标识
			}else if(list.size()==3){
				Map<String, Object> m21 = new HashMap<String, Object>();
				m21.put("value", getCpmc(json.getString("ProductGroup"),list.get(1),list.get(0),dydzmap));
				rawData.put("_widget_1597219747013", m21);// 物料名称-打印产品名称(洗水唛)
				Map<String, Object> m22 = new HashMap<String, Object>();
				m22.put("value", "");
				rawData.put("_widget_1597219747196", m22);// 物料名称-皮号/色号
				
				Map<String, Object> m23 = new HashMap<String, Object>();
				m23.put("value", list.get(2));
				rawData.put("_widget_1597219747075", m23);// 物料名称-产品规格
				Map<String, Object> m39 = new HashMap<String, Object>();
				m39.put("value", getBgjDyCpmc(json.getString("ProductGroup"),list.get(1),list.get(0),dydzmap));
				rawData.put("_widget_1621825010924", m39);// 物料名称-打印产品名称（不干胶及派工单）
				Map<String, Object> m41 = new HashMap<String, Object>();
				m41.put("value", getZPState(json.getString("ProductGroup"),list.get(2),""));
				rawData.put("_widget_1621825011671", m41);// 床架全真皮/半真皮/无真皮标识
				
				
				Map<String, Object> m42 = new HashMap<String, Object>();
				m42.put("value", getGGCS(list.get(2)).size()>=1?getGGCS(list.get(2)).get(0):"");
				rawData.put("_widget_1621825013561", m42);// 规格参数1
				Map<String, Object> m43 = new HashMap<String, Object>();
				m43.put("value", getGGCS(list.get(2)).size()>=2?getGGCS(list.get(2)).get(1):"");
				rawData.put("_widget_1621825013581", m43);// 规格参数2
				Map<String, Object> m44 = new HashMap<String, Object>();
				m44.put("value", getGGCS(list.get(2)).size()>=3?getGGCS(list.get(2)).get(2):"");
				rawData.put("_widget_1621825013601", m44);// 规格参数3
				Map<String, Object> m45 = new HashMap<String, Object>();
				m45.put("value", getGGCS(list.get(2)).size()>=4?getGGCS(list.get(2)).get(3):"");
				rawData.put("_widget_1621825013621", m45);// 规格参数4
				Map<String, Object> m46 = new HashMap<String, Object>();
				m46.put("value", getGGCS(list.get(2)).size()>4?getGGCS(list.get(2)).get(4):"");
				rawData.put("_widget_1621825013641", m46);// 规格参数5
			}else if(list.size()>=4){
				Map<String, Object> m21 = new HashMap<String, Object>();
				m21.put("value", getCpmc(json.getString("ProductGroup"),list.get(1),list.get(0),dydzmap));
				rawData.put("_widget_1597219747013", m21);// 物料名称-打印产品名称（洗水唛）
				
				//String ps=json.getString("ProductDescription").substring(json.getString("ProductDescription").indexOf(list.get(1))+list.get(1).length()+1, json.getString("ProductDescription").lastIndexOf("/"));
				Map<String, Object> m22 = new HashMap<String, Object>();
				m22.put("value", list.get(2));
				rawData.put("_widget_1597219747196", m22);// 物料名称-皮号/色号
				
				//String gg=json.getString("ProductDescription").substring(json.getString("ProductDescription").lastIndexOf("/")+1, json.getString("ProductDescription").length());
				Map<String, Object> m23 = new HashMap<String, Object>();
				m23.put("value", list.get(3));
				rawData.put("_widget_1597219747075", m23);// 物料名称-产品规格
				Map<String, Object> m39 = new HashMap<String, Object>();
				m39.put("value", getBgjDyCpmc(json.getString("ProductGroup"),list.get(1),list.get(0),dydzmap));
				rawData.put("_widget_1621825010924", m39);// 物料名称-打印产品名称（不干胶及派工单）
				Map<String, Object> m41 = new HashMap<String, Object>();
				m41.put("value", getZPState(json.getString("ProductGroup"),list.get(3),list.get(2)));
				rawData.put("_widget_1621825011671", m41);// 床架全真皮/半真皮/无真皮标识
				
				Map<String, Object> m42 = new HashMap<String, Object>();
				m42.put("value", getGGCS(list.get(3)).size()>=1?getGGCS(list.get(3)).get(0):"");
				rawData.put("_widget_1621825013561", m42);// 规格参数1
				Map<String, Object> m43 = new HashMap<String, Object>();
				m43.put("value", getGGCS(list.get(3)).size()>=2?getGGCS(list.get(3)).get(1):"");
				rawData.put("_widget_1621825013581", m43);// 规格参数2
				Map<String, Object> m44 = new HashMap<String, Object>();
				m44.put("value", getGGCS(list.get(3)).size()>=3?getGGCS(list.get(3)).get(2):"");
				rawData.put("_widget_1621825013601", m44);// 规格参数3
				Map<String, Object> m45 = new HashMap<String, Object>();
				m45.put("value", getGGCS(list.get(3)).size()>=4?getGGCS(list.get(3)).get(3):"");
				rawData.put("_widget_1621825013621", m45);// 规格参数4
				Map<String, Object> m46 = new HashMap<String, Object>();
				m46.put("value", getGGCS(list.get(3)).size()>4?getGGCS(list.get(3)).get(4):"");
				rawData.put("_widget_1621825013641", m46);// 规格参数5
			}
			
			
			StringBuilder sb=new StringBuilder();
			if(!"".equals(json.getString("YY1_CJPTCDGD_SDI"))){
				sb.append(json.getString("YY1_CJPTCDGD_SDI")+",");
			}
			if(!"".equals(json.getString("YY1_CJPTCJ_SDI"))){
				sb.append(json.getString("YY1_CJPTCJ_SDI")+",");
			}
			if(!"".equals(json.getString("YY1_DHQTBZ_SDI"))){
				sb.append(json.getString("YY1_DHQTBZ_SDI"));
			}
			if(!"".equals(sb.toString()) && sb.toString().substring(sb.toString().length()-1).equals(",")){
				Map<String, Object> m24 = new HashMap<String, Object>();
				m24.put("value", sb.toString().substring(0,sb.toString().length()-1));
				rawData.put("_widget_1597219746331", m24);// 备注
			}else{
				Map<String, Object> m24 = new HashMap<String, Object>();
				m24.put("value", sb.toString());
				rawData.put("_widget_1597219746331", m24);// 备注
			}
			Map<String, Object> m25 = new HashMap<String, Object>();
			m25.put("value", json.getString("IsMarkedForDeletion"));
			rawData.put("_widget_1599018910205", m25);// 是否被标记删除
			Map<String, Object> m26 = new HashMap<String, Object>();
			m26.put("value", DateUtils.getLongToString(json.getString("CreationDate"),DateUtils.FORMAT_1_STRING));
			rawData.put("_widget_1599449214532", m26);// 创建日期
			Map<String, Object> m27 = new HashMap<String, Object>();
			m27.put("value", "未完工");
			rawData.put("_widget_1599559701213", m27);// 完工状态
			Map<String, Object> m28 = new HashMap<String, Object>();
			m28.put("value", json.get("MfgOrderItemPlannedTotalQty"));
			rawData.put("_widget_1599707904636", m28);// 总单数
			Map<String, Object> m29 = new HashMap<String, Object>();
			m29.put("value", map.get(json.getString("ProductGroup")));
			rawData.put("_widget_1599815993717", m29);// 交货周期
			Map<String, Object> m30 = new HashMap<String, Object>();
			m30.put("value", DateUtils.getStringDate(json.getString("MfgOrderActualReleaseDate"),map.get(json.getString("ProductGroup"))));
			rawData.put("_widget_1599815993815", m30);// 计划完工日期
			Map<String, Object> m31 = new HashMap<String, Object>();
			m31.put("value", 1);
			rawData.put("_widget_1599810056377", m31);// 计数
			Map<String, Object> m32 = new HashMap<String, Object>();
			m32.put("value", 0);
			rawData.put("_widget_1599898485433", m32);// 完工数量
			Map<String, Object> m33 = new HashMap<String, Object>();
			m33.put("value", barcode);
			rawData.put("_widget_1600159799124", m33);// 产品防伪码
			Map<String, Object> m34 = new HashMap<String, Object>();
			m34.put("value", jhzqs.get(json.getString("SalesOrder")));
			rawData.put("_widget_1600323433746", m34);// 交货周期
			Map<String, Object> m35 = new HashMap<String, Object>();
			m35.put("value", DateUtils.getStringDate(json.getString("CreationDate"),jhzqs.get(json.getString("SalesOrder"))));
			rawData.put("_widget_1600323433792", m35);// 计划完工日期(跟单齐套)
			
			String ctglx="";
			for (int i = 0; i < ctg_list.size(); i++) {
				List<Map<String, Object>> lists=(List<Map<String, Object>>)ctg_list.get(i).get("_widget_1604470170618");
				for (int j = 0; j < lists.size(); j++) {
					if(json.getString("ProductDescription").contains(lists.get(j).get("_widget_1604470170634").toString())){
						ctglx=ctg_list.get(i).get("_widget_1604470170601").toString();
					}
				}
			}
			Map<String, Object> m36 = new HashMap<String, Object>();
			m36.put("value", ctglx);
			rawData.put("_widget_1604470358340", m36);// 床头柜类型
			Map<String, Object> m37 = new HashMap<String, Object>();
			m37.put("value", json.get("MaterialVolume"));
			rawData.put("_widget_1611719897815", m37);// 外包装体积
			Map<String, Object> m38 = new HashMap<String, Object>();
			m38.put("value", json.get("ProductType"));
			rawData.put("_widget_1618035788195", m38);// 产品类型
			Map<String, Object> m40 = new HashMap<String, Object>();
			m40.put("value", getMSJjcpxl(list.get(0),list.get(1),json.getString("ProductGroup")));
			rawData.put("_widget_1621818999358", m40);// 计件产品系列
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rawData;
	}
	
	public Map<String, Object> getUpdateData(JSONObject json,String qrCode,String str,Map<String,Long> map,Map<String,Long> jhzqs,Map<String,String> dydzmap){
		Map<String, Object> rawData=null;
		try {
			List<String> list=Arrays.asList(json.getString("ProductDescription").split("/"));
			rawData = new HashMap<String, Object>();
			Map<String, Object> m1 = new HashMap<String, Object>();
			m1.put("value", qrCode);
			rawData.put("_widget_1597219749750", m1);// 二维码数据
			Map<String, Object> m3 = new HashMap<String, Object>();
			m3.put("value", json.get("ManufacturingOrder"));
			rawData.put("_widget_1597218715227", m3);// 订单
			Map<String, Object> m6 = new HashMap<String, Object>();
			m6.put("value", str);
			rawData.put("_widget_1597218715274", m6);// 流水号
			Map<String, Object> m7 = new HashMap<String, Object>();
			m7.put("value", list.get(0));
			rawData.put("_widget_1597218715486", m7);// 品牌
			Map<String, Object> m8 = new HashMap<String, Object>();
			m8.put("value", DateUtils.getLongToString(json.getString("MfgOrderScheduledReleaseDate"),DateUtils.FORMAT_1_STRING));
			rawData.put("_widget_1597219257856", m8);// 计划下达日期
			Map<String, Object> m9 = new HashMap<String, Object>();
			m9.put("value", DateUtils.getLongToString(json.getString("MfgOrderActualReleaseDate"),DateUtils.FORMAT_1_STRING));
			rawData.put("_widget_1597219257966", m9);// 实际下达日期
			Map<String, Object> m10 = new HashMap<String, Object>();
			m10.put("value", DateUtils.getLongToString(json.getString("MfgOrderItemPlndDeliveryDate"),DateUtils.FORMAT_1_STRING));
			rawData.put("_widget_1597395915492", m10);// 计划交货日期
			Map<String, Object> m11 = new HashMap<String, Object>();
			m11.put("value", json.get("SalesOrder"));
			rawData.put("_widget_1597219257988", m11);// 销售订单
			Map<String, Object> m12 = new HashMap<String, Object>();
			m12.put("value", json.get("SalesOrderItem"));
			rawData.put("_widget_1597219577354", m12);// 销售订单项目
			Map<String, Object> m15 = new HashMap<String, Object>();
			m15.put("value",  json.get("SoldToParty"));
			rawData.put("_widget_1597219746134", m15);// 经销商编码
			Map<String, Object> m16 = new HashMap<String, Object>();
			m16.put("value", json.get("CustomerName"));
			rawData.put("_widget_1597219746117", m16);// 经销商名称
			Map<String, Object> m17 = new HashMap<String, Object>();
			m17.put("value", json.get("ProductGroup"));
			rawData.put("_widget_1597219746483", m17);// 物料组编码
			Map<String, Object> m18 = new HashMap<String, Object>();
			m18.put("value", json.get("MaterialGroupName"));
			rawData.put("_widget_1597219746515", m18);// 物料组名称
			Map<String, Object> m19 = new HashMap<String, Object>();
			m19.put("value", json.get("Material"));
			rawData.put("_widget_1597219746726", m19);// 物料编码
			Map<String, Object> m20 = new HashMap<String, Object>();
			m20.put("value", json.get("ProductDescription"));
			rawData.put("_widget_1597219746773", m20);// 物料名称
			if(list.size()==1){
				Map<String, Object> m21 = new HashMap<String, Object>();
				m21.put("value", "");
				rawData.put("_widget_1597219747013", m21);// 物料名称-打印产品名称（洗水唛）
				Map<String, Object> m22 = new HashMap<String, Object>();
				m22.put("value", "");
				rawData.put("_widget_1597219747196", m22);// 物料名称-皮号/色号
				Map<String, Object> m23 = new HashMap<String, Object>();
				m23.put("value", "");
				rawData.put("_widget_1597219747075", m23);// 物料名称-产品规格
				Map<String, Object> m39 = new HashMap<String, Object>();
				m39.put("value", "");
				rawData.put("_widget_1621825010924", m39);// 物料名称-打印产品名称（不干胶及派工单）
				Map<String, Object> m41 = new HashMap<String, Object>();
				m41.put("value", "");
				rawData.put("_widget_1621825011671", m41);// 床架全真皮/半真皮/无真皮标识
			}else if(list.size()==2){
				Map<String, Object> m21 = new HashMap<String, Object>();
				m21.put("value", getCpmc(json.getString("ProductGroup"),list.get(1),list.get(0),dydzmap));
				rawData.put("_widget_1597219747013", m21);// 物料名称-打印产品名称（洗水唛）
				Map<String, Object> m22 = new HashMap<String, Object>();
				m22.put("value", "");
				rawData.put("_widget_1597219747196", m22);// 物料名称-皮号/色号
				Map<String, Object> m23 = new HashMap<String, Object>();
				m23.put("value", "");
				rawData.put("_widget_1597219747075", m23);// 物料名称-产品规格
				Map<String, Object> m39 = new HashMap<String, Object>();
				m39.put("value", getBgjDyCpmc(json.getString("ProductGroup"),list.get(1),list.get(0),dydzmap));
				rawData.put("_widget_1621825010924", m39);// 物料名称-打印产品名称（不干胶及派工单）
				Map<String, Object> m41 = new HashMap<String, Object>();
				m41.put("value", "");
				rawData.put("_widget_1621825011671", m41);// 床架全真皮/半真皮/无真皮标识
			}else if(list.size()==3){
				Map<String, Object> m21 = new HashMap<String, Object>();
				m21.put("value", getCpmc(json.getString("ProductGroup"),list.get(1),list.get(0),dydzmap));
				rawData.put("_widget_1597219747013", m21);// 物料名称-打印产品名称（洗水唛）
				Map<String, Object> m22 = new HashMap<String, Object>();
				m22.put("value", "");
				rawData.put("_widget_1597219747196", m22);// 物料名称-皮号/色号
				
				Map<String, Object> m23 = new HashMap<String, Object>();
				m23.put("value", list.get(2));
				rawData.put("_widget_1597219747075", m23);// 物料名称-产品规格
				Map<String, Object> m39 = new HashMap<String, Object>();
				m39.put("value", getBgjDyCpmc(json.getString("ProductGroup"),list.get(1),list.get(0),dydzmap));
				rawData.put("_widget_1621825010924", m39);// 物料名称-打印产品名称（不干胶）
				Map<String, Object> m41 = new HashMap<String, Object>();
				m41.put("value", getZPState(json.getString("ProductGroup"),list.get(2),""));
				rawData.put("_widget_1621825011671", m41);// 床架全真皮/半真皮/无真皮标识
				
				Map<String, Object> m42 = new HashMap<String, Object>();
				m42.put("value", getGGCS(list.get(2)).size()>=1?getGGCS(list.get(2)).get(0):"");
				rawData.put("_widget_1621825013561", m42);// 规格参数1
				Map<String, Object> m43 = new HashMap<String, Object>();
				m43.put("value", getGGCS(list.get(2)).size()>=2?getGGCS(list.get(2)).get(1):"");
				rawData.put("_widget_1621825013581", m43);// 规格参数2
				Map<String, Object> m44 = new HashMap<String, Object>();
				m44.put("value", getGGCS(list.get(2)).size()>=3?getGGCS(list.get(2)).get(2):"");
				rawData.put("_widget_1621825013601", m44);// 规格参数3
				Map<String, Object> m45 = new HashMap<String, Object>();
				m45.put("value", getGGCS(list.get(2)).size()>=4?getGGCS(list.get(2)).get(3):"");
				rawData.put("_widget_1621825013621", m45);// 规格参数4
				Map<String, Object> m46 = new HashMap<String, Object>();
				m46.put("value", getGGCS(list.get(2)).size()>4?getGGCS(list.get(2)).get(4):"");
				rawData.put("_widget_1621825013641", m46);// 规格参数5
			}else if(list.size()>=4){
				Map<String, Object> m21 = new HashMap<String, Object>();
				m21.put("value", getCpmc(json.getString("ProductGroup"),list.get(1),list.get(0),dydzmap));
				rawData.put("_widget_1597219747013", m21);// 物料名称-打印产品名称（洗水唛）
				
				//String ps=json.getString("ProductDescription").substring(json.getString("ProductDescription").indexOf(list.get(1))+list.get(1).length()+1, json.getString("ProductDescription").lastIndexOf("/"));
				Map<String, Object> m22 = new HashMap<String, Object>();
				m22.put("value", list.get(2));
				rawData.put("_widget_1597219747196", m22);// 物料名称-皮号/色号
				
				//String gg=json.getString("ProductDescription").substring(json.getString("ProductDescription").lastIndexOf("/")+1, json.getString("ProductDescription").length());
				Map<String, Object> m23 = new HashMap<String, Object>();
				m23.put("value", list.get(3));
				rawData.put("_widget_1597219747075", m23);// 物料名称-产品规格
				Map<String, Object> m39 = new HashMap<String, Object>();
				m39.put("value", getBgjDyCpmc(json.getString("ProductGroup"),list.get(1),list.get(0),dydzmap));
				rawData.put("_widget_1621825010924", m39);// 物料名称-打印产品名称（不干胶）
				
				Map<String, Object> m41 = new HashMap<String, Object>();
				m41.put("value", getZPState(json.getString("ProductGroup"),list.get(3),list.get(2)));
				rawData.put("_widget_1621825011671", m41);// 床架全真皮/半真皮/无真皮标识
				
				Map<String, Object> m42 = new HashMap<String, Object>();
				m42.put("value", getGGCS(list.get(3)).size()>=1?getGGCS(list.get(3)).get(0):"");
				rawData.put("_widget_1621825013561", m42);// 规格参数1
				Map<String, Object> m43 = new HashMap<String, Object>();
				m43.put("value", getGGCS(list.get(3)).size()>=2?getGGCS(list.get(3)).get(1):"");
				rawData.put("_widget_1621825013581", m43);// 规格参数2
				Map<String, Object> m44 = new HashMap<String, Object>();
				m44.put("value", getGGCS(list.get(3)).size()>=3?getGGCS(list.get(3)).get(2):"");
				rawData.put("_widget_1621825013601", m44);// 规格参数3
				Map<String, Object> m45 = new HashMap<String, Object>();
				m45.put("value", getGGCS(list.get(3)).size()>=4?getGGCS(list.get(3)).get(3):"");
				rawData.put("_widget_1621825013621", m45);// 规格参数4
				Map<String, Object> m46 = new HashMap<String, Object>();
				m46.put("value", getGGCS(list.get(3)).size()>4?getGGCS(list.get(3)).get(4):"");
				rawData.put("_widget_1621825013641", m46);// 规格参数5
			}
			
			Map<String, Object> m25 = new HashMap<String, Object>();
			m25.put("value", json.getString("IsMarkedForDeletion"));
			rawData.put("_widget_1599018910205", m25);// 是否被标记删除
			Map<String, Object> m26 = new HashMap<String, Object>();
			m26.put("value", DateUtils.getLongToString(json.getString("CreationDate"),DateUtils.FORMAT_1_STRING));
			rawData.put("_widget_1599449214532", m26);// 创建日期
			Map<String, Object> m28 = new HashMap<String, Object>();
			m28.put("value", json.get("MfgOrderItemPlannedTotalQty"));
			rawData.put("_widget_1599707904636", m28);// 总单数
			Map<String, Object> m29 = new HashMap<String, Object>();
			m29.put("value", map.get(json.get("ProductGroup")));
			rawData.put("_widget_1599815993717", m29);// 交货周期
			Map<String, Object> m30 = new HashMap<String, Object>();
			m30.put("value", DateUtils.getStringDate(json.getString("MfgOrderActualReleaseDate"),map.get(json.get("ProductGroup"))));
			rawData.put("_widget_1599815993815", m30);// 计划完工日期
			Map<String, Object> m34 = new HashMap<String, Object>();
			m34.put("value", jhzqs.get(json.getString("SalesOrder")));
			rawData.put("_widget_1600323433746", m34);// 交货周期
			Map<String, Object> m35 = new HashMap<String, Object>();
			m35.put("value", DateUtils.getStringDate(json.getString("CreationDate"),jhzqs.get(json.getString("SalesOrder"))));
			rawData.put("_widget_1600323433792", m35);// 计划完工日期(跟单齐套)
			Map<String, Object> m36 = new HashMap<String, Object>();
			m36.put("value", json.get("MaterialVolume"));
			rawData.put("_widget_1611719897815", m36);// 外包装体积
			Map<String, Object> m37 = new HashMap<String, Object>();
			m37.put("value", json.get("ProductType"));
			rawData.put("_widget_1618035788195", m37);// 产品类型
			Map<String, Object> m40 = new HashMap<String, Object>();
			m40.put("value", getMSJjcpxl(list.get(0),list.get(1),json.getString("ProductGroup")));
			rawData.put("_widget_1621818999358", m40);// 计件产品系列
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rawData;
	}
	
	public SapScpgTb getSapScpgTb(JSONObject json,String qrCode,String str,Integer dyzs,String tempname,String status,String barcode){
		List<String> list=Arrays.asList(json.getString("ProductDescription").split("/"));
		SapScpgTb sapScpgTb=new SapScpgTb();
		sapScpgTb.setId(qrCode);
		sapScpgTb.setDdh(json.getString("ManufacturingOrder"));
		sapScpgTb.setLsh(str);
		sapScpgTb.setPp(list.get(0));
		sapScpgTb.setJhxdrq(DateUtils.getLongToString(json.getString("MfgOrderScheduledReleaseDate"),DateUtils.FORMAT_1_STRING));
		sapScpgTb.setSjxdrq(DateUtils.getLongToString(json.getString("MfgOrderActualReleaseDate"),DateUtils.FORMAT_1_STRING));
		sapScpgTb.setJhjhrq(DateUtils.getLongToString(json.getString("MfgOrderItemPlndDeliveryDate"),DateUtils.FORMAT_1_STRING));
		sapScpgTb.setXsdd(json.getString("SalesOrder"));
		sapScpgTb.setXsddh(json.getString("SalesOrderItem"));
		sapScpgTb.setJxsbm(json.getString("SoldToParty"));
		sapScpgTb.setJxsmc(json.getString("CustomerName"));
		sapScpgTb.setWlzbm(json.getString("ProductGroup"));
		sapScpgTb.setWlzmc(json.getString("MaterialGroupName"));
		sapScpgTb.setWlbm(json.getString("Material"));
		sapScpgTb.setWlmc(json.getString("ProductDescription"));
		if(list.size()==1){
			sapScpgTb.setCpmc("");
			sapScpgTb.setPsph("");
			sapScpgTb.setCpgg("");
		}else if(list.size()==2){
			sapScpgTb.setCpmc(list.get(1));
			sapScpgTb.setPsph("");
			sapScpgTb.setCpgg("");
		}else if(list.size()==3){
			sapScpgTb.setCpmc(list.get(1));
			sapScpgTb.setPsph("");
			sapScpgTb.setCpgg(list.get(2));
		}else if(list.size()>=4){
			sapScpgTb.setCpmc(list.get(1));
			sapScpgTb.setPsph(json.getString("ProductDescription").substring(json.getString("ProductDescription").indexOf(list.get(1))+list.get(1).length()+1, json.getString("ProductDescription").lastIndexOf("/")));
			sapScpgTb.setCpgg(json.getString("ProductDescription").substring(json.getString("ProductDescription").lastIndexOf("/")+1, json.getString("ProductDescription").length()));
		}
		
		
		StringBuilder sb=new StringBuilder();
		if(!"".equals(json.getString("YY1_CJPTCDGD_SDI"))){
			sb.append(json.getString("YY1_CJPTCDGD_SDI")+",");
		}
		if(!"".equals(json.getString("YY1_CJPTCJ_SDI"))){
			sb.append(json.getString("YY1_CJPTCJ_SDI")+",");
		}
		if(!"".equals(json.getString("YY1_DHQTBZ_SDI"))){
			sb.append(json.getString("YY1_DHQTBZ_SDI"));
		}
		if(!"".equals(sb.toString()) && sb.toString().substring(sb.toString().length()-1).equals(",")){
			sapScpgTb.setBz(sb.toString().substring(0,sb.toString().length()-1));
		}else{
			sapScpgTb.setBz(sb.toString());
		}
		sapScpgTb.setSfbbjsc(json.getString("IsMarkedForDeletion"));
		sapScpgTb.setCjrq(DateUtils.getLongToString(json.getString("CreationDate"),DateUtils.FORMAT_1_STRING));
		sapScpgTb.setDyzs(dyzs);
		sapScpgTb.setTempname(tempname);
		sapScpgTb.setStatus(status);
		sapScpgTb.setBarcode(barcode);
		return sapScpgTb;
	}
	
	
	public String getStr(){
		Random rm = new Random();
		String value=String.valueOf(rm.ints(100000000, (999999999 + 1)).limit(1).findFirst().getAsInt())+String.valueOf(rm.ints(100000000, (999999999 + 1)).limit(1).findFirst().getAsInt());
        return value;
	}
	
	public static JSONArray getJSONArray(String url){
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
				JSONObject  obj1 = jsonObject1.getJSONObject("d");
				array = obj1.getJSONArray("results");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return array;     
	}
	               
	//洗水唛    
	public String getCpmc(String wlz,String wlms,String ppbm,Map<String,String> dydzmap){
		String cpmc=wlms;
		if("312".equals(wlz)){
			if(wlms.contains("床屏+床框")){
				cpmc=wlms.replace("床屏+床框", "");
			}else if(wlms.contains("床屏+排骨架")){
				cpmc=wlms.replace("床屏+排骨架", "");
			}else if(wlms.contains("床框+排骨架")){
				cpmc=wlms.replace("床框+排骨架", "");
			}else if(wlms.contains("床框")){
				cpmc=wlms.replace("床框", "");
			}else if(wlms.contains("床屏")){
				cpmc=wlms.replace("床屏", "");
			}
		}
		String A=cpmc;
		if(ppbm.equals("MS") && (wlz.equals("311") || wlz.equals("318"))){
			String cpmc1=cpmc.substring(0,cpmc.length()-2);
			String cpmc2=cpmc.substring(cpmc.length()-2, cpmc.length());
			if(cpmc2.contains("金")){
				cpmc2=cpmc2.replace("金", "");
			}
			if(cpmc2.contains("绿")){
				cpmc2=cpmc2.replace("绿", "");
			}
			if(cpmc2.contains("蓝")){
				cpmc2=cpmc2.replace("蓝", "");
			}
			if(cpmc2.contains("红")){
				cpmc2=cpmc2.replace("红", "");
			}
			cpmc=cpmc1+cpmc2;
		}
		String B=dydzmap.get(cpmc);
		if(B!=null){
			A=A.replace(cpmc, B);
		}
		return A;
	}
	
	//不干胶及派工单打印的产品名称
	public String getBgjDyCpmc(String wlz,String wlms,String ppbm,Map<String,String> dydzmap){
		String cpmc=wlms;
		if("312".equals(wlz)){
			if(wlms.contains("床屏+床框")){
				cpmc=wlms.replace("床屏+床框", "");
			}else if(wlms.contains("床屏+排骨架")){
				cpmc=wlms.replace("床屏+排骨架", "");
			}else if(wlms.contains("床框+排骨架")){
				cpmc=wlms.replace("床框+排骨架", "");
			}else if(wlms.contains("床框")){
				cpmc=wlms.replace("床框", "");
			}else if(wlms.contains("床屏")){
				cpmc=wlms.replace("床屏", "");
			}
		}
		if(ppbm.equals("MS") && (wlz.equals("311") || wlz.equals("318"))){
			String cpmc1=cpmc.substring(0,cpmc.length()-2);
			String cpmc2=cpmc.substring(cpmc.length()-2, cpmc.length());
			if(cpmc2.contains("金")){
				cpmc2=cpmc2.replace("金", "");
			}
			if(cpmc2.contains("绿")){
				cpmc2=cpmc2.replace("绿", "");
			}
			if(cpmc2.contains("蓝")){
				cpmc2=cpmc2.replace("蓝", "");
			}
			if(cpmc2.contains("红")){
				cpmc2=cpmc2.replace("红", "");
			}
			cpmc=cpmc1+cpmc2;
		}
		String B=dydzmap.get(cpmc);
		if(B!=null){
			wlms=wlms.replace(cpmc, B);
		}
		return wlms;
		
	}
	
	public String getMSJjcpxl(String ppbm,String wlms,String wlz){
		String cpmc=wlms;
		String cpmc1=cpmc.substring(0,cpmc.length()-2);
		String cpmc2=cpmc.substring(cpmc.length()-2, cpmc.length());
		if(ppbm.equals("MS") && (wlz.equals("311") || wlz.equals("318"))){
			if(cpmc2.contains("金")){
				cpmc2=cpmc2.replace("金", "");
			}
			if(cpmc2.contains("绿")){
				cpmc2=cpmc2.replace("绿", "");
			}
			if(cpmc2.contains("蓝")){
				cpmc2=cpmc2.replace("蓝", "");
			}
			if(cpmc2.contains("红")){
				cpmc2=cpmc2.replace("红", "");
			}
		}
		return cpmc1+cpmc2;
	}
	
	public String getZPState(String wlz,String gg,String ps){
		if(!wlz.equals("312")){
			return "";
		}
		String state="无真皮";
		if(gg.contains("全真皮")){
			state="全真皮";
		}else if(ps.length()>=3 && (ps.substring(0, 3).equals("C07") || ps.substring(0,1).equals("H") || ps.substring(0,1).equals("N"))){
			state="半真皮";
		}
		return state;
	}
	
	public List<String> getGGCS(String str){
		str=str.trim();
		String str2="";
		if(str != null && !"".equals(str)){
			for(int i=0;i<str.length();i++){
				if(str.charAt(i)>=48 && str.charAt(i)<=57){
					str2+=str.charAt(i);
				}else{
					str2+="/";
				}
			}
		}
		List<String> list=Arrays.asList(str2.split("/"));
		List<String> list1=new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			if(!"".equals(list.get(i))){
				list1.add(list.get(i));
			}
		}
		return list1;
	}
}

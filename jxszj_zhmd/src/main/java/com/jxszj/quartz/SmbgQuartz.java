package com.jxszj.quartz;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.jxszj.utils.DateUtils;
import com.jxszj.utils.JDYAPIUtils;
import com.jxszj.utils.ObjectUtils;
import com.taobao.api.ApiException;

public class SmbgQuartz {
	
	// 简道云华生集团
	final static String APPID = "5c048a8379332d0978a68b8e";
	final static String APIKEY = "SGmAqjtntz3q5M0rZqm2b4nGlRpSlah6";
	
	//扫码报工
	final static String SMBG_ENTRYID = "5f378cb69e92a60006c3deea";
	
	//（床垫组）产品系列工序工价维护
	final static String CD_ENTRYID = "6098d745a933dd0008c957b6";
	
	//（床架组）产品系列工序工价维护
	final static String CJ_ENTRYID = "609f7e99f8da3900078dc7e6";
	
	public void execute(){
		try {
			JDYAPIUtils smbg_api = new JDYAPIUtils(APPID, SMBG_ENTRYID, APIKEY);
			JDYAPIUtils cd_api = new JDYAPIUtils(APPID, CD_ENTRYID, APIKEY);
			JDYAPIUtils cj_api = new JDYAPIUtils(APPID, CJ_ENTRYID, APIKEY);
			Set<Object> wl=new HashSet<>();
			//床垫的标准规格尺寸
			Map<String,String> bzgg=new HashMap<>();
			bzgg.put("900*1900", "标准规格");
			bzgg.put("900*2000", "标准规格");
			bzgg.put("1200*1900", "标准规格");
			bzgg.put("1200*2000", "标准规格");
			bzgg.put("1500*1900", "标准规格");
			bzgg.put("1500*2000", "标准规格");
			bzgg.put("1530*2030", "标准规格");
			bzgg.put("1800*2000", "标准规格");
			bzgg.put("1930*2030", "标准规格");
			bzgg.put("2000*2000", "标准规格");
			bzgg.put("2000*2200", "标准规格");
			//查询扫码报工表中工价是空的数据
			final List<Map<String, Object>> condList = new ArrayList<Map<String, Object>>();
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("field", "_widget_1605940938943");//工价
			map.put("type", "number");
			map.put("method", "empty");
	        condList.add(map);
	        Map<String, Object> m=new HashMap<String, Object>();
			m.put("field", "_widget_1625724578246");//创建日期
			m.put("type", "text");
			m.put("method", "eq");
			m.put("value",DateUtils.getNowDateToString(DateUtils.FORMAT_1_STRING));
	        condList.add(m);
	        Map<String, Object> filter = new HashMap<String, Object>(){
	            {
	                put("rel", "and");
	                put("cond", condList);
	            }
	        };
	        List<Map<String, Object>> list= smbg_api.getAllFormData(null, filter);
	        for (int i = 0; i < list.size(); i++) {
	        	String wlz=ObjectUtils.getString(list.get(i).get("_widget_1597476026509"));
	        	wlz="木架".equals(wlz)?"床头柜自制":wlz;
				if("床架自制".equals(wlz) || "床头柜自制".equals(wlz) || "沙发自制".equals(wlz)){
					final List<Map<String, Object>> condList1 = new ArrayList<Map<String, Object>>();
					Map<String, Object> map1=new HashMap<String, Object>();
					map1.put("field", "_widget_1621066171579");//选择产品系列
					map1.put("type", "text");
					map1.put("method", "eq");
					map1.put("value",list.get(i).get("_widget_1597476026267")); //物料名称-产品名称
					condList1.add(map1);
					Map<String, Object> map2=new HashMap<String, Object>();
					map2.put("field", "_widget_1621066171855");//物料组
					map2.put("type", "text");
					map2.put("method", "eq");
					map2.put("value",wlz);
			        condList1.add(map2);
			        Map<String, Object> map3=new HashMap<String, Object>();
			        map3.put("field", "_widget_1621077504791");//工序编码
			        map3.put("type", "text");
			        map3.put("method", "eq");
			        map3.put("value",list.get(i).get("_widget_1597480209790"));
			        condList1.add(map3);
			        Map<String, Object> filter1 = new HashMap<String, Object>(){
			            {
			                put("rel", "and");
			                put("cond", condList1);
			            }
			        };
			        List<Map<String, Object>> list1= cj_api.getAllFormData(null, filter1);
			        
			        //当有多个值的时候取更新时间最近的一个
			        if(list1.size()>1){
			        	Collections.sort(list1, new Comparator<Map<String, Object>>() {
				            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				                Long longDate1 = DateUtils.getLongDate(o1.get("updateTime").toString()) ;
				                Long longDate2 = DateUtils.getLongDate(o2.get("updateTime").toString()) ; 
				                return longDate2.compareTo(longDate1);
				            }
				        });
			        }else if(list1.size()==0){
			        	wl.add(list.get(i).get("_widget_1597476026267"));
			        	continue;
			        }
			        Object gj=list1.get(0).get("_widget_1621077505086");//取出工价
			        
			        Map<String, Object> rawData = new HashMap<String, Object>();
			        Map<String, Object> m1 = new HashMap<String, Object>();
					m1.put("value", gj);
					rawData.put("_widget_1605940938943", m1);
					smbg_api.updateForData(list.get(i).get("_id").toString(), rawData);
			        
				}else if("床垫自制".equals(wlz) || "电动床自制".equals(wlz)){
					String wlmc=ObjectUtils.getString(list.get(i).get("_widget_1597476026267"));
					if(wlmc.contains("C2")){
						wlmc="C2";
					}else if(wlmc.contains("C4")){
						wlmc="C4";
					}else if(wlmc.contains("C6")){
						wlmc="C6";
					}else if(wlmc.contains("C8")){
						wlmc="C8";
					}else if(wlmc.contains("C10")){
						wlmc="C10";
					}else if(wlmc.contains("环球V8")){
						wlmc="环球V8";
					}else if(wlmc.contains("V9")){
						wlmc="V9";
					}else if(wlmc.contains("Candy糖果II")){
						wlmc="Candy糖果II";
					}else if(wlmc.contains("mm魔法定制天然乳胶垫")){
						wlmc="mm魔法定制天然乳胶垫";
					}else if(wlmc.contains("V400")){
						wlmc="V400";
					}else if(wlmc.contains("V600")){
						wlmc="V600";
					}else if(wlmc.contains("V800")){
						wlmc="V800";
					}else if(wlmc.contains("V1000")){
						wlmc="V1000";
					}
					final List<Map<String, Object>> condList1 = new ArrayList<Map<String, Object>>();
					Map<String, Object> map1=new HashMap<String, Object>();
					map1.put("field", "_widget_1620629314061");//选择产品系列
					map1.put("type", "text");
					map1.put("method", "eq");
					map1.put("value",list.get(i).get("_widget_1597476026267"));
					condList1.add(map1);
					Map<String, Object> map2=new HashMap<String, Object>();
					map2.put("field", "_widget_1620809283373");//物料组
					map2.put("type", "text");
					map2.put("method", "eq");
					map2.put("value",wlz);
			        condList1.add(map2);
			        Map<String, Object> map3=new HashMap<String, Object>();
			        map3.put("field", "_widget_1620808660482");//工序编码
			        map3.put("type", "text");
			        map3.put("method", "eq");
			        map3.put("value",list.get(i).get("_widget_1597480209790"));
			        condList1.add(map3);
			        Map<String, Object> map4=new HashMap<String, Object>();
			        map4.put("field", "_widget_1620809284026");//是否有此工序
			        map4.put("type", "text");
			        map4.put("method", "eq");
			        map4.put("value","是");
			        condList1.add(map4);
			        Map<String, Object> filter1 = new HashMap<String, Object>(){
			            {
			                put("rel", "and");
			                put("cond", condList1);
			            }
			        };
			        List<Map<String, Object>> list1= cd_api.getAllFormData(null, filter1);
			        
			        //当有多个值的时候取更新时间最近的一个
			        if(list1.size()>1){
			        	Collections.sort(list1, new Comparator<Map<String, Object>>() {
				            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				                Long longDate1 = DateUtils.getLongDate(o1.get("updateTime").toString()) ;
				                Long longDate2 = DateUtils.getLongDate(o2.get("updateTime").toString()) ; 
				                return longDate2.compareTo(longDate1);
				            }
				        });
			        }else if(list1.size()==0){
			        	wl.add(list.get(i).get("_widget_1597476026267"));
			        	continue;
			        }
			        Object gj=null;
			        int width=0;
			        //取出扫码报工中的规格型号
			        String ggxh= ObjectUtils.getString(list.get(i).get("_widget_1597476026316"));
			        //截取第二个*前面的内容
			        ggxh= ggxh.substring(0, ggxh.lastIndexOf('*'));
			        if(bzgg.get(ggxh) != null){
			        	width=Integer.valueOf(ggxh.substring(0, ggxh.indexOf('*')));
			        	if(width<=1500){
			        		gj=list1.get(0).get("_widget_1620809284451");
			        	}else{
			        		gj=list1.get(0).get("_widget_1620809975529");
			        	}
			        }else{
			        	width=Integer.valueOf(ggxh.substring(0, ggxh.indexOf('*')));
			        	if(width<=1500){
			        		gj=list1.get(0).get("_widget_1620809975551");
			        	}else{
			        		gj=list1.get(0).get("_widget_1620809975573");
			        	}
			        }
			        
			        Map<String, Object> rawData = new HashMap<String, Object>();
			        Map<String, Object> m1 = new HashMap<String, Object>();
					m1.put("value", gj);
					rawData.put("_widget_1605940938943", m1);
					smbg_api.updateForData(list.get(i).get("_id").toString(), rawData);
				}else{
					wl.add(list.get(i).get("_widget_1597476026267"));
				}
				
			}
	        robotMessage(wl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void robotMessage(Set<Object> wl){
		try {
			DingTalkClient client1 = new DefaultDingTalkClient("https://oapi.dingtalk.com/robot/send?access_token=8bff8e9197e962d2ae968b29cce27c6ac71b5e9a04def4f14cddb7ef67ae33f8");
			OapiRobotSendRequest request = new OapiRobotSendRequest();
			request.setMsgtype("text");
			OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
			text.setContent("以下物料工价同步异常：\n"+wl);
			request.setText(text);
			client1.execute(request);
		} catch (ApiException e) {
			e.printStackTrace();
		}
	}
}

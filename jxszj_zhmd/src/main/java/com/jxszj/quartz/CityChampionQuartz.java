package com.jxszj.quartz;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.request.OapiRobotSendRequest.Actioncard;
import com.jxszj.utils.DateUtils;
import com.jxszj.utils.JDYAPIUtils;
import com.jxszj.utils.ObjectUtils;

//MS和PD群的每日城市冠军排行榜
public class CityChampionQuartz {
	
	// 简道云
    private static final String  APPID = "5cc110c3b3c41744aaa12b2e";
    //DS2-门店实时销售提报
    private static final String ENTRYID_DS2 = "5d2d229eee266915714e9e1e";
    private static final String APIKEY = "uWEAujuxvzv5fMVifOvMRzlIJcPLgYkv";
    
    //DF1-销售收款单
    private static final String ENTRYID_DF1 = "5d3bc22704614439fd55e71d";
    
    
    //3-开店申请
    private static final String ENTRYID_KDSQ = "5d102d3f2144352834656205";
    
    public void execute(){
    	champion1();
    	champion2();
    	champion3();
    }
    
    //PD/MS睡眠顾问分享群
    public void champion1(){
    	try {
    		JDYAPIUtils api = new JDYAPIUtils(APPID, ENTRYID_DS2, APIKEY);
        	List<String> pp=new ArrayList<String>();
        	pp.add("MS");
        	pp.add("PD");
        	for (int j = 0; j < pp.size(); j++) {
        		String jqrUrl="";
        		if("MS".equals(pp.get(j))){
    	    		jqrUrl="https://oapi.dingtalk.com/robot/send?access_token=6033afd51915bcdab55afcb93067661e791908d343542bd5a6a3d2e69af18872";
    	    	}else if("PD".equals(pp.get(j))){
    	    		jqrUrl="https://oapi.dingtalk.com/robot/send?access_token=726ad5a3356c837f2bf1b0bd46b1f23f13ebb647ea75ea82e98dfe1ce69aedac";
    	    	}
        	
        		final List<Map<String, Object>> condList = new ArrayList<Map<String, Object>>();
        		Map<String, Object> map = new HashMap<String, Object>();
        		map.put("field", "_widget_1563246850783");//开单年月
        		map.put("type", "text");
        		map.put("method", "eq");
        		map.put("value", DateUtils.getNowDateToString(DateUtils.FORMAT_STRING1_MINUTE));
        		condList.add(map);
        		Map<String, Object> map1 = new HashMap<String, Object>();
        		map1.put("field", "_widget_1563246848536");
        		map1.put("type", "text");
        		map1.put("method", "eq");
        		map1.put("value", pp.get(j));
        		condList.add(map1);
        		Map<String, Object> map2 = new HashMap<String, Object>();
        		map2.put("field", "_widget_1563356837948");
        		map2.put("type", "text");
        		map2.put("method", "eq");
        		map2.put("value", "审批通过");
        		condList.add(map2);
        		Map<String, Object> filter = new HashMap<String, Object>() {
        			{
        				put("rel", "and");
        				put("cond", condList);
        			}
        		};
        		
                List<Map<String, Object>> formData = api.getAllFormData(null,filter);
                
                if(formData.size()==0){
                	continue;
                }
                
        		Set<String> A=new HashSet<String>();//A级城市门店
        		Set<String> B=new HashSet<String>();//B级城市门店
        		Set<String> C=new HashSet<String>();//C级城市门店
        		for (int i = 0; i < formData.size(); i++) {
        			if("A".equals(ObjectUtils.getString(formData.get(i).get("_widget_1563246848832")))){
        				A.add(ObjectUtils.getString(formData.get(i).get("_widget_1563246847853")));
        			}else if("B".equals(ObjectUtils.getString(formData.get(i).get("_widget_1563246848832")))){
        				B.add(ObjectUtils.getString(formData.get(i).get("_widget_1563246847853")));
        			}else if("C".equals(ObjectUtils.getString(formData.get(i).get("_widget_1563246848832")))){
        				C.add(ObjectUtils.getString(formData.get(i).get("_widget_1563246847853")));
        			}
        		}
        		Map<String,Double> map_a=new HashMap<String,Double>(); //A级城市中每个门店每月的累计订单金额
        		for (String a : A) {
        			double num=0.0;
        			for (int i = 0; i < formData.size(); i++) {
        				if(a.equals(ObjectUtils.getString(formData.get(i).get("_widget_1563246847853")))){
        					num+=ObjectUtils.getObjectToDouble(ObjectUtils.getString(formData.get(i).get("_widget_1563246851087")));
        				}
        			}
        			map_a.put(a, num);
        		}
        		
        		Map<String,Double> map_b=new HashMap<String,Double>(); //B级城市中每个门店每月的累计订单金额
        		for (String b : B) {
        			double num=0.0;
        			for (int i = 0; i < formData.size(); i++) {
        				if(b.equals(ObjectUtils.getString(formData.get(i).get("_widget_1563246847853")))){
        					num+=ObjectUtils.getObjectToDouble(ObjectUtils.getString(formData.get(i).get("_widget_1563246851087")));
        				}
        			}
        			map_b.put(b, num);
        		}
        		
        		Map<String,Double> map_c=new HashMap<String,Double>(); //C级城市中每个门店每月的累计订单金额
        		for (String c : C) {
        			double num=0.0;
        			for (int i = 0; i < formData.size(); i++) {
        				if(c.equals(ObjectUtils.getString(formData.get(i).get("_widget_1563246847853")))){
        					num+=ObjectUtils.getObjectToDouble(ObjectUtils.getString(formData.get(i).get("_widget_1563246851087")));
        				}
        			}
        			map_c.put(c, num);
        		}
        		 
        		List<Entry<String, Double>> list_a=mapSort(map_a);
        		List<Entry<String, Double>> list_b=mapSort(map_b);
        		List<Entry<String, Double>> list_c=mapSort(map_c);
        		DecimalFormat df = new DecimalFormat("###.####");
        		
        		
//        		String strA="\n";
//        		String strB="\n";
//        		String strC="\n";
//        		
//        		if(list_a.size()==1){
//        			strA="  \n	🥇1  "+list_a.get(0).getKey()+"("+df.format(list_a.get(0).getValue())+")";
//        		}else if(list_a.size()==2){
//        			strA="  \n	🥇1  "+list_a.get(0).getKey()+"("+df.format(list_a.get(0).getValue())+")  \n	🥈2  "+list_a.get(1).getKey()+"("+df.format(list_a.get(1).getValue())+")";
//        		}else if(list_a.size()==3){
//        			strA="  \n	🥇1  "+list_a.get(0).getKey()+"("+df.format(list_a.get(0).getValue())+")  \n	🥈2  "+list_a.get(1).getKey()+"("+df.format(list_a.get(1).getValue())+")  \n	🥉3  "+list_a.get(2).getKey()+"("+df.format(list_a.get(2).getValue())+")";
//        		}else if(list_a.size()==4){
//        			strA="  \n	🥇1  "+list_a.get(0).getKey()+"("+df.format(list_a.get(0).getValue())+")  \n	🥈2  "+list_a.get(1).getKey()+"("+df.format(list_a.get(1).getValue())+")  \n	🥉3  "+list_a.get(2).getKey()+"("+df.format(list_a.get(2).getValue())
//        			+")  \n	🏅第4名  "+list_a.get(3).getKey()+"  "+df.format(list_a.get(3).getValue());
//        		}else if(list_a.size()>=5){
//        			strA="  \n	🥇1  "+list_a.get(0).getKey()+"("+df.format(list_a.get(0).getValue())+")  \n	🥈2  "+list_a.get(1).getKey()+"("+df.format(list_a.get(1).getValue())+")  \n	🥉3  "+list_a.get(2).getKey()+"("+df.format(list_a.get(2).getValue())
//        			+")  \n	🏅4  "+list_a.get(3).getKey()+"("+df.format(list_a.get(3).getValue())
//        			+")  \n	🏅5  "+list_a.get(4).getKey()+"("+df.format(list_a.get(4).getValue())+")";
//        		}
//        		
//        		if(list_b.size()==1){
//        			strB="  \n	🥇1  "+list_b.get(0).getKey()+"("+df.format(list_b.get(0).getValue())+")";
//        		}else if(list_b.size()==2){
//        			strB="  \n	🥇1  "+list_b.get(0).getKey()+"("+df.format(list_b.get(0).getValue())+")  \n	🥈2  "+list_b.get(1).getKey()+"("+df.format(list_b.get(1).getValue())+")";
//        		}else if(list_b.size()==3){
//        			strB="  \n	🥇1  "+list_b.get(0).getKey()+"("+df.format(list_b.get(0).getValue())+" ) \n	🥈2  "+list_b.get(1).getKey()+"("+df.format(list_b.get(1).getValue())+")  \n	🥉3  "+list_b.get(2).getKey()+"("+df.format(list_b.get(2).getValue())+")";
//        		}else if(list_b.size()==4){
//        			strB="  \n	🥇1  "+list_b.get(0).getKey()+"("+df.format(list_b.get(0).getValue())+")  \n	🥈2  "+list_b.get(1).getKey()+"("+df.format(list_b.get(1).getValue())+")  \n	🥉3  "+list_b.get(2).getKey()+"("+df.format(list_b.get(2).getValue())
//        			+")  \n	🏅4  "+list_b.get(3).getKey()+"("+df.format(list_b.get(3).getValue())+")";
//        		}else if(list_b.size()>=5){
//        			strB="  \n	🥇1  "+list_b.get(0).getKey()+"("+df.format(list_b.get(0).getValue())+")  \n	🥈2  "+list_b.get(1).getKey()+"("+df.format(list_b.get(1).getValue())+")  \n	🥉3  "+list_b.get(2).getKey()+"("+df.format(list_b.get(2).getValue())
//        			+")  \n	🏅4  "+list_b.get(3).getKey()+"("+df.format(list_b.get(3).getValue())
//        			+")  \n	🏅5  "+list_b.get(4).getKey()+"("+df.format(list_b.get(4).getValue())+")";
//        		}
//        		
//        		if(list_c.size()==1){
//        			strC="  \n	🥇1  "+list_c.get(0).getKey()+"("+df.format(list_c.get(0).getValue())+")";
//        		}else if(list_c.size()==2){
//        			strC="  \n	🥇1  "+list_c.get(0).getKey()+"("+df.format(list_c.get(0).getValue())+")  \n	🥈2  "+list_c.get(1).getKey()+"("+df.format(list_c.get(1).getValue())+")";
//        		}else if(list_c.size()==3){
//        			strC="  \n	🥇1  "+list_c.get(0).getKey()+"("+df.format(list_c.get(0).getValue())+")  \n	🥈2  "+list_c.get(1).getKey()+"("+df.format(list_c.get(1).getValue())+")  \n	🥉3  "+list_c.get(2).getKey()+"("+df.format(list_c.get(2).getValue())+")";
//        		}else if(list_c.size()==4){
//        			strC="  \n	🥇1  "+list_c.get(0).getKey()+"("+df.format(list_c.get(0).getValue())+")  \n	🥈2  "+list_c.get(1).getKey()+"("+df.format(list_c.get(1).getValue())+")  \n	🥉3  "+list_c.get(2).getKey()+"("+df.format(list_c.get(2).getValue())
//        			+")  \n	🏅4  "+list_c.get(3).getKey()+"("+df.format(list_c.get(3).getValue())+")";
//        		}else if(list_c.size()>=5){
//        			strC="  \n	🥇1  "+list_c.get(0).getKey()+"("+df.format(list_c.get(0).getValue())+")  \n	🥈2  "+list_c.get(1).getKey()+"("+df.format(list_c.get(1).getValue())+")  \n	🥉3  "+list_c.get(2).getKey()+"("+df.format(list_c.get(2).getValue())
//        			+")  \n	🏅4  "+list_c.get(3).getKey()+"("+df.format(list_c.get(3).getValue())
//        			+")  \n	🏅5  "+list_c.get(4).getKey()+"("+df.format(list_c.get(4).getValue())+")";
//        		}
        		
        		
        		
        		StringBuilder strA=new StringBuilder("  \n	");
        		StringBuilder strB=new StringBuilder("  \n	");
        		StringBuilder strC=new StringBuilder("  \n	");
        		for (int i = 0; i < list_a.size(); i++) {
					if(i<=8){
						strA.append("NO. "+(i+1)+"      "+list_a.get(i).getKey().substring(2,list_a.get(i).getKey().length())+"("+list_a.get(i).getValue()+")  \n	");
					}else if(i==9){
						strA.append("NO."+(i+1)+"     "+list_a.get(i).getKey().substring(2,list_a.get(i).getKey().length())+"("+list_a.get(i).getValue()+")  \n	");
					}
				}
        		
        		for (int i = 0; i < list_b.size(); i++) {
        			if(i<=8){
        				strB.append("NO. "+(i+1)+"      "+list_b.get(i).getKey().substring(2, list_b.get(i).getKey().length())+"("+list_b.get(i).getValue()+")  \n	");
					}else if(i==9){
						strB.append("NO."+(i+1)+"     "+list_b.get(i).getKey().substring(2, list_b.get(i).getKey().length())+"("+list_b.get(i).getValue()+")  \n	");
					}
				}
        		
        		for (int i = 0; i < list_c.size(); i++) {
        			if(i<=8){
        				strC.append("NO. "+(i+1)+"      "+list_c.get(i).getKey().substring(2,list_c.get(i).getKey().length())+"("+list_c.get(i).getValue()+")  \n	");
					}else if(i==9){
						strC.append("NO."+(i+1)+"     "+list_c.get(i).getKey().substring(2,list_c.get(i).getKey().length())+"("+list_c.get(i).getValue()+")  \n	");
					}
				}
        		
        		String str="🏆**"+pp.get(j)+DateUtils.getMonth()+"月门店业绩冠军榜**🏆"
        				+ "  \n⭐⭐⭐**A级城市门店**⭐⭐⭐"+strA
        				+ "  \n⭐⭐⭐**B级城市门店**⭐⭐⭐"+strB
        				+ "  \n⭐⭐⭐**C级城市门店**⭐⭐⭐"+strC
        				+ "\n\n"
        				+ "  \n	[鼓掌][鼓掌][鼓掌]为第一名喝彩[鼓掌][鼓掌][鼓掌]"
        				+ "  \n	业绩冲冲，上榜、上榜  \n	业绩冲冲，上榜、上榜  \n	业绩冲冲，上榜、上榜";
        		DingTalkClient client = new DefaultDingTalkClient(jqrUrl);
        		OapiRobotSendRequest request = new OapiRobotSendRequest();
        		request.setMsgtype("actionCard");
        		Actioncard actioncard=new Actioncard();
        		actioncard.setTitle(pp.get(j)+DateUtils.getMonth()+"月门店业绩冠军榜");
        		String json="截至"+DateUtils.getNowDateToString(DateUtils.FORMAT_3_STRING)+"  \n	"+str;
        		actioncard.setText(json);
        		request.setActionCard(actioncard);
        		client.execute(request);
        		
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    
    //经销商群的排名,本年的收款金额
    public void champion2(){
    	try {
    		JDYAPIUtils api = new JDYAPIUtils(APPID, ENTRYID_DF1, APIKEY);
        	List<String> pp=new ArrayList<String>();
        	pp.add("MS");
        	pp.add("PD");
        	for (int j = 0; j < pp.size(); j++) {
        		String jqrUrl="";
        		if("MS".equals(pp.get(j))){
    	    		jqrUrl="https://oapi.dingtalk.com/robot/send?access_token=7b4f2efbf193c881c122fc360b17c64ecd9c3fef838f9910ba2edf369d3e83b0";
    	    	}else if("PD".equals(pp.get(j))){
    	    		jqrUrl="https://oapi.dingtalk.com/robot/send?access_token=bc41275a7ad9824e0a16cddc8caf93333f143d4ab836d4b13ebf270ef36e8575";
    	    	}
            	final List<Map<String, Object>> condList = new ArrayList<Map<String, Object>>();
        		Map<String, Object> map = new HashMap<String, Object>();
        		map.put("field", "_widget_1564272871299");
        		map.put("type", "text");
        		map.put("method", "eq");
        		map.put("value", DateUtils.getNowDateToString(DateUtils.FORMAT_STRING_YEAR));
        		condList.add(map);
        		Map<String, Object> map2 = new HashMap<String, Object>();
        		map2.put("field", "_widget_1593141119943");
        		map2.put("type", "text");
        		map2.put("method", "not_empty");
        		map2.put("value", "");
        		condList.add(map2);
        		Map<String, Object> map3 = new HashMap<String, Object>();
        		map3.put("field", "_widget_1548057662316");
        		map3.put("type", "text");
        		map3.put("method", "eq");
        		map3.put("value", pp.get(j));
        		condList.add(map3);
        		Map<String, Object> filter = new HashMap<String, Object>() {
        			{
        				put("rel", "and");
        				put("cond", condList);
        			}
        		};
        		
                List<Map<String, Object>> formData = api.getAllFormData(null,filter);
                
                if(formData.size()==0){
                	continue;
                }
                
        		Set<String> A=new HashSet<String>();//A级城市
        		Set<String> B=new HashSet<String>();//B级城市
        		Set<String> C=new HashSet<String>();//C级城市
        		for (int i = 0; i < formData.size(); i++) {
        			if("A".equals(ObjectUtils.getString(formData.get(i).get("_widget_1569114309696")))){
        				A.add(ObjectUtils.getString(formData.get(i).get("_widget_1548057662521")));
        			}else if("B".equals(ObjectUtils.getString(formData.get(i).get("_widget_1569114309696")))){
        				B.add(ObjectUtils.getString(formData.get(i).get("_widget_1548057662521")));
        			}else if("C".equals(ObjectUtils.getString(formData.get(i).get("_widget_1569114309696")))){
        				C.add(ObjectUtils.getString(formData.get(i).get("_widget_1548057662521")));
        			}
        		}
        		
        		Map<String,Double> map_a=new HashMap<String,Double>(); //A级城市中每个城市每月的累计日常返单金额
        		for (String a : A) {
        			double num=0.0;
        			for (int i = 0; i < formData.size(); i++) {
        				if(a.equals(ObjectUtils.getString(formData.get(i).get("_widget_1548057662521")))){
        					num+=ObjectUtils.getObjectToDouble(ObjectUtils.getString(formData.get(i).get("_widget_1564390193051")));
        				}
        			}
        			map_a.put(a, num);
        		}
        		
        		Map<String,Double> map_b=new HashMap<String,Double>(); //B级城市中每个城市每月的累计日常返单金额
        		for (String b : B) {
        			double num=0.0;
        			for (int i = 0; i < formData.size(); i++) {
        				if(b.equals(ObjectUtils.getString(formData.get(i).get("_widget_1548057662521")))){
        					num+=ObjectUtils.getObjectToDouble(ObjectUtils.getString(formData.get(i).get("_widget_1564390193051")));
        				}
        			}
        			map_b.put(b, num);
        		}
        		
        		Map<String,Double> map_c=new HashMap<String,Double>(); //C级城市中每个城市每月的累计日常返单金额
        		for (String c : C) {
        			double num=0.0;
        			for (int i = 0; i < formData.size(); i++) {
        				if(c.equals(ObjectUtils.getString(formData.get(i).get("_widget_1548057662521")))){
        					num+=ObjectUtils.getObjectToDouble(ObjectUtils.getString(formData.get(i).get("_widget_1564390193051")));
        				}
        			}
        			map_c.put(c, num);
        		}
        		
        		List<Entry<String, Double>> list_a=mapSort(map_a);
        		List<Entry<String, Double>> list_b=mapSort(map_b);
        		List<Entry<String, Double>> list_c=mapSort(map_c);
        		
        		
        		StringBuilder strA=new StringBuilder("  \n	");
        		StringBuilder strB=new StringBuilder("  \n	");
        		StringBuilder strC=new StringBuilder("  \n	");
        		for (int i = 0; i < list_a.size(); i++) {
					if(i<=8){
						strA.append("第 "+(i+1)+" 名          "+list_a.get(i).getKey()+"  \n	");
					}else if(i==9){
						strA.append("第"+(i+1)+"名          "+list_a.get(i).getKey()+"  \n	");
					}
				}
        		
        		for (int i = 0; i < list_b.size(); i++) {
        			if(i<=8){
        				strB.append("第 "+(i+1)+" 名          "+list_b.get(i).getKey()+"  \n	");
					}else if(i==9){
						strB.append("第"+(i+1)+"名         "+list_b.get(i).getKey()+"  \n	");
					}
				}
        		
        		for (int i = 0; i < list_c.size(); i++) {
        			if(i<=8){
        				strC.append("第 "+(i+1)+" 名          "+list_c.get(i).getKey()+"  \n	");
					}else if(i==9){
						strC.append("第"+(i+1)+"名          "+list_c.get(i).getKey()+"  \n	");
					}
				}
        		
        		String str="🏆**"+DateUtils.getNowDateToString(DateUtils.FORMAT_STRING_YEAR)+pp.get(j)+"下单业绩排行榜**🏆"
        				+ "  \n	             ⭐⭐⭐**A级城市**⭐⭐⭐          "+strA.toString()
        				+ "            ⭐⭐⭐**B级城市**⭐⭐⭐          "+strB.toString()
        				+ "            ⭐⭐⭐**C级城市**⭐⭐⭐          "+strC.toString()
        				+ "  \n	TOP前10（截至"+DateUtils.getNowDateToString(DateUtils.FORMAT_2_STRING)+")"
        				+ "  \n	[鼓掌][鼓掌][鼓掌]为第一名喝彩[鼓掌][鼓掌][鼓掌]"
        				+ "  \n	业绩冲冲，上榜、上榜  \n	业绩冲冲，上榜、上榜  \n	业绩冲冲，上榜、上榜";
        		DingTalkClient client = new DefaultDingTalkClient(jqrUrl);
        		OapiRobotSendRequest request = new OapiRobotSendRequest();
        		request.setMsgtype("actionCard");
        		Actioncard actioncard=new Actioncard();
        		actioncard.setTitle(pp.get(j)+DateUtils.getMonth()+"月回款业绩排行榜");
        		String json="截至"+DateUtils.getNowDateToString(DateUtils.FORMAT_3_STRING)+"  \n	"+str;
        		actioncard.setText(json);
        		request.setActionCard(actioncard);
        		client.execute(request);
        	}
        	
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    
    
    //经销商群的排名,本月的收款金额
    public void champion3(){
    	try {
    		//查询营业中的门店
    		JDYAPIUtils kdsq = new JDYAPIUtils(APPID, ENTRYID_KDSQ, APIKEY);
    		final List<Map<String, Object>> condList0 = new ArrayList<Map<String, Object>>();
    		Map<String, Object> map0 = new HashMap<String, Object>();
    		map0.put("field", "_widget_1560998925033");//运营状态
    		map0.put("type", "text");
    		map0.put("method", "eq");
    		map0.put("value", "营业中");
    		condList0.add(map0);
    		Map<String, Object> filter0 = new HashMap<String, Object>() {
    			{
    				put("rel", "and");
    				put("cond", condList0);
    			}
    		};
    		
            List<Map<String, Object>> formData0 = kdsq.getAllFormData(null,filter0);
    		
    		
    		JDYAPIUtils api = new JDYAPIUtils(APPID, ENTRYID_DF1, APIKEY);
        	List<String> pp=new ArrayList<String>();
        	pp.add("MS");
        	pp.add("PD");
        	for (int j = 0; j < pp.size(); j++) {
        		String jqrUrl="";
        		if("MS".equals(pp.get(j))){
    	    		jqrUrl="https://oapi.dingtalk.com/robot/send?access_token=6033afd51915bcdab55afcb93067661e791908d343542bd5a6a3d2e69af18872";
    	    	}else if("PD".equals(pp.get(j))){
    	    		jqrUrl="https://oapi.dingtalk.com/robot/send?access_token=726ad5a3356c837f2bf1b0bd46b1f23f13ebb647ea75ea82e98dfe1ce69aedac";
    	    	}
            	final List<Map<String, Object>> condList1 = new ArrayList<Map<String, Object>>();
        		Map<String, Object> map1 = new HashMap<String, Object>();
        		map1.put("field", "_widget_1548049038941");//收款年月
        		map1.put("type", "text");
        		map1.put("method", "eq");
        		map1.put("value", DateUtils.getNowDateToString(DateUtils.FORMAT_STRING1_MINUTE));
        		condList1.add(map1);
        		Map<String, Object> map2 = new HashMap<String, Object>();
        		map2.put("field", "_widget_1593141119943");//SAP的凭证号
        		map2.put("type", "text");
        		map2.put("method", "not_empty");
        		condList1.add(map2);
        		Map<String, Object> map3 = new HashMap<String, Object>();
        		map3.put("field", "_widget_1548057662316");
        		map3.put("type", "text");
        		map3.put("method", "eq");
        		map3.put("value", pp.get(j));
        		condList1.add(map3);
        		Map<String, Object> filter = new HashMap<String, Object>() {
        			{
        				put("rel", "and");
        				put("cond", condList1);
        			}
        		};
        		
                List<Map<String, Object>> formData = api.getAllFormData(null,filter);
                
                if(formData.size()==0){
                	continue;
                }
                
                
                //先获取收款的经销商
                Set<String> setKdsq=new HashSet<>();
                for (int i = 0; i < formData.size(); i++) {
                	setKdsq.add(formData.get(i).get("_widget_1548037673470").toString());
    			}
                //找出经销商所对应的门店数
                Map<String,Integer> mapKdsq=new HashMap<>();
                for(String jxs:setKdsq){
                	int num=0;
                	for (int i = 0; i < formData0.size(); i++) {
    					if(jxs.equals(formData0.get(i).get("_widget_1543818219429").toString())){
    						num++;
    					}
    				}
                	mapKdsq.put(jxs, num);
                }
                
                
        		Set<String> A=new HashSet<String>();//A级城市
        		Set<String> B=new HashSet<String>();//B级城市
        		Set<String> C=new HashSet<String>();//C级城市
        		for (int i = 0; i < formData.size(); i++) {
        			if("A".equals(ObjectUtils.getString(formData.get(i).get("_widget_1569114309696")))){
        				A.add(ObjectUtils.getString(formData.get(i).get("_widget_1548037673470")));
        			}else if("B".equals(ObjectUtils.getString(formData.get(i).get("_widget_1569114309696")))){
        				B.add(ObjectUtils.getString(formData.get(i).get("_widget_1548037673470")));
        			}else if("C".equals(ObjectUtils.getString(formData.get(i).get("_widget_1569114309696")))){
        				C.add(ObjectUtils.getString(formData.get(i).get("_widget_1548037673470")));
        			}
        		}
        		
        		DecimalFormat df = new DecimalFormat("##.##");
        		Map<String,Double> map_a=new HashMap<String,Double>(); //A级城市中每个经销商每月的累计日常返单金额
        		for (String a : A) {
        			double num=0.0;
        			for (int i = 0; i < formData.size(); i++) {
        				if(a.equals(ObjectUtils.getString(formData.get(i).get("_widget_1548037673470")))){
        					num+=ObjectUtils.getObjectToDouble(ObjectUtils.getString(formData.get(i).get("_widget_1564390193051")));
        				}
        			}
        			if(mapKdsq.get(a)==null || mapKdsq.get(a)==0){
        				continue;
        			}
        			map_a.put(a, Double.valueOf(df.format(num/mapKdsq.get(a))));
        		}
        		
        		Map<String,Double> map_b=new HashMap<String,Double>(); //B级城市中每个经销商每月的累计日常返单金额
        		for (String b : B) {
        			double num=0.0;
        			for (int i = 0; i < formData.size(); i++) {
        				if(b.equals(ObjectUtils.getString(formData.get(i).get("_widget_1548037673470")))){
        					num+=ObjectUtils.getObjectToDouble(ObjectUtils.getString(formData.get(i).get("_widget_1564390193051")));
        				}
        			}
        			if(mapKdsq.get(b)==null || mapKdsq.get(b)==0){
        				continue;
        			}
        			map_b.put(b, Double.valueOf(df.format(num/mapKdsq.get(b))));
        		}
        		
        		Map<String,Double> map_c=new HashMap<String,Double>(); //C级城市中每个经销商每月的累计日常返单金额
        		for (String c : C) {
        			double num=0.0;
        			for (int i = 0; i < formData.size(); i++) {
        				if(c.equals(ObjectUtils.getString(formData.get(i).get("_widget_1548037673470")))){
        					num+=ObjectUtils.getObjectToDouble(ObjectUtils.getString(formData.get(i).get("_widget_1564390193051")));
        				}
        			}
        			if(mapKdsq.get(c)==null || mapKdsq.get(c)==0){
        				continue;
        			}
        			map_c.put(c, Double.valueOf(df.format(num/mapKdsq.get(c))));
        		}
        		
        		//先对经销商排名
        		List<Entry<String, Double>> list_a=mapSort(map_a);
        		List<Entry<String, Double>> list_b=mapSort(map_b);
        		List<Entry<String, Double>> list_c=mapSort(map_c);
        		
        		
        		//
        		int mds_a=0;//A级城市门店数
        		List<String> jxs_a=new ArrayList<>();//A级城市的经销商
        		for (int i = 0; i < list_a.size(); i++) {
        			if(mds_a<10){
        				mds_a+=mapKdsq.get(list_a.get(i).getKey());
        				jxs_a.add(list_a.get(i).getKey());
        			}
				}
        		StringBuilder str_a=new StringBuilder("  \n	");
        		int num_a=1;
        		int index_a=0;
        		for (int i = 0; i < jxs_a.size(); i++) {
					for (int k = 0; k < formData0.size(); k++) {
						if(jxs_a.get(i).equals(formData0.get(k).get("_widget_1543818219429").toString())){
							String md=formData0.get(k).get("_widget_1543818219658").toString();
							if(num_a<10){
								str_a.append("NO."+num_a+"      "+md.substring(2, md.length())+"  \n	");
							}else{
								str_a.append("NO."+num_a+"    "+md.substring(2, md.length())+"  \n	");
							}
							index_a++;
						}
					}
					num_a=num_a+index_a;
					index_a=0;
				}
        		
        		int mds_b=0;//B级城市门店数
        		List<String> jxs_b=new ArrayList<>();//B级城市的经销商
        		for (int i = 0; i < list_b.size(); i++) {
        			if(mds_b<10){
        				mds_b+=mapKdsq.get(list_b.get(i).getKey());
        				jxs_b.add(list_b.get(i).getKey());
        			}
				}
        		StringBuilder str_b=new StringBuilder("  \n	");
        		int num_b=1;
        		int index_b=0;
        		for (int i = 0; i < jxs_b.size(); i++) {
					for (int k = 0; k < formData0.size(); k++) {
						if(jxs_b.get(i).equals(formData0.get(k).get("_widget_1543818219429").toString())){
							String md=formData0.get(k).get("_widget_1543818219658").toString();
							if(num_b<10){
								str_b.append("NO."+num_b+"      "+md.substring(2, md.length())+"  \n	");
							}else{
								str_b.append("NO."+num_b+"    "+md.substring(2, md.length())+"  \n	");
							}
							index_b++;
						}
					}
					num_b=num_b+index_b;
					index_b=0;
				}
        		
        		
        		
        		int mds_c=0;//C级城市门店数
        		List<String> jxs_c=new ArrayList<>();//C级城市的经销商
        		for (int i = 0; i < list_c.size(); i++) {
        			if(mds_c<10){
        				mds_c+=mapKdsq.get(list_c.get(i).getKey());
        				jxs_c.add(list_c.get(i).getKey());
        			}
				}
        		StringBuilder str_c=new StringBuilder("  \n	");
        		int num_c=1;
        		int index_c=0;
        		for (int i = 0; i < jxs_c.size(); i++) {
					for (int k = 0; k < formData0.size(); k++) {
						if(jxs_c.get(i).equals(formData0.get(k).get("_widget_1543818219429").toString())){
							String md=formData0.get(k).get("_widget_1543818219658").toString();
							if(num_c<10){
								str_c.append("NO."+num_c+"      "+md.substring(2, md.length())+"  \n	");
							}else{
								str_c.append("NO."+num_c+"    "+md.substring(2, md.length())+"  \n	");
							}
							index_c++;
						}
					}
					num_c=num_c+index_c;
					index_c=0;
				}
        		
//        		StringBuilder strA=new StringBuilder("  \n	");
//        		StringBuilder strB=new StringBuilder("  \n	");
//        		StringBuilder strC=new StringBuilder("  \n	");
//        		for (int i = 0; i < list_a.size(); i++) {
//					if(i<=8){
//						strA.append("第 "+(i+1)+" 名    "+list_a.get(i).getKey()+"  \n	");
//					}else if(i==9){
//						strA.append("第"+(i+1)+"名    "+list_a.get(i).getKey()+"  \n	");
//					}
//				}
//        		
//        		for (int i = 0; i < list_b.size(); i++) {
//        			if(i<=8){
//        				strB.append("第 "+(i+1)+" 名    "+list_b.get(i).getKey()+"  \n	");
//					}else if(i==9){
//						strB.append("第"+(i+1)+"名    "+list_b.get(i).getKey()+"  \n	");
//					}
//				}
//        		
//        		for (int i = 0; i < list_c.size(); i++) {
//        			if(i<=8){
//        				strC.append("第 "+(i+1)+" 名    "+list_c.get(i).getKey()+"  \n	");
//					}else if(i==9){
//						strC.append("第"+(i+1)+"名    "+list_c.get(i).getKey()+"  \n	");
//					}
//				}
//        		DingTalkClient client1 = new DefaultDingTalkClient(jqrUrl);
//        		OapiRobotSendRequest request = new OapiRobotSendRequest();
//        		request.setMsgtype("text");
//        		OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
//        		text.setContent("🏆"+DateUtils.getNowDateToString(DateUtils.FORMAT_STRING1_MINUTE)+pp.get(j)+"下单业绩排行榜🏆"
//        				+ "\n            A级城市经销商          "+strA.toString()
//        				+ "            B级城市经销商          "+strB.toString()
//        				+ "            C级城市经销商          "+strC.toString()
//        				+ "\nTOP前10（截至"+DateUtils.getNowDateToString(DateUtils.FORMAT_2_STRING)+")"
//        				+ "\n[鼓掌][鼓掌][鼓掌]为第一名喝彩[鼓掌][鼓掌][鼓掌]"
//        				+ "\n业绩冲冲，上榜、上榜\n业绩冲冲，上榜、上榜\n业绩冲冲，上榜、上榜");
//        		request.setText(text);
//        		client1.execute(request);
        		
        		String str="🏆**"+DateUtils.getNowDateToString(DateUtils.FORMAT_STRING1_MINUTE)+pp.get(j)+"回款业绩排行榜**🏆"
        				+ "  \n	                ⭐⭐⭐**A级城市门店**⭐⭐⭐          "+str_a.toString()
        				+ "            ⭐⭐⭐**B级城市门店**⭐⭐⭐          "+str_b.toString()
        				+ "            ⭐⭐⭐**C级城市门店**⭐⭐⭐          "+str_c.toString()
        				+ "  \n	"
        				+ "  \n	[鼓掌][鼓掌][鼓掌]为第一名喝彩[鼓掌][鼓掌][鼓掌]"
        				+ "  \n	业绩冲冲，上榜、上榜  \n	业绩冲冲，上榜、上榜  \n	业绩冲冲，上榜、上榜";
        		DingTalkClient client = new DefaultDingTalkClient(jqrUrl);
        		OapiRobotSendRequest request = new OapiRobotSendRequest();
        		request.setMsgtype("actionCard");
        		Actioncard actioncard=new Actioncard();
        		actioncard.setTitle(pp.get(j)+DateUtils.getMonth()+"月回款业绩排行榜");
        		String json="截至"+DateUtils.getNowDateToString(DateUtils.FORMAT_3_STRING)+"  \n	"+str;
        		actioncard.setText(json);
        		request.setActionCard(actioncard);
        		client.execute(request);
        	}
        	
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    
	public static List<Map.Entry<String,Double>> mapSort(Map<String,Double> map){
		//将map.entrySet()转换成list
        List<Map.Entry<String,Double>> list = new ArrayList<Map.Entry<String,Double>>(map.entrySet());
        //然后通过比较器来实现排序
        Collections.sort(list,new Comparator<Map.Entry<String,Double>>() {
            //升序排序
            public int compare(Entry<String, Double> o1,
                    Entry<String, Double> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
		return list;
	}
	
}

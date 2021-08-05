package com.jxszj.quartz;

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
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.request.OapiRobotSendRequest.Actioncard;
import com.dingtalk.api.request.OapiRobotSendRequest.Btns;
import com.jxszj.mapper.sap.SapScjhwlTbMapper;
import com.jxszj.pojo.sap.SapScjhwlTb;
import com.jxszj.pojo.sap.SapScjhwlTbExample;
import com.jxszj.pojo.sap.SapScjhwlTbExample.Criteria;
import com.jxszj.utils.DateUtils;
import com.jxszj.utils.JDYAPIUtils;

/**
 * 
 * <pre>
 * <b>Description:</b> 
 *    定时任务：生产交货物料凭证列表API
 * <b>Author:</b> yanwei
 * <b>Date:</b> 2021年3月16日上午10:19:32
 * </pre>
 */
@Service
public class ScjhwlQuartz {
	
	@Autowired
	private SapScjhwlTbMapper sapScjhwlTbMapper;
	
	// 简道云
	private static final  String APP_ID = "5c048a8379332d0978a68b8e";
	private static final  String ENTRY_ID = "60822f25aa402f000850a7d7";
	private static final  String API_KEY = "SGmAqjtntz3q5M0rZqm2b4nGlRpSlah6";
	JDYAPIUtils api = new JDYAPIUtils(APP_ID, ENTRY_ID, API_KEY);
	
    public void execute(){
    	JSONArray array=getJSONArray();
    	List<SapScjhwlTb> sapScjhwlTbs=new ArrayList<>();
    	for (int i = 0; i < array.size(); i++) {
    		SapScjhwlTb sapScjhwlTb=new SapScjhwlTb();
    		sapScjhwlTb.setGzrq(DateUtils.getLongToString(array.getJSONObject(i).getString("ActualGoodsMovementDate"),DateUtils.FORMAT_1_STRING));
    		sapScjhwlTb.setWlzbm(array.getJSONObject(i).getString("MaterialGroup"));
    		sapScjhwlTb.setWlzmc(array.getJSONObject(i).getString("MaterialGroupText"));
    		sapScjhwlTb.setSl(array.getJSONObject(i).getDouble("ActualDeliveryQuantity").intValue());
    		sapScjhwlTbs.add(sapScjhwlTb);
		}
    	if(sapScjhwlTbs.size()==0){
    		return;
    	}
    	sapScjhwlTbMapper.insertByBatch(sapScjhwlTbs);
    	//查询当天已交货的物料组
    	SapScjhwlTbExample example=new SapScjhwlTbExample();
    	Criteria Criteria=example.createCriteria();
    	Criteria.andGzrqEqualTo(sapScjhwlTbs.get(0).getGzrq());
    	sapScjhwlTbs=sapScjhwlTbMapper.selectByExample(example);
    	for (int j = 0; j < sapScjhwlTbs.size(); j++) {
    		addData(sapScjhwlTbs.get(j));//添加到简道云
		}
//    	Criteria.andWlzbmLike("3%");
//    	example.setOrderByClause("wlzbm ASC");
//    	sapScjhwlTbs=sapScjhwlTbMapper.selectByExample(example);
    	
    	//机器人播报
    	pushDDJQR(sapScjhwlTbs);
    	
    }
    
    public void addData(SapScjhwlTb sapScjhwlTb){
    	Map<String, Object>rawData = new HashMap<String, Object>();
		Map<String, Object> m1 = new HashMap<String, Object>();
		m1.put("value", sapScjhwlTb.getGzrq());
		rawData.put("_widget_1616576063843", m1);// 过账日期
		Map<String, Object> m2 = new HashMap<String, Object>();
		m2.put("value", sapScjhwlTb.getWlzmc());
		rawData.put("_widget_1616576063867", m2);// 物料组名称
		Map<String, Object> m3 = new HashMap<String, Object>();
		m3.put("value", sapScjhwlTb.getWlzbm());
		rawData.put("_widget_1617849191061", m3);// 物料组编码
		Map<String, Object> m4 = new HashMap<String, Object>();
		m4.put("value", sapScjhwlTb.getSl());
		rawData.put("_widget_1618224607833", m4);// 入库数量
		api.createCpData(rawData);
    }
    
    
    //钉钉机器人
    public void pushDDJQR(List<SapScjhwlTb> sapScjhwlTbs){
    	try {
//    		int max=0;
//    		for (SapScjhwlTb sapScjhwlTb:sapScjhwlTbs) {
//    			if(max<sapScjhwlTb.getWlzmc().replace("自制","").length()){
//    				max=sapScjhwlTb.getWlzmc().replace("自制","").length();
//    			}
//			}
    		Map<String,Integer> map=new HashMap<>();
    		for (int i = 0; i < sapScjhwlTbs.size(); i++) {
				map.put(sapScjhwlTbs.get(i).getWlzbm(), sapScjhwlTbs.get(i).getSl());
			}
    		
    		StringBuilder sb=new StringBuilder();
    		sb.append("  \n ------------------");
    		sb.append("  \n ==========*自制*==========");
    		sb.append("  \n ------------------");
			sb.append("  \n	床垫  \n	"+getStr(4,String.valueOf(map.get("311")!=null?map.get("311"):0))+getDays("311"));
			sb.append("  \n ------------------");
			sb.append("  \n	床架  \n	"+getStr(4,String.valueOf(map.get("312")!=null?map.get("312"):0))+getDays("312"));
			sb.append("  \n ------------------");
			sb.append("  \n	床头柜  \n	"+getStr(4,String.valueOf(map.get("313")!=null?map.get("313"):0))+getDays("313"));
			sb.append("  \n ------------------");
			sb.append("  \n	电动床  \n	"+getStr(4,String.valueOf(map.get("318")!=null?map.get("318"):0))+getDays("318"));
			sb.append("  \n ------------------");
			sb.append("  \n	沙发  \n	"+getStr(4,String.valueOf(map.get("317")!=null?map.get("317"):0))+getDays("317"));
			sb.append("  \n ------------------");
			sb.append("  \n	家具/道具  \n	"+getStr(4,String.valueOf(map.get("316")!=null?map.get("316"):0))+getDays("316"));
			sb.append("  \n ------------------");
			sb.append("  \n	枕头  \n	"+getStr(4,String.valueOf(map.get("314")!=null?map.get("314"):0))+getDays("314"));
			sb.append("  \n ------------------");
			sb.append("  \n	床品  \n	"+getStr(4,String.valueOf(map.get("315")!=null?map.get("315"):0))+getDays("315"));
			sb.append("  \n ------------------");
			sb.append("  \n =========*非自制*=========");
			sb.append("  \n ------------------");
			sb.append("  \n	国内外购床垫  \n	"+getStr(4,String.valueOf(map.get("411")!=null?map.get("411"):0))+getDays("411"));
			sb.append("  \n ------------------");
			sb.append("  \n	进口床垫  \n	"+getStr(4,String.valueOf(map.get("412")!=null?map.get("412"):0))+getDays("412"));
			sb.append("  \n ------------------");
			sb.append("  \n	进口床架  \n	"+getStr(4,String.valueOf(map.get("414")!=null?map.get("414"):0))+getDays("414"));
			sb.append("  \n ------------------");
			sb.append("  \n	饰品/道具/家具  \n	"+getStr(4,String.valueOf(map.get("419")!=null?map.get("419"):0))+getDays("419"));
			
			
    		List<String> urls=new ArrayList<>();
    		urls.add("https://oapi.dingtalk.com/robot/send?access_token=1fc6e3796fadda50d78c9264d9a92ae87292298ba364fced0c44d8dd91af014a");
    		urls.add("https://oapi.dingtalk.com/robot/send?access_token=5c15bdd890ab387f6663f6d4d62d40cff8a7a37a32a168c905b28b078c744090");
    		urls.add("https://oapi.dingtalk.com/robot/send?access_token=9ca7726af64f24d491871c444151f39b0cc16846e0aa7f13263824b2327f217e");
    		for (int i = 0; i < urls.size(); i++) {
            	DingTalkClient client = new DefaultDingTalkClient(urls.get(i));
        		OapiRobotSendRequest request = new OapiRobotSendRequest();
        		request.setMsgtype("actionCard");
        		Actioncard actioncard=new Actioncard();
        		actioncard.setTitle("当日发货信息");
        		String json="截至"+DateUtils.getNowDateToString(DateUtils.FORMAT_3_STRING)+"  \n **发货日报** \n "+sb.toString();
        		
        		actioncard.setText(json);
        		List<Btns> btns=new ArrayList<Btns>();
        		Btns btn1=new Btns();
        		btn1.setTitle("点击查看更多自制发货");
        		btn1.setActionURL("https://dingtalk.jiandaoyun.com/dashboard#/app/5c048a8379332d0978a68b8e/dash/608245c2a9e6fd00084ad4d8");
        		btns.add(btn1);
        		
        		actioncard.setBtns(btns);
        		actioncard.setBtnOrientation("0");
        		request.setActionCard(actioncard);
        		client.execute(request);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    
    public String getDays(String wlzbm){
    	SapScjhwlTbExample example=new SapScjhwlTbExample();
    	Criteria Criteria=example.createCriteria();
    	Criteria.andGzrqGreaterThanOrEqualTo(DateUtils.getStringToOneDay());
    	Criteria.andWlzbmEqualTo(wlzbm);
    	List<SapScjhwlTb> list=sapScjhwlTbMapper.selectByExample(example);
    	//当月每个物料的总交货数量
    	int total=0;
    	for(SapScjhwlTb sapScjhwlTb1:list){
    		total+=sapScjhwlTb1.getSl();
    	}
    	String str="";
    	if(list.size()!=0){
    		str="累"+getStr(5,String.valueOf(total))+"均"+(total/list.size());
    	}else{
    		str="累"+getStr(5,"0")+"均0";
    	}
    	return str;
    }
    
    public String getStr(int length,String str){
    	StringBuilder sb=new StringBuilder();
    	if(length>str.length()){
    		for (int i = 0; i < length-str.length(); i++) {
				sb.append("  ");
			}
    	}
    	return str+sb.toString()+"            ";
    }
    public JSONArray getJSONArray(){
    	JSONArray  array=null;
    	try {
    		//获取当天入库的物料
    		CloseableHttpClient httpClient = HttpClientBuilder.create().build();   
    		 HttpGet httpGet = new HttpGet("https://my300201-api.saps4hanacloud.cn/sap/opu/odata/sap/YY1_DELIVERYDOCUMENTSAPI_CDS/YY1_DeliveryDocumentsAPI?$filter=ActualGoodsMovementDate%20eq%20datetime'" + DateUtils.getNowDateToString() + "T00:00:00'");
			CloseableHttpResponse response = null;
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
				array = jsonObject.getJSONArray("results");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return array;
    }
    
}

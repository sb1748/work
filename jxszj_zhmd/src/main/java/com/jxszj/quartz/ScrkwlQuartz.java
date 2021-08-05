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
import com.jxszj.mapper.sap.SapScrkwlTbMapper;
import com.jxszj.mapper.sap.SapXsddpdscTbMapper;
import com.jxszj.pojo.sap.SapScrkwlTb;
import com.jxszj.pojo.sap.SapScrkwlTbExample;
import com.jxszj.pojo.sap.SapScrkwlTbExample.Criteria;
import com.jxszj.pojo.sap.SapXsddpdscTb;
import com.jxszj.pojo.sap.SapXsddpdscTbExample;
import com.jxszj.utils.DateUtils;
import com.jxszj.utils.JDYAPIUtils;

/**
 * 
 * <pre>
 * <b>Description:</b> 
 *    定时任务：生产入库物料凭证列表API
 * <b>Author:</b> yanwei
 * <b>Date:</b> 2021年3月16日上午10:19:32
 * </pre>
 */
@Service
public class ScrkwlQuartz {
	
	@Autowired
	private SapScrkwlTbMapper sapScrkwlTbMapper;
	
	@Autowired
	private SapXsddpdscTbMapper sapXsddpdscTbMapper;
	
	// 简道云
	private static final  String APP_ID = "5c048a8379332d0978a68b8e";
	private static final  String ENTRY_ID = "605afe3f1fe053000706ff47";
	private static final  String API_KEY = "SGmAqjtntz3q5M0rZqm2b4nGlRpSlah6";
	JDYAPIUtils api = new JDYAPIUtils(APP_ID, ENTRY_ID, API_KEY);
	

    public void execute(){
    	JSONArray array=getJSONArray();
    	List<SapScrkwlTb> sapScrkwlTbs=new ArrayList<>();
    	for (int i = 0; i < array.size(); i++) {
    		SapScrkwlTb sapScrkwlTb=new SapScrkwlTb();
    		sapScrkwlTb.setGzrq(DateUtils.getLongToString(array.getJSONObject(i).getString("PostingDate"),DateUtils.FORMAT_1_STRING));
    		sapScrkwlTb.setWlzbm(array.getJSONObject(i).getString("ProductGroup"));
    		sapScrkwlTb.setWlzmc(array.getJSONObject(i).getString("MaterialGroupName"));
    		sapScrkwlTb.setSl(array.getJSONObject(i).getDouble("QuantityInBaseUnit").intValue());
    		sapScrkwlTbs.add(sapScrkwlTb);
		}
    	if(sapScrkwlTbs.size()==0){
    		return;
    	}
    	sapScrkwlTbMapper.insertByBatch(sapScrkwlTbs);
    	//查询当天已入库的物料组
    	SapScrkwlTbExample example=new SapScrkwlTbExample();
    	Criteria Criteria=example.createCriteria();
    	Criteria.andGzrqEqualTo(sapScrkwlTbs.get(0).getGzrq());
    	sapScrkwlTbs=sapScrkwlTbMapper.selectByExample(example);
    	for (int i = 0; i < sapScrkwlTbs.size(); i++) {
    		addData(sapScrkwlTbs.get(i));//添加到简道云
		}
    	//只查询成品
    	Criteria.andWlzbmLike("3%");
    	example.setOrderByClause("wlzbm ASC");
    	sapScrkwlTbs=sapScrkwlTbMapper.selectByExample(example);
    	
    	//机器人播报
    	pushDDJQR(sapScrkwlTbs);
    	
    	//更新销售订单的生产排单
    	for (int i = 0; i < sapScrkwlTbs.size(); i++) {
    		//更新到排单生产
			if("311".equals(sapScrkwlTbs.get(i).getWlzbm())){
				updatePdsc(sapScrkwlTbs.get(0).getGzrq(),"311",sapScrkwlTbs.get(i).getSl());
			}else if("312".equals(sapScrkwlTbs.get(i).getWlzbm())){
				updatePdsc(sapScrkwlTbs.get(0).getGzrq(),"312",sapScrkwlTbs.get(i).getSl());
			}else if("313".equals(sapScrkwlTbs.get(i).getWlzbm())){
				updatePdsc(sapScrkwlTbs.get(0).getGzrq(),"313",sapScrkwlTbs.get(i).getSl());
			}
		}
    }
    
    public void updatePdsc(String gzrq,String wlzbm,int sl){
    	SapXsddpdscTbExample sapXsddpdscTbExample=new SapXsddpdscTbExample();
		SapXsddpdscTbExample.Criteria criteria =sapXsddpdscTbExample.createCriteria();
		criteria.andPdrqEqualTo(gzrq);
		criteria.andWlzEqualTo(wlzbm);
		List<SapXsddpdscTb> sapXsddpdscTb=sapXsddpdscTbMapper.selectByExample(sapXsddpdscTbExample);
		if(sapXsddpdscTb.size()==1){
			sapXsddpdscTb.get(0).setRksl(sl);
			sapXsddpdscTbMapper.updateByExample(sapXsddpdscTb.get(0), sapXsddpdscTbExample);
		}
    }
    
    public void addData(SapScrkwlTb sapScrkwlTb){
    	Map<String, Object>rawData = new HashMap<String, Object>();
		Map<String, Object> m1 = new HashMap<String, Object>();
		m1.put("value", sapScrkwlTb.getGzrq());
		rawData.put("_widget_1616576063843", m1);// 过账日期
		Map<String, Object> m2 = new HashMap<String, Object>();
		m2.put("value", sapScrkwlTb.getWlzmc());
		rawData.put("_widget_1616576063867", m2);// 物料组名称
		Map<String, Object> m3 = new HashMap<String, Object>();
		m3.put("value", sapScrkwlTb.getWlzbm());
		rawData.put("_widget_1617849191061", m3);// 物料组编码
		Map<String, Object> m4 = new HashMap<String, Object>();
		m4.put("value", sapScrkwlTb.getSl());
		rawData.put("_widget_1618224607833", m4);// 入库数量
		api.createCpData(rawData);
    }
    //钉钉机器人
    public void pushDDJQR(List<SapScrkwlTb> sapScrkwlTbs){
    	try {
    		int max=0;
    		for (SapScrkwlTb sapScrkwlTb:sapScrkwlTbs) {
    			if(max<sapScrkwlTb.getWlzmc().replace("自制","").length()){
    				max=sapScrkwlTb.getWlzmc().replace("自制","").length();
    			}
			}
    		StringBuilder sb=new StringBuilder();
    		for(SapScrkwlTb sapScrkwlTb:sapScrkwlTbs){
    			SapScrkwlTbExample example=new SapScrkwlTbExample();
    			Criteria Criteria=example.createCriteria();
    	    	Criteria.andGzrqGreaterThanOrEqualTo(DateUtils.getStringToOneDay());
    	    	Criteria.andWlzbmEqualTo(sapScrkwlTb.getWlzbm());
    	    	List<SapScrkwlTb> list=sapScrkwlTbMapper.selectByExample(example);
    	    	//当月每个物料的总入库数量
    	    	int total=0;
    	    	for(SapScrkwlTb sapScrkwlTb1:list){
    	    		total+=sapScrkwlTb1.getSl();
    	    	}
    	    	sb.append("  \n ------------------");
    			sb.append("  \n	"+sapScrkwlTb.getWlzmc().replace("自制","")+"  \n	"+getStr(4,String.valueOf(sapScrkwlTb.getSl()))+"累"+getStr(5,String.valueOf(total))+"均"+(list.size()!=0?total/list.size():0));
    		}
    		List<String> urls=new ArrayList<>();
    		urls.add("https://oapi.dingtalk.com/robot/send?access_token=1fc6e3796fadda50d78c9264d9a92ae87292298ba364fced0c44d8dd91af014a");
    		urls.add("https://oapi.dingtalk.com/robot/send?access_token=5c15bdd890ab387f6663f6d4d62d40cff8a7a37a32a168c905b28b078c744090");
    		urls.add("https://oapi.dingtalk.com/robot/send?access_token=9ca7726af64f24d491871c444151f39b0cc16846e0aa7f13263824b2327f217e");
    		for (int i = 0; i < urls.size(); i++) {
            	DingTalkClient client = new DefaultDingTalkClient(urls.get(i));
        		OapiRobotSendRequest request = new OapiRobotSendRequest();
        		request.setMsgtype("actionCard");
        		Actioncard actioncard=new Actioncard();
        		actioncard.setTitle("当日入库信息");
        		String json="截至"+DateUtils.getNowDateToString(DateUtils.FORMAT_3_STRING)+"  \n **自制入库日报**  \n "+sb.toString();
        		
        		actioncard.setText(json);
        		List<Btns> btns=new ArrayList<Btns>();
        		Btns btn1=new Btns();
        		btn1.setTitle("点击查看更多自制入库");
        		btn1.setActionURL("https://dingtalk.jiandaoyun.com/dashboard#/app/5c048a8379332d0978a68b8e/dash/607421a51dbfc00008a0930b");
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
    		 HttpGet httpGet = new HttpGet("https://my300201-api.saps4hanacloud.cn/sap/opu/odata/sap/YY1_MANUFMADOCITEMLISTAPI_CDS/YY1_ManuFMaDocItemListAPI?$filter=PostingDate%20eq%20datetime'" + DateUtils.getNowDateToString() + "T00:00:00'");
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

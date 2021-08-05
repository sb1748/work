package com.jxszj.quartz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.request.OapiRobotSendRequest.Actioncard;
import com.dingtalk.api.request.OapiRobotSendRequest.Btns;
import com.jxszj.mapper.sap.SapJdrbTbMapper;
import com.jxszj.pojo.sap.SapJdrbTb;
import com.jxszj.pojo.sap.SapJdrbTbExample;
import com.jxszj.pojo.sap.SapJdrbTbExample.Criteria;
import com.jxszj.utils.DateUtils;

/**
 * 
 * <pre>
 * <b>Description:</b> 
 *    定时任务：接单日报
 * <b>Author:</b> yanwei
 * <b>Date:</b> 2021年3月16日上午10:19:32
 * </pre>
 */
@Service
public class JdrbQuartz {
	
	@Autowired
	private SapJdrbTbMapper sapJdrbTbMapper;
	
    public void execute(){
    	try {
            
            SapJdrbTbExample example=new SapJdrbTbExample();
            Criteria criteria=example.createCriteria();
            criteria.andJdrqEqualTo(DateUtils.getNowDateToString());
            List<SapJdrbTb> sapJdrbTbs=sapJdrbTbMapper.selectByExample(example);
        	
    		Map<String,Integer> map=new HashMap<>();
    		for (int i = 0; i < sapJdrbTbs.size(); i++) {
				map.put(sapJdrbTbs.get(i).getWlzbm(), sapJdrbTbs.get(i).getSl());
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
        		actioncard.setTitle("当日接单信息");
        		String json="截至"+DateUtils.getNowDateToString(DateUtils.FORMAT_3_STRING)+"  \n **订单接单日报** \n "+sb.toString();
        		
        		actioncard.setText(json);
        		List<Btns> btns=new ArrayList<Btns>();
        		Btns btn1=new Btns();
        		btn1.setTitle("点击查看更多接单信息");
        		btn1.setActionURL("https://dingtalk.jiandaoyun.com/dashboard#/app/5c048a8379332d0978a68b8e/dash/609911436779b50007e2af8a");
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
    	SapJdrbTbExample example=new SapJdrbTbExample();
        Criteria criteria=example.createCriteria();
        criteria.andJdrqGreaterThanOrEqualTo(DateUtils.getStringToOneDay());
        criteria.andWlzbmEqualTo(wlzbm);
    	List<SapJdrbTb> list=sapJdrbTbMapper.selectByExample(example);
    	//当月每个物料的总交货数量
    	int total=0;
    	for(SapJdrbTb SapJdrbTb:list){
    		total+=SapJdrbTb.getSl();
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
    
}



import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.jxszj.utils.JDYAPIUtils;

import kingdee.bos.webapi.client.K3CloudApiClient;
//刷新DM1中的数据
public class DM1 {

    // 简道云      DJ4-开店申请（门店信息）   	
 	private static final String DJ4_APPID = "5cc110c3b3c41744aaa12b2e";
 	private static final String DJ4_ENTRYID = "5d102d3f2144352834656205";
 	
    // 简道云      DM1-销售提成_(自动)   	
 	private static final String DM1_APPID = "5cc110c3b3c41744aaa12b2e";
 	private static final String DM1_ENTRYID = "5d8883aa1b7fa5166a4c255d";
 	
    // 简道云      DF1-销售收款单   	
 	private static final String DF1_APPID = "5cc110c3b3c41744aaa12b2e";
 	private static final String DF1_ENTRYID = "5d3bc22704614439fd55e71d";
 	
 	private static final String APIKEY = "uWEAujuxvzv5fMVifOvMRzlIJcPLgYkv";
    
	public static void main(String[] args) {
		
		final List<Map<String, Object>> condList1 = new ArrayList<Map<String, Object>>();
		String startDate="2020-07-01 00:00:00";
        String endDate="2020-07-31 23:59:59";
        String[] str=new String[2];
        str[0]=startDate;
        str[1]=endDate;
		Map<String, Object> map1=new HashMap<String, Object>();
		map1=new HashMap<String, Object>();
		map1.put("field", "createTime");
		map1.put("type", "text");
		map1.put("method", "range");
		map1.put("value",str);
		condList1.add(map1);
		Map<String, Object> filter = new HashMap<String, Object>(){
            {
                put("rel", "and");
                put("cond", condList1);
            }
        };
        JDYAPIUtils api = new JDYAPIUtils(DF1_APPID, DF1_ENTRYID, APIKEY);
        List<Map<String, Object>> xsskd = api.getAllFormData(null,filter);
        System.out.println(xsskd.size());
        for (int i = 0; i < xsskd.size(); i++) {
        	if("审批通过".equals(xsskd.get(i).get("_widget_1548037675919")) && "1".equals(xsskd.get(i).get("flowState")+"")){
    			//获取DJ4中的门店计数、经销商门店回款月度目标，并对其进行各种汇总
     			final List<Map<String, Object>> condList = new ArrayList<Map<String, Object>>();
   				map1=new HashMap<String, Object>();
   				map1.put("field", "_widget_1564361880783");
   				map1.put("type", "text");
   				map1.put("method", "eq");
   				map1.put("value",xsskd.get(i).get("_widget_1548037673508")+"审批通过"+"营业中"+"启用");
   		        condList.add(map1);
   		        filter = new HashMap<String, Object>(){
   		            {
   		                put("rel", "and");
   		                put("cond", condList);
   		            }
   		        };
   		        api = new JDYAPIUtils(DJ4_APPID, DJ4_ENTRYID, APIKEY);
   		        List<Map<String, Object>> kdsq = api.getAllFormData(null,filter);
   		        int mds=0;
   		        double mb=0.0;
   		        for(int j=0;j<kdsq.size();j++) {
   					mds+=Integer.valueOf(kdsq.get(j).get("_widget_1561097948495").toString());
   					mb+=Double.valueOf(kdsq.get(j).get("_widget_1564232147813").toString());
   				}
   		        
   		        //获取DF1中的收款金额、日常返单金额，并对其进行各种汇总
   		        final List<Map<String, Object>> condList2 = new ArrayList<Map<String, Object>>();
 				map1=new HashMap<String, Object>();
 				map1.put("field", "_widget_1565060809315");
 				map1.put("type", "text");
 				map1.put("method", "eq");
 				map1.put("value",xsskd.get(i).get("_widget_1565060809315"));
 				condList2.add(map1);
 		        filter = new HashMap<String, Object>(){
 		            {
 		                put("rel", "and");
 		                put("cond", condList2);
 		            }
 		        };
 		        api = new JDYAPIUtils(DF1_APPID, DF1_ENTRYID, APIKEY);
 		        List<Map<String, Object>> xsskds = api.getAllFormData(null,filter);
 		        Double skje=0.0;
 		        Double rcfdje=0.0;
 		        for(int j=0;j<xsskds.size();j++) {
 		        	skje+=Double.valueOf(xsskds.get(j).get("_widget_1548037675480").toString());
 		        	rcfdje+=Double.valueOf(xsskds.get(j).get("_widget_1564390193051").toString());
 				}
     			  
     			  
     			Map<String, Object> rawData = new HashMap<String, Object>();
     			Map<String, Object> m1 = new HashMap<String, Object>();
     			m1.put("value","审批通过");
     			rawData.put("_widget_1565054282382", m1);// 审批状态
     			Map<String, Object> m2 = new HashMap<String, Object>();
     			m2.put("value","营业中");
     			rawData.put("_widget_1565054282475", m2);// 营业状态
     			Map<String, Object> m3 = new HashMap<String, Object>();
     			m3.put("value","启用");
     			rawData.put("_widget_1565054280172", m3);// 使用标识
     			Map<String, Object> m4 = new HashMap<String, Object>();
     			m4.put("value",xsskd.get(i).get("_widget_1548037673508")+"审批通过"+"营业中"+"启用");
     			rawData.put("_widget_1565054282503", m4);// 门店所属经销商标识
     			Map<String, Object> m5 = new HashMap<String, Object>();
     			m5.put("value",xsskd.get(i).get("_widget_1565060809315"));
     			rawData.put("_widget_1565054283205", m5);// 【月累计收款取数标识】公司编码+品牌编码+客户编码+审批状态+年月
     			Map<String, Object> m6 = new HashMap<String, Object>();
     			m6.put("value",xsskd.get(i).get("_widget_1548037673508"));
     			rawData.put("_widget_1565054280890", m6);// 经销商编码
     			Map<String, Object> m7 = new HashMap<String, Object>();
     			m7.put("value",xsskd.get(i).get("_widget_1548037673470"));
     			rawData.put("_widget_1564996674855", m7);// 经销商
     			Map<String, Object> m8 = new HashMap<String, Object>();
     			m8.put("value",xsskd.get(i).get("_widget_1548057662333"));
     			rawData.put("_widget_1565054280408", m8);// 品牌
     			Map<String, Object> m9 = new HashMap<String, Object>();
     			m9.put("value",xsskd.get(i).get("_widget_1548057662316"));
     			rawData.put("_widget_1565054280535", m9);// 品牌编码
     			Map<String, Object> m10 = new HashMap<String, Object>();
     			m10.put("value",xsskd.get(i).get("_widget_1548057663778"));
     			rawData.put("_widget_1565054283269", m10);// 公司
     			Map<String, Object> m11 = new HashMap<String, Object>();
     			m11.put("value",xsskd.get(i).get("_widget_1548037673908"));
     			rawData.put("_widget_1565054283284", m11);// 公司编码
     			Map<String, Object> m12 = new HashMap<String, Object>();
     			m12.put("value",xsskd.get(i).get("_widget_1567042903337"));
     			rawData.put("_widget_1567042226458", m12);// 业务
     			Map<String, Object> m13 = new HashMap<String, Object>();
     			m13.put("value",xsskd.get(i).get("_widget_1548319045972"));
     			rawData.put("_widget_1564996674896", m13);// 督导
     			Map<String, Object> m14 = new HashMap<String, Object>();
     			m14.put("value",xsskd.get(i).get("_widget_1564272870818"));
     			rawData.put("_widget_1565054281513", m14);// 跟单
     			Map<String, Object> m15 = new HashMap<String, Object>();
     			m15.put("value",xsskd.get(i).get("_widget_1548037674843"));
     			rawData.put("_widget_1565054281209", m15);// 日期
     			Map<String, Object> m16 = new HashMap<String, Object>();
     			m16.put("value",xsskd.get(i).get("_widget_1564272871299"));
     			rawData.put("_widget_1565054282027", m16);// 年
     			Map<String, Object> m17 = new HashMap<String, Object>();
     			m17.put("value",xsskd.get(i).get("_widget_1548049038941"));
     			rawData.put("_widget_1564996675064", m17);// 年月
     			Map<String, Object> m18 = new HashMap<String, Object>();
     			m18.put("value",xsskd.get(i).get("_widget_1564272871532"));
     			rawData.put("_widget_1565054281937", m18);// 年月日
     			Map<String, Object> m19 = new HashMap<String, Object>();
     			m19.put("value",mds);
     			rawData.put("_widget_1565166083759", m19);// 门店数
     			Map<String, Object> m20 = new HashMap<String, Object>();
     			m20.put("value",mb);
     			rawData.put("_widget_1565166083774", m20);// 目标
     			Map<String, Object> m21 = new HashMap<String, Object>();
     			m21.put("value",skje);
     			rawData.put("_widget_1565166084025", m21);// 回款
     			Map<String, Object> m22 = new HashMap<String, Object>();
     			if(mb==0){
     				m22.put("value",0.00);
     			}else{
     				DecimalFormat df = new DecimalFormat("#.0000");
     				m22.put("value",df.format(skje/mb));
     			}
     			rawData.put("_widget_1564996675036", m22);// 回款率
     			Map<String, Object> m23 = new HashMap<String, Object>();
     			m23.put("value",rcfdje);
     			rawData.put("_widget_1565166084168", m23);// 回款(日常返单)
     			Map<String, Object> m24 = new HashMap<String, Object>();
     			
     			double djfdhk=0.00;
     			if(mds!=0){
     				DecimalFormat df = new DecimalFormat("#.00");
     				djfdhk=Double.valueOf(df.format(rcfdje/mds));
     			}
     			m24.put("value",djfdhk);
     			rawData.put("_widget_1565065411232", m24);// 店均返单回款
     			Map<String, Object> m25 = new HashMap<String, Object>();
     			if(djfdhk<50000){
     				m25.put("value",0);
     			}else if(djfdhk>=50000 && djfdhk<70000){
     				m25.put("value",djfdhk*0.005*mds);
     			}else if(djfdhk>=70000 && djfdhk<100000){
     				m25.put("value",djfdhk*0.01*mds);
     			}else if(djfdhk>=100000){
     				m25.put("value",djfdhk*0.02*mds);
     			}
     			rawData.put("_widget_1564996675255", m25);// (督导)销售提成
     			Map<String, Object> m26 = new HashMap<String, Object>();
     			if("HMW".equals(xsskd.get(i).get("_widget_1548057662316"))){
     				m26.put("value",skje*0.003);
     			}else if("MS".equals(xsskd.get(i).get("_widget_1548057662316"))){
     				m26.put("value",skje*0.003);
     			}else if("PD".equals(xsskd.get(i).get("_widget_1548057662316"))){
     				m26.put("value",skje*0.003);
     			}else if("DUX".equals(xsskd.get(i).get("_widget_1548057662316"))){
     				m26.put("value",skje*0.0015);
     			}else if("NB".equals(xsskd.get(i).get("_widget_1548057662316"))){
     				m26.put("value",skje*0.003);
     			}
     			rawData.put("_widget_1565074205311", m26);// (跟单)销售提成
     			Map<String, Object> m27 = new HashMap<String, Object>();
     			m27.put("value",xsskd.get(i).get("updateTime"));
     			rawData.put("_widget_1565074204963", m27);// 操作更新时间
     			Map<String, Object> m28 = new HashMap<String, Object>();
     			m28.put("value",xsskd.get(i).get("_widget_1569114309696"));
     			rawData.put("_widget_1573095139039", m28);// 城市等级
     			
     			//获取DM1-销售提成_(自动)中是否存在要添加的数据
   		        final List<Map<String, Object>> condList3 = new ArrayList<Map<String, Object>>();
 				map1=new HashMap<String, Object>();
 				map1.put("field", "_widget_1565054283205");
 				map1.put("type", "text");
 				map1.put("method", "eq");
 				map1.put("value",xsskd.get(i).get("_widget_1565060809315"));
 				condList3.add(map1);
 		        filter = new HashMap<String, Object>(){
 		            {
 		                put("rel", "and");
 		                put("cond", condList3);
 		            }
 		        };
 		       api = new JDYAPIUtils(DM1_APPID, DM1_ENTRYID, APIKEY);
 		       List<Map<String, Object>> df1 = api.getAllFormData(null,filter);
 		       if(df1.size()==0){
 		    	  api.createData(rawData);// 向简道云添加DM1-销售提成_(自动)
 		       }else if(df1.size()>0){
 		    	  api.updateData(df1.get(0).get("_id").toString(), rawData);// 向简道云更新DM1-销售提成_(自动)
 		       }
 		      
    		 }
		}

	}

}

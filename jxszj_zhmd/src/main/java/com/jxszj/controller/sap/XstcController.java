package com.jxszj.controller.sap;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jxszj.pojo.sap.YdmbTb;
import com.jxszj.service.sap.IYdmbService;
import com.jxszj.utils.DateUtils;
import com.jxszj.utils.JDYAPIUtils;
import com.jxszj.utils.MessageResult;
import com.jxszj.utils.ObjectUtils;

/**
 * 
 * <pre>
 * <b>.</b>
 * <b>Description:</b> 
 *    DM1-销售提成_(自动)
 * <b>Author:</b> yanwei
 * <b>Date:</b> 2020年1月10日上午11:40:15
 * </pre>
 */
@Controller
@RequestMapping("/jxszj/xstc")
public class XstcController {

	// 简道云 DJ4-开店申请（门店信息）
	private static final String DJ4_ENTRYID = "5d102d3f2144352834656205";
	
	// 简道云DJ1-开户申请（经销商信息）
	private static final String DJ1_ENTRYID = "5d102d3721443528346561db";

	// 简道云 DM1-销售提成_(自动)
	private static final String DM1_ENTRYID = "5d8883aa1b7fa5166a4c255d";
	
    // 简道云      DF1-销售收款单   	
 	private static final String DF1_ENTRYID = "5d3bc22704614439fd55e71d";
 	
 	 // 简道云      品牌年度目标维护
 	private static final String PDND_ENTRYID = "60419107f4cb6a0007d2d389";
 	
	 // 简道云      督导年度目标维护
	private static final String DDND_ENTRYID = "60418c3a693be10008e9c061";
	
	 // 简道云      年度目标维护
	private static final String ND_ENTRYID = "6042e1e0d75cf1000701dcc5";

	 // 简道云      维护新零售月度目标
	private static final String XLS_ENTRYID = "601376d61b40030008166808";
	
 	private static final String APPID = "5cc110c3b3c41744aaa12b2e";
	private static final String APIKEY = "uWEAujuxvzv5fMVifOvMRzlIJcPLgYkv";
	
	@Autowired
	private IYdmbService ydmbService;
	
	/**
	 * 
	 *<pre>
	 *<b>.</b>
	 *<b>Description:</b> 
	 *    将 DJ4-开店申请（门店信息）同步到 DM1-销售提成_(自动)
	 *<b>Author:</b> yanwei
	 *<b>Date:</b> 2020年1月10日 下午12:05:13
	 *@return
	 *</pre>
	 */
	@RequestMapping("/kdsqSendXstc")
	@ResponseBody
	public MessageResult kdsqSendXstc(){
		List<YdmbTb> ydmbs=ydmbService.getYdmbs();
		Map<String,Double> ydmbMap=new HashMap<>();
		for (int i = 0; i < ydmbs.size(); i++) {
			ydmbMap.put(ydmbs.get(i).getJxsbm(), ydmbs.get(i).getBymbhk());
		}
		
		JDYAPIUtils api = new JDYAPIUtils(APPID, DJ4_ENTRYID, APIKEY);
		List<Map<String, Object>> kdsq = api.getAllFormData(null, null);
		//获取年度目标
		JDYAPIUtils nd_api = new JDYAPIUtils(APPID, ND_ENTRYID, APIKEY);
		List<Map<String, Object>> ndmb = nd_api.getAllFormData(null, null);
		
		//查询本月PD和MS新零售目标
		final List<Map<String, Object>> condList = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("field", "_widget_1611888343351");
		map.put("type", "text");
		map.put("method", "eq");
		map.put("value", DateUtils.getNowDateToString("yyyy年M月"));
		condList.add(map);
		Map<String, Object> filter = new HashMap<String, Object>() {
			{
				put("rel", "and");
				put("cond", condList);
			}
		};
		JDYAPIUtils xls_api = new JDYAPIUtils(APPID, XLS_ENTRYID, APIKEY);
		List<Map<String, Object>> xlsmb = xls_api.getAllFormData(null, filter);
		Map<String,Double> xlsMap=new HashMap<>();
		for (int i = 0; i < xlsmb.size(); i++) {
			xlsMap.put(xlsmb.get(i).get("_widget_1611888343332").toString(),Double.valueOf(xlsmb.get(i).get("_widget_1611888343370").toString()));
		}
		
		//查询品牌年度目标
		api = new JDYAPIUtils(APPID, PDND_ENTRYID, APIKEY);
		List<Map<String, Object>> pdndmb = api.getAllFormData(null, null);
		Map<String,Object> ppMap=new HashMap<>();
		for (int i = 0; i < pdndmb.size(); i++) {
			ppMap.put(pdndmb.get(i).get("_widget_1614909850145").toString(), pdndmb.get(i).get("_widget_1614908475285"));
		}
		//查询督导年度目标
		api = new JDYAPIUtils(APPID, DDND_ENTRYID, APIKEY);
		List<Map<String, Object>> ddndmb = api.getAllFormData(null, null);
		Map<String,Object> ddMap=new HashMap<>();
		for (int i = 0; i < ddndmb.size(); i++) {
			ddMap.put(ddndmb.get(i).get("_widget_1614908474837").toString(), ddndmb.get(i).get("_widget_1614908475285"));
		}
		
		//先删除当月的销售提成
		JDYAPIUtils dm1_api = new JDYAPIUtils(APPID, DM1_ENTRYID, APIKEY);
		final List<Map<String, Object>> condList2 = new ArrayList<Map<String, Object>>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("field", "_widget_1564996675064");
		map2.put("type", "text");
		map2.put("method", "eq");
		map2.put("value", DateUtils.getNowDateToString("yyyy年M月"));
		condList2.add(map2);
		Map<String, Object> filter2 = new HashMap<String, Object>() {
			{
				put("rel", "and");
				put("cond", condList2);
			}
		};
		List<Map<String, Object>> dm1 = dm1_api.getAllFormData(null, filter2);
		for (int i = 0; i < dm1.size(); i++) {
			dm1_api.deleteData(dm1.get(i).get("_id").toString());
		}
		
		
		final List<Map<String, Object>> condList3 = new ArrayList<Map<String, Object>>();
		Map<String, Object> map3 = new HashMap<String, Object>();
		map3.put("field", "_widget_1548316175994");
		map3.put("type", "text");
		map3.put("method", "eq");
		map3.put("value", "启用");
		condList3.add(map3);
		Map<String, Object> map4 = new HashMap<String, Object>();
		map4.put("field", "_widget_1560850883479");
		map4.put("type", "text");
		map4.put("method", "eq");
		map4.put("value", "审批通过");
		condList3.add(map4);
		Map<String, Object> filter3 = new HashMap<String, Object>() {
			{
				put("rel", "and");
				put("cond", condList3);
			}
		};
		JDYAPIUtils dj1_api = new JDYAPIUtils(APPID, DJ1_ENTRYID, APIKEY);
		List<Map<String, Object>> dj1 = dj1_api.getAllFormData(null, filter3);
		
		int mds = 0;
		for (int i = 0; i < dj1.size(); i++) {
			for (int j = 0; j <kdsq.size(); j++) {
				if(dj1.get(i).get("_widget_1543814801964").toString().equals(kdsq.get(j).get("_widget_1543981212621").toString()) && "营业中".equals(kdsq.get(j).get("_widget_1560998925033").toString())){
					mds+=Integer.valueOf(kdsq.get(j).get("_widget_1561097948495").toString());
				}
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
 			m4.put("value",dj1.get(i).get("_widget_1543814801964")+"审批通过"+"营业中"+"启用");
 			rawData.put("_widget_1565054282503", m4);// 门店所属经销商标识
 			Map<String, Object> m5 = new HashMap<String, Object>();
 			m5.put("value",dj1.get(i).get("_widget_1561617485309").toString()+dj1.get(i).get("_widget_1545377155914").toString()+dj1.get(i).get("_widget_1543814801964")+"审批通过"+DateUtils.getNowDateToString("yyyy年M月"));
 			rawData.put("_widget_1565054283205", m5);// 【月累计收款取数标识】公司编码+品牌编码+客户编码+审批状态+年月
 			Map<String, Object> m6 = new HashMap<String, Object>();
 			m6.put("value",dj1.get(i).get("_widget_1543814801964"));
 			rawData.put("_widget_1565054280890", m6);// 经销商编码
 			Map<String, Object> m7 = new HashMap<String, Object>();
 			m7.put("value",dj1.get(i).get("_widget_1543814802008"));
 			rawData.put("_widget_1564996674855", m7);// 经销商
 			Map<String, Object> m8 = new HashMap<String, Object>();
 			m8.put("value",dj1.get(i).get("_widget_1543814802272"));
 			rawData.put("_widget_1565054280408", m8);// 品牌
 			Map<String, Object> m9 = new HashMap<String, Object>();
 			m9.put("value",dj1.get(i).get("_widget_1545377155914"));
 			rawData.put("_widget_1565054280535", m9);// 品牌编码
 			Map<String, Object> m10 = new HashMap<String, Object>();
 			m10.put("value",dj1.get(i).get("_widget_1561617485294"));
 			rawData.put("_widget_1565054283269", m10);// 公司
 			Map<String, Object> m11 = new HashMap<String, Object>();
 			m11.put("value",dj1.get(i).get("_widget_1561617485309"));
 			rawData.put("_widget_1565054283284", m11);// 公司编码
 			Map<String, Object> m12 = new HashMap<String, Object>();
 			m12.put("value",dj1.get(i).get("_widget_1566469277094"));
 			rawData.put("_widget_1567042226458", m12);// 业务
 			Map<String, Object> m13 = new HashMap<String, Object>();
 			m13.put("value",dj1.get(i).get("_widget_1548225231293"));
 			rawData.put("_widget_1564996674896", m13);// 督导
 			Map<String, Object> m15 = new HashMap<String, Object>();
 			m15.put("value",DateUtils.getNowDateToString(DateUtils.FORMAT_1_STRING));
 			rawData.put("_widget_1565054281209", m15);// 日期
 			Map<String, Object> m16 = new HashMap<String, Object>();
 			m16.put("value",DateUtils.getNowDateToString(DateUtils.FORMAT_STRING_YEAR));
 			rawData.put("_widget_1565054282027", m16);// 年
 			Map<String, Object> m17 = new HashMap<String, Object>();
 			m17.put("value",DateUtils.getNowDateToString("yyyy年M月"));
 			rawData.put("_widget_1564996675064", m17);// 年月
 			Map<String, Object> m18 = new HashMap<String, Object>();
 			m18.put("value",DateUtils.getNowDateToString("yyyy年M月dd日"));
 			rawData.put("_widget_1565054281937", m18);// 年月日
 			Map<String, Object> m19 = new HashMap<String, Object>();
 			m19.put("value",mds);
 			rawData.put("_widget_1565166083759", m19);// 门店数
 			Map<String, Object> m20 = new HashMap<String, Object>();
 			m20.put("value",ydmbMap.get(ObjectUtils.getString(dj1.get(i).get("_widget_1543814801964")))!=null?ydmbMap.get(ObjectUtils.getString(dj1.get(i).get("_widget_1543814801964"))):0);
 			rawData.put("_widget_1565166083774", m20);// 目标
 			Map<String, Object> m21 = new HashMap<String, Object>();
 			m21.put("value",0);
 			rawData.put("_widget_1565166084025", m21);// 回款
 			Map<String, Object> m22 = new HashMap<String, Object>();
 			m22.put("value",0);
 			rawData.put("_widget_1564996675036", m22);// 回款率
 			Map<String, Object> m23 = new HashMap<String, Object>();
 			m23.put("value",0);
 			rawData.put("_widget_1565166084168", m23);// 回款(日常返单)
 			Map<String, Object> m24 = new HashMap<String, Object>();
 			m24.put("value",0);
 			rawData.put("_widget_1565065411232", m24);// 店均返单回款
 			Map<String, Object> m25 = new HashMap<String, Object>();
 			m25.put("value",0);
 			rawData.put("_widget_1564996675255", m25);// (督导)销售提成
 			Map<String, Object> m26 = new HashMap<String, Object>();
 			m26.put("value",0);
 			rawData.put("_widget_1565074205311", m26);// (跟单)销售提成
 			Map<String, Object> m27 = new HashMap<String, Object>();
 			m27.put("value",DateUtils.getNowDateToString(DateUtils.FORMAT_1_LONG));
 			rawData.put("_widget_1565074204963", m27);// 操作更新时间
 			Map<String, Object> m28 = new HashMap<String, Object>();
 			m28.put("value",dj1.get(i).get("_widget_1560762110588"));
 			rawData.put("_widget_1573095139039", m28);// 城市等级
 			Map<String, Object> m29 = new HashMap<String, Object>();
 			m29.put("value",dj1.get(i).get("_widget_1563435448247"));
 			rawData.put("_widget_1565054281513", m29);// 跟单
 			Map<String, Object> m30 = new HashMap<String, Object>();
 			m30.put("value",dj1.get(i).get("_widget_1545375767934"));
 			rawData.put("_widget_1588925232200", m30);// 区域
 			Map<String, Object> m31 = new HashMap<String, Object>();
 			m31.put("value",dj1.get(i).get("_widget_1544178899217"));
 			rawData.put("_widget_1588925232215", m31);// 省份
 			Map<String, Object> m32 = new HashMap<String, Object>();
 			m32.put("value",dj1.get(i).get("_widget_1544178899432"));
 			rawData.put("_widget_1588925232243", m32);// 城市
 			Map<String, Object> m33 = new HashMap<String, Object>();
 			m33.put("value",ddMap.get(ObjectUtils.getString(dj1.get(i).get("_widget_1548225231565")))!=null?ddMap.get(ObjectUtils.getString(dj1.get(i).get("_widget_1548225231565"))):0);
 			rawData.put("_widget_1614911874299", m33);// 督导年度目标
 			Map<String, Object> m34 = new HashMap<String, Object>();
 			m34.put("value",ppMap.get(ObjectUtils.getString(dj1.get(i).get("_widget_1545377155914")))!=null?ppMap.get(ObjectUtils.getString(dj1.get(i).get("_widget_1545377155914"))):0);
 			rawData.put("_widget_1614911874278", m34);// 品牌年度目标
 			Map<String, Object> m35 = new HashMap<String, Object>();
 			m35.put("value",ndmb.get(0).get("_widget_1614908475285"));
 			rawData.put("_widget_1614996372547", m35);// 年度目标
 			Map<String, Object> m36 = new HashMap<String, Object>();
 			m36.put("value",xlsMap.get(ObjectUtils.getString(dj1.get(i).get("_widget_1545377155914")))!=null?xlsMap.get(ObjectUtils.getString(dj1.get(i).get("_widget_1545377155914")))*mds:0);
 			rawData.put("_widget_1611902543094", m36);// 新零售目标
 			try {
 				dm1_api.createForData(rawData);// 向简道云添加DM1-销售提成_(自动)
			} catch (Exception e) {
				e.printStackTrace();
				return MessageResult.build(null, "同步失败，请联系IT或重新上传同步数据！");
			}
			mds=0;
		}
		return MessageResult.build(200, "同步成功！");
	}
	
	/**
	 * 
	 *<pre>
	 *<b>.</b>
	 *<b>Description:</b> 
	 *    将本月的 DF1-销售收款单 同步到  DM1-销售提成_(自动)
	 *<b>Author:</b> yanwei
	 *<b>Date:</b> 2020年1月10日 下午12:06:09
	 *@return
	 *</pre>
	 */
	@RequestMapping("/skdSendXstc")
	@ResponseBody
	public MessageResult skdSendXstc(){
		JDYAPIUtils dmapi = new JDYAPIUtils(APPID, DM1_ENTRYID, APIKEY);
		try {
			final List<Map<String, Object>> condList = new ArrayList<Map<String, Object>>();
	        String endDate=DateUtils.getNowDateToString(DateUtils.FORMAT_STRING_MINUTE1);
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("field", "_widget_1548049038941");
			map.put("type", "text");
			map.put("method", "eq");
			map.put("value",endDate);
			condList.add(map);
			Map<String, Object> map1=new HashMap<String, Object>();
			map1.put("field", "_widget_1548037675919");
			map1.put("type", "text");
			map1.put("method", "eq");
			map1.put("value","审批通过");
			condList.add(map1);
			Map<String, Object> map2=new HashMap<String, Object>();
			map2.put("field", "_widget_1611649660788");
			map2.put("type", "text");
			map2.put("method", "ne");
			map2.put("value","活动扣款");
			condList.add(map2);
			Map<String, Object> filter1 = new HashMap<String, Object>(){
	            {
	                put("rel", "and");
	                put("cond", condList);
	            }
	        };
	        JDYAPIUtils api = new JDYAPIUtils(APPID, DF1_ENTRYID, APIKEY);
	        List<Map<String, Object>> xsskd = api.getAllFormData(null,filter1);
	        //获取【月累计收款取数标识(审批通过)】公司编码+品牌编码+客户编码+审批状态+年月
	        Set<String> set=new HashSet<>();
	        for (int i = 0; i < xsskd.size(); i++) {
				set.add(xsskd.get(i).get("_widget_1565060809315").toString());
			}
//	        List<String> ll=new ArrayList<>();
	        for (String str:set) {
	        	//获取DM1-销售提成_(自动)中是否存在要更新的数据
   		        final List<Map<String, Object>> condList3 = new ArrayList<Map<String, Object>>();
   		        Map<String, Object> map3=new HashMap<String, Object>();
   		        map3.put("field", "_widget_1565054283205");
   		        map3.put("type", "text");
   		        map3.put("method", "eq");
   		        map3.put("value",str);
 				condList3.add(map3);
 				Map<String, Object> filter3 = new HashMap<String, Object>(){
 		            {
 		                put("rel", "and");
 		                put("cond", condList3);
 		            }
 		        };
 		       List<Map<String, Object>> DM1 = dmapi.getAllFormData(null,filter3);
 		       if(DM1.size()==0){
// 		    	  ll.add(str);
 		    	  addDate(str);
 		       }else if(DM1.size()==1){
 	 		       Double skje=0.0;
 			       Double rcfdje=0.0;
 			       Double xlsje=0.0;
 	 		       for (int j = 0; j < xsskd.size(); j++) {
 	 		    	   if(str.equals(xsskd.get(j).get("_widget_1565060809315").toString())){
 	 		    		  skje+=Double.valueOf(xsskd.get(j).get("_widget_1548037675480").toString());
 	 		    		  rcfdje+=Double.valueOf(xsskd.get(j).get("_widget_1564390193051").toString());
 	 		    	   }
 	 		    	   if(str.equals(xsskd.get(j).get("_widget_1565060809315").toString()) && "新零售分销".equals(xsskd.get(j).get("_widget_1611649660788").toString())){
 	 		    		  xlsje+=Double.valueOf(xsskd.get(j).get("_widget_1548037675480").toString());
 	 		    	   }
 	 		       }
 	   		        
 	 		       DecimalFormat df = new DecimalFormat("#0.0000");  
 	 		       Map<String, Object> rawData = new HashMap<String, Object>();
 	 		       Map<String, Object> m1 = new HashMap<String, Object>();
 	 		       m1.put("value",skje);
 	 		       rawData.put("_widget_1565166084025", m1);// 回款
 	 		       
 	 		       double mb=Double.valueOf(DM1.get(0).get("_widget_1565166083774").toString());
 	 		       Map<String, Object> m2 = new HashMap<String, Object>();
 	 		       if(mb!=0.0){
 	 		    	  m2.put("value",df.format(skje/mb));
 	 		       }else{
 	 		    	  m2.put("value",0);
 	 		       }
 	 		       rawData.put("_widget_1564996675036", m2);// 回款率
 	 		       
 	 		       Map<String, Object> m3 = new HashMap<String, Object>();
 	 		       m3.put("value",rcfdje);
 	 		       rawData.put("_widget_1565166084168", m3);// 回款(日常返单)
 	 		       
// 	 		       Map<String, Object> m4 = new HashMap<String, Object>();
// 	 		       double mds=Double.valueOf(DM1.get(0).get("_widget_1565166083759").toString());
// 	 		       double djfdhk=0.0;
// 	 		       if(mds!=0.0){
// 	 		    	  djfdhk=Double.valueOf(df.format(rcfdje/mds));
// 	 		       }
// 	 		       m4.put("value",djfdhk);
// 	 		       rawData.put("_widget_1565065411232", m4);// 店均返单回款
 	     			
// 	 		       Map<String, Object> m5 = new HashMap<String, Object>();
// 	 		       if(djfdhk<50000){
// 	 		    	  m5.put("value",0);
// 	 		       }else if(djfdhk>=50000 && djfdhk<70000){
// 	 		    	  m5.put("value",djfdhk*0.005*mds);
// 	 		       }else if(djfdhk>=70000 && djfdhk<100000){
// 	 		    	  m5.put("value",djfdhk*0.01*mds);
// 	 		       }else if(djfdhk>=100000){
// 	 		    	  m5.put("value",djfdhk*0.02*mds);
// 	 		       }
// 	 		       rawData.put("_widget_1564996675255", m5);// (督导)销售提成
 	 		       
 	 		       Map<String, Object> m6 = new HashMap<String, Object>();
 	 		       if("HMW".equals(DM1.get(0).get("_widget_1565054280535").toString())){
 	 		    	  m6.put("value",skje*0.003);
 	 		       }else if("MS".equals(DM1.get(0).get("_widget_1565054280535").toString())){
 	 		    	  m6.put("value",skje*0.003);
 	 		       }else if("PD".equals(DM1.get(0).get("_widget_1565054280535").toString())){
 	 		    	  m6.put("value",skje*0.003);
 	 		       }else if("DUX".equals(DM1.get(0).get("_widget_1565054280535").toString())){
 	 		    	  m6.put("value",skje*0.0015);
 	 		       }else if("NB".equals(DM1.get(0).get("_widget_1565054280535").toString())){
 	 		    	  m6.put("value",skje*0.003);
 	 		       }
 	 		       rawData.put("_widget_1565074205311", m6);// (跟单)销售提成
 	 		       
 	 		       Map<String, Object> m7 = new HashMap<String, Object>();
 	 		       m7.put("value",DateUtils.getNowDateToString(DateUtils.FORMAT_1_LONG));
 	 		       rawData.put("_widget_1565074204963", m7);// 操作更新时间
 	 		       
 	 		       Map<String, Object> m8 = new HashMap<String, Object>();
 			       m8.put("value",xlsje);
 			       rawData.put("_widget_1611902543190", m8);// 新零售订单金额
 			       
 			       Double xlsmb=ObjectUtils.getDouble(DM1.get(0).get("_widget_1611902543094"));
 			       Map<String, Object> m9 = new HashMap<String, Object>();
 			       m9.put("value",xlsmb!=0.0?df.format(xlsje/xlsmb):0);
 			       rawData.put("_widget_1611902543438", m9);// 新零售目标完成率
 	     			
 	 		       dmapi.updateData(DM1.get(0).get("_id").toString(), rawData);// 向简道云更新DM1-销售提成_(自动)  
 		       }
			}
	        DecimalFormat df = new DecimalFormat("#0.00");  
	        //查询M1-销售提成_(自动)当前月份的数据
	        final List<Map<String, Object>> condList4 = new ArrayList<Map<String, Object>>();
	        Map<String, Object> map4=new HashMap<String, Object>();
	        map4.put("field", "_widget_1564996675064");
	        map4.put("type", "text");
	        map4.put("method", "eq");
	        map4.put("value",DateUtils.getNowDateToString("yyyy年M月"));
			condList4.add(map4);
			Map<String, Object> filter4 = new HashMap<String, Object>(){
	            {
	                put("rel", "and");
	                put("cond", condList4);
	            }
	        };
	       List<Map<String, Object>> DM1 = dmapi.getAllFormData(null,filter4);
	       //取出当月的所有督导
	       Set<Object> dd=new HashSet<>();
	       for (int i = 0; i < DM1.size(); i++) {
	    	   dd.add(DM1.get(i).get("_widget_1564996674896"));
	       }
	       for(Object obj:dd){
	    	   	final List<Map<String, Object>> condList5 = new ArrayList<Map<String, Object>>();
		        Map<String, Object> map5=new HashMap<String, Object>();
		        map5.put("field", "_widget_1564996674896");
		        map5.put("type", "text");
		        map5.put("method", "eq");
		        map5.put("value",obj);
				condList5.add(map5);
		        Map<String, Object> map6=new HashMap<String, Object>();
		        map6.put("field", "_widget_1564996675064");
		        map6.put("type", "text");
		        map6.put("method", "eq");
		        map6.put("value",DateUtils.getNowDateToString("yyyy年M月"));
				condList5.add(map6);
				Map<String, Object> map7=new HashMap<String, Object>();
		        map7.put("field", "_widget_1565166083759");
		        map7.put("type", "text");
		        map7.put("method", "ne");
		        map7.put("value",0);
				condList5.add(map7);
				Map<String, Object> filter5 = new HashMap<String, Object>(){
		            {
		                put("rel", "and");
		                put("cond", condList5);
		            }
		        };
		       List<Map<String, Object>> M1 = dmapi.getAllFormData(null,filter5);
		       //当前督导负责的门店数
		       int mds=0;
		       double hkje=0.0;
		       for (int i = 0; i < M1.size(); i++) {
		    	   mds+=Integer.valueOf(M1.get(i).get("_widget_1565166083759").toString());
		    	   hkje+=Double.valueOf(M1.get(i).get("_widget_1565166084168").toString());
		       }
		       //修改
		       for(int i = 0; i < M1.size(); i++){
		    	   double hk=Double.valueOf(M1.get(i).get("_widget_1565166084025").toString());
		    	   Map<String, Object> rawData = new HashMap<String, Object>();
  	 		       Map<String, Object> m1 = new HashMap<String, Object>();
  	 		       double djfdhk=0.0;
  	 		       if(mds!=0.0){
  	 		    	  djfdhk=Double.valueOf(df.format(hkje/mds));
  	 		       }
  	 		       m1.put("value",djfdhk);
  	 		       rawData.put("_widget_1565065411232", m1);// 店均返单回款
  	     			
  	 		       Map<String, Object> m2 = new HashMap<String, Object>();
  	 		       if(djfdhk<40000){
  	 		    	  m2.put("value",0);
  	 		       }else if(djfdhk>=40000 && djfdhk<60000){
  	 		    	  m2.put("value",hk*0.005);
  	 		       }else if(djfdhk>=60000 && djfdhk<90000){
  	 		    	  m2.put("value",hk*0.01);
  	 		       }else if(djfdhk>=90000){
  	 		    	  m2.put("value",hk*0.02);
  	 		       }
  	 		       rawData.put("_widget_1564996675255", m2);// (督导)销售提成
  	 		       dmapi.updateData(M1.get(i).get("_id").toString(), rawData);// 向简道云更新DM1-销售提成_(自动)
		       }
	       }
	        
		} catch (Exception e) {
			e.printStackTrace();
			return MessageResult.build(null, "同步失败，请重新同步目标！");
		}
		return MessageResult.build(200, "同步成功！");
	}
	
	public static List<Map<String, Object>> removeDuplicate(List<Map<String, Object>> list) {
		for (int i = 0; i < list.size() - 1; i++) {
			for (int j = list.size() - 1; j > i; j--) {
				if (list.get(j).get("jxsbm").toString().equals(list.get(i).get("jxsbm").toString())) {
					list.remove(j);
				}
			}
		}
		return list;
	}
	
	//str 【月累计收款取数标识(审批通过)】公司编码+品牌编码+客户编码+审批状态+年月
	public void addDate(String str){
		try {
			final List<Map<String, Object>> condList = new ArrayList<Map<String, Object>>();
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("field", "_widget_1565060809315");
			map.put("type", "text");
			map.put("method", "eq");
			map.put("value",str);
			condList.add(map);
			Map<String, Object> filter = new HashMap<String, Object>(){
	            {
	                put("rel", "and");
	                put("cond", condList);
	            }
	        };
	        JDYAPIUtils api = new JDYAPIUtils(APPID, DF1_ENTRYID, APIKEY);
	        List<Map<String, Object>> xsskd = api.getAllFormData(null,filter);
	        
	        Double skje=0.0;
	        Double rcfdje=0.0;
	        Double xlsje=0.0;
	        for (int j = 0; j < xsskd.size(); j++) {
	        	skje+=Double.valueOf(xsskd.get(j).get("_widget_1548037675480").toString());
	    		rcfdje+=Double.valueOf(xsskd.get(j).get("_widget_1564390193051").toString());
	    		if("新零售分销".equals(xsskd.get(j).get("_widget_1611649660788").toString())){
	    			xlsje+=Double.valueOf(xsskd.get(j).get("_widget_1548037675480").toString());
	    		}
	        }
	        
	        String jxsbm=xsskd.get(0).get("_widget_1548037673508").toString();
	        final List<Map<String, Object>> condList1 = new ArrayList<Map<String, Object>>();
			Map<String, Object> map3 = new HashMap<String, Object>();
			map3.put("field", "_widget_1543814801964");
			map3.put("type", "text");
			map3.put("method", "eq");
			map3.put("value", jxsbm);
			condList1.add(map3);
			Map<String, Object> filter1 = new HashMap<String, Object>() {
				{
					put("rel", "and");
					put("cond", condList1);
				}
			};
			JDYAPIUtils dj1_api = new JDYAPIUtils(APPID, DJ1_ENTRYID, APIKEY);
			List<Map<String, Object>> dj1 = dj1_api.getAllFormData(null, filter1);
			
			//获取年度目标
			JDYAPIUtils nd_api = new JDYAPIUtils(APPID, ND_ENTRYID, APIKEY);
			List<Map<String, Object>> ndmb = nd_api.getAllFormData(null, null);
			
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
 			m4.put("value",jxsbm+"审批通过"+"营业中"+"启用");
 			rawData.put("_widget_1565054282503", m4);// 门店所属经销商标识
 			Map<String, Object> m5 = new HashMap<String, Object>();
 			m5.put("value",dj1.get(0).get("_widget_1561617485309").toString()+dj1.get(0).get("_widget_1545377155914").toString()+jxsbm+"审批通过"+DateUtils.getNowDateToString("yyyy年M月"));
 			rawData.put("_widget_1565054283205", m5);// 【月累计收款取数标识】公司编码+品牌编码+客户编码+审批状态+年月
 			Map<String, Object> m6 = new HashMap<String, Object>();
 			m6.put("value",jxsbm);
 			rawData.put("_widget_1565054280890", m6);// 经销商编码
 			Map<String, Object> m7 = new HashMap<String, Object>();
 			m7.put("value",dj1.get(0).get("_widget_1543814802008"));
 			rawData.put("_widget_1564996674855", m7);// 经销商
 			Map<String, Object> m8 = new HashMap<String, Object>();
 			m8.put("value",dj1.get(0).get("_widget_1543814802272"));
 			rawData.put("_widget_1565054280408", m8);// 品牌
 			Map<String, Object> m9 = new HashMap<String, Object>();
 			m9.put("value",dj1.get(0).get("_widget_1545377155914"));
 			rawData.put("_widget_1565054280535", m9);// 品牌编码
 			Map<String, Object> m10 = new HashMap<String, Object>();
 			m10.put("value",dj1.get(0).get("_widget_1561617485294"));
 			rawData.put("_widget_1565054283269", m10);// 公司
 			Map<String, Object> m11 = new HashMap<String, Object>();
 			m11.put("value",dj1.get(0).get("_widget_1561617485309"));
 			rawData.put("_widget_1565054283284", m11);// 公司编码
 			Map<String, Object> m12 = new HashMap<String, Object>();
 			m12.put("value",dj1.get(0).get("_widget_1566469277094"));
 			rawData.put("_widget_1567042226458", m12);// 业务
 			Map<String, Object> m13 = new HashMap<String, Object>();
 			m13.put("value",dj1.get(0).get("_widget_1548225231293"));
 			rawData.put("_widget_1564996674896", m13);// 督导
 			Map<String, Object> m15 = new HashMap<String, Object>();
 			m15.put("value",DateUtils.getNowDateToString(DateUtils.FORMAT_1_STRING));
 			rawData.put("_widget_1565054281209", m15);// 日期
 			Map<String, Object> m16 = new HashMap<String, Object>();
 			m16.put("value",DateUtils.getNowDateToString(DateUtils.FORMAT_STRING_YEAR));
 			rawData.put("_widget_1565054282027", m16);// 年
 			Map<String, Object> m17 = new HashMap<String, Object>();
 			m17.put("value",DateUtils.getNowDateToString("yyyy年M月"));
 			rawData.put("_widget_1564996675064", m17);// 年月
 			Map<String, Object> m18 = new HashMap<String, Object>();
 			m18.put("value",DateUtils.getNowDateToString("yyyy年M月dd日"));
 			rawData.put("_widget_1565054281937", m18);// 年月日
 			Map<String, Object> m19 = new HashMap<String, Object>();
 			m19.put("value",0);
 			rawData.put("_widget_1565166083759", m19);// 门店数
 			Map<String, Object> m20 = new HashMap<String, Object>();
 			m20.put("value",0);
 			rawData.put("_widget_1565166083774", m20);// 目标
 			Map<String, Object> m21 = new HashMap<String, Object>();
 			m21.put("value",skje);
 			rawData.put("_widget_1565166084025", m21);// 回款
 			Map<String, Object> m22 = new HashMap<String, Object>();
 			m22.put("value",0);
 			rawData.put("_widget_1564996675036", m22);// 回款率
 			Map<String, Object> m23 = new HashMap<String, Object>();
 			m23.put("value",rcfdje);
 			rawData.put("_widget_1565166084168", m23);// 回款(日常返单)
 			Map<String, Object> m24 = new HashMap<String, Object>();
 			m24.put("value",0);
 			rawData.put("_widget_1565065411232", m24);// 店均返单回款
 			Map<String, Object> m25 = new HashMap<String, Object>();
 			m25.put("value",0);
 			rawData.put("_widget_1564996675255", m25);// (督导)销售提成
 			Map<String, Object> m26 = new HashMap<String, Object>();
		       if("HMW".equals(dj1.get(0).get("_widget_1543814802272").toString())){
		    	   m26.put("value",skje*0.003);
		       }else if("MS".equals(dj1.get(0).get("_widget_1543814802272").toString())){
		    	   m26.put("value",skje*0.003);
		       }else if("PD".equals(dj1.get(0).get("_widget_1543814802272").toString())){
		    	   m26.put("value",skje*0.003);
		       }else if("DUX".equals(dj1.get(0).get("_widget_1543814802272").toString())){
		    	   m26.put("value",skje*0.0015);
		       }else if("NB".equals(dj1.get(0).get("_widget_1543814802272").toString())){
		    	   m26.put("value",skje*0.003);
		       }
		    rawData.put("_widget_1565074205311", m26);// (跟单)销售提成
 			
 			Map<String, Object> m27 = new HashMap<String, Object>();
 			m27.put("value",DateUtils.getNowDateToString(DateUtils.FORMAT_1_LONG));
 			rawData.put("_widget_1565074204963", m27);// 操作更新时间
 			Map<String, Object> m28 = new HashMap<String, Object>();
 			m28.put("value",dj1.get(0).get("_widget_1560762110588"));
 			rawData.put("_widget_1573095139039", m28);// 城市等级
 			Map<String, Object> m29 = new HashMap<String, Object>();
 			m29.put("value",dj1.get(0).get("_widget_1563435448247"));
 			rawData.put("_widget_1565054281513", m29);// 跟单
 			Map<String, Object> m30 = new HashMap<String, Object>();
 			m30.put("value",dj1.get(0).get("_widget_1545375767934"));
 			rawData.put("_widget_1588925232200", m30);// 区域
 			Map<String, Object> m31 = new HashMap<String, Object>();
 			m31.put("value",dj1.get(0).get("_widget_1544178899217"));
 			rawData.put("_widget_1588925232215", m31);// 省份
 			Map<String, Object> m32 = new HashMap<String, Object>();
 			m32.put("value",dj1.get(0).get("_widget_1544178899432"));
 			rawData.put("_widget_1588925232243", m32);// 城市
 			Map<String, Object> m33 = new HashMap<String, Object>();
 			m33.put("value",0);
 			rawData.put("_widget_1614911874299", m33);// 督导年度目标
 			Map<String, Object> m34 = new HashMap<String, Object>();
 			m34.put("value",0);
 			rawData.put("_widget_1614911874278", m34);// 品牌年度目标
 			Map<String, Object> m35 = new HashMap<String, Object>();
 			m35.put("value",ndmb.get(0).get("_widget_1614908475285"));
 			rawData.put("_widget_1614996372547", m35);// 年度目标
 			Map<String, Object> m36 = new HashMap<String, Object>();
 			m36.put("value",0);
 			rawData.put("_widget_1611902543094", m36);// 新零售目标
 			Map<String, Object> m37 = new HashMap<String, Object>();
 			m37.put("value",xlsje);
 			rawData.put("_widget_1611902543190", m37);// 新零售订单金额
 			Map<String, Object> m38 = new HashMap<String, Object>();
 			m38.put("value",0);
 			rawData.put("_widget_1611902543438", m38);// 新零售目标完成率
 			JDYAPIUtils dmapi = new JDYAPIUtils(APPID, DM1_ENTRYID, APIKEY);
 			dmapi.createForData(rawData);// 向简道云添加DM1-销售提成_(自动)
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}

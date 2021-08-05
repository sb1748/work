package com.jxszj.quartz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jxszj.utils.DateUtils;
import com.jxszj.utils.JDYAPIUtils;


/**
 * 
 * <pre>
 * <b>.</b>
 * <b>Description:</b> 
 *    将价格副表中的数据移动到价格表中
 * <b>Author:</b> yanwei
 * <b>Date:</b> 2020年5月12日下午1:45:28
 * </pre>
 */
public class PriceMoveQuartz {

	private static Logger logger = LoggerFactory.getLogger(PriceMoveQuartz.class.getName());
	
	public void execute(){
		try {
			// 简道云经销商之家
			String jxszj_appId = "5cc110c3b3c41744aaa12b2e";
			String jxszj_entryId = "5e848d96f4cca90006acb5ff";
			String jxszj_apiKey = "uWEAujuxvzv5fMVifOvMRzlIJcPLgYkv";
			
			
			String a_entryId = "5f4dfa0a6c18270006e4c29f";//副表
			
			JDYAPIUtils jxszj_api = new JDYAPIUtils(jxszj_appId, jxszj_entryId, jxszj_apiKey);
			
			JDYAPIUtils a_api = new JDYAPIUtils(jxszj_appId, a_entryId, jxszj_apiKey);
			
			//先查询
			final List<Map<String, Object>> condList = new ArrayList<Map<String, Object>>();
			condList.add(new HashMap<String, Object>() {
				{
					put("field", "_widget_1598165436554");//有效期开始时间
					put("type", "text");
					put("method", "eq");
					put("value", DateUtils.getNowDateToString());
				}
			});
			Map<String, Object> filter = new HashMap<String, Object>() {
				{
					put("rel", "and");
					put("cond", condList);
				}
			};
			List<Map<String, Object>> formData = a_api.getAllFormData(null, filter);
			for (int j = 0; j < formData.size(); j++) {
				Object id=formData.get(j).get("_widget_1591845499086");
				final List<Map<String, Object>> condList1 = new ArrayList<Map<String, Object>>();
				condList1.add(new HashMap<String, Object>() {
					{
						put("field", "_widget_1591845499086");//唯一码
						put("type", "text");
						put("method", "eq");
						put("value", id);
					}
				});
				Map<String, Object> filter1 = new HashMap<String, Object>() {
					{
						put("rel", "and");
						put("cond", condList1);
					}
				};
				List<Map<String, Object>> formData1 = jxszj_api.getAllFormData(null, filter1);
				if(formData1.size()==0){
					jxszj_api.createForData(getData(formData.get(j)));
				}else if(formData1.size()==1){
					jxszj_api.updateData(formData1.get(0).get("_id").toString(), getData(formData.get(j)));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public  Map<String, Object> getData(Map<String,Object> map){
		Map<String, Object> rawData = new HashMap<String, Object>();
		Map<String, Object> m1 = new HashMap<String, Object>();
		m1.put("value", map.get("_widget_1585745302686"));
		rawData.put("_widget_1585745302686", m1);// 客户价格组
		Map<String, Object> m2 = new HashMap<String, Object>();
		m2.put("value", map.get("_widget_1585745302701"));
		rawData.put("_widget_1585745302701", m2);// 客户编码
		Map<String, Object> m3 = new HashMap<String, Object>();
		m3.put("value", map.get("_widget_1585745302716"));
		rawData.put("_widget_1585745302716", m3);// 物料编码
		Map<String, Object> m4 = new HashMap<String, Object>();
		m4.put("value", map.get("_widget_1585745302731"));
		rawData.put("_widget_1585745302731", m4);// 单价
		Map<String, Object> m5 = new HashMap<String, Object>();
		m5.put("value", map.get("_widget_1585901362201"));
		rawData.put("_widget_1585901362201", m5);// 单位
		Map<String, Object> m6 = new HashMap<String, Object>();
		m6.put("value", map.get("_widget_1585901362080"));
		rawData.put("_widget_1585901362080", m6);// 币种
		Map<String, Object> m7 = new HashMap<String, Object>();
		m7.put("value",map.get("_widget_1586482329032"));
		rawData.put("_widget_1586482329032", m7);//条件类型
		Map<String, Object> m8 = new HashMap<String, Object>();
		m8.put("value",map.get("_widget_1586499946999"));
		rawData.put("_widget_1586499946999", m8);//PPR0标识
		Map<String, Object> m_8 = new HashMap<String, Object>();
		m_8.put("value",map.get("_widget_1586499946984"));
		rawData.put("_widget_1586499946984", m_8);//YK07标识
		
		Map<String, Object> m9 = new HashMap<String, Object>();
		m9.put("value",map.get("_widget_1586499946729"));
		rawData.put("_widget_1586499946729", m9);//销售组织
		Map<String, Object> m10 = new HashMap<String, Object>();
		m10.put("value",map.get("_widget_1586499946714"));
		rawData.put("_widget_1586499946714", m10);//分销渠道
		
		Map<String, Object> m11 = new HashMap<String, Object>();
		m11.put("value",map.get("_widget_1591845499086"));
		rawData.put("_widget_1591845499086", m11);//唯一码
		
		Map<String, Object> m12 = new HashMap<String, Object>();
		m12.put("value",map.get("_widget_1598165436554"));
		rawData.put("_widget_1598165436554", m12);//有效期开始时间
		
		Map<String, Object> m13 = new HashMap<String, Object>();
		m13.put("value",map.get("_widget_1598165436576"));
		rawData.put("_widget_1598165436576", m13);//有效期结束时间
		
		Map<String, Object> m14 = new HashMap<String, Object>();
		m14.put("value",map.get("_widget_1598944894560"));
		rawData.put("_widget_1598944894560", m14);//条件记录编号
		return rawData;
	}
}

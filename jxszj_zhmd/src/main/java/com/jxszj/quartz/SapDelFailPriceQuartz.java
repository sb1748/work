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
 *    每天5点清理失效价格
 * <b>Author:</b> yanwei
 * <b>Date:</b> 2020年5月12日下午1:45:28
 * </pre>
 */
public class SapDelFailPriceQuartz {

	private static Logger logger = LoggerFactory.getLogger(SapDelFailPriceQuartz.class.getName());
	
	public void execute(){
		logger.info("--------------------开始清理失效价格--------------------");
		// 简道云经销商之家
		String jxszj_appId = "5cc110c3b3c41744aaa12b2e";
		String jxszj_entryId = "5e848d96f4cca90006acb5ff";
		String jxszj_apiKey = "uWEAujuxvzv5fMVifOvMRzlIJcPLgYkv";
		
		JDYAPIUtils jxszj_api = new JDYAPIUtils(jxszj_appId, jxszj_entryId, jxszj_apiKey);
		
		String[] str=new String[2];
		str[0]="1979-01-01";
		str[1]=DateUtils.getYesterday();
		
		//先查询
		final List<Map<String, Object>> condList = new ArrayList<Map<String, Object>>();
		condList.add(new HashMap<String, Object>() {
			{
				put("field", "_widget_1598165436576");//有效期结束时间
				put("type", "text");
				put("method", "range");
				put("value", str);
			}
		});
		Map<String, Object> filter = new HashMap<String, Object>() {
			{
				put("rel", "and");
				put("cond", condList);
			}
		};
		List<Map<String, Object>> formData1 = jxszj_api.getAllFormData(null, filter);
		for (int j = 0; j < formData1.size(); j++) {
			jxszj_api.deleteData(formData1.get(j).get("_id").toString());
		}
	}
	
}

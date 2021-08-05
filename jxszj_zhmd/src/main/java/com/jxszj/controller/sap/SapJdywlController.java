package com.jxszj.controller.sap;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.entity.StringEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jxszj.pojo.EUDataGridResult;
import com.jxszj.pojo.sap.SapJdywlTb;
import com.jxszj.service.sap.ISapJdywlService;
import com.jxszj.utils.ExcelUtil;
import com.jxszj.utils.JDYAPIUtils;
import com.jxszj.utils.MessageResult;
import com.jxszj.utils.ObjectUtils;

/**
 * 
 * <pre>
 * <b>.</b>
 * <b>Description:</b> 
 *    简道云物料
 * <b>Author:</b> yanwei
 * <b>Date:</b> 2020年7月5日上午11:37:57
 * </pre>
 */
@Controller
public class SapJdywlController {

	@Autowired
	private ISapJdywlService sapJdywlService;

	// 简道云经销商之家物料
	String appId = "5cc110c3b3c41744aaa12b2e";
	String entryId = "5e7f03d274924c000681ad02";
	String apiKey = "uWEAujuxvzv5fMVifOvMRzlIJcPLgYkv";

	// 简道云智慧门店系统
//	String zhmd_appId = "5c6fa6da7eacb23f3daf642e";
//	String zhmd_entryId = "5ee334cb3752de0007c7c491";
//	String zhmd_apiKey = "O3nyFjunjn5neMk0c4cfEogVLFFXqHfU";

	public static String status = "true";

	@RequestMapping("/addJdywl")
	@ResponseBody
	public MessageResult addJdywl(String jdycpmclike) {
		try {
			if (status.equals("false")) {
				return MessageResult.build(500, "系统检测到正在获取物料，请等待...");
			}
			status = "false";

			JDYAPIUtils api = new JDYAPIUtils(appId, entryId, apiKey);
			List<Map<String, Object>> lists = api.getAllFormData(null, null);
			List<SapJdywlTb> sapJdywlTbs = new ArrayList<SapJdywlTb>();
			for (int i = 0; i < lists.size(); i++) {
				SapJdywlTb sapJdywlTb = new SapJdywlTb();
				sapJdywlTb.setId(getString(lists.get(i).get("_widget_1591091866205")));
				sapJdywlTb.setXszz(getString(lists.get(i).get("_widget_1585385207654")));
				sapJdywlTb.setFxqd(getString(lists.get(i).get("_widget_1585385207669")));
				sapJdywlTb.setCplx(getString(lists.get(i).get("_widget_1591081777035")));
				sapJdywlTb.setSap(getString(lists.get(i).get("_widget_1586152118862")));
				sapJdywlTb.setPpmc(getString(lists.get(i).get("_widget_1585908861148")));
				sapJdywlTb.setWlzbm(getString(lists.get(i).get("_widget_1586158226585")));
				sapJdywlTb.setWlzmc(getString(lists.get(i).get("_widget_1590977351648")));
				sapJdywlTb.setFlbs(getString(lists.get(i).get("_widget_1591705259670")));
				sapJdywlTb.setCpbm(getString(lists.get(i).get("_widget_1585385207624")));
				sapJdywlTb.setCpmc(getString(lists.get(i).get("_widget_1585385207639")));
				sapJdywlTb.setGgcc(getString(lists.get(i).get("_widget_1585385207744")));
				sapJdywlTbs.add(sapJdywlTb);
			}

			int num = sapJdywlService.insertByBatch(sapJdywlTbs);

			status = "true";
			return MessageResult.build(200, "获取到" + num + "条物料，获取完成！");
		} catch (Exception e) {
			e.printStackTrace();
		}
		status = "true";
		return MessageResult.build(500, "获取失败！");
	}

	@RequestMapping("/getJdywl")
	@ResponseBody
	public EUDataGridResult getJdywl(Integer page, Integer rows, String jdycpmc) {
		EUDataGridResult result = sapJdywlService.getJdywl(page, rows, jdycpmc);
		return result;
	}

	@RequestMapping("/delJdywl")
	@ResponseBody
	public MessageResult delJdywl(String[] ids) {
		try {
			int i = 0;
			int num = 0;
			// 简道云 添加、删除产品类别(DJ11)数据接口
			JDYAPIUtils api = new JDYAPIUtils(appId, entryId, apiKey);
			for (int k = 0; k < ids.length; k++) {
				final List<Map<String, Object>> condList = new ArrayList<Map<String, Object>>();
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("field", "_widget_1591091866205");
				map.put("type", "text");
				map.put("method", "eq");
				map.put("value", ids[k].toString());
				condList.add(map);

				Map<String, Object> filter = new HashMap<String, Object>() {
					{
						put("rel", "and");
						put("cond", condList);
					}
				};
				List<Map<String, Object>> list = api.getAllFormData(null, filter);
				for (int j = 0; j < list.size(); j++) {
					api.deleteData(list.get(j).get("_id").toString());
					num++;
				}
			}
			return MessageResult.build(200, "已成功删除经销商之家" + num + "条物料");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MessageResult.build(null, "删除失败！");
	}

	@RequestMapping("jdywl/uploadPostExcel")
	@ResponseBody
	public MessageResult uploadPostExcel(@RequestParam("jdywlExcelFile") MultipartFile jdywlExcelFile,
			HttpServletRequest request, HttpServletResponse response) {
		int num = 0;
		try {
			if (jdywlExcelFile == null) {
				return MessageResult.build(null, "上传Excel失败,请重试！");
			}
			InputStream in = null;
			List<List<Object>> listob = null;
			in = jdywlExcelFile.getInputStream();
			listob = new ExcelUtil().getBankListByExcel(in, jdywlExcelFile.getOriginalFilename());

			JDYAPIUtils api = new JDYAPIUtils(appId, entryId, apiKey);
			for (int i = 0; i < listob.size(); i++) {
				final List<Map<String, Object>> condList = new ArrayList<Map<String, Object>>();
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("field", "_widget_1585385207624");
				map.put("type", "text");
				map.put("method", "eq");
				map.put("value", ObjectUtils.getString(listob.get(i).get(0)));
				condList.add(map);

				Map<String, Object> filter = new HashMap<String, Object>() {
					{
						put("rel", "and");
						put("cond", condList);
					}
				};
				List<Map<String, Object>> list = api.getAllFormData(null, filter);
				for (int j = 0; j < list.size(); j++) {
					api.deleteData(list.get(j).get("_id").toString());
					num++;
				}
			}

		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return MessageResult.build(200, "已成功删除经销商之家" + num + "条物料");
	}

	public String getString(Object obj) {
		String str = "";
		if (obj == null) {
			return str;
		}
		return obj.toString();
	}

}

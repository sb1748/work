package com.jxszj.controller.sap;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jxszj.pojo.EUDataGridResult;
import com.jxszj.pojo.sap.SapWlTb;
import com.jxszj.service.sap.ISapWllbService;
import com.jxszj.utils.MessageResult;


@Controller
@RequestMapping("/sap/wlbm")
public class SapWllbController {

	@Autowired
	private ISapWllbService sapWllbService;

	@RequestMapping("/getWlbmList")
	@ResponseBody
	public EUDataGridResult getWlbmList(Integer page, Integer rows) {
		EUDataGridResult result = sapWllbService.getWlbmList(page, rows);
		return result;
	}
	
	@RequestMapping("/getWlbmView")
	@ResponseBody
	public List<SapWlTb> getWlbmView() {
		List<SapWlTb> list = sapWllbService.getWlbmLists();
//		EUDataGridResult result = new EUDataGridResult();
//		result.setTotal(list.size());
//		result.setRows(list);
        return list;
	}

	@RequestMapping(value ="/getWlbmLists", method = RequestMethod.GET)
	@ResponseBody
	public List<SapWlTb> getWlbmLists() {
		List<SapWlTb> zhmdCplbTbs = sapWllbService.getWlbmLists();
		return zhmdCplbTbs;
	}

	@RequestMapping("/addWlbm")
	@ResponseBody
	public MessageResult addWlbm(SapWlTb wlbm) {
		try {	
				int i = sapWllbService.insertWlbm(wlbm);
				if (i > 0) {
					return MessageResult.build(200, "添加成功！");
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MessageResult.build(null, "添加失败！");
	}

	@RequestMapping("/editWlbm")
	@ResponseBody
	public MessageResult editWlbm(SapWlTb wlbm) {
		try {
				int i = sapWllbService.updateWlbm(wlbm);
				if (i > 0) {
					return MessageResult.build(200, "修改成功！");
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MessageResult.build(null, "修改失败！");
	}

	@RequestMapping("/deleteWlbm")
	@ResponseBody
	public MessageResult deleteWlbm(Long[] ids) {
		try {
				int i=sapWllbService.deleteWlbm(Arrays.asList(ids));
				if (i > 0) {
					return MessageResult.build(200, "删除成功！");
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MessageResult.build(null, "删除失败！");
	}
	
}

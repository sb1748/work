package com.jxszj.controller.sap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jxszj.pojo.EUDataGridResult;
import com.jxszj.pojo.sap.SapBmTb;
import com.jxszj.pojo.sap.SapDataTb;
import com.jxszj.service.sap.ISapBmService;
import com.jxszj.service.sap.ISapDataService;
import com.jxszj.utils.DateUtils;
import com.jxszj.utils.MessageResult;


@Controller
@RequestMapping("/sap/bm")
public class SapBmController {

	@Autowired
	private ISapBmService sapBmService;
	
	@Autowired
	private ISapDataService sapDataService;
	
	public static List<String> list;
	
	@RequestMapping("/getSapBmList")
	@ResponseBody
	public EUDataGridResult getSapBmList(Integer page, Integer rows,String newWlbm,String newName,String oldWlbm,String oldName,String bom){
		EUDataGridResult result =sapBmService.getSapBmList(page, rows,newWlbm,newName,oldWlbm,oldName,bom);
		return result;
	}
	
	
	@RequestMapping("/getSapBmRemList")
	@ResponseBody
	public EUDataGridResult getSapBmRemList(Integer page, Integer rows,String newWlbm,String newName,String oldWlbm,String oldName,String bom){
		EUDataGridResult result =sapBmService.getSapBmRemList(page, rows,newWlbm,newName,oldWlbm,oldName,bom);
		return result;
	}
	
	
	@RequestMapping(value ="/getSapDataLists")
	public void getSapDataLists(String bmz,String gzs,HttpServletResponse resp){
        try {
        	List<SapDataTb> sapDataTbs=sapDataService.getSapDataLists(bmz,gzs);
    		if(sapDataTbs.size()==0 || sapDataTbs==null){
    			return;
    		}
    		list =new ArrayList<String>();
        	String strData=sapDataTbs.get(0).getData();
        	String wlyl=sapDataTbs.get(0).getWlmclz();
        	String ggxhyl=sapDataTbs.get(0).getGgxhlz();
        	String[] strArray=strData.split(";");
        	resp.setContentType("text/html");
        	resp.setCharacterEncoding("utf-8");
        	resp.getWriter().print("<tr><td>物料名称:</td><td><input  id='name' name='name' prompt='样例："+wlyl+"' class='easyui-textbox' style='width: 350px;'></td></tr><tr><td>规格型号:</td><td><input class='easyui-textbox' prompt='例如："+ggxhyl+"'  name='ggxh' id='ggxh' style='width:350px;' /></td></tr>");
        	for (int i = 0; i < strArray.length; i++) {
				if(!strArray[i].contains(",")){
					String id=strArray[i].replace("-", "_");
					list.add(id.substring(0, id.indexOf("(")));
					resp.getWriter().print("<tr><td>"+strArray[i].substring(0, strArray[i].indexOf("("))+"码:</td><td><input  id='"+id.substring(0, id.indexOf("("))+"' name='"+id.substring(0, id.indexOf("("))+"' class='easyui-textbox' prompt='"+strArray[i].substring(strArray[i].indexOf("(")+1, strArray[i].indexOf(")"))+"' style='width: 350px;'></td></tr>");
				}else{
					int begin=strArray[i].indexOf(":");
					int last=strArray[i].length();
					String[] strs=strArray[i].substring(begin+1,last).split(",");
					StringBuilder options=new StringBuilder();
					for (int j = 0; j < strs.length; j++) {
						int e=strs[j].indexOf("-");
						options.append("<option value='"+strs[j].substring(e+1,strs[j].length())+"'>"+strs[j].substring(0,e)+"</option>");
					}
					String id=strArray[i].substring(0, begin).replace("-", "_");
					list.add(id.substring(0, id.indexOf("(")));
					resp.getWriter().print("<tr><td>"+strArray[i].substring(0, strArray[i].indexOf("("))+"码:</td><td><select class='easyui-combobox' editable='false' panelHeight='auto' prompt='"+strArray[i].substring(strArray[i].indexOf("(")+1, strArray[i].indexOf(")"))+"' id='"+id.substring(0, id.indexOf("("))+"' name='"+id.substring(0, id.indexOf("("))+"' style='width:350px;'>"+options+"</select></td></tr>");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@RequestMapping("/addOldSapBm")
	@ResponseBody
	public MessageResult addOldSapBm(SapBmTb sapBm){
		try {
			List<SapBmTb> sapBms=sapBmService.getSapBmListsByNewWlbm(sapBm.getNewWlbm());
			if(sapBms.size()>0){
				return MessageResult.build(null,"添加失败,新物料编码已存在！");
			}
			sapBms=sapBmService.getSapBmListsByNewName(sapBm.getNewName());
			if(sapBms.size()>0){
				return MessageResult.build(null,"添加失败,新物料名称已存在！");
			}
			if(!"".equals(sapBm.getOldWlbm())){
				sapBms=sapBmService.getSapBmListsByOldWlbm(sapBm.getOldWlbm());
				if(sapBms.size()>0){
					return MessageResult.build(null,"添加失败,旧物料编码已存在！");
				}
			}
			
			if(!"".equals(sapBm.getOldName())){
				sapBms=sapBmService.getSapBmListsByOldName(sapBm.getOldName());
				if(sapBms.size()>0){
					return MessageResult.build(null,"添加失败,旧物料名称已存在！");
				}
			}
			
			sapBm.setCreatetime(DateUtils.getNowDateToString());
			sapBm.setStatus("n");
			int i=sapBmService.insertSapBm(sapBm);
			if(i>0){
				return MessageResult.build(200,"添加成功！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MessageResult.build(null,"添加失败！");
	}
	
	@RequestMapping("/editSapBm")
	@ResponseBody
	public MessageResult editSapBm(SapBmTb sapBm){
		try {
			sapBm.setCreatetime(DateUtils.getNowDateToString());
			sapBm.setStatus("n");
			int i=sapBmService.updateSapBm(sapBm);
			if(i>0){
				return MessageResult.build(200,"修改成功！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MessageResult.build(null,"修改失败！");
	}
	
	@RequestMapping("/removeSapBm")
	@ResponseBody
	public MessageResult removeSapBm(Integer[] ids){
		try {
			int i=sapBmService.removeSapBm(Arrays.asList(ids));
			if(i>0){
				return MessageResult.build(200,"删除成功！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MessageResult.build(null,"删除失败！");
	}
	
	@RequestMapping("/restoreSapBm")
	@ResponseBody
	public MessageResult restoreSapBm(Integer[] ids){
		try {
			int i=sapBmService.restoreSapBm(Arrays.asList(ids));
			if(i>0){
				return MessageResult.build(200,"还原成功！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MessageResult.build(null,"还原失败！");
	}
	
	@RequestMapping("/deleteSapBm")
	@ResponseBody
	public MessageResult deleteSapBm(Integer[] ids){
		try {
			int i=sapBmService.deleteSapBm(Arrays.asList(ids));
			if(i>0){
				return MessageResult.build(200,"删除成功！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MessageResult.build(null,"删除失败！");
	}
	
	
	@RequestMapping("/deleteAllSapBm")
	@ResponseBody
	public MessageResult deleteAllSapBm(){
		try {
			sapBmService.deleteAllSapBm();
			return MessageResult.build(200,"删除成功！");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MessageResult.build(null,"删除失败！");
	}
	
}

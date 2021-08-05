package com.jxszj.service.sap.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxszj.mapper.sap.YdmbTbMapper;
import com.jxszj.pojo.EUDataGridResult;
import com.jxszj.pojo.sap.YdmbTb;
import com.jxszj.pojo.sap.YdmbTbExample;
import com.jxszj.pojo.sap.YdmbTbExample.Criteria;
import com.jxszj.service.sap.IYdmbService;
import com.jxszj.utils.DateUtils;
import com.jxszj.utils.ExcelUtil;
import com.jxszj.utils.JDYAPIUtils;
import com.jxszj.utils.ObjectUtils;

@Service
public class YdmbServiceImpl implements IYdmbService {

	@Autowired
	private YdmbTbMapper ydmbTbMapper;
	

	@Override
	public EUDataGridResult getYdmb(int page, int rows) {
		YdmbTbExample example = new YdmbTbExample();
		// 分页处理
		PageHelper.startPage(page, rows);
		List<YdmbTb> item = ydmbTbMapper.selectByExample(example);
		// 创建一个返回值对象
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(item);
		// 取记录总条数
		PageInfo<YdmbTb> pageInfo = new PageInfo<>(item);
		result.setTotal(pageInfo.getTotal());
		return result;
	}
	
	@Override
	public XSSFWorkbook getXSSFWorkbook(String excelUrl, String ppbm,String dd) {
		XSSFWorkbook wb = null;
		try {
			
			String DJ1_ENTRYID = "5d102d3721443528346561db";
			String DJ4_ENTRYID = "5d102d3f2144352834656205";
			String APIKEY = "uWEAujuxvzv5fMVifOvMRzlIJcPLgYkv";
			String APPID = "5cc110c3b3c41744aaa12b2e";
			
			final List<Map<String, Object>> condList = new ArrayList<Map<String, Object>>();
			if(!"".equals(ppbm)){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("field", "_widget_1545377155914");
				map.put("type", "text");
				map.put("method", "in");
				map.put("value", ppbm.split(","));
				condList.add(map);
			}
			if(!"".equals(dd)){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("field", "_widget_1548225231293");
				map.put("type", "text");
				map.put("method", "eq");
				map.put("value", dd);
				condList.add(map);
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("field", "_widget_1548316175994");
			map.put("type", "text");
			map.put("method", "eq");
			map.put("value", "启用");
			condList.add(map);
			Map<String, Object> map1 = new HashMap<String, Object>();
			map1.put("field", "_widget_1560850883479");
			map1.put("type", "text");
			map1.put("method", "eq");
			map1.put("value", "审批通过");
			condList.add(map1);
			Map<String, Object> filter = new HashMap<String, Object>() {
				{
					put("rel", "and");
					put("cond", condList);
				}
			};
			JDYAPIUtils api = new JDYAPIUtils(APPID, DJ1_ENTRYID, APIKEY);
			List<Map<String, Object>> kfsq = api.getAllFormData(null, filter);
			
			File fi = new File(excelUrl);
			// 读取excel模板
			wb = new XSSFWorkbook(new FileInputStream(fi));
			// 读取了模板内所有sheet内容
			XSSFSheet sheet = wb.getSheetAt(0);

			int rowIndex = 1;
			for (int i = 0; i < kfsq.size(); i++) {
				
				XSSFRow row = sheet.getRow(rowIndex);
				if (null == row) {
					row = sheet.createRow(rowIndex);
				}

				XSSFCell cell0 = row.getCell(0);
				if (null == cell0) {
					cell0 = row.createCell(0);
				}
				cell0.setCellValue(kfsq.get(i).get("_widget_1543814802008").toString());

				XSSFCell cell1 = row.getCell(1);
				if (null == cell1) {
					cell1 = row.createCell(1);
				}
				cell1.setCellValue(kfsq.get(i).get("_widget_1543814801964").toString());

				XSSFCell cell2 = row.getCell(2);
				if (null == cell2) {
					cell2 = row.createCell(2);
				}
				cell2.setCellValue(kfsq.get(i).get("_widget_1545377155914").toString());

				XSSFCell cell3 = row.getCell(3);
				if (null == cell3) {
					cell3 = row.createCell(3);
				}
				cell3.setCellValue(kfsq.get(i).get("_widget_1548225231293").toString());

				XSSFCell cell4 = row.getCell(4);
				if (null == cell4) {
					cell4 = row.createCell(4);
				}
				cell4.setCellValue(kfsq.get(i).get("_widget_1548316175994").toString());
				
				XSSFCell cell5 = row.getCell(5);
				if (null == cell5) {
					cell5 = row.createCell(5);
				}
				cell5.setCellValue("");
				
				rowIndex++;
			}

		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wb;
	}

	@Override
	public int uploadYdmbExcel(HttpServletRequest request, HttpServletResponse response, MultipartFile file)
			throws Exception {
		InputStream in = null;
		List<List<Object>> listob = null;
		int num=0;
		try {
			in = file.getInputStream();
			listob = new ExcelUtil().getBankListByExcel(in, file.getOriginalFilename());
			List<YdmbTb > ydmbTbs =new ArrayList<YdmbTb>();
			List<String> list=new ArrayList<>();
			for (int i = 1; i < listob.size(); i++) {
				//将Excel中的数据提出来
				YdmbTb ydmbTb=new YdmbTb();
				ydmbTb.setJxsmc(listob.get(i).get(0).toString());
				ydmbTb.setJxsbm(listob.get(i).get(1).toString());
				ydmbTb.setPpbm(listob.get(i).get(2).toString());
				ydmbTb.setDd(listob.get(i).get(3).toString());
				ydmbTb.setYyzt(listob.get(i).get(4).toString());
				ydmbTb.setBymbhk(ObjectUtils.getObjectToDouble(listob.get(i).get(5)));
				ydmbTb.setDrsj(DateUtils.getNowDateToString());
				ydmbTbs.add(ydmbTb);
				list.add(listob.get(i).get(2).toString());
			}
			//先将历史数据删除
			YdmbTbExample example = new YdmbTbExample();
			Criteria criteria=example.createCriteria();
			list=list.stream().distinct().collect(Collectors.toList());
			criteria.andPpbmIn(list);
			ydmbTbMapper.deleteByExample(example);
			//批量添加
			num=ydmbTbMapper.insertByBatch(ydmbTbs);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return num;
	}

	@Override
	public List<YdmbTb> getYdmbs() {
		YdmbTbExample example = new YdmbTbExample();
		List<YdmbTb> item = ydmbTbMapper.selectByExample(example);
		return item;
	}
	
	public int addData(List<YdmbTb > ydmbTbs){
		YdmbTbExample example = new YdmbTbExample();
		ydmbTbMapper.deleteByExample(example);
		int num=ydmbTbMapper.insertByBatch(ydmbTbs);
		return num;
	}
	
}

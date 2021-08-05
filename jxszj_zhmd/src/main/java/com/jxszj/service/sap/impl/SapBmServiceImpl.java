package com.jxszj.service.sap.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

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
import com.jxszj.mapper.sap.SapBmTbMapper;
import com.jxszj.pojo.EUDataGridResult;
import com.jxszj.pojo.sap.SapBmTb;
import com.jxszj.pojo.sap.SapBmTbExample;
import com.jxszj.pojo.sap.SapBmTbExample.Criteria;
import com.jxszj.service.sap.ISapBmService;
import com.jxszj.utils.DateUtils;
import com.jxszj.utils.ExcelUtil;
import com.jxszj.utils.MessageResult;
import com.jxszj.utils.ObjectUtils;

@Service
public class SapBmServiceImpl implements ISapBmService {

	@Autowired
	private SapBmTbMapper sapBmTbMapper;

	@Override
	public EUDataGridResult getSapBmList(int page, int rows,String newWlbm,String newName,String oldWlbm,String oldName,String bom) {
		SapBmTbExample example = new SapBmTbExample();
		Criteria criteria=example.createCriteria();
		if(newWlbm!=null&&!("".equals(newWlbm))){
			criteria.andNewWlbmLike(newWlbm+"%");
		}
		if(newName!=null&&!("".equals(newName))){
			criteria.andNewNameLike("%"+newName+"%");
		}
		if(oldWlbm!=null&&!("".equals(oldWlbm))){
			criteria.andOldWlbmLike("%"+oldWlbm+"%");
		}
		if(oldName!=null&&!("".equals(oldName))){
			criteria.andOldNameLike("%"+oldName+"%");
		}
		if(bom!=null&&!("".equals(bom))){
			criteria.andBomLike("%"+bom+"%");
		}
		criteria.andStatusEqualTo("n");
		// 分页处理
		PageHelper.startPage(page, rows);
		List<SapBmTb> item = sapBmTbMapper.selectByExampleGroup(example);
		// 创建一个返回值对象
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(item);
		// 取记录总条数
		PageInfo<SapBmTb> pageInfo = new PageInfo<>(item);
		result.setTotal(pageInfo.getTotal());
		return result;
	}
	
	
	@Override
	public EUDataGridResult getSapBmRemList(int page, int rows,String newWlbm,String newName,String oldWlbm,String oldName,String bom) {
		SapBmTbExample example = new SapBmTbExample();
		Criteria criteria=example.createCriteria();
		if(newWlbm!=null&&!("".equals(newWlbm))){
			criteria.andNewWlbmLike(newWlbm+"%");
		}
		if(newName!=null&&!("".equals(newName))){
			criteria.andNewNameLike("%"+newName+"%");
		}
		if(oldWlbm!=null&&!("".equals(oldWlbm))){
			criteria.andOldWlbmLike("%"+oldWlbm+"%");
		}
		if(oldName!=null&&!("".equals(oldName))){
			criteria.andOldNameLike("%"+oldName+"%");
		}
		if(bom!=null&&!("".equals(bom))){
			criteria.andBomLike("%"+bom+"%");
		}
		criteria.andStatusEqualTo("y");
		// 分页处理
		PageHelper.startPage(page, rows);
		List<SapBmTb> item = sapBmTbMapper.selectByExampleGroup(example);
		// 创建一个返回值对象
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(item);
		// 取记录总条数
		PageInfo<SapBmTb> pageInfo = new PageInfo<>(item);
		result.setTotal(pageInfo.getTotal());
		return result;
	}

	@Override
	public List<SapBmTb> getSapBmListsByNewWlbm(String newWlbm) {
		SapBmTbExample example = new SapBmTbExample();
		Criteria criteria = example.createCriteria();
		criteria.andNewWlbmEqualTo(newWlbm);
		List<SapBmTb> item = sapBmTbMapper.selectByExample(example);
		return item;
	}
	
	@Override
	public List<SapBmTb> getSapBmListsByNewName(String newName) {
		SapBmTbExample example = new SapBmTbExample();
		Criteria criteria = example.createCriteria();
		criteria.andNewNameEqualTo(newName);
		List<SapBmTb> item = sapBmTbMapper.selectByExample(example);
		return item;
	}
	
	@Override
	public List<SapBmTb> getSapBmListsByOldWlbm(String oldWlbm) {
		SapBmTbExample example = new SapBmTbExample();
		Criteria criteria = example.createCriteria();
		criteria.andOldWlbmEqualTo(oldWlbm);
		List<SapBmTb> item = sapBmTbMapper.selectByExample(example);
		return item;
	}
	
	@Override
	public List<SapBmTb> getSapBmListsByOldName(String oldName) {
		SapBmTbExample example = new SapBmTbExample();
		Criteria criteria = example.createCriteria();
		criteria.andOldNameEqualTo(oldName);
		List<SapBmTb> item = sapBmTbMapper.selectByExample(example);
		return item;
	}

	@Override
	public int insertSapBm(SapBmTb sapBmTb) {
		int i = sapBmTbMapper.insert(sapBmTb);
		return i;
	}

	@Override
	public int updateSapBm(SapBmTb sapBmTb) {
		int i = sapBmTbMapper.updateByPrimaryKey(sapBmTb);
		return i;
	}

	@Override
	public int deleteSapBm(List<Integer> id) {
		SapBmTbExample example = new SapBmTbExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdIn(id);
		int i = sapBmTbMapper.deleteByExample(example);
		return i;
	}
	
	@Override
	public int deleteAllSapBm() {
		int i = sapBmTbMapper.deleteAll();
		return i;
	}
	
	@Override
	public int removeSapBm(List<Integer> id) {
		SapBmTbExample example = new SapBmTbExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdIn(id);
		List<SapBmTb> list= sapBmTbMapper.selectByExample(example);
		for (int j = 0; j < list.size(); j++) {
			list.get(j).setStatus("y");
		}
		int i=sapBmTbMapper.updateBatch(list);
		return i;
	}

	@Override
	public int restoreSapBm(List<Integer> id) {
		SapBmTbExample example = new SapBmTbExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdIn(id);
		List<SapBmTb> list= sapBmTbMapper.selectByExample(example);
		for (int j = 0; j < list.size(); j++) {
			list.get(j).setStatus("n");
		}
		int i=sapBmTbMapper.updateBatch(list);
		return i;
	}
	
	@Override
	public MessageResult uploadExcel(HttpServletRequest request, HttpServletResponse response, MultipartFile file) throws Exception {
		InputStream in = null;
		List<List<Object>> listob = null;
		try {
			in = file.getInputStream();
			listob = new ExcelUtil().getBankListByExcel(in, file.getOriginalFilename());
			String wlbm=ObjectUtils.getString(listob.get(0).get(0));
			if(!"新物料编码".equals(wlbm)){
				return MessageResult.build(null,"请使用下载的Excel模板进行上传！");
			}
			
			if(duplicateList(listob,0)){
				return MessageResult.build(null,"添加失败，在Excel中含有相同的新物料编码，请检查重新上传！");
			}else if(duplicateList(listob,1)){
				return MessageResult.build(null,"添加失败，在Excel中含有相同的新物料名称，请检查重新上传！");
			}else if(duplicateList(listob,2)){
				return MessageResult.build(null,"添加失败，在Excel中含有相同的旧物料编码，请检查重新上传！");
			}else if(duplicateList(listob,3)){
				return MessageResult.build(null,"添加失败，在Excel中含有相同的旧物料名称，请检查重新上传！");
			}else if(duplicateLists(listob)){
				return MessageResult.build(null,"添加失败，在Excel中含有相同的新旧物料编码，请检查重新上传！");
			}
			
			SapBmTbExample example = new SapBmTbExample();
			List<SapBmTb> item = sapBmTbMapper.selectByExampleGroup(example);
			List<String> newWlbmlists =new ArrayList<String>();
			List<String> newNamelists =new ArrayList<String>();
			List<String> oldWlbmlists =new ArrayList<String>();
			List<String> oldNamelists =new ArrayList<String>();
			for (int i = 1; i < listob.size(); i++) {
				for (int j = 0; j < item.size(); j++) {
					if(ObjectUtils.getString(listob.get(i).get(0)).equals(item.get(j).getNewWlbm())){
						newWlbmlists.add(String.valueOf(i+1));
					}
					if(ObjectUtils.getString(listob.get(i).get(1)).equals(item.get(j).getNewName())){
						newNamelists.add(String.valueOf(i+1));
					}
					if(ObjectUtils.getString(listob.get(i).get(2)).equals(item.get(j).getOldWlbm()) && !"".equals(ObjectUtils.getString(listob.get(i).get(2))) && !"".equals(item.get(j).getOldWlbm())){
						oldWlbmlists.add(String.valueOf(i+1));
					}
					if(ObjectUtils.getString(listob.get(i).get(3)).equals(item.get(j).getOldName()) && !"".equals(ObjectUtils.getString(listob.get(i).get(3))) && !"".equals(item.get(j).getOldName())){
						oldNamelists.add(String.valueOf(i+1));
					}
				}
			}
			if(newWlbmlists.size()>0){
				return MessageResult.build(null,"添加失败，Excel中第"+String.join("，", newWlbmlists)+"行的新物料编码已存在，请检查重新上传");
			}else if(newNamelists.size()>0){
				return MessageResult.build(null,"添加失败，Excel中第"+String.join("，", newNamelists)+"行的新物料名称已存在，请检查重新上传");
			}else if(oldWlbmlists.size()>0){
				return MessageResult.build(null,"添加失败，Excel中第"+String.join("，", oldWlbmlists)+"行的旧物料编码已存在，请检查重新上传");
			}else if(oldNamelists.size()>0){
				return MessageResult.build(null,"添加失败，Excel中第"+String.join("，", oldNamelists)+"行的旧物料名称已存在，请检查重新上传");
			}
			
			List<SapBmTb> list=new ArrayList<SapBmTb>();
			for (int i = 1; i < listob.size(); i++) {
				SapBmTb sapBm=new SapBmTb();
				sapBm.setNewWlbm(ObjectUtils.getString(listob.get(i).get(0)));
				sapBm.setNewName(ObjectUtils.getString(listob.get(i).get(1)));
				sapBm.setOldWlbm(ObjectUtils.getString(listob.get(i).get(2)));
				sapBm.setOldName(ObjectUtils.getString(listob.get(i).get(3)));
				sapBm.setBom(ObjectUtils.getString(listob.get(i).get(4)));
				sapBm.setCreatetime(DateUtils.getNowDateToString());
				sapBm.setStatus("n");
				list.add(sapBm);
			}
			int num=sapBmTbMapper.insertByBatch(list);
			return MessageResult.build(200,"成功添加"+num+"条数据");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MessageResult.build(null,"上传Excel失败,请重试！");
	}
	
	@Override
	public XSSFWorkbook getXSSFWorkbook(String excelUrl) {
		XSSFWorkbook wb = null;
		try {
			SapBmTbExample example = new SapBmTbExample();
			List<SapBmTb> item = sapBmTbMapper.selectByExampleGroup(example);

			File fi = new File(excelUrl);
			// 读取excel模板
			wb = new XSSFWorkbook(new FileInputStream(fi));
			// 读取了模板内所有sheet内容
			XSSFSheet sheet = wb.getSheetAt(0);

			int rowIndex = 1;
			for (SapBmTb sapBmTb : item) {
				XSSFRow row = sheet.getRow(rowIndex);
				if (null == row) {
					row = sheet.createRow(rowIndex);
				}

				XSSFCell cell0 = row.getCell(0);
				if (null == cell0) {
					cell0 = row.createCell(0);
				}
				cell0.setCellValue(sapBmTb.getNewWlbm());

				XSSFCell cell1 = row.getCell(1);
				if (null == cell1) {
					cell1 = row.createCell(1);
				}
				cell1.setCellValue(sapBmTb.getNewName());

				XSSFCell cell2 = row.getCell(2);
				if (null == cell2) {
					cell2 = row.createCell(2);
				}
				cell2.setCellValue(sapBmTb.getOldWlbm());

				XSSFCell cell3 = row.getCell(3);
				if (null == cell3) {
					cell3 = row.createCell(3);
				}
				cell3.setCellValue(sapBmTb.getOldName());

				XSSFCell cell4 = row.getCell(4);
				if (null == cell4) {
					cell4 = row.createCell(4);
				}
				cell4.setCellValue(sapBmTb.getBom());
				rowIndex++;
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wb;
	}
	
	
	/**
	 * 
	 *<pre>
	 *<b>.</b>
	 *<b>Description:</b> 
	 *    判断list是否有重复的元素
	 *<b>Author:</b> yanwei
	 *<b>Date:</b> 2020年5月13日 上午10:44:17
	 *@param list
	 *@param index 取出哪一列元素
	 *@return
	 *</pre>
	 */
	public boolean duplicateList(List<List<Object>> list ,int index){
		boolean mark=false;
		List<String> lists=new ArrayList<String>();
		for (int i = 0; i < list.size() ; i++) {
			if(!"".equals(ObjectUtils.getString(list.get(i).get(index)))){
				lists.add(ObjectUtils.getString(list.get(i).get(index)));
			}
		}
		HashSet<String> set = new HashSet<String>(lists);
		if(set.size() != lists.size()){
			mark=true;
		}
		return mark;
	}
	
	/**
	 * 
	 *<pre>
	 *<b>.</b>
	 *<b>Description:</b> 
	 *    判断两个list是否有重复的元素
	 *<b>Author:</b> yanwei
	 *<b>Date:</b> 2020年5月13日 上午10:44:17
	 *@param list
	 *@return
	 *</pre>
	 */
	public boolean duplicateLists(List<List<Object>> list){
		boolean mark=false;
		List<String> list1=new ArrayList<String>();
		for (int i = 0; i < list.size() ; i++) {
			if(!"".equals(ObjectUtils.getString(list.get(i).get(0)))){
				list1.add(ObjectUtils.getString(list.get(i).get(0)));
			}
		}
		List<String> list2=new ArrayList<String>();
		for (int i = 0; i < list.size() ; i++) {
			if(!"".equals(ObjectUtils.getString(list.get(i).get(2)))){
				list2.add(ObjectUtils.getString(list.get(i).get(2)));
			}
		}
		System.out.println(Collections.disjoint(list1, list2));
		if(Collections.disjoint(list1, list2)==false){
			mark=true;
		}
		return mark;
	}

}

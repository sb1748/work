package com.jxszj.service.sap.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxszj.mapper.sap.SapWlTbMapper;
import com.jxszj.pojo.EUDataGridResult;
import com.jxszj.pojo.sap.SapWlTb;
import com.jxszj.pojo.sap.SapWlTbExample;
import com.jxszj.service.sap.ISapWllbService;

@Service
public class SapWllbServiceImpl implements ISapWllbService {

	@Autowired
	private SapWlTbMapper sapWlTbMapper;
	
	
	@Override
	public EUDataGridResult getWlbmList(int page, int rows) {
		SapWlTbExample example=new SapWlTbExample();
		// 分页处理
		PageHelper.startPage(page, rows);
		List<SapWlTb> list=sapWlTbMapper.selectByExample(example);
//		list = getTree(list);
		// 创建一个返回值对象
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(list);
		// 取记录总条数
		PageInfo<SapWlTb> pageInfo = new PageInfo<>(list);
		result.setTotal(pageInfo.getTotal());
		return result;
	}
	
	@Override
	public List<SapWlTb> getWlbmLists() {
		SapWlTbExample example=new SapWlTbExample();
		List<SapWlTb> list=sapWlTbMapper.selectByExample(example);
		List<SapWlTb> newList = getTree(list);
		return newList;
	}
	
    public List<SapWlTb> getTree(List<SapWlTb> list)
    {
        List<SapWlTb> nodeList = new ArrayList<SapWlTb>();
        for (SapWlTb f : list)
        {
            boolean mark = false;
            for (SapWlTb f2 : list)
            {
                if (f2.getId().equals(f.getPid()) )
                {
                    mark = true;
                    if (f2.getChildren() == null)
                    {
                        f2.setChildren(new ArrayList<SapWlTb>());
                    }
                    f2.getChildren().add(f);
                    break;
                }
            }
            if (!mark)
            {
                nodeList.add(f);
            }
        }
        return nodeList;
    }
	

	@Override
	public int insertWlbm(SapWlTb wl) {
		int i=sapWlTbMapper.insert(wl);
		return i;
	}

	@Override
	public int deleteWlbm(List<Long> ids) {
		int i=sapWlTbMapper.deleteByIdOrPid(ids);
		return i;
	}

	@Override
	public int updateWlbm(SapWlTb wl) {
		int i=sapWlTbMapper.updateByPrimaryKey(wl);
		return i;
	}

}

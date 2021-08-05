package com.jxszj.mapper.sap;

import com.jxszj.pojo.sap.SapBmTb;
import com.jxszj.pojo.sap.SapBmTbExample;
import java.util.List;
public interface SapBmTbMapper { 

    int deleteByExample(SapBmTbExample example);

    int insert(SapBmTb record);

    List<SapBmTb> selectByExample(SapBmTbExample example);
    
    List<SapBmTb> selectByExampleGroup(SapBmTbExample example);

    int updateByPrimaryKey(SapBmTb record);
    
    int insertByBatch(List<SapBmTb > sapBmList);
    
    int updateBatch(List<SapBmTb > sapBmList);
    
    int deleteAll();
}
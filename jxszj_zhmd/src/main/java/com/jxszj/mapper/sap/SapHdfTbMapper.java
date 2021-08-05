package com.jxszj.mapper.sap;

import com.jxszj.pojo.sap.SapFwfTb;
import com.jxszj.pojo.sap.SapHdfTb;
import com.jxszj.pojo.sap.SapHdfTbExample;

import java.util.List;

public interface SapHdfTbMapper {

    int deleteByExample(SapHdfTbExample example);

    int insert(SapHdfTb record);

    List<SapHdfTb> selectByExample(SapHdfTbExample example);

    int updateByPrimaryKey(SapFwfTb record);
    
    int insertByBatch(List<SapHdfTb > sapHdfTbList);
}
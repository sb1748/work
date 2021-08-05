package com.jxszj.mapper.sap;

import com.jxszj.pojo.sap.SapFwfTb;
import com.jxszj.pojo.sap.SapFwfTbExample;
import java.util.List;

public interface SapFwfTbMapper {

    int deleteByExample(SapFwfTbExample example);

    int insert(SapFwfTb record);

    List<SapFwfTb> selectByExample(SapFwfTbExample example);

    int updateByPrimaryKey(SapFwfTb record);
    
    int insertByBatch(List<SapFwfTb > sapFwfTbList);
}
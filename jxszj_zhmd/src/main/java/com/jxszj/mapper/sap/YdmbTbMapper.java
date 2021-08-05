package com.jxszj.mapper.sap;

import com.jxszj.pojo.sap.YdmbTb;
import com.jxszj.pojo.sap.YdmbTbExample;
import java.util.List;

public interface YdmbTbMapper {

    int deleteByExample(YdmbTbExample example);

    List<YdmbTb> selectByExample(YdmbTbExample example);
    
    int insertByBatch(List<YdmbTb > ydmbTb);
    
    void truncateTable();

}
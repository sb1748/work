package com.jxszj.mapper.sap;

import com.jxszj.pojo.sap.SapScrkwlTb;
import com.jxszj.pojo.sap.SapScrkwlTbExample;
import java.util.List;

public interface SapScrkwlTbMapper {

    List<SapScrkwlTb> selectByExample(SapScrkwlTbExample example);
    
    int insertByBatch(List<SapScrkwlTb> sapScrkwlTb);
    
    int deleteByExample(SapScrkwlTbExample example);
}
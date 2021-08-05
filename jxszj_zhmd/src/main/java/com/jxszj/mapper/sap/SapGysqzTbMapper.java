package com.jxszj.mapper.sap;

import com.jxszj.pojo.sap.SapGysqzTb;
import com.jxszj.pojo.sap.SapGysqzTbExample;

import java.util.List;

public interface SapGysqzTbMapper {

    List<SapGysqzTb> selectByExample(SapGysqzTbExample example);
    
    int insertByBatch(List<SapGysqzTb > sapGysqzTb);

    int updateByPrimaryKey(SapGysqzTb record);
    
    int deleteByExample(SapGysqzTbExample example);

}
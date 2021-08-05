package com.jxszj.mapper.sap;

import com.jxszj.pojo.sap.SapWlpzqdCqTb;
import com.jxszj.pojo.sap.SapWlpzqdCqTbExample;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SapWlpzqdCqTbMapper {

    List<SapWlpzqdCqTb> selectByExample(SapWlpzqdCqTbExample example);

    int updateByExample(@Param("record") SapWlpzqdCqTb record, @Param("example") SapWlpzqdCqTbExample example);
    
    int insertByBatch(List<SapWlpzqdCqTb > sapWlpzqdCqTb);
    
    void createWlpzqdView(String currentDate);
    
    void dropView();
    
    void truncateTable();
    
    int updateWlpzqd();
}
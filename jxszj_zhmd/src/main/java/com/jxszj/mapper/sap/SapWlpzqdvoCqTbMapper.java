package com.jxszj.mapper.sap;

import com.jxszj.pojo.sap.SapWlpzqdvoCqTb;
import com.jxszj.pojo.sap.SapWlpzqdvoCqTbExample;
import java.util.List;

public interface SapWlpzqdvoCqTbMapper {

    int deleteByExample(SapWlpzqdvoCqTbExample example);

    List<SapWlpzqdvoCqTb> selectByExample(SapWlpzqdvoCqTbExample example);
    
    int insertByBatch(List<SapWlpzqdvoCqTb > sapWlpzqdvoCqTb);
    
    void truncateTable();
}
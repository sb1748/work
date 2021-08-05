package com.jxszj.mapper.sap;

import com.jxszj.pojo.sap.SapJdywlTb;
import com.jxszj.pojo.sap.SapJdywlTbExample;

import java.util.List;

public interface SapJdywlTbMapper {
	
	int deleteByExample(SapJdywlTbExample example);

    List<SapJdywlTb> selectByExample(SapJdywlTbExample example);
    
    int insertByBatch(List<SapJdywlTb> sapJdywlTb);
    
    void truncateTable();

}
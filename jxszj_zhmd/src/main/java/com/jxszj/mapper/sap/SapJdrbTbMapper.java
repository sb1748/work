package com.jxszj.mapper.sap;

import com.jxszj.pojo.sap.SapJdrbTb;
import com.jxszj.pojo.sap.SapJdrbTbExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface SapJdrbTbMapper {

    List<SapJdrbTb> selectByExample(SapJdrbTbExample example);

    int insertByBatch(List<SapJdrbTb> sapJdrbTb);
    
    int deleteByExample(SapJdrbTbExample example);
    
    int updateByExample(@Param("record") SapJdrbTb record, @Param("example") SapJdrbTbExample example);
}
package com.jxszj.mapper.sap;

import com.jxszj.pojo.sap.SapPriceTb;
import com.jxszj.pojo.sap.SapPriceTbExample;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SapPriceTbMapper {

    int deleteByExample(SapPriceTbExample example);

    int insert(SapPriceTb record);

    List<SapPriceTb> selectByExample(SapPriceTbExample example);
    
    int updateByExample(@Param("record") SapPriceTb record, @Param("example") SapPriceTbExample example);
    
    int updateByPrimaryKey(SapPriceTb record);
    
    SapPriceTb selectByPrimaryKey(String id);
    
    int insertByBatch(List<SapPriceTb > sapPriceTb);
    
    int updateTbzt(String id);
}
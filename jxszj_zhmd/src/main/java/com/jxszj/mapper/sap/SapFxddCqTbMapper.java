package com.jxszj.mapper.sap;

import com.jxszj.pojo.sap.SapFxddCqTb;
import com.jxszj.pojo.sap.SapFxddCqTbExample;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SapFxddCqTbMapper {

    List<SapFxddCqTb> selectByExample(SapFxddCqTbExample example);

    int updateByExample(@Param("record") SapFxddCqTb record, @Param("example") SapFxddCqTbExample example);
    
    int insertByBatch(List<SapFxddCqTb > sapFxddCqTb);
    
    List<SapFxddCqTb> selectByFxdd(List<String> fxdds);
    
    void truncateTable();
}
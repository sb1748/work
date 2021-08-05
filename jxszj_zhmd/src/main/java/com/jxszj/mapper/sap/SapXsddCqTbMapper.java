package com.jxszj.mapper.sap;

import com.jxszj.pojo.sap.SapXsddCqTb;
import com.jxszj.pojo.sap.SapXsddCqTbExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface SapXsddCqTbMapper {

    List<SapXsddCqTb> selectByExample(SapXsddCqTbExample example);
    
    SapXsddCqTb selectByPrimaryKey(String id);

    int updateByPrimaryKey(SapXsddCqTb record);
    
    int insertByBatch(List<SapXsddCqTb > sapXsddCqTb);
    
    SapXsddCqTb selectMaxjhrq(String xspz);
//    List<SapXsddCqTb> selectMaxjhrq();
    
    int updateNjhrq(SapXsddCqTb sapXsddCqTb);
    
    int deleteByExample(SapXsddCqTbExample example);
    
    void createXsddView(@Param("currentDate") String currentDate,@Param("pastDate") String pastDate);
    
    void dropView();
    
    //查询没有绑单的销售订单
    List<Map<String,Object>> selectCqXsdd1();
    
    //查询被绑单，但绑单数量小于订单数
    List<Map<String,Object>> selectCqXsdd2();
    
    void truncateTable();
}
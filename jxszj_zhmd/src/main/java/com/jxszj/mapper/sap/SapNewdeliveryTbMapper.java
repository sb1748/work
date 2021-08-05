package com.jxszj.mapper.sap;

import com.jxszj.pojo.sap.SapDeliveryTb;

import java.util.List;

public interface SapNewdeliveryTbMapper {
    
    int insertByBatch(List<SapDeliveryTb> sapDeliveryTb);
    
    List<SapDeliveryTb> selectByGroupById();
    
    void truncateTable();
    
    List<SapDeliveryTb> selectCompare1();
    
    List<SapDeliveryTb> selectCompare2();
}
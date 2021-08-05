package com.jxszj.mapper.sap;

import com.jxszj.pojo.sap.SapDeliveryTb;
import java.util.List;

public interface SapOlddeliveryTbMapper {
	
    int insertByBatch(List<SapDeliveryTb> sapDeliveryTb);
    
    void truncateTable();
    
    List<SapDeliveryTb> selectCompare();
}
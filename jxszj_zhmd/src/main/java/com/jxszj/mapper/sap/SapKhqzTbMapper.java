package com.jxszj.mapper.sap;

import com.jxszj.pojo.sap.SapKhqzTb;
import com.jxszj.pojo.sap.SapKhqzTbExample;
import java.util.List;

public interface SapKhqzTbMapper {

    List<SapKhqzTb> selectByExample(SapKhqzTbExample example);

    int insertByBatch(List<SapKhqzTb > sapKhqzTb);

    int updateByPrimaryKey(SapKhqzTb record);
    
    int deleteByExample(SapKhqzTbExample example);
}
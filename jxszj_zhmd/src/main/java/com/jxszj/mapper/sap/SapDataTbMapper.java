package com.jxszj.mapper.sap;

import com.jxszj.pojo.sap.SapDataTb;
import com.jxszj.pojo.sap.SapDataTbExample;
import java.util.List;

public interface SapDataTbMapper {

    int deleteByExample(SapDataTbExample example);

    int insert(SapDataTb record);

    List<SapDataTb> selectByExample(SapDataTbExample example);

    int updateByPrimaryKey(SapDataTb record);
}
package com.jxszj.mapper.sap;

import java.util.List;

import com.jxszj.pojo.sap.SapScpgTb;
import com.jxszj.pojo.sap.SapScpgTbExample;

public interface SapScpgTbMapper {

    int saveOrUpdate(List<SapScpgTb> record);

    List<SapScpgTb> selectByExample(SapScpgTbExample example);

    int updateByPrimaryKey(SapScpgTb record);
}
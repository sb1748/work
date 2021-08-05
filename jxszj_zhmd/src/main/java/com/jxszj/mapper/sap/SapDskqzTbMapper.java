package com.jxszj.mapper.sap;

import com.jxszj.pojo.sap.SapDskqzTb;
import com.jxszj.pojo.sap.SapDskqzTbExample;

import java.util.List;

public interface SapDskqzTbMapper {

    List<SapDskqzTb> selectByExample(SapDskqzTbExample example);

    int insertByBatch(List<SapDskqzTb > sapDskqzTbList);

    int updateByPrimaryKey(SapDskqzTb record);
    
    int deleteByExample(SapDskqzTbExample example);
}
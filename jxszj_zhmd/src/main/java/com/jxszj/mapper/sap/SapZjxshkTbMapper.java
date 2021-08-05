package com.jxszj.mapper.sap;

import com.jxszj.pojo.sap.SapZjxshkTb;
import com.jxszj.pojo.sap.SapZjxshkTbExample;
import java.util.List;

public interface SapZjxshkTbMapper {

    int deleteByExample(SapZjxshkTbExample example);

    int insert(SapZjxshkTb record);

    List<SapZjxshkTb> selectByExample(SapZjxshkTbExample example);

    int updateByPrimaryKey(SapZjxshkTb record);
    
    int insertByBatch(List<SapZjxshkTb > sapZjxshkTbList);
}
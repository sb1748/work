package com.jxszj.mapper.sap;

import com.jxszj.pojo.sap.DailyuserTb;
import com.jxszj.pojo.sap.DailyuserTbExample;
import java.util.List;

public interface DailyuserTbMapper {

    int deleteByPrimaryKey(String userId);
    
    int deleteByExample(DailyuserTbExample example);

    int insert(DailyuserTb record);

    List<DailyuserTb> selectByExample(DailyuserTbExample example);

    DailyuserTb selectByPrimaryKey(String userId);

}
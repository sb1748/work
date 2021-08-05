package com.jxszj.mapper.sap;

import com.jxszj.pojo.sap.SapWlTb;
import com.jxszj.pojo.sap.SapWlTbExample;
import java.util.List;

public interface SapWlTbMapper {

    int deleteByIdOrPid(List<Long> ids);

    int insert(SapWlTb record);

    List<SapWlTb> selectByExample(SapWlTbExample example);

    int updateByPrimaryKey(SapWlTb record);
}
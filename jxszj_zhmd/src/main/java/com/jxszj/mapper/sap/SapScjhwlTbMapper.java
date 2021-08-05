package com.jxszj.mapper.sap;

import com.jxszj.pojo.sap.SapScjhwlTb;
import com.jxszj.pojo.sap.SapScjhwlTbExample;

import java.util.List;

public interface SapScjhwlTbMapper {

    List<SapScjhwlTb> selectByExample(SapScjhwlTbExample example);

    int insertByBatch(List<SapScjhwlTb> sapScjhwlTb);
}
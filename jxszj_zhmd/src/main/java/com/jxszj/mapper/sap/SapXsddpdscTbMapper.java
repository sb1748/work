package com.jxszj.mapper.sap;

import com.jxszj.pojo.sap.SapXsddpdscTb;
import com.jxszj.pojo.sap.SapXsddpdscTbExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SapXsddpdscTbMapper {

    List<SapXsddpdscTb> selectByExample(SapXsddpdscTbExample example);
    
    int updateByExample(@Param("record") SapXsddpdscTb record, @Param("example") SapXsddpdscTbExample example);
}
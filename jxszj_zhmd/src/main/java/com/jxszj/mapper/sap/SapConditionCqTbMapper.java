package com.jxszj.mapper.sap;

import com.jxszj.pojo.sap.SapConditionCqTb;
import com.jxszj.pojo.sap.SapConditionCqTbExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SapConditionCqTbMapper {
    int countByExample(SapConditionCqTbExample example);

    int deleteByExample(SapConditionCqTbExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SapConditionCqTb record);

    int insertSelective(SapConditionCqTb record);

    List<SapConditionCqTb> selectByExample(SapConditionCqTbExample example);

    SapConditionCqTb selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SapConditionCqTb record, @Param("example") SapConditionCqTbExample example);

    int updateByExample(@Param("record") SapConditionCqTb record, @Param("example") SapConditionCqTbExample example);

    int updateByPrimaryKeySelective(SapConditionCqTb record);

    int updateByPrimaryKey(SapConditionCqTb record);
}
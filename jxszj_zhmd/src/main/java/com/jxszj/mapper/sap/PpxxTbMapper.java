package com.jxszj.mapper.sap;

import com.jxszj.pojo.sap.PpxxTb;
import com.jxszj.pojo.sap.PpxxTbExample;
import java.util.List;

public interface PpxxTbMapper {
    List<PpxxTb> selectByExample(PpxxTbExample example);
}
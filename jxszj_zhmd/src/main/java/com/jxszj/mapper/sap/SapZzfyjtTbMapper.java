package com.jxszj.mapper.sap;

import com.jxszj.pojo.sap.SapZzfyjtTb;
import com.jxszj.pojo.sap.SapZzfyjtTbExample;
import java.util.List;

public interface SapZzfyjtTbMapper {

    List<SapZzfyjtTb> selectByExample(SapZzfyjtTbExample example);
    
    int insertByBatch(List<SapZzfyjtTb > sapZzfyjtTb);
    
    int deleteByExample(SapZzfyjtTbExample example);

}
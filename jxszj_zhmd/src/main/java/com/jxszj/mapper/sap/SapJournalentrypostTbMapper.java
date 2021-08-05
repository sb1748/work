package com.jxszj.mapper.sap;

import com.jxszj.pojo.sap.SapJournalentrypostTb;
import com.jxszj.pojo.sap.SapJournalentrypostTbExample;
import java.util.List;

public interface SapJournalentrypostTbMapper {

    List<SapJournalentrypostTb> selectByExample(SapJournalentrypostTbExample example);
    
    int insertByBatch(List<SapJournalentrypostTb > sapJournalentrypostTbList);
    
    int updateByPrimaryKey(SapJournalentrypostTb record);
    
    int deleteByExample(SapJournalentrypostTbExample example);

}
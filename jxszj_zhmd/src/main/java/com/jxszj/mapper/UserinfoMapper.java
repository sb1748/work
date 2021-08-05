package com.jxszj.mapper;

import com.jxszj.pojo.Userinfo;
import com.jxszj.pojo.UserinfoExample;
import java.util.List;

public interface UserinfoMapper {

    List<Userinfo> selectByExample(UserinfoExample example);

    int updateByPrimaryKey(Userinfo record);
}
package com.ssm.demo.dao;

import com.ssm.demo.entity.UserData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDataMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserData record);

    int insertSelective(UserData record);

    UserData selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserData record);

    int updateByPrimaryKey(UserData record);

    List<UserData> query(@Param("begin") long begin, @Param("end") long end, @Param("moduleId") String moduleId);
}
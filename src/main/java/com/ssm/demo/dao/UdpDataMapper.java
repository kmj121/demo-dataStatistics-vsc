package com.ssm.demo.dao;

import com.ssm.demo.entity.UdpData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface UdpDataMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UdpData record);

    int insertSelective(UdpData record);

    UdpData selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UdpData record);

    int updateByPrimaryKey(UdpData record);

    List<UdpData> query(@Param("begin") long begin, @Param("end") long end, @Param("moduleId") String moduleId);
}
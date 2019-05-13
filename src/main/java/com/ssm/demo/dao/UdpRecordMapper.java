package com.ssm.demo.dao;

import com.ssm.demo.entity.UdpRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface UdpRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UdpRecord record);

    int insertSelective(UdpRecord record);

    UdpRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UdpRecord record);

    int updateByPrimaryKey(UdpRecord record);
}
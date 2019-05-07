package com.ssm.demo.service;

import com.ssm.demo.common.Constant;
import com.ssm.demo.dao.DictMapper;
import com.ssm.demo.dao.UserDataMapper;
import com.ssm.demo.dto.DataStatisticsQueryDto;
import com.ssm.demo.dto.DataStatisticsQueryDtoOut;
import com.ssm.demo.entity.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @Description
 * @Author Roge
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2019/5/7
 */
@Service
public class DataStatisticsService {
    @Autowired
    private DictMapper dictMapper;
    @Autowired
    private UserDataMapper userDataMapper;

    public DataStatisticsQueryDtoOut query(DataStatisticsQueryDto dto) {
        //日期转化为精确到秒的时间戳
        long begin = dto.getDayTime().getTime()/1000;
        long end = begin + Constant.ONE_DAY_IN_SECOND;

        //获取数据
        List<UserData> list = userDataMapper.query(begin, end, dto.getModuleId());

        //获取紫外线阈值
        String sunLabel = dictMapper.getValue(Constant.SUN_LABEL);

        //获取运动阈值
        String sportLabel = dictMapper.getValue(Constant.SPORT_LABEL);


        return null;
    }
}

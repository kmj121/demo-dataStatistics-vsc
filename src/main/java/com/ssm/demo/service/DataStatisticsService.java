package com.ssm.demo.service;

import com.ssm.demo.common.Constant;
import com.ssm.demo.dao.DictMapper;
import com.ssm.demo.dao.UdpDataMapper;
import com.ssm.demo.dto.DataStatisticsQueryDto;
import com.ssm.demo.dto.DataStatisticsQueryDtoOut;
import com.ssm.demo.entity.UdpData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @Description
 * @Author Roge
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2019/5/7
 */
@Service
@Transactional(isolation = Isolation.READ_COMMITTED)
public class DataStatisticsService {
    @Autowired
    private DictMapper dictMapper;
    @Autowired
    private UdpDataMapper udpDataMapper;

    public List<DataStatisticsQueryDtoOut> query(DataStatisticsQueryDto dto) {

        List<DataStatisticsQueryDtoOut> dtoOutList = new ArrayList<>();

        //日期（2019-01-01）转化为精确到秒的时间戳
        long begin = dto.getDayTime().getTime() / 1000;
        long end = begin + Constant.ONE_DAY_IN_SECOND;

        //获取数据
        List<UdpData> list = udpDataMapper.query(begin, end, dto.getModuleId());

        //获取紫外线阈值
        String sunData = dictMapper.getValue(Constant.SUN_LABEL);

        //获取运动阈值
        String sportData = dictMapper.getValue(Constant.SPORT_LABEL);

        long time = 0;

        for (long i = begin; i < end; i = i + 60) {
            List<UdpData> userDataList = new ArrayList<>();
            time++;

//            Map map = new HashMap<>();
            //取出每一分钟的数据，time值即为分钟数
//            long count2 = 1;
            for (UdpData udpData : list) {

                //取出time分钟内对应的数据（time从一天的第一分钟开始到最后一分钟），放入到一个list集合中。
                //将每分钟的有效的数据放入到一个Map中。
                if (udpData != null && udpData.getRecordTime() >= i && udpData.getRecordTime() < i + 60) {
                    userDataList.add(udpData);
                }

                //count2为分钟数，userDataList为该分钟内对应的数据
//                map.put(count2, userDataList);
//                count2++;
            }

            //取出每一分钟的数据，进行分析。
            //判断集合中的数据大小，如果少于30，则判定这分钟内没有数据，则状态为0
            DataStatisticsQueryDtoOut dtoOut = new DataStatisticsQueryDtoOut();
            double[] x = new double[userDataList.size()];
            double[] y = new double[userDataList.size()];
            double[] z = new double[userDataList.size()];
            if (userDataList.size() < 30) {
                dtoOut.setStatus(0);
                dtoOut.setMinute(time);
                dtoOutList.add(dtoOut);
            } else {
                //大于等于30，则基数为数据个数
                int count = 0;
                float sum = 0;
                for (UdpData userData2 : userDataList) {
                    x[count] = userData2.getSensorX();
                    y[count] = userData2.getSensorY();
                    z[count] = userData2.getSensorZ();

                    sum = sum + userData2.getUvsensor0();

                    count++;
                }
                //计算运动方差
                Double varianceX = Variance(x);
                Double varianceY = Variance(y);
                Double varianceZ = Variance(z);

                //计算紫外线平均数
                float average = sum / userDataList.size();

                //如果任一标准差大于阈值，则为运动，否则为静止
                Double parseDouble = Double.parseDouble(sportData);
                //判断室内和室外
                float parseFloat = Float.parseFloat(sunData);
                if (Math.sqrt(varianceX) > parseDouble || Math.sqrt(varianceY) > parseDouble || Math.sqrt(varianceZ) > parseDouble) {

                    //大于平均数，则为户外，否则为室内
                    if (average > parseFloat) {
                        dtoOut.setStatus(1);
                    } else {
                        dtoOut.setStatus(2);
                    }
                } else {
                    //大于平均数，则为户外，否则为室内
                    if (average > parseFloat) {
                        dtoOut.setStatus(3);
                    } else {
                        dtoOut.setStatus(4);
                    }
                }
                //分钟数
                dtoOut.setMinute(time);
                //将该对象添加到list中返回
                dtoOutList.add(dtoOut);
            }
        }
        return dtoOutList;
    }

    //方差s^2=[(x1-x)^2 +...(xn-x)^2]/n
    public static double Variance(double[] x) {
        int m = x.length;
        double sum = 0;
        for (int i = 0; i < m; i++) {//求和
            sum += x[i];
        }
        double dAve = sum / m;//求平均值
        double dVar = 0;
        for (int i = 0; i < m; i++) {//求方差
            dVar += (x[i] - dAve) * (x[i] - dAve);
        }
        return dVar / m;
    }

    public List<UdpData> listExport(String moduleId, Date dayTime) {
        //日期（2019-01-01）转化为精确到秒的时间戳
        long begin = dayTime.getTime() / 1000;
        long end = begin + Constant.ONE_DAY_IN_SECOND;
        List<UdpData> list = udpDataMapper.query(begin, end, moduleId);
        return list;
    }
}

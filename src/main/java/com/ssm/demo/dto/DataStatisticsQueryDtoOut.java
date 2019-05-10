package com.ssm.demo.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @Description
 * @Author Roge
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2019/2/21
 */
public class DataStatisticsQueryDtoOut {
    @ApiModelProperty(value = "0：没有数据，1：户外运动，2：室内运动，3：户外静止，4：室内静止")
    private Integer status;
    @ApiModelProperty(value = "分钟数")
    private long minute;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public long getMinute() {
        return minute;
    }

    public void setMinute(long minute) {
        this.minute = minute;
    }
}

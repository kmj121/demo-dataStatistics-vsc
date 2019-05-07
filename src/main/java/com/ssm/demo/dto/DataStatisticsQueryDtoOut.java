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
    private List<Integer> status;

    public List<Integer> getStatus() {
        return status;
    }

    public void setStatus(List<Integer> status) {
        this.status = status;
    }
}

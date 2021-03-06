package com.ssm.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Description
 * @Author Roge
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2019/2/21
 */
public class DataStatisticsQueryDto {
    @ApiModelProperty(value = "日期(以天为单位)", example = "2019-02-20", required = true)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date dayTime;
//    @ApiModelProperty(value = "日期(以小时为单位)", example = "2019-02-20 10", required = true)
//    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
//    private Date hourTime;
    @ApiModelProperty(value = "设备号", required = true)
    @NotNull
    @NotEmpty
    private String moduleId;

    public Date getDayTime() {
        return dayTime;
    }

    public void setDayTime(Date dayTime) {
        this.dayTime = dayTime;
    }

//    public Date getHourTime() {
//        return hourTime;
//    }
//
//    public void setHourTime(Date hourTime) {
//        this.hourTime = hourTime;
//    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }
}

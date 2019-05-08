package com.ssm.demo.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Description
 * @Author Roge
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2019/5/5
 */
public class ExportDtoOut {
    @ApiModelProperty(value = "文件名")
    private String fileName;
    @ApiModelProperty(value = "保存名")
    private String saveName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSaveName() {
        return saveName;
    }

    public void setSaveName(String saveName) {
        this.saveName = saveName;
    }
}

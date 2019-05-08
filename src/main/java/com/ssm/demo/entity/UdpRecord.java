package com.ssm.demo.entity;

import java.util.Date;

public class UdpRecord {
    private Integer id;

    private String udpMsg;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUdpMsg() {
        return udpMsg;
    }

    public void setUdpMsg(String udpMsg) {
        this.udpMsg = udpMsg;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
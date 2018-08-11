package com.bigbang.smartbutler.entity;

/**
 * Author: litte
 * Created on 2018/8/6  11:26
 * Project Name:Smartbutler
 * Package Name:com.bigbang.smartbutler.entity
 * Description: 物流信息
 * Copyright (c) 2018, gwl All Rights Reserved.
 */
public class LogisticInformation {

    /**
     * datetime : 2016-06-15 21:44:04
     * remark : 离开郴州市 发往长沙市【郴州市】
     * zone :
     */

    /* 物流事件发生的时间 */
    private String datetime;
    private String remark; /* 物流事件的描述 */
    private String zone; /* 快件当时所在区域，由于快递公司升级，现大多数快递不提供此信息 */

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }
}

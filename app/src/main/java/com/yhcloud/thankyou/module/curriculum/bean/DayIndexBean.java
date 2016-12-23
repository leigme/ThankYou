package com.yhcloud.thankyou.module.curriculum.bean;

/**
 * Created by Administrator on 2016/12/14.
 */

public class DayIndexBean {

    /**
     * name : 早自习
     * startTime : 09:00:00
     * endTime : 09:05:00
     * isCurrent : -1
     */

    private String name;
    private String startTime;
    private String endTime;
    private String isCurrent;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getIsCurrent() {
        return isCurrent;
    }

    public void setIsCurrent(String isCurrent) {
        this.isCurrent = isCurrent;
    }
}

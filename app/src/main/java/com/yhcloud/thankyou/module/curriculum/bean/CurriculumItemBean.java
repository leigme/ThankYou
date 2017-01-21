package com.yhcloud.thankyou.module.curriculum.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/1/18.
 */

public class CurriculumItemBean implements Serializable {

    private String startTime, title, endTime;
    private int type;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

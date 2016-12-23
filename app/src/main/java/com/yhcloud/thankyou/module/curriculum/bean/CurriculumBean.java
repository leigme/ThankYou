package com.yhcloud.thankyou.module.curriculum.bean;

/**
 * Created by Administrator on 2016/12/14.
 */

public class CurriculumBean {

    private String startTime, title, endTime, dayTitle;
    private boolean isHeader, isDay;

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

    public boolean isHeader() {
        return isHeader;
    }

    public void setHeader(boolean header) {
        isHeader = header;
    }

    public boolean isDay() {
        return isDay;
    }

    public void setDay(boolean day) {
        isDay = day;
    }

    public String getDayTitle() {
        return dayTitle;
    }

    public void setDayTitle(String dayTitle) {
        this.dayTitle = dayTitle;
    }
}

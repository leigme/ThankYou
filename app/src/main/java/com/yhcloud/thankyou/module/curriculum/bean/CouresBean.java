package com.yhcloud.thankyou.module.curriculum.bean;

/**
 * Created by Administrator on 2016/12/14.
 */

public class CouresBean {

    /**
     * type :
     * Id :
     * desc :
     * name :
     * Weekday : 1
     * isCurrent : -1
     * Weekdayname : 星期一
     */

    private String type;
    private String Id;
    private String desc;
    private String name;
    private int Weekday;
    private String isCurrent;
    private String Weekdayname;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeekday() {
        return Weekday;
    }

    public void setWeekday(int Weekday) {
        this.Weekday = Weekday;
    }

    public String getIsCurrent() {
        return isCurrent;
    }

    public void setIsCurrent(String isCurrent) {
        this.isCurrent = isCurrent;
    }

    public String getWeekdayname() {
        return Weekdayname;
    }

    public void setWeekdayname(String Weekdayname) {
        this.Weekdayname = Weekdayname;
    }
}

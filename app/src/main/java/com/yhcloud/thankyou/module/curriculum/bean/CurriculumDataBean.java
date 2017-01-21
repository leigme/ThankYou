package com.yhcloud.thankyou.module.curriculum.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/1/18.
 */

public class CurriculumDataBean implements Serializable {

    /**
     * Coures : [{"type":"","Id":"","desc":"","name":"","Weekday":1,"isCurrent":"-1","Weekdayname":"星期一"},{"type":"","Id":"","desc":"","name":"","Weekday":2,"isCurrent":"-1","Weekdayname":"星期二"},{"type":"","Id":"","desc":"","name":"","Weekday":3,"isCurrent":"-1","Weekdayname":"星期三"},{"type":"","Id":"","desc":"","name":"","Weekday":4,"isCurrent":"-1","Weekdayname":"星期四"},{"type":"","Id":"","desc":"","name":"","Weekday":5,"isCurrent":"-1","Weekdayname":"星期五"},{"type":"","Id":"","desc":"","name":"","Weekday":6,"isCurrent":"-1","Weekdayname":"星期六"},{"type":"","Id":"","desc":"","name":"","Weekday":7,"isCurrent":"-1","Weekdayname":"星期天"}]
     * LessonIndex : 1
     * dayIndex : {"name":"早自习","startTime":"09:00:00","endTime":"09:05:00","isCurrent":"-1"}
     */

    private int LessonIndex;
    private DayIndexBean dayIndex;
    private List<CouresBean> Coures;

    public int getLessonIndex() {
        return LessonIndex;
    }

    public void setLessonIndex(int LessonIndex) {
        this.LessonIndex = LessonIndex;
    }

    public DayIndexBean getDayIndex() {
        return dayIndex;
    }

    public void setDayIndex(DayIndexBean dayIndex) {
        this.dayIndex = dayIndex;
    }

    public List<CouresBean> getCoures() {
        return Coures;
    }

    public void setCoures(List<CouresBean> Coures) {
        this.Coures = Coures;
    }

    public static class DayIndexBean {
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

    public static class CouresBean {
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
}

package com.yhcloud.thankyou.module.todayrecipes.bean;

/**
 * Created by leig on 2017/2/16.
 */

public class TodayRecipesTimeBean {

    /**
     * Id : 10 [学期ID]
     * Title : 2015-2016学年上学期 [学期Title]
     * SchoolId : 12
     * weekNum : 25 [周次ID]
     */

    private String Id;
    private String Title;
    private String SchoolId;
    private String weekNum;

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getSchoolId() {
        return SchoolId;
    }

    public void setSchoolId(String SchoolId) {
        this.SchoolId = SchoolId;
    }

    public String getWeekNum() {
        return weekNum;
    }

    public void setWeekNum(String weekNum) {
        this.weekNum = weekNum;
    }
}

package com.yhcloud.thankyou.bean;

import com.yhcloud.thankyou.utils.myview.pickerview.model.IPickerViewData;

/**
 * Created by leig on 2017/2/15.
 */

public class TermBean implements IPickerViewData {

    /**
     * id : 8
     * Title : 2015-2016学年-第二学期
     * currenTerm : -1
     * StartTime : 2016-02-21 00:00:00
     * EndTime : 2016-08-31 00:00:00
     * TermYear : 11
     * SchoolId : 12
     */

    private String id;
    private String Title;
    private String currenTerm;
    private String StartTime;
    private String EndTime;
    private String TermYear;
    private String SchoolId;

    @Override
    public String getPickerViewText() {
        return Title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getCurrenTerm() {
        return currenTerm;
    }

    public void setCurrenTerm(String currenTerm) {
        this.currenTerm = currenTerm;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String StartTime) {
        this.StartTime = StartTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String EndTime) {
        this.EndTime = EndTime;
    }

    public String getTermYear() {
        return TermYear;
    }

    public void setTermYear(String TermYear) {
        this.TermYear = TermYear;
    }

    public String getSchoolId() {
        return SchoolId;
    }

    public void setSchoolId(String SchoolId) {
        this.SchoolId = SchoolId;
    }
}

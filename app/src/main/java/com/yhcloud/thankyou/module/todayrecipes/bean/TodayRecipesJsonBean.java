package com.yhcloud.thankyou.module.todayrecipes.bean;

import java.io.Serializable;

/**
 * Created by leig on 2017/2/14.
 */

public class TodayRecipesJsonBean  implements Serializable{

    /**
     * WeekIndex : 1
     * SchoolId : 12
     * UserId : 3237
     * CreateTime : 2016-09-18 00:00:00
     * TermId : 10
     * Id : 108
     * Phase : 2 [1:早餐 2:中餐 3:晚餐]
     * Title : 红烧肉
     * PicUrl : /edu/uploads/tempfiles/cookbookfaces/RES20160918/RES201609181754494921.jpg
     * Type : null
     * Description : null
     * Price : null
     * Material : null
     * DayIndex : 1
     */

    private String WeekIndex;
    private String SchoolId;
    private String UserId;
    private String CreateTime;
    private String TermId;
    private String Id;
    private String Phase;
    private String Title;
    private String PicUrl;
    private String Type;
    private String Description;
    private String Price;
    private String Material;
    private String DayIndex;

    public String getWeekIndex() {
        return WeekIndex;
    }

    public void setWeekIndex(String WeekIndex) {
        this.WeekIndex = WeekIndex;
    }

    public String getSchoolId() {
        return SchoolId;
    }

    public void setSchoolId(String SchoolId) {
        this.SchoolId = SchoolId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String UserId) {
        this.UserId = UserId;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }

    public String getTermId() {
        return TermId;
    }

    public void setTermId(String TermId) {
        this.TermId = TermId;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getPhase() {
        return Phase;
    }

    public void setPhase(String Phase) {
        this.Phase = Phase;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String PicUrl) {
        this.PicUrl = PicUrl;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getMaterial() {
        return Material;
    }

    public void setMaterial(String material) {
        Material = material;
    }

    public String getDayIndex() {
        return DayIndex;
    }

    public void setDayIndex(String DayIndex) {
        this.DayIndex = DayIndex;
    }
}

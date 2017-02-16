package com.yhcloud.thankyou.module.todayrecipes.bean;

import java.io.Serializable;

/**
 * Created by leig on 2017/2/9.
 */

public class RecipesBean implements Serializable {

    private String mTitle;
    private String mTitleInfo;
    private String mImageUrl;
    private String mInfo;
    private String mDay;
    private String mTag;

    public RecipesBean() {}

    public RecipesBean(String title, String titleInfo, String day) {
        this.mTitle = title;
        this.mTitleInfo = titleInfo;
        this.mDay = day;
    }

    public RecipesBean(String tag, String title, String titleInfo, String day) {
        this.mTag = tag;
        this.mTitle = title;
        this.mTitleInfo = titleInfo;
        this.mDay = day;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getTitleInfo() {
        return mTitleInfo;
    }

    public void setTitleInfo(String titleInfo) {
        mTitleInfo = titleInfo;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    public String getInfo() {
        return mInfo;
    }

    public void setInfo(String info) {
        mInfo = info;
    }

    public String getDay() {
        return mDay;
    }

    public void setDay(String day) {
        this.mDay = day;
    }

    public String getTag() {
        return mTag;
    }

    public void setTag(String tag) {
        mTag = tag;
    }
}

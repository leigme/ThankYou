package com.yhcloud.thankyou.module.todayrecipes.bean;

import java.io.Serializable;

/**
 * Created by leig on 2017/2/9.
 */

public class TodayRecipesBean implements Serializable {

    private String mTitle;
    private String mImageUrl;
    private String mInfo;
    private int mType;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
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

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        mType = type;
    }
}

package com.yhcloud.thankyou.bean;

import android.content.Intent;

import java.io.Serializable;

/**
 * Created by leig on 2016/12/4.
 */

public class FunctionBean implements Serializable {
    //功能编号
    private int mId;
    //功能小图标
    private int mIcon = 0;
    //功能大图标
    private int mImage = 0;
    //功能标题
    private String mTitle;
    //功能地址
    private Intent mIntent;

    public FunctionBean() {}
    public FunctionBean(int icon, int image, String title, Intent intent) {
        this.mIcon = icon;
        this.mImage = image;
        this.mTitle = title;
        this.mIntent = intent;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public int getIcon() {
        return mIcon;
    }

    public void setIcon(int icon) {
        mIcon = icon;
    }

    public int getImage() {
        return mImage;
    }

    public void setImage(int image) {
        mImage = image;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Intent getIntent() {
        return mIntent;
    }

    public void setIntent(Intent intent) {
        mIntent = intent;
    }
}

package com.yhcloud.thankyou.bean;

import android.content.Intent;

import java.io.Serializable;

/**
 * Created by leig on 2016/12/4.
 */

public class FunctionBean implements Serializable {
    private int mId;
    private int mIcon;
    private int mImage;
    private String mTitle;
    private Intent mIntent;

    public FunctionBean() {}
    public FunctionBean(int id, int icon, int image, String title, Intent intent) {
        this.mId = id;
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

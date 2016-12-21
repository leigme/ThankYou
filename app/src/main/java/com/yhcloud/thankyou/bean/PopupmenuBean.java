package com.yhcloud.thankyou.bean;

import android.content.Intent;

/**
 * Created by Administrator on 2016/12/21.
 */

public class PopupMenuBean {

    private int mResId;
    private String mTitle;
    private Intent mIntent;

    public PopupMenuBean() {}

    public PopupMenuBean(int resId, String title, Intent intent) {
        this.mResId = resId;
        this.mTitle = title;
        this.mIntent = intent;
    }

    public int getResId() {
        return mResId;
    }

    public void setResId(int resId) {
        this.mResId = resId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public Intent getIntent() {
        return mIntent;
    }

    public void setIntent(Intent intent) {
        mIntent = intent;
    }
}

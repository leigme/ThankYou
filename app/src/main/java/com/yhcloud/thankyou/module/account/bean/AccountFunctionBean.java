package com.yhcloud.thankyou.module.account.bean;

import android.content.Intent;

/**
 * Created by Administrator on 2016/11/25.
 */

public class AccountFunctionBean {

    private int mId;
    private String mTitle;
    private int mBgResId;
    private Intent mIntent;

    public AccountFunctionBean() {}
    public AccountFunctionBean(String title, int bgResId) {
        this.mTitle = title;
        this.mBgResId = bgResId;
    }
    public AccountFunctionBean(int id, String title, int bgResId, Intent intent) {
        this.mId = id;
        this.mTitle = title;
        this.mBgResId = bgResId;
        this.mIntent = intent;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public int getBgResId() {
        return mBgResId;
    }

    public void setBgResId(int bgResId) {
        this.mBgResId = bgResId;
    }

    public Intent getIntent() {
        return mIntent;
    }

    public void setIntent(Intent intent) {
        mIntent = intent;
    }
}

package com.yhcloud.thankyou.module.account.bean;

import android.content.Intent;

/**
 * Created by Administrator on 2016/11/25.
 */

public class AccountFunctionBean {

    private int id;
    private String title;
    private int bgResId;
    private Intent mIntent;

    public AccountFunctionBean() {}
    public AccountFunctionBean(int id, String title, int bgResId, Intent intent) {
        this.id = id;
        this.title = title;
        this.bgResId = bgResId;
        this.mIntent = intent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getBgResId() {
        return bgResId;
    }

    public void setBgResId(int bgResId) {
        this.bgResId = bgResId;
    }

    public Intent getIntent() {
        return mIntent;
    }

    public void setIntent(Intent intent) {
        mIntent = intent;
    }
}

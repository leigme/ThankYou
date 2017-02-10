package com.yhcloud.thankyou.module.todayrecipes.bean;

import android.view.View;

import java.io.Serializable;

/**
 * Created by leig on 2017/2/10.
 */

public class TodayRecipesPagerBean implements Serializable {

    private String mTitle;
    private View mView;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public View getView() {
        return mView;
    }

    public void setView(View view) {
        mView = view;
    }
}

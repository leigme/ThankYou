package com.yhcloud.thankyou.module.todayrecipes.bean;

import com.yhcloud.thankyou.utils.myview.pickerview.model.IPickerViewData;

/**
 * Created by leig on 2017/2/15.
 */

public class WeekBean implements IPickerViewData {

    private String mId;
    private String mTitle;

    public WeekBean(){}

    public WeekBean(String id, String title){
        this.mId = id;
        this.mTitle = title;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    @Override
    public String getPickerViewText() {
        return mTitle;
    }
}

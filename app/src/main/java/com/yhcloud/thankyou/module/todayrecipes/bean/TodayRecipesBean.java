package com.yhcloud.thankyou.module.todayrecipes.bean;

import com.yhcloud.thankyou.minterface.Pinnable;

import java.util.ArrayList;

/**
 * Created by leig on 2017/2/14.
 */

public class TodayRecipesBean implements Pinnable {

    private String mTag, mInfo;
    private int mColor;
    private ArrayList<RecipesBean> mBeen;

    public String getTag() {
        return mTag;
    }

    public void setTag(String tag) {
        mTag = tag;
    }

    public String getInfo() {
        return mInfo;
    }

    public void setInfo(String info) {
        mInfo = info;
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        mColor = color;
    }

    public ArrayList<RecipesBean> getBeen() {
        return mBeen;
    }

    public void setBeen(ArrayList<RecipesBean> been) {
        mBeen = been;
    }

    @Override
    public boolean isPanned() {
        return true;
    }

    @Override
    public String getPinnedTag() {
        return mTag;
    }

    @Override
    public String getPinnedInfo() {
        return mInfo;
    }

    @Override
    public int getPinnedColor() {
        return mColor;
    }
}

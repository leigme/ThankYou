package com.yhcloud.thankyou.module.homework.bean;

import android.view.View;

import com.yhcloud.thankyou.module.homework.adapter.StudentQuestionListAdpater;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/12.
 */

public class HomeworkInfoViewPagerBean {
    private View mView;
    private ArrayList<String> mStrings;
    private StudentQuestionListAdpater mAdpater;
    private ArrayList<QuestionBean> mBeen;

    public View getView() {
        return mView;
    }

    public void setView(View view) {
        mView = view;
    }

    public ArrayList<String> getStrings() {
        return mStrings;
    }

    public void setStrings(ArrayList<String> strings) {
        mStrings = strings;
    }

    public StudentQuestionListAdpater getAdpater() {
        return mAdpater;
    }

    public void setAdpater(StudentQuestionListAdpater adpater) {
        mAdpater = adpater;
    }

    public ArrayList<QuestionBean> getBeen() {
        return mBeen;
    }

    public void setBeen(ArrayList<QuestionBean> been) {
        mBeen = been;
    }
}

package com.yhcloud.thankyou.module.homework.bean;

import android.view.View;

/**
 * Created by Administrator on 2017/1/12.
 */

public class HomeworkInfoViewPagerBean {

    private View mView;
    private StudentQuestionBean mStudentBeen;
    private TeacherQuestionBean mTeacherBeen;

    public View getView() {
        return mView;
    }

    public void setView(View view) {
        mView = view;
    }

    public StudentQuestionBean getStudentBeen() {
        return mStudentBeen;
    }

    public void setStudentBeen(StudentQuestionBean studentBeen) {
        mStudentBeen = studentBeen;
    }

    public TeacherQuestionBean getTeacherBeen() {
        return mTeacherBeen;
    }

    public void setTeacherBeen(TeacherQuestionBean teacherBeen) {
        mTeacherBeen = teacherBeen;
    }
}

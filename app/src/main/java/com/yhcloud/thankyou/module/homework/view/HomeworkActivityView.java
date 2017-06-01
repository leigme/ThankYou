package com.yhcloud.thankyou.module.homework.view;

import com.yhcloud.thankyou.minterface.BaseActivityView;
import com.yhcloud.thankyou.module.homework.bean.StudentHomeworkBean;
import com.yhcloud.thankyou.module.homework.bean.TeacherHomeworkBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/11.
 */

public interface HomeworkActivityView extends BaseActivityView {
    void showRight();
    void showTeacherHomeworkList(ArrayList<TeacherHomeworkBean> list);
    void showStudentHomeworkList(ArrayList<StudentHomeworkBean> list);
    void completeRefresh();
}

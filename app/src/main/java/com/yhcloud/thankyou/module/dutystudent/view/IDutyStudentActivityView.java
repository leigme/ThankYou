package com.yhcloud.thankyou.module.dutystudent.view;

import com.yhcloud.thankyou.bean.TeacherBean;
import com.yhcloud.thankyou.mInterface.IBaseActivityView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/23.
 */

public interface IDutyStudentActivityView extends IBaseActivityView {
    void showList(ArrayList<TeacherBean> list);
}

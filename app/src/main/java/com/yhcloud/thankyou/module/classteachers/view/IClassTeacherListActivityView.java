package com.yhcloud.thankyou.module.classteachers.view;

import com.yhcloud.thankyou.bean.TeacherBean;
import com.yhcloud.thankyou.minterface.IBaseActivityView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/23.
 */

public interface IClassTeacherListActivityView extends IBaseActivityView {
    void showList(ArrayList<TeacherBean> list);
}

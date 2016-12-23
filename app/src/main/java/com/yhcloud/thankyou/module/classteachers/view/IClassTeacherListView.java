package com.yhcloud.thankyou.module.classteachers.view;

import com.yhcloud.thankyou.bean.TeacherBean;
import com.yhcloud.thankyou.mInterface.IBaseView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/23.
 */

public interface IClassTeacherListView extends IBaseView {
    void showList(ArrayList<TeacherBean> list);
}

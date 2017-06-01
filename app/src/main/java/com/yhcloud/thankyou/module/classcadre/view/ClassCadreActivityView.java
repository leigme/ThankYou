package com.yhcloud.thankyou.module.classcadre.view;

import com.yhcloud.thankyou.bean.TeacherBean;
import com.yhcloud.thankyou.minterface.BaseActivityView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/23.
 */

public interface ClassCadreActivityView extends BaseActivityView {
    void showList(ArrayList<TeacherBean> list);
}

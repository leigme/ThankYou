package com.yhcloud.thankyou.module.classcadre.view;

import com.yhcloud.thankyou.bean.TeacherBean;
import com.yhcloud.thankyou.mInterfacea.IBaseActivityView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/23.
 */

public interface IClassCadreActivityView extends IBaseActivityView {
    void showList(ArrayList<TeacherBean> list);
}

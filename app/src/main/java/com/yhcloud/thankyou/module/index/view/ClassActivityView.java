package com.yhcloud.thankyou.module.index.view;

import com.yhcloud.thankyou.bean.UserInfoBean;
import com.yhcloud.thankyou.minterface.BaseActivityView;
import com.yhcloud.thankyou.manage.ClassManage;

import java.util.ArrayList;

/**
 * Created by leig on 2016/12/4.
 */

public interface ClassActivityView extends BaseActivityView {
    void showList(ArrayList<UserInfoBean> list);
    ClassManage getClassManage();
}

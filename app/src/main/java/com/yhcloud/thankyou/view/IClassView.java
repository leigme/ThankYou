package com.yhcloud.thankyou.view;

import com.yhcloud.thankyou.bean.UserInfoBean;
import com.yhcloud.thankyou.mInterface.IBaseView;
import com.yhcloud.thankyou.manage.ClassManage;

import java.util.ArrayList;

/**
 * Created by leig on 2016/12/4.
 */

public interface IClassView extends IBaseView {
    void showList(ArrayList<UserInfoBean> list);
    ClassManage getClassManage();
}

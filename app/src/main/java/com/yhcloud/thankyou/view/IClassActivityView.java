package com.yhcloud.thankyou.view;

import com.yhcloud.thankyou.bean.UserInfoBean;
import com.yhcloud.thankyou.mInterfacea.IBaseActivityView;
import com.yhcloud.thankyou.manage.ClassManage;

import java.util.ArrayList;

/**
 * Created by leig on 2016/12/4.
 */

public interface IClassActivityView extends IBaseActivityView {
    void showList(ArrayList<UserInfoBean> list);
    ClassManage getClassManage();
}

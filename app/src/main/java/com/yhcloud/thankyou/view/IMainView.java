package com.yhcloud.thankyou.view;

import com.yhcloud.thankyou.bean.ClassInfoBean;

import java.util.ArrayList;

/**
 * Created by leig on 2016/11/18.
 */

public interface IMainView {
    void initView();
    void initData();
    void initEvent();
    void setTitle(String str);
    void setDrawer();
    void showDrawer(ArrayList<ClassInfoBean> classInfoBeen);
    void showFragment(int i);
    void setHeaderLeftImage(String url);
    void setHeaderRightImage(int resId);
}

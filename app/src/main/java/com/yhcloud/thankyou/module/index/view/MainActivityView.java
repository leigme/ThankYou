package com.yhcloud.thankyou.module.index.view;

import android.support.v4.app.Fragment;

import com.yhcloud.thankyou.bean.ClassInfoBean;
import com.yhcloud.thankyou.bean.FunctionBean;
import com.yhcloud.thankyou.comm.BaseView;

import java.util.ArrayList;

/**
 * Created by leig on 2016/11/18.
 */

public interface MainActivityView extends BaseView {
    void initFragments(ArrayList<Fragment> list);
    void showDrawer(ArrayList<ClassInfoBean> classInfoBeen);
    void setDrawerHeadImg(String url);
    void setDrawerUsername(String username);
    void setDrawerClassname(String classname);
    void showFragment(int i);
    void setHeaderLeftImage(String url);
    void initHeaderRightButton(boolean showed);
    void showTrm(ArrayList<FunctionBean> list);
}

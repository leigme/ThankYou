package com.yhcloud.thankyou.view;

import android.support.v4.app.Fragment;

import com.yhcloud.thankyou.bean.ClassInfoBean;
import com.yhcloud.thankyou.bean.PopupMenuBean;
import com.yhcloud.thankyou.mInterface.IBaseView;

import java.util.ArrayList;

/**
 * Created by leig on 2016/11/18.
 */

public interface IMainView extends IBaseView {
    void initFragments(ArrayList<Fragment> list);
    void showDrawer(ArrayList<ClassInfoBean> classInfoBeen);
    void showFragment(int i);
    void setHeaderLeftImage(String url);
    void setHeaderRightImage(int resId);
    void showHeaderRightButton(boolean showed);
    void initPopupMenu(ArrayList<PopupMenuBean> list);
}

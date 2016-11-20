package com.yhcloud.thankyou.view;

import com.yhcloud.thankyou.bean.ClassInfo;

import java.util.ArrayList;

/**
 * Created by leig on 2016/11/18.
 */

public interface IMainView {
    public void setTitle(String str);
    public void setDrawer();
    public void showDrawer(ArrayList<ClassInfo> classInfos);
    public void showFragment(int i);
}
